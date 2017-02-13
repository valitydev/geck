package com.rbkmoney.kebab;

import com.rbkmoney.kebab.test.*;

import java.util.*;
import java.util.function.IntFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by vpankrashkin on 08.02.17.
 */
public class KebabUtil {
    public static TestObject getTestObject(int statusCount, IntFunction<Status> statusGen) {
        TestObject testObject = getTestObject();
        List<Status> lists = IntStream.range(0, statusCount).mapToObj(statusGen::apply).collect(Collectors.toList());
        testObject.setStatuses(lists);
        return testObject;
    }

    public static TestObject getTestObject() {
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

        Map<String, Integer> map = new HashMap<>();
        map.put("kek1", 455);
        map.put("kek2", 564);
        map.put("kek3", 565);
        //map.put(null, 666);
        //map.put("null", null);
        testObject.setMaps(map);

        testObject.setStatus(Status.fail(new Fail(fail)));

        List<Status> lists = Collections.nCopies(10, Status.unknown(new Unknown("SomeData")));
        testObject.setStatuses(lists);

        testObject.setActive(true);

        Map<Map<Set<Kek>, Status>, Map<Status, Set<Ids>>> kebabMap = new HashMap<>();

        Map<Set<Kek>, Status> key = new HashMap<>();

        Set<Kek> keyKey = new HashSet<>();
        keyKey.add(Kek.TEST1);
        keyKey.add(Kek.TEST2);
        keyKey.add(Kek.TEST3);

        Status status = Status.ok(new Ok());
        key.put(keyKey, status);

        Map<Status, Set<Ids>> value = new HashMap<>();
        Fail failKey = new Fail();
        failKey.setReasons(new HashSet<>(Arrays.asList("qwe", "aasd", "ads")));


        Set<Ids> valueIds = new HashSet<>();
        valueIds.add(ids);

        Ids idsTwo = new Ids();
        idsTwo.setBigId(123123);
        idsTwo.setId(44);
        idsTwo.setMiniId((short) 2334);
        idsTwo.setMicroId((byte) 12);
        valueIds.add(idsTwo);
        value.put(Status.fail(failKey), valueIds);

        kebabMap.put(key, value);

        testObject.setKebabMap(kebabMap);

        testObject.setActive(true);

        return testObject;
    }
}
