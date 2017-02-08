package com.rbkmoney.kebab.kit.msgpack;

import com.rbkmoney.kebab.StructHandler;
import com.rbkmoney.kebab.exception.BadFormatException;
import gnu.trove.map.hash.TObjectCharHashMap;
import org.msgpack.core.MessagePack;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.buffer.ArrayBufferOutput;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by vpankrashkin on 31.01.17.
 */
public abstract class MsgPackHandler<R> implements StructHandler<R> {

    private final boolean useDictionary;
    private final TObjectCharHashMap<String> dictionary;
    private final char noDictEntryValue = 0;
    private char nextDictIdx = 0;
    protected final MessagePacker msgPacker;
    protected final Object dataTarget;

    public static MsgPackHandler<OutputStream> newStreamedInstance(OutputStream stream, boolean autoClose, boolean useDictionary) {
        return new MsgPackHandler<OutputStream>(stream, useDictionary) {
            @Override
            protected MessagePacker createPacker(Object dataTarget) {
                return MessagePack.newDefaultPacker((OutputStream) dataTarget);
            }

            @Override
            public OutputStream getResult() throws IOException {
                if (autoClose) {
                    msgPacker.close();
                } else {
                    msgPacker.flush();
                }
                return (OutputStream) dataTarget;
            }
        };
    }

    public static MsgPackHandler<byte[]> newBufferedInstance(boolean useDictionary) {
        return new MsgPackHandler<byte[]>(new ArrayBufferOutput(), useDictionary) {
            @Override
            protected MessagePacker createPacker(Object dataTarget) {
                return MessagePack.newDefaultPacker((ArrayBufferOutput) dataTarget);
            }

            @Override
            public byte[] getResult() throws IOException {
                ArrayBufferOutput abo = ((ArrayBufferOutput) dataTarget);
                msgPacker.flush();
                byte[] result = abo.toByteArray();
                abo.clear();
                return result;
            }
        };
    }

    public MsgPackHandler(Object dataTarget, boolean useDictionary) {
        this.dataTarget = dataTarget;
        this.msgPacker = createPacker(dataTarget);
        this.useDictionary = useDictionary;
        this.dictionary = useDictionary ? new TObjectCharHashMap<>(64, 0.5f, noDictEntryValue) : null;
    }

    @Override
    public void beginStruct(int size) throws IOException {
        msgPacker.packExtensionTypeHeader(MsgPackFlags.startStruct, size);
    }

    @Override
    public void endStruct() throws IOException {
        //msgPacker.packExtensionTypeHeader(endStruct, 0);
    }

    /**
     * @param size if >= 0, expected to be defined list size; if < 0, means stream mode with not fixed size
     */
    @Override
    public void beginList(int size) throws IOException {
        if (size >= 0) {
            msgPacker.packArrayHeader(size);
        } else {
            throw new BadFormatException("Unsupported option");
            //msgPacker.packExtensionTypeHeader(startList, 0);
        }
    }

    @Override
    public void endList() throws IOException {
        //msgPacker.packExtensionTypeHeader(MsgPackFlags.endList, 0);
    }

    @Override
    public void beginMap(int size) throws IOException {
        if (size >= 0) {
            msgPacker.packMapHeader(size);
        } else {
            throw new BadFormatException("Unsupported option");
            //msgPacker.packExtensionTypeHeader(startMap, 0);
        }
    }

    @Override
    public void endMap() throws IOException {
        //msgPacker.packExtensionTypeHeader(endMap, 0);
    }

    @Override
    public void beginKey() throws IOException {
        //msgPacker.packExtensionTypeHeader(startMapKey, 0);
    }

    @Override
    public void endKey() throws IOException {
        //msgPacker.packExtensionTypeHeader(endMapKey, 0);
    }

    @Override
    public void beginValue() throws IOException {
        //msgPacker.packExtensionTypeHeader(startMapValue, 0);
    }

    @Override
    public void endValue() throws IOException {
        //msgPacker.packExtensionTypeHeader(endMapValue, 0);
    }

    /**
     * @param name - only ASCII symbols expected
     */
    @Override
    public void name(String name) throws IOException {
        int length = name.length();
        if (useDictionary && length > 3) {
            char idx;
            if ((idx = dictionary.putIfAbsent(name, nextDictIdx)) == noDictEntryValue) {
                byte[] data = StringUtil.compressAsciiString(name);
                msgPacker.packExtensionTypeHeader(MsgPackFlags.pointDictionary, data.length);
                msgPacker.writePayload(data);
                msgPacker.packInt(nextDictIdx++);
            } else {
                msgPacker.packExtensionTypeHeader(MsgPackFlags.pointDictionaryRef, 0);
                msgPacker.packInt(idx);
            }
        } else {
            byte[] data = StringUtil.toAsciiBytes(name);
            msgPacker.packRawStringHeader(length);
            msgPacker.writePayload(data);
        }
    }

    @Override
    public void value(boolean value) throws IOException {
        msgPacker.packBoolean(value);
    }

    @Override
    public void value(String value) throws IOException {
        msgPacker.packString(value);
    }

    @Override
    public void value(double value) throws IOException {
        msgPacker.packDouble(value);
    }

    @Override
    public void value(long value) throws IOException {
        msgPacker.packLong(value);
    }

    @Override
    public void value(byte[] value) throws IOException {
        msgPacker.packBinaryHeader(value.length);
        msgPacker.writePayload(value);
    }

    @Override
    public void nullValue() throws IOException {
        msgPacker.packNil();
    }

    abstract protected MessagePacker createPacker(Object dataTarget);
}
