package com.rbkmoney.geck.serializer.kit.msgpack;

import com.rbkmoney.geck.common.util.StringUtil;
import com.rbkmoney.geck.serializer.exception.BadFormatException;
import com.rbkmoney.geck.serializer.StructHandler;
import com.rbkmoney.geck.serializer.StructProcessor;
import gnu.trove.map.hash.TCharObjectHashMap;
import org.msgpack.core.ExtensionTypeHeader;
import org.msgpack.core.MessageFormat;
import org.msgpack.core.MessagePack;
import org.msgpack.core.MessageUnpacker;
import org.msgpack.value.ValueType;

import java.io.IOException;

import static com.rbkmoney.geck.serializer.kit.EventFlags.*;

/**
 * Created by vpankrashkin on 07.02.17.
 */
public abstract class MsgPackProcessor<S> implements StructProcessor<S> {
    private final TCharObjectHashMap<String> dictionary;
    private final char noDictEntryValue = 0;


    public static MsgPackProcessor<byte[]> newBinaryInstance() {
        return new MsgPackProcessor<byte[]>() {
            @Override
            protected MessageUnpacker getUnpacker(byte[] value) {
                return MessagePack.newDefaultUnpacker(value);
            }

            @Override
            protected MessageUnpacker releaseUnpacker(MessageUnpacker unpacker, byte[] value) {
                return unpacker;
            }
        };
    }

    public MsgPackProcessor() {
        this.dictionary = new TCharObjectHashMap<>(64, 0.5f, noDictEntryValue) ;
    }

    @Override
    public <R> R process(S value, StructHandler<R> handler) throws IOException {
        MessageUnpacker unpacker = getUnpacker(value);
        try {
            processStart(unpacker, handler);
        } finally {
            releaseUnpacker(unpacker, value);
        }
        return handler.getResult();
    }

    private void processStart(MessageUnpacker unpacker, StructHandler handler) throws IOException {
        if (unpacker.hasNext()) {
            processStruct(unpacker, handler, getExtensionTypeHeader(startStruct, unpacker));
        }
    }

    private void processStruct(MessageUnpacker unpacker, StructHandler handler, ExtensionTypeHeader typeHeader) throws IOException {
        int length = typeHeader.getLength();
        handler.beginStruct(length);
        for (int i = 0; i < length; ++i) {
            processName(unpacker, handler);
            processValue(unpacker, handler, unpacker.getNextFormat());
        }
        handler.endStruct();
    }

    private void processName(MessageUnpacker unpacker, StructHandler handler) throws IOException {
        MessageFormat format = unpacker.getNextFormat();
        String name;
        switch (format.getValueType()) {
            case STRING:
                name = unpacker.unpackString();
                break;
            case EXTENSION:
                ExtensionTypeHeader header = unpacker.unpackExtensionTypeHeader();
                switch (header.getType()) {
                    case pointDictionary:
                        byte[] data = unpacker.readPayload(header.getLength());
                        int key = unpacker.unpackInt();
                        name = putInDictionary(key, data);
                        break;
                    case pointDictionaryRef:
                        name = getFromDictionary(unpacker.unpackInt());
                        break;
                    default:
                        throw new BadFormatException("Bad extension type: "+ header.getType() + " [dict point or ref expected]");
                }
                break;
            default:
                throw new BadFormatException("Bad format type: "+ format + " [str or ext expected]");
        }
        handler.name(name);
    }

    private void processValue(MessageUnpacker unpacker, StructHandler handler, MessageFormat format) throws IOException {
        switch (format.getValueType()) {
            case BOOLEAN:
                handler.value(unpacker.unpackBoolean());
                break;
            case INTEGER:
                handler.value(unpacker.unpackLong());
                break;
            case FLOAT:
                handler.value(unpacker.unpackDouble());
                break;
            case STRING:
                handler.value(unpacker.unpackString());
                break;
            case NIL:
                handler.nullValue();
                break;
            case ARRAY:
                processList(unpacker, handler, format);
                break;
            case MAP:
                processMap(unpacker, handler, format);
                break;
            case BINARY:
                handler.value(unpacker.readPayload(unpacker.unpackBinaryHeader()));
                break;
            case EXTENSION:
                ExtensionTypeHeader typeHeader = unpacker.unpackExtensionTypeHeader();
                switch (typeHeader.getType()) {
                    case startStruct:
                        processStruct(unpacker, handler, typeHeader);
                        break;
                    case startSet:
                        processSet(unpacker, handler, typeHeader);
                        break;
                    default:
                        getExtensionTypeHeader(startSet, typeHeader);//err
                }
                break;
            default:
                throw new BadFormatException("Unexpected format type: " + format);
        }
    }

    private void processList(MessageUnpacker unpacker, StructHandler handler, MessageFormat format) throws IOException {
        int length = unpacker.unpackArrayHeader();
        handler.beginList(length);
        for (int i = 0; i < length; ++i) {
            processValue(unpacker, handler, unpacker.getNextFormat());
        }
        handler.endList();
    }

    private void processMap(MessageUnpacker unpacker, StructHandler handler, MessageFormat format) throws IOException {
        int length = unpacker.unpackMapHeader();
        handler.beginMap(length);
        for (int i = 0; i < length; ++i) {
            handler.beginKey();
            processValue(unpacker, handler, unpacker.getNextFormat());
            handler.endKey();
            handler.beginValue();
            processValue(unpacker, handler, unpacker.getNextFormat());
            handler.endValue();
        }
        handler.endMap();
    }

    private void processSet(MessageUnpacker unpacker, StructHandler handler, ExtensionTypeHeader typeHeader) throws IOException {
        int length = typeHeader.getLength();
        handler.beginSet(length);
        for (int i = 0; i < length; ++i) {
            processValue(unpacker, handler, unpacker.getNextFormat());
        }
        handler.endSet();
    }

    private String putInDictionary(int key, byte[] data) throws BadFormatException {
        if (key > Character.MAX_VALUE) {
            throw new BadFormatException("Dictionary key is too long: "+ key);
        }
        String str = StringUtil.expandAsciiString(data);
        if (dictionary.putIfAbsent((char) key, str) != null) {
            throw new BadFormatException("Dictionary key is already set: " + key);
        }
        return str;
    }

    private String getFromDictionary(int key) throws BadFormatException {
        if (key > Character.MAX_VALUE) {
            throw new BadFormatException("Dictionary key is too long: "+ key);
        }
        String str = dictionary.get((char) key);
        if (str == null) {
            throw new BadFormatException("Not found dictionary key: " + key);
        }
        return str;
    }

    private ExtensionTypeHeader getExtensionTypeHeader(int expectedType, MessageUnpacker unpacker) throws IOException {
        getHeader(ValueType.EXTENSION, unpacker);
        return getExtensionTypeHeader(expectedType, unpacker.unpackExtensionTypeHeader());
    }

    private ExtensionTypeHeader getExtensionTypeHeader(int expectedType, ExtensionTypeHeader typeHeader) throws BadFormatException {
        if (typeHeader.getType() == expectedType) {
            return typeHeader;
        }
        throwBadFormat("wrong extension type", startStruct, typeHeader);
        return null;
    }

    private MessageFormat getHeader(ValueType expectedType, MessageUnpacker unpacker) throws IOException {
        MessageFormat format = unpacker.getNextFormat();
        if (format.getValueType() == expectedType) {
            return format;
        }
        throwBadFormat("wrong format type", expectedType, format);
        return null;
    }

    protected abstract MessageUnpacker getUnpacker(S value) throws IOException;

    protected abstract MessageUnpacker releaseUnpacker(MessageUnpacker unpacker, S value) throws IOException;

    private static void throwBadFormat(String message, ValueType expectedType, MessageFormat actualFormat) throws BadFormatException {
        throw new BadFormatException("MsgPack bad format: " + message + ", expected type: " + expectedType + ", actual format: " + actualFormat);
    }

    private static void throwBadFormat(String message, int expectedType, ExtensionTypeHeader actualType) throws BadFormatException {
        throw new BadFormatException("MsgPack bad format: " + message + ", expected type: " + expectedType + ", actual type: " + actualType);
    }
}
