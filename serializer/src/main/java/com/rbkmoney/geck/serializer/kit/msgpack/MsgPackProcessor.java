package com.rbkmoney.geck.serializer.kit.msgpack;

import com.rbkmoney.geck.common.util.StringUtil;
import com.rbkmoney.geck.serializer.StructHandleResult;
import com.rbkmoney.geck.serializer.StructHandler;
import com.rbkmoney.geck.serializer.StructProcessor;
import com.rbkmoney.geck.serializer.exception.BadFormatException;
import gnu.trove.map.hash.TCharObjectHashMap;
import org.msgpack.core.ExtensionTypeHeader;
import org.msgpack.core.MessageFormat;
import org.msgpack.core.MessagePack;
import org.msgpack.core.MessageUnpacker;
import org.msgpack.value.ValueType;

import java.io.IOException;

import static com.rbkmoney.geck.serializer.StructHandleResult.*;
import static com.rbkmoney.geck.serializer.kit.EventFlags.*;


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
        this.dictionary = new TCharObjectHashMap<>(64, 0.5f, noDictEntryValue);
    }

    @Override
    public <R> R process(S value, StructHandler<R> handler) throws IOException {
        MessageUnpacker unpacker = getUnpacker(value);
        try {
            processStart(unpacker, handler);
        } finally {
            dictionary.clear();
            releaseUnpacker(unpacker, value);
        }
        return handler.getResult();
    }

    private void processStart(MessageUnpacker unpacker, StructHandler handler) throws IOException {
        if (unpacker.hasNext()) {
            processStruct(unpacker, handler, getExtensionTypeHeader(startStruct, unpacker));
        }
    }

    private StructHandleResult skipOrGo(StructHandleResult handleResult, HandleAction skipAction, HandleTask goAction) throws IOException {
        switch (handleResult) {
            case CONTINUE:
                return goAction.get();
            case SKIP_SUBTREE:
                skipAction.consume();
                return CONTINUE;
            case JUMP_VALUE:
                skipAction.consume();
                return JUMP_VALUE;
            case SKIP_SIBLINGS:
                skipAction.consume();
                return SKIP_SIBLINGS;
            case TERMINATE:
                return handleResult;
            default:
                throw new BadFormatException("Bad handle result: " + handleResult);
        }
    }

    private void skipStruct(MessageUnpacker unpacker, ExtensionTypeHeader typeHeader) throws IOException {
        int length = typeHeader.getLength();
        for (int i = 0; i < length; ++i) {
            skipName(unpacker);
            skipValue(unpacker, unpacker.getNextFormat());
        }
    }

    private StructHandleResult processStruct(MessageUnpacker unpacker, StructHandler handler, ExtensionTypeHeader typeHeader) throws IOException {
        int length = typeHeader.getLength();

        handler.beginStruct(length);
        return skipOrGo(handler.getLastHandleResult(),
                () -> skipStruct(unpacker, typeHeader),
                () -> {
                    StructHandleResult entryRes = CONTINUE;
                    for (int i = 0; i < length; ++i) {
                        entryRes = skipOrGo(
                                entryRes, () -> {
                                    skipName(unpacker);
                                    skipValue(unpacker, unpacker.getNextFormat());
                                },
                                () -> skipOrGo(
                                        processName(unpacker, handler),
                                        () -> skipValue(unpacker, unpacker.getNextFormat()),
                                        () -> {
                                            StructHandleResult valRest = processValue(unpacker, handler, unpacker.getNextFormat());
                                            return (valRest == SKIP_SIBLINGS | valRest == SKIP_SUBTREE) ? CONTINUE : valRest;
                                        }
                                )
                        );
                    }
                    goIfAlive(entryRes, () -> handler.endStruct());
                    return handler.getLastHandleResult();
                });
    }

    private void skipName(MessageUnpacker unpacker) throws IOException {
        unpacker.skipValue();//skip byte id
        MessageFormat format = unpacker.getNextFormat();
        switch (format.getValueType()) {
            case STRING:
                unpacker.skipValue();//skip string name
                break;
            case EXTENSION:
                ExtensionTypeHeader header = unpacker.unpackExtensionTypeHeader();
                switch (header.getType()) {
                    case pointDictionary:
                        byte[] data = unpacker.readPayload(header.getLength());
                        int key = unpacker.unpackInt();
                        putInDictionary(key, data);
                        break;
                    case pointDictionaryRef:
                        unpacker.skipValue();//skip int key;
                        break;
                    default:
                        throw new BadFormatException("Bad extension type: " + header.getType() + " [dict point or ref expected]");
                }
        }
    }

    private StructHandleResult processName(MessageUnpacker unpacker, StructHandler handler) throws IOException {
        byte id = unpacker.unpackByte();
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
                        throw new BadFormatException("Bad extension type: " + header.getType() + " [dict point or ref expected]");
                }

                break;
            default:
                throw new BadFormatException("Bad format type: " + format + " [str or ext expected]");
        }
        handler.name(id, name);
        return handler.getLastHandleResult();
    }

    private void skipValue(MessageUnpacker unpacker, MessageFormat format) throws IOException {
        switch (format.getValueType()) {
            case BOOLEAN:
            case INTEGER:
            case FLOAT:
            case STRING:
            case NIL:
            case BINARY:
                unpacker.skipValue();
                break;
            case ARRAY:
                skipList(unpacker, format);
                break;
            case MAP:
                skipMap(unpacker, format);
                break;
            case EXTENSION:
                ExtensionTypeHeader typeHeader = unpacker.unpackExtensionTypeHeader();
                switch (typeHeader.getType()) {
                    case startStruct:
                        skipStruct(unpacker, typeHeader);
                        break;
                    case startSet:
                        skipSet(unpacker, typeHeader);
                        break;
                    default:
                        getExtensionTypeHeader(startSet, typeHeader);//err
                }
                break;
            default:
                throw new BadFormatException("Unexpected format type: " + format);
        }
    }

    private StructHandleResult processValue(MessageUnpacker unpacker, StructHandler handler, MessageFormat format) throws IOException {
        switch (format.getValueType()) {
            case BOOLEAN:
                handler.value(unpacker.unpackBoolean());
                return handler.getLastHandleResult();
            case INTEGER:
                handler.value(unpacker.unpackLong());
                return handler.getLastHandleResult();
            case FLOAT:
                handler.value(unpacker.unpackDouble());
                return handler.getLastHandleResult();
            case STRING:
                handler.value(unpacker.unpackString());
                return handler.getLastHandleResult();
            case NIL:
                handler.nullValue();
                return handler.getLastHandleResult();
            case ARRAY:
                return processList(unpacker, handler, format);
            case MAP:
                return processMap(unpacker, handler, format);
            case BINARY:
                handler.value(unpacker.readPayload(unpacker.unpackBinaryHeader()));
                return handler.getLastHandleResult();
            case EXTENSION:
                ExtensionTypeHeader typeHeader = unpacker.unpackExtensionTypeHeader();
                switch (typeHeader.getType()) {
                    case startStruct:
                        return processStruct(unpacker, handler, typeHeader);
                    case startSet:
                        return processSet(unpacker, handler, typeHeader);
                    default:
                        getExtensionTypeHeader(startSet, typeHeader);//err
                }
                break;
            default:
                throw new BadFormatException("Unexpected format type: " + format);
        }
        return StructHandleResult.CONTINUE;
    }

    private void skipList(MessageUnpacker unpacker, MessageFormat format, int length) throws IOException {
        for (int i = 0; i < length; ++i) {
            skipValue(unpacker, unpacker.getNextFormat());
        }
    }

    private void skipList(MessageUnpacker unpacker, MessageFormat format) throws IOException {
        int length = unpacker.unpackArrayHeader();
        skipList(unpacker, format, length);
    }

    private StructHandleResult processList(MessageUnpacker unpacker, StructHandler handler, MessageFormat format) throws IOException {
        int length = unpacker.unpackArrayHeader();
        handler.beginList(length);
        return skipOrGo(handler.getLastHandleResult(),
                () -> skipList(unpacker, format, length),
                () -> {
                    StructHandleResult entryRes = CONTINUE;
                    for (int i = 0; i < length; ++i) {
                        entryRes = skipOrGo(entryRes,
                                () -> skipValue(unpacker, unpacker.getNextFormat()),
                                () -> processValue(unpacker, handler, unpacker.getNextFormat())
                        );
                        entryRes = entryRes == SKIP_SUBTREE ? CONTINUE : entryRes;
                    }
                    goIfAlive(entryRes, () -> handler.endList());
                    return handler.getLastHandleResult();
                });
    }

    private void skipMap(MessageUnpacker unpacker, MessageFormat format, int length) throws IOException {
        for (int i = 0; i < length; ++i) {
            skipValue(unpacker, unpacker.getNextFormat());//skip key
            skipValue(unpacker, unpacker.getNextFormat());//skip value
        }
    }

    private void skipMap(MessageUnpacker unpacker, MessageFormat format) throws IOException {
        int length = unpacker.unpackMapHeader();
        skipMap(unpacker, format, length);
    }

    private StructHandleResult processMap(MessageUnpacker unpacker, StructHandler handler, MessageFormat format) throws IOException {
        int length = unpacker.unpackMapHeader();
        handler.beginMap(length);
        return skipOrGo(handler.getLastHandleResult(),
                () -> skipMap(unpacker, format, length),
                () -> {
                    StructHandleResult entryRes = CONTINUE;
                    for (int i = 0; i < length; ++i) {
                        entryRes = skipOrGo(
                                entryRes, () -> {
                                    skipValue(unpacker, unpacker.getNextFormat());//skip key
                                    skipValue(unpacker, unpacker.getNextFormat());//skip value
                                },
                                () -> skipOrGo(
                                        processMapKey(unpacker, handler),
                                        () -> skipValue(unpacker, unpacker.getNextFormat()),
                                        () -> processMapValue(unpacker, handler)
                                )
                        );
                    }
                    goIfAlive(entryRes, () -> handler.endMap());
                    return handler.getLastHandleResult();
                });
    }

    private StructHandleResult processMapKey(MessageUnpacker unpacker, StructHandler handler) throws IOException {
        handler.beginKey();
        //handler.getLastHandleResult();// > /dev/null
        StructHandleResult handleResult = processValue(unpacker, handler, unpacker.getNextFormat());
        if (handleResult == CONTINUE) {
            handler.endKey();
        }
        //handler.getLastHandleResult();// > /dev/null
        return handleResult == JUMP_VALUE ? CONTINUE : handleResult;
    }

    private StructHandleResult processMapValue(MessageUnpacker unpacker, StructHandler handler) throws IOException {
        handler.beginValue();
        //handler.getLastHandleResult();// > /dev/null
        StructHandleResult handleResult = processValue(unpacker, handler, unpacker.getNextFormat());
        if (handleResult == CONTINUE) {
            handler.endValue();
        }
        //handler.getLastHandleResult();// > /dev/null
        return handleResult == SKIP_SUBTREE ? CONTINUE : handleResult;
    }

    private void skipSet(MessageUnpacker unpacker, ExtensionTypeHeader typeHeader) throws IOException {
        int length = typeHeader.getLength();
        for (int i = 0; i < length; ++i) {
            skipValue(unpacker, unpacker.getNextFormat());
        }
    }

    private StructHandleResult processSet(MessageUnpacker unpacker, StructHandler handler, ExtensionTypeHeader typeHeader) throws IOException {
        int length = typeHeader.getLength();
        handler.beginSet(length);
        return skipOrGo(handler.getLastHandleResult(),
                () -> skipSet(unpacker, typeHeader),
                () -> {
                    StructHandleResult entryRes = CONTINUE;
                    for (int i = 0; i < length; ++i) {
                        entryRes = skipOrGo(entryRes,
                                () -> skipValue(unpacker, unpacker.getNextFormat()),
                                () -> processValue(unpacker, handler, unpacker.getNextFormat()));
                    }
                    goIfAlive(entryRes, () -> handler.endSet());
                    return handler.getLastHandleResult();
                });

    }

    private void goIfAlive(StructHandleResult result, HandleAction goAction) throws IOException {
        if (result != TERMINATE && result != JUMP_VALUE) {
            goAction.consume();
        }
    }

    private String putInDictionary(int key, byte[] data) throws BadFormatException {
        if (key > Character.MAX_VALUE) {
            throw new BadFormatException("Dictionary key is too long: " + key);
        }
        String str = StringUtil.expandAsciiString(data);
        if (dictionary.putIfAbsent((char) key, str) != null) {
            throw new BadFormatException("Dictionary key is already set: " + key);
        }
        return str;
    }

    private String getFromDictionary(int key) throws BadFormatException {
        if (key > Character.MAX_VALUE) {
            throw new BadFormatException("Dictionary key is too long: " + key);
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

    private interface HandleAction {
        void consume() throws IOException;
    }

    private interface HandleTask {
        StructHandleResult get() throws IOException;
    }
}
