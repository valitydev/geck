package dev.vality.geck.serializer.kit.object;

import dev.vality.geck.serializer.domain.SetTest;
import dev.vality.geck.serializer.kit.mock.MockTBaseProcessor;
import dev.vality.geck.serializer.kit.tbase.TBaseHandler;
import dev.vality.geck.serializer.kit.tbase.TBaseProcessor;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class ObjectProcessorTest {

    @Test
    public void shouldRoundTripSetsWithoutFallingThroughToOtherBranch() throws IOException {
        SetTest source = new MockTBaseProcessor().process(new SetTest(), new TBaseHandler<>(SetTest.class));

        Object encoded = new TBaseProcessor().process(source, new ObjectHandler());
        SetTest restored = new ObjectProcessor().process(encoded, new TBaseHandler<>(SetTest.class));

        Assert.assertEquals(source, restored);
    }
}
