package dev.vality.geck.serializer;

import dev.vality.geck.serializer.handler.HandlerStub;
import dev.vality.geck.serializer.kit.mock.MockTBaseProcessor;
import dev.vality.geck.serializer.kit.msgpack.MsgPackHandler;
import dev.vality.geck.serializer.kit.msgpack.MsgPackProcessor;
import dev.vality.geck.serializer.kit.object.ObjectHandler;
import dev.vality.geck.serializer.kit.object.ObjectProcessor;
import dev.vality.geck.serializer.kit.tbase.TBaseHandler;
import dev.vality.geck.serializer.kit.tbase.TBaseProcessor;
import dev.vality.geck.serializer.domain.*;
import org.apache.thrift.TException;
import org.apache.thrift.TSerializer;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.transport.TTransportException;
import org.junit.Ignore;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.function.IntFunction;
import java.util.stream.IntStream;
import java.util.zip.GZIPOutputStream;

import static org.junit.Assert.assertEquals;

public class GeckTest {
    Geck geck = new Geck();

    @Test
    public void testKebab() throws IOException {
        TestObject testObject1 = GeckTestUtil.getTestObject(100, i -> Status.unknown(new Unknown("unknown")));//new MockTBaseProcessor().process(new TestObject(), new TBaseHandler<>(TestObject.class));
        TestObject testObject2 = new ObjectProcessor().process(
                MsgPackProcessor.newBinaryInstance().process(
                        new TBaseProcessor().process(testObject1, MsgPackHandler.newBufferedInstance(true)),
                        new ObjectHandler()), new TBaseHandler<>(TestObject.class));
        assertEquals(testObject1, testObject2);
    }

    @Test
    public void msgPackTest() throws Exception {
        TestObject testObject = GeckTestUtil.getTestObject();
        byte[] msgPack = geck.toMsgPack(testObject, true);
        byte[] tCompact = new TSerializer(new TCompactProtocol.Factory()).serialize(testObject);
        byte[] tBinary = new TSerializer(new TBinaryProtocol.Factory()).serialize(testObject);
        System.out.println("MsgPack:" + msgPack.length);
        System.out.println("Compact:" + tCompact.length);
        System.out.println("Binary:" + tBinary.length);

        ByteArrayOutputStream bos1 = new ByteArrayOutputStream(msgPack.length);
        GZIPOutputStream gzip1 = new GZIPOutputStream(bos1);
        gzip1.write(msgPack);
        gzip1.close();
        System.out.println("GZip:MsgPack:" + bos1.toByteArray().length);

        ByteArrayOutputStream bos2 = new ByteArrayOutputStream(tBinary.length);
        GZIPOutputStream gzip2 = new GZIPOutputStream(bos2);
        gzip2.write(tBinary);
        gzip2.close();
        System.out.println("GZip:Binary:" + bos2.toByteArray().length);

        ByteArrayOutputStream bos3 = new ByteArrayOutputStream(tCompact.length);
        GZIPOutputStream gzip3 = new GZIPOutputStream(bos3);
        gzip3.write(tCompact);
        gzip3.close();
        System.out.println("GZip:Compact:" + bos3.toByteArray().length);


    }

    @Test
    public void whenUseIdTest() throws IOException {
        Ids ids = new Ids();
        ids = new MockTBaseProcessor().process(ids, new TBaseHandler<>(Ids.class));
        Ids2 ids2 = MsgPackProcessor.newBinaryInstance().process(new TBaseProcessor().process(ids, MsgPackHandler.newBufferedInstance(true)), new TBaseHandler<>(Ids2.class));
        MsgPackProcessor.newBinaryInstance().process(
                new TBaseProcessor().process(ids2, MsgPackHandler.newBufferedInstance(true)),
                new TBaseHandler<>(Ids3.class, TBaseHandler.Mode.PREFER_NAME));
    }

    @Test
    public void msgPackTestList() throws Exception {
        TestObject testObject = GeckTestUtil.getTestObject();
        List<Status> lists = Collections.nCopies(1000, Status.unknown(new Unknown("SomeData")));
        testObject.setStatuses(lists);

        byte[] msgPack = geck.toMsgPack(testObject, true);
        byte[] tCompact = new TSerializer(new TCompactProtocol.Factory()).serialize(testObject);
        byte[] tBinary = new TSerializer(new TBinaryProtocol.Factory()).serialize(testObject);
        System.out.println("MsgPack:" + msgPack.length);
        System.out.println("Compact:" + tCompact.length);
        System.out.println("Binary:" + tBinary.length);

        ByteArrayOutputStream bos1 = new ByteArrayOutputStream(msgPack.length);
        GZIPOutputStream gzip1 = new GZIPOutputStream(bos1);
        gzip1.write(msgPack);
        gzip1.close();
        System.out.println("GZip:MsgPack:" + bos1.toByteArray().length);

        ByteArrayOutputStream bos2 = new ByteArrayOutputStream(tBinary.length);
        GZIPOutputStream gzip2 = new GZIPOutputStream(bos2);
        gzip2.write(tBinary);
        gzip2.close();
        System.out.println("GZip:Binary:" + bos2.toByteArray().length);
    }

    @Test
    @Ignore
    public void testPerformance() throws IOException, TTransportException {
        boolean useDict = true;
        TestObject testObject = GeckTestUtil.getTestObject(100, i -> Status.unknown(new Unknown("SomeData")));
        HandlerStub writerStub = new HandlerStub();
        TSerializer binarySerializer = new TSerializer(new TBinaryProtocol.Factory());
        IntFunction<Integer> stubConsumer = i -> geck.write(testObject, writerStub).length;
        IntFunction<Integer> msgPackConsumer = i -> geck.toMsgPack(testObject, useDict).length;
        IntFunction<Integer> jsonConsumer = i -> geck.toJson(testObject).length();
        IntFunction<Integer> tBinaryWriter = i -> {
            try {
                return binarySerializer.serialize(testObject).length;
            } catch (TException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        };
        List<Map.Entry<String, IntFunction<Integer>>> consumers = Arrays.asList(
                new AbstractMap.SimpleEntry("Stub", stubConsumer),
                new AbstractMap.SimpleEntry("TBinary", tBinaryWriter),
                new AbstractMap.SimpleEntry("MsgPack", msgPackConsumer),
                new AbstractMap.SimpleEntry("Json", jsonConsumer)
        );
        System.out.println("Warmup...");
        consumers.stream().forEach(entry -> IntStream.range(0, 10000).forEach(i -> entry.getValue().apply(i)));

        System.out.println("Test...");
        consumers.stream().forEach(entry -> {
            long startTime = System.currentTimeMillis();
            int bytes = IntStream.range(0, 50000).map(i -> entry.getValue().apply(i)).sum();
            System.out.println(entry.getKey() + "\t Time:\t" + (System.currentTimeMillis() - startTime) + "\t Bytes:\t" + bytes);
        });

    }


}
