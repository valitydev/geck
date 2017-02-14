package com.rbkmoney.kebab.kit.object;

import com.bazaarvoice.jolt.Chainr;
import com.bazaarvoice.jolt.JsonUtils;
import com.bazaarvoice.jolt.utils.JoltUtils;
import com.rbkmoney.kebab.Kebab;
import com.rbkmoney.kebab.KebabUtil;
import com.rbkmoney.kebab.kit.mock.MockTBaseProcessor;
import com.rbkmoney.kebab.kit.tbase.TBaseHandler;
import com.rbkmoney.kebab.kit.tbase.TBaseProcessor;
import com.rbkmoney.kebab.test.Status;
import com.rbkmoney.kebab.test.TestObject;
import com.rbkmoney.kebab.test.Unknown;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static com.rbkmoney.kebab.KebabUtil.getTestObject;
import static org.junit.Assert.assertEquals;

/**
 * Created by vpankrashkin on 09.02.17.
 */
public class ObjectHandlerTest {

    @Test
    public void test() {
        Object inputJSON = JsonUtils.jsonToObject(this.getClass().getResourceAsStream("../../../../../input1.json" ));
        System.out.println(inputJSON.toString());

        List chainrSpecJSON = JsonUtils.jsonToList(this.getClass().getResourceAsStream( "../../../../../spec1.json" ));
        Chainr chainr = Chainr.fromSpec( chainrSpecJSON );
        //((Map)((Map)((Map) inputJSON).get("rating")).get("primary")).put("values", new byte[]{1, 2, 3});

        Object transformedOutput = chainr.transform( inputJSON );
        System.out.println( JsonUtils.toJsonString( transformedOutput ) );
    }

    @Test
    public void testHandler() throws IOException {
        Kebab kebab = new Kebab();
        TestObject testObject = KebabUtil.getTestObject();
        Object result = kebab.write(testObject, new ObjectHandler());
        TestObject restoredTestObject = new ObjectProcessor().process(result, new TBaseHandler<>(TestObject.class));
        assertEquals(testObject, restoredTestObject);
        System.out.println(result);
    }

    @Test
    public void test2() throws IOException {
        TestObject testObject1 = new MockTBaseProcessor().process(new TestObject(), new TBaseHandler<>(TestObject.class));
        Object src = new TBaseProcessor().process(testObject1, new ObjectHandler());
        System.out.println(JsonUtils.toJsonString(src));
        Object dst =
        new TBaseProcessor().process(new ObjectProcessor().process(src, new TBaseHandler<>(TestObject.class)), new ObjectHandler());
        System.out.println(JsonUtils.toJsonString(dst));
        assertEquals(src, dst);

    }


}
