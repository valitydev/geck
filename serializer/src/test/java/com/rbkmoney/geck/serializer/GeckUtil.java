package com.rbkmoney.geck.serializer;

import com.rbkmoney.geck.serializer.test.Status;
import com.rbkmoney.geck.serializer.test.TestObject;
import com.rbkmoney.geck.serializer.kit.mock.MockTBaseProcessor;
import com.rbkmoney.geck.serializer.kit.tbase.TBaseHandler;

import java.io.IOException;
import java.util.*;
import java.util.function.IntFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by vpankrashkin on 08.02.17.
 */
public class GeckUtil {
    public static TestObject getTestObject(int statusCount, IntFunction<Status> statusGen) throws IOException {
        TestObject testObject = getTestObject();
        List<Status> lists = IntStream.range(0, statusCount).mapToObj(statusGen::apply).collect(Collectors.toList());
        testObject.setStatuses(lists);
        return testObject;
    }

    public static TestObject getTestObject() throws IOException {
        return new MockTBaseProcessor().process(new TestObject(), new TBaseHandler<>(TestObject.class));
    }
}
