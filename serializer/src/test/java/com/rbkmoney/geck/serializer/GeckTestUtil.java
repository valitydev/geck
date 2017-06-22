package com.rbkmoney.geck.serializer;

import com.rbkmoney.damsel.v130.payment_processing.InvoicePaymentStarted;
import com.rbkmoney.geck.serializer.kit.mock.MockTBaseProcessor;
import com.rbkmoney.geck.serializer.kit.tbase.TBaseHandler;
import com.rbkmoney.geck.serializer.test.Status;
import com.rbkmoney.geck.serializer.test.TestObject;

import java.io.IOException;
import java.util.List;
import java.util.function.IntFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by vpankrashkin on 08.02.17.
 */
public class GeckTestUtil {
    public static TestObject getTestObject(int statusCount, IntFunction<Status> statusGen) throws IOException {
        TestObject testObject = getTestObject();
        List<Status> lists = IntStream.range(0, statusCount).mapToObj(statusGen::apply).collect(Collectors.toList());
        testObject.setStatuses(lists);
        return testObject;
    }

    public static TestObject getTestObject() throws IOException {
        return new MockTBaseProcessor().process(new TestObject(), new TBaseHandler<>(TestObject.class));
    }

    public static InvoicePaymentStarted getInvoicePaymentStarted() throws IOException {
        return new MockTBaseProcessor().process(new InvoicePaymentStarted(), new TBaseHandler<>(InvoicePaymentStarted.class));
    }
}
