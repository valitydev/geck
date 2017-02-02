package com.rbkmoney.kebab;

import com.rbkmoney.kebab.test.Fail;
import com.rbkmoney.kebab.test.Ids;
import com.rbkmoney.kebab.test.Status;
import com.rbkmoney.kebab.test.TestObject;
import org.junit.Test;

import java.util.*;

/**
 * Created by tolkonepiu on 25/01/2017.
 */
public class KebabTest {

    @Test
    public void kebabTesting() {
        TestObject testObject = new TestObject();
        testObject.setDescription("kek");
        testObject.setValue(2.32);

        Ids ids = new Ids();
        ids.setBigId(2141214124L);
        ids.setId(12312);
        ids.setMiniId((short) 2334);
        ids.setMicroId((byte) 127);

        testObject.setIds(ids);

        testObject.setData(new byte[]{4, 2});

        testObject.setNumbers(Arrays.asList(1, 2, 3, 4, 5));

        Set<String> suk = new HashSet<>(Arrays.asList("kek1", "kek2"));

        testObject.setFuck(Arrays.asList(suk, suk, suk));

        Fail fail = new Fail();
        fail.setReasons(new HashSet<>(Arrays.asList("kek1", "kek2")));
        testObject.setStatus(Status.fail(new Fail(fail)));
        Map<String, Integer> map = new HashMap<>();
        map.put("kek1", 455);
        map.put("kek2", 564);
        map.put("kek3", 565);
        map.put(null, null);
        testObject.setMaps(map);

        Kebab kebab = new Kebab();
        System.out.println(kebab.toJson(testObject));
    }

}
