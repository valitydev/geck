package dev.vality.geck.common.util;

import java.nio.ByteBuffer;

public final class BinaryUtil {

    private BinaryUtil() {
    }

    public static byte[] toByteArray(ByteBuffer buffer) {
        ByteBuffer currentRange = buffer.slice();
        byte[] bytes = new byte[currentRange.remaining()];
        currentRange.get(bytes);
        return bytes;
    }
}
