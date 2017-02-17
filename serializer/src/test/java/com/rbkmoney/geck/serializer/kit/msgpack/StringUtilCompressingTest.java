package com.rbkmoney.geck.serializer.kit.msgpack;

import com.rbkmoney.geck.serializer.exception.BadFormatException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;
import static java.lang.System.*;

/**
 * Created by vpankrashkin on 03.02.17.
 */
@RunWith(Parameterized.class)
public class StringUtilCompressingTest {
    private String testString;
    private int expectedLen;

    public StringUtilCompressingTest(String testString, int expectedLen) {
        this.testString = testString;
        this.expectedLen = expectedLen;
    }

    @Test
    public void test() throws BadFormatException {
        assertTrue(StringUtil.isAsciiString(testString));
        out.println("Original:");
        testString.chars().forEach(c -> out.print(StringUtil.intToString(c, 8, 8) + " "));
        out.println();
        byte[] data = StringUtil.compactAsciiString(testString);
        assertEquals(expectedLen, data.length);
        out.println("Compressed:");
        for (int i = 0; i < data.length; ++i) {
            out.print(StringUtil.intToString(((int)data[i]) & 0xFF, 8, 8) + " ");
        }
        out.println();
         StringUtil.expandAsciiString(data);
        assertEquals(testString, StringUtil.expandAsciiString(data));
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"", 0},
                {"_ame}mm", 5},
                {"a", 1},
                {"0", 1},
                {"ab", 2},
                {"abc", 2},
                {"abcd", 3},
                {"abcde", 4},
                {"abcde1", 6}
        });
    }

    @Test
    public void  bytesTest() throws BadFormatException {
        String str = "_ame}mm";
        byte[] data = StringUtil.compactAsciiString(str);
        assertArrayEquals(new byte[]{(byte) 0b10000100, 0b01101111, 0b00111111, (byte) 0b11011110, (byte) 0b11110000}, data);
        StringUtil.expandAsciiString(data);
        assertEquals(str, StringUtil.expandAsciiString(data));
    }

}
