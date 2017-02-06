package com.rbkmoney.kebab.handler;

import com.rbkmoney.kebab.StructHandler;
import com.rbkmoney.kebab.exception.BadFormatException;
import gnu.trove.map.hash.TObjectCharHashMap;
import org.msgpack.core.MessagePack;
import org.msgpack.core.MessagePacker;

import java.io.IOException;
import java.io.OutputStream;

import static com.rbkmoney.kebab.handler.StringUtil.compressAsciiString;
import static com.rbkmoney.kebab.handler.StringUtil.toAsciiBytes;

/**
 * Created by vpankrashkin on 31.01.17.
 */
public class MsgPackHandler implements StructHandler<OutputStream> {
    private static final byte nop = 0;
    private static final byte startStruct = 1;
    private static final byte endStruct = 2;
    private static final byte startList = 3;
    private static final byte endList = 4;
    private static final byte startMap = 5;
    private static final byte endMap = 6;
    private static final byte startMapKey = 7;
    private static final byte endMapKey = 8;
    private static final byte startMapValue = 9;
    private static final byte endMapValue = 10;
    private static final byte pointName = 11;
    private static final byte pointValue = 12;
    private static final byte pointDictionary = 13;
    private static final byte pointDictionaryRef = 14;

    private final boolean autoClose;
    private final boolean useDictionary;
    private final MessagePacker msgPacker;
    private final TObjectCharHashMap<String> dictionary;
    private final char noDictEntryValue = 0;
    private char nextDictIdx = 0;

    public MsgPackHandler(OutputStream stream, boolean autoClose, boolean useDictionary) {
        this.autoClose = autoClose;
        this.msgPacker = MessagePack.newDefaultPacker(stream);
        this.useDictionary = useDictionary;
        this.dictionary = useDictionary ? new TObjectCharHashMap<>(64, 0.5f, noDictEntryValue) : null;
    }

    @Override
    public void beginStruct(int size) throws IOException {
        msgPacker.packExtensionTypeHeader(startStruct, size);
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
        msgPacker.packExtensionTypeHeader(endList, 0);
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
                byte[] data = compressAsciiString(name);
                msgPacker.packExtensionTypeHeader(pointDictionary, data.length);
                msgPacker.writePayload(data);
                msgPacker.packInt(nextDictIdx++);
            } else {
                msgPacker.packExtensionTypeHeader(pointDictionaryRef, 0);
                msgPacker.packInt(idx);
            }
        } else {
            byte[] data = toAsciiBytes(name);
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
    public void value(byte value) throws IOException {
        msgPacker.packByte(value);
    }

    @Override
    public void value(short value) throws IOException {
        msgPacker.packShort(value);
    }

    @Override
    public void value(int value) throws IOException {
        msgPacker.packInt(value);
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

    @Override
    public OutputStream getResult() throws IOException {
        if (autoClose) {
            msgPacker.close();
        } else {
            msgPacker.flush();
        }

        return null;
    }

}
