package com.rbkmoney.geck.serializer.kit.msgpack;

import com.rbkmoney.geck.serializer.GeckTestUtil;
import com.rbkmoney.geck.serializer.test.TestObject;
import com.rbkmoney.geck.serializer.Geck;
import com.rbkmoney.geck.serializer.test.Status;
import com.rbkmoney.geck.serializer.test.Unknown;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by vpankrashkin on 08.02.17.
 */
public class MsgPackTest {
    Geck geck = new Geck();
    @Test
    public void test() throws IOException {
        TestObject testObject = GeckTestUtil.getTestObject(100, i -> Status.unknown(new Unknown("SomeData"+i)));
        byte[] serializedData = geck.toMsgPack(testObject, true);
        byte[] doubleSerialized = MsgPackProcessor.newBinaryInstance().process(serializedData, MsgPackHandler.newBufferedInstance(true));
        Assert.assertArrayEquals(serializedData, doubleSerialized);
    }
}
