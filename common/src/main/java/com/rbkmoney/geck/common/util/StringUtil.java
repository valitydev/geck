package com.rbkmoney.geck.common.util;

import java.nio.charset.Charset;
import java.util.StringTokenizer;

public final class StringUtil {
    public static final Charset CHARSET = Charset.forName("UTF-8");
    private static final int LOW_ASCII_FLAG = 0x80;

    public static byte[] compactAsciiString(String str) {
        if (str.length() == 0) {
            return new byte[0];
        }
        byte[] bytes = new byte[(int) Math.ceil((str.length() * 5 + 1) / 8.)];
        bytes[0] = (byte) LOW_ASCII_FLAG;
        int bitIndex = 1;
        for (int i = 0; i < str.length(); ++i) {
            char c = str.charAt(i);
            if ((c & LOW_ASCII_FLAG) != 0) {
                throw new IllegalArgumentException("Only ASCII symbols're expected: " + c + " in: " + str);
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

    public static String expandAsciiString(byte[] bytes) {
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
                chars[(bitIndex - 1) / 5 - 1] = (char) ((c + 0x5F) - 1);
            }
        }
        int cLength = c != 0 ? (bitIndex - 1) / 5 : (bitIndex - 1) / 5 - 1;
        return new String(chars, 0, cLength);
    }

    public static byte[] toAsciiBytes(final String str) {
        final byte[] bytes = new byte[str.length()];
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if ((c & LOW_ASCII_FLAG) != 0) {
                throw new IllegalArgumentException("Only ASCII symbols're expected: " + c + " in: " + str);
            }
            bytes[i] = (byte) c;
        }
        return bytes;
    }

    public static String fromAsciiBytes(byte[] bytes) {
        return new String(bytes, CHARSET);
    }

    public static boolean isAsciiString(String str) {
        for (int i = 0; i < str.length(); ++i) {
            if ((str.charAt(i) & LOW_ASCII_FLAG) != 0) {
                return false;
            }
        }
        return true;
    }

    public static String[] split(String value, String delimiter) {
        StringTokenizer tokenizer = new StringTokenizer(value, delimiter);
        String[] items = new String[tokenizer.countTokens()];

        int itemId = 0;
        while (tokenizer.hasMoreTokens()) {
            items[itemId++] = tokenizer.nextToken();
        }

        return items;
    }


    public static String intToString(int number, int groupSize, int maxBits) {
        StringBuilder result = new StringBuilder();

        for (int i = maxBits - 1; i >= 0; i--) {
            int mask = 1 << i;
            result.append((number & mask) != 0 ? "1" : "0");

            if (i % groupSize == 0 && i > 0)
                result.append(" ");
        }
        return result.toString();
    }
}
