package dev.vality.geck.serializer;

import com.rbkmoney.damsel.v130.payment_processing.InvoicePaymentStarted;
import dev.vality.geck.serializer.kit.mock.MockTBaseProcessor;
import dev.vality.geck.serializer.kit.tbase.TBaseHandler;
import dev.vality.geck.serializer.domain.Status;
import dev.vality.geck.serializer.domain.TestObject;

import java.io.IOException;
import java.util.List;
import java.util.function.IntFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class GeckTestUtil {
    public static TestObject getTestObject(int statusCount, IntFunction<Status> statusGen) throws IOException {
        TestObject testObject = getTestObject();
        List<Status> lists = IntStream.range(0, statusCount).mapToObj(statusGen).collect(Collectors.toList());
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
