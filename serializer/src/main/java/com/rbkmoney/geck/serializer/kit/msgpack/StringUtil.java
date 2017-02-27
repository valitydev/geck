package com.rbkmoney.geck.serializer.kit.msgpack;

import com.rbkmoney.geck.serializer.exception.BadFormatException;

import java.nio.charset.Charset;

/**
 * Created by vpankrashkin on 03.02.17.
 */
public final class StringUtil {
    public static final Charset CHARSET = Charset.forName("UTF-8");
    private static final int LOW_ASCII_FLAG = 0x80;

    static byte[] compactAsciiString(String str) throws BadFormatException {
        if (str.length() == 0) {
            return new byte[0];
        }
        byte[] bytes = new byte[(int)Math.ceil((str.length()*5+1) / 8.)];
        bytes[0] = (byte) LOW_ASCII_FLAG;
        int bitIndex = 1;
        for (int i = 0; i < str.length(); ++i) {
            char c = str.charAt(i);
            if ((c & LOW_ASCII_FLAG) != 0) {
                throw new BadFormatException("Only ASCII symbols're expected: "+ c + " in: "+str);
            }

            if (c < 0x5F || c > 0x7D) {
                return toAsciiBytes(str);
            }
            byte cb = (byte) ((c - 0x5F) + 1);
            bytes[bitIndex >> 3] |= ((cb & 0b10000) >> 4) << (7 - (bitIndex++ % 8));
            bytes[bitIndex >> 3] |= ((cb & 0b01000) >> 3) << (7 - (bitIndex++ % 8));
            bytes[bitIndex >> 3] |= ((cb & 0b00100) >> 2) << (7 - (bitIndex++ % 8));
            bytes[bitIndex >> 3] |= ((cb & 0b00010) >> 1) << (7 - (bitIndex++ % 8));
            bytes[bitIndex >> 3] |= ((cb & 0b00001) >> 0) << (7 - (bitIndex++ % 8));
        }
        return bytes;
    }

    static String expandAsciiString(byte[] bytes) throws BadFormatException {
        if (bytes.length == 0 || (bytes[0] & LOW_ASCII_FLAG) == 0) {
            return fromAsciiBytes(bytes);
        }

        char[] chars = new char[((bytes.length << 3) - 1) / 5];
        int maxBits = chars.length * 5 + 1;

        char c = 0;
        int bitIndex = 1;
        while (bitIndex < maxBits) {
            c = 0;
            c |= (bytes[bitIndex >> 3] >> (7 - (bitIndex++ % 8)) & 1) << 4;
            c |= (bytes[bitIndex >> 3] >> (7 - (bitIndex++ % 8)) & 1) << 3;
            c |= (bytes[bitIndex >> 3] >> (7 - (bitIndex++ % 8)) & 1) << 2;
            c |= (bytes[bitIndex >> 3] >> (7 - (bitIndex++ % 8)) & 1) << 1;
            c |= (bytes[bitIndex >> 3] >> (7 - (bitIndex++ % 8)) & 1) << 0;
            if (c != 0) {
                chars[(bitIndex-1) / 5 - 1] = (char) ((c + 0x5F) - 1);
            }
        }
        int cLength = c != 0 ? (bitIndex-1) / 5 : (bitIndex-1) / 5 - 1;
        return new String(chars, 0, cLength);
    }

    static byte[] toAsciiBytes(final String str) throws BadFormatException {
        final byte[] bytes = new byte[str.length()];
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if ((c & LOW_ASCII_FLAG ) != 0) {
                throw new BadFormatException("Only ASCII symbols're expected: "+c + " in: "+str);
            }
            bytes[i] = (byte) c;
        }
        return bytes;
    }

    static String fromAsciiBytes(byte[] bytes) {
        return new String(bytes, CHARSET);
    }

    static boolean isAsciiString(String str) {
        for (int i = 0; i < str.length(); ++i) {
            if ((str.charAt(i) & LOW_ASCII_FLAG) != 0) {
                return false;
            }
        }
        return true;
    }


    public static String intToString(int number, int groupSize, int maxBits) {
        StringBuilder result = new StringBuilder();

        for(int i = maxBits - 1; i >= 0 ; i--) {
            int mask = 1 << i;
            result.append((number & mask) != 0 ? "1" : "0");

            if (i % groupSize == 0 && i > 0)
                result.append(" ");
        }
        return result.toString();
    }
}
