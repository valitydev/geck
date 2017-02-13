package com.rbkmoney.kebab.kit.tbase;

import com.rbkmoney.kebab.test.TestObject;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

import static com.rbkmoney.kebab.KebabUtil.getTestObject;

/**
 * Created by tolkonepiu on 08/02/2017.
 */
public class TBaseTest {

    @Test
    public void tBaseTest() throws IOException {
        TestObject testObject1 = getTestObject();
        TestObject testObject2 = new TBaseProcessor().process(testObject1, new TBaseHandler<>(TestObject.class));
        Assert.assertEquals(testObject1, testObject2);
    }

}
