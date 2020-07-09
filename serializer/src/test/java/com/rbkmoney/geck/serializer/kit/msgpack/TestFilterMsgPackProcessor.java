package com.rbkmoney.geck.serializer.kit.msgpack;

import com.rbkmoney.geck.serializer.Geck;
import com.rbkmoney.geck.serializer.StructHandleResult;
import com.rbkmoney.geck.serializer.StructHandler;
import com.rbkmoney.geck.serializer.kit.msgpack.supply.NoFilterMsgPackProcessor;
import com.rbkmoney.geck.serializer.kit.tbase.TBaseProcessor;
import com.rbkmoney.geck.serializer.domain.*;
import org.apache.thrift.TBase;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.rbkmoney.geck.serializer.GeckTestUtil.getTestObject;
import static com.rbkmoney.geck.serializer.StructHandleResult.CONTINUE;
import static com.rbkmoney.geck.serializer.StructHandleResult.SKIP_SIBLINGS;
import static com.rbkmoney.geck.serializer.StructHandleResult.SKIP_SUBTREE;
import static org.mockito.Mockito.*;

public class TestFilterMsgPackProcessor {
    @Test
    @Ignore
    public void testPerformance() throws IOException {
        MsgPackProcessor<byte[]> fp = MsgPackProcessor.newBinaryInstance();
        MsgPackProcessor<byte[]> nfp = NoFilterMsgPackProcessor.newBinaryInstance();
        TBaseProcessor tbp = new TBaseProcessor();
        MsgPackHandler<byte[]> handler = MsgPackHandler.newBufferedInstance();
        for (int i = 0; i < 10000; i++) {
            TestObject testObject1 = getTestObject(100, j -> Status.unknown(new Unknown("unknown")));
            tbp.process(testObject1, handler);
            tbp.process(testObject1, handler);
        }
        List<byte[]> list = IntStream.range(0, 10000).mapToObj(i -> {
            try {
                return tbp.process(getTestObject(100, j -> Status.unknown(new Unknown("unknown"))), handler);
            } catch (IOException e) {
                return null;
            }}).collect(Collectors.toList());

        long start = System.currentTimeMillis();
        iterate(nfp, handler, list, 1);
        long time = System.currentTimeMillis() - start;
        System.out.println("NoFilter Time(ms):"+time);

        start = System.currentTimeMillis();
        iterate(fp, handler, list, 1);
        time = System.currentTimeMillis() - start;
        System.out.println("Filter Time(ms):"+time);

        start = System.currentTimeMillis();
        iterate(nfp, handler, list, 1);
        time = System.currentTimeMillis() - start;
        System.out.println("NoFilter Time(ms):"+time);

        start = System.currentTimeMillis();
        iterate(fp, handler, list, 1);
        time = System.currentTimeMillis() - start;
        System.out.println("Filter Time(ms):"+time);


    }

    private void iterate(MsgPackProcessor processor, MsgPackHandler handler, List<byte[]> elements, int iterCount) throws IOException {
        for (int i = 0; i < iterCount; i++)
            for (byte[] bytes : elements)
                processor.process(bytes, handler);
    }

    @Test
    public void testSkipKebab() throws IOException {
        TestObject testObject1 = getTestObject(100, i -> Status.unknown(new Unknown("unknown")));
        StructHandler mocked = mock(StructHandler.class);
        when(mocked.getLastHandleResult()).thenReturn(StructHandleResult.SKIP_SUBTREE);
        testSkipAll(toMsgPack(testObject1), mocked);
    }

    @Test
    public void testSkipSubtreeAll() throws IOException {
        StructHandler mocked = mock(StructHandler.class);
        when(mocked.getLastHandleResult()).thenReturn(StructHandleResult.SKIP_SUBTREE);
        testSkipAll(toMsgPack(genSample1()), mocked);
    }

    @Test
    public void testSkipSiblingsAll() throws IOException {
        StructHandler mocked = mock(StructHandler.class);
        when(mocked.getLastHandleResult()).thenReturn(StructHandleResult.SKIP_SIBLINGS);
        testSkipAll(toMsgPack(genSample1()), mocked);
    }

    @Test
    public void testSkipTerminateAll() throws IOException {
        StructHandler mocked = mock(StructHandler.class);
        when(mocked.getLastHandleResult()).thenReturn(StructHandleResult.TERMINATE);
        testSkipAll(toMsgPack(genSample1()), mocked);
    }

    private void testSkipAll(byte[] data, StructHandler mocked) throws IOException {
        MsgPackProcessor.newBinaryInstance().process(data, mocked);
        verify(mocked).beginStruct(anyInt());
        verify(mocked).getLastHandleResult();
        verify(mocked).getResult();
        verifyNoMoreInteractions(mocked);
    }

    @Test
    public void testSkipSiblings1Lvl() throws Exception {
        HandleResultAnswer handleResultAnswer = new HandleResultAnswer(CONTINUE);
        OnValueAnswer onValueAnswer = new OnValueAnswer(handleResultAnswer, "sname1", SKIP_SIBLINGS, 1);
        StructHandler mocked = mock(StructHandler.class);
        doAnswer(onValueAnswer).when(mocked).name(anyByte(), anyString());
        doAnswer(handleResultAnswer).when(mocked).getLastHandleResult();

        MsgPackProcessor.newBinaryInstance().process(toMsgPack(genSample1()), mocked);
        InOrder inOrder = inOrder(mocked);
        intAndLastResult(inOrder, mocked, mocked::beginStruct);
        nStrAndLastResult(inOrder, mocked, mocked::name, "sname1");
        naAndLastResult(inOrder, mocked, mocked::endStruct);
        inOrder.verify(mocked).getResult();
        verifyZeroInteractions(mocked);
    }

    @Test
    public void testSkipSubtree1Lvl() throws Exception {
        HandleResultAnswer handleResultAnswer = new HandleResultAnswer(CONTINUE);
        OnValueAnswer onValueAnswer = new OnValueAnswer(handleResultAnswer, "uname1", SKIP_SUBTREE, 1);
        StructHandler mocked = mock(StructHandler.class);
        doAnswer(onValueAnswer).when(mocked).name(anyByte(), anyString());
        doAnswer(handleResultAnswer).when(mocked).getLastHandleResult();

        MsgPackProcessor.newBinaryInstance().process(toMsgPack(genSample1()), mocked);
        InOrder inOrder = inOrder(mocked);
        intAndLastResult(inOrder, mocked, mocked::beginStruct);

        nStrAndLastResult(inOrder, mocked, mocked::name, "sname1");
        strAndLastResult(inOrder, mocked, mocked::value, "val1");

        nStrAndLastResult(inOrder, mocked, mocked::name, "uname1");

        nStrAndLastResult(inOrder, mocked, mocked::name, "sname2");
        strAndLastResult(inOrder, mocked, mocked::value, "val2");

        naAndLastResult(inOrder, mocked, mocked::endStruct);
        inOrder.verify(mocked).getResult();
        verifyZeroInteractions(mocked);
    }

    @Test
    public void testSkipMapEntryByKey() throws Exception {
        HandleResultAnswer handleResultAnswer = new HandleResultAnswer(CONTINUE);
        OnValueAnswer onValueAnswer = new OnValueAnswer(handleResultAnswer, "mkey1", SKIP_SUBTREE, 0);
        StructHandler mocked = mock(StructHandler.class);
        doAnswer(onValueAnswer).when(mocked).value(anyString());
        doAnswer(handleResultAnswer).when(mocked).getLastHandleResult();

        MsgPackProcessor.newBinaryInstance().process(toMsgPack(genSample2()), mocked);
        InOrder inOrder = inOrder(mocked);
        intAndLastResult(inOrder, mocked, mocked::beginStruct);

        nStrAndLastResult(inOrder, mocked, mocked::name, "map1");
        intAndLastResult(inOrder, mocked, mocked::beginMap);

        inOrder.verify(mocked).beginKey();
        strAndLastResult(inOrder, mocked, mocked::value, "mkey1");

        inOrder.verify(mocked).beginKey();
        strAndLastResult(inOrder, mocked, mocked::value, "mkey2");
        inOrder.verify(mocked).endKey();

        inOrder.verify(mocked).beginValue();
        intAndLastResult(inOrder, mocked, mocked::beginStruct);
        nStrAndLastResult(inOrder, mocked, mocked::name, "description");
        strAndLastResult(inOrder, mocked, mocked::value, "descr2");
        naAndLastResult(inOrder, mocked, mocked::endStruct);
        inOrder.verify(mocked).endValue();

        naAndLastResult(inOrder, mocked, mocked::endMap);

        naAndLastResult(inOrder, mocked, mocked::endStruct);
        inOrder.verify(mocked).getResult();
        verifyZeroInteractions(mocked);
    }

    private void nStrAndLastResult(InOrder inOrder, StructHandler mock, T1BiConsumer<Byte, String> method, String val) throws Exception {
        inOrder.verify(mock);
        method.accept(anyByte(), eq(val));
        inOrder.verify(mock).getLastHandleResult();
    }

    private void strAndLastResult(InOrder inOrder, StructHandler mock, T1Consumer<String> method, String str) throws Exception {
        inOrder.verify(mock);
        method.accept(eq(str));
        inOrder.verify(mock).getLastHandleResult();
    }

    private void intAndLastResult(InOrder inOrder, StructHandler mock, T1Consumer<Integer> method) throws Exception {
        inOrder.verify(mock);
        method.accept(anyInt());
        inOrder.verify(mock).getLastHandleResult();
    }

    private void naAndLastResult(InOrder inOrder, StructHandler mock, T0Consumer method) throws Exception {
        inOrder.verify(mock);
        method.accept();
        inOrder.verify(mock).getLastHandleResult();
    }

    private void andLastResult(StructHandler mock, T1Consumer<Integer> func) throws Exception {
        func.accept(0);
        verify(mock).getLastHandleResult();
    }

    private byte[] toMsgPack(TBase tBase) throws IOException {
        return Geck.toMsgPack(tBase);
    }

    private FilterObject genSample1() {
        return new FilterObject("val1", FilterUnion.list_object_1(new FilterListObject(Arrays.asList("lval1", "lval2", "lval3"))), "val2");
    }

    private FilterMapObject genSample2() {
        return new FilterMapObject(new LinkedHashMap(){{
            put("mkey1", new Unknown("descr1"));
            put("mkey2", new Unknown("descr2"));
        }});
    }

    static class HandleResultAnswer implements Answer<StructHandleResult> {
        private StructHandleResult responseStatus;
        public StructHandleResult newResponseStatus;
        public HandleResultAnswer(StructHandleResult responseStatus) {
            this.responseStatus = responseStatus;
            this.newResponseStatus = responseStatus;
        }

        @Override
        public StructHandleResult answer(InvocationOnMock invocation) throws Throwable {
            StructHandleResult result = newResponseStatus;
            newResponseStatus = responseStatus;
            return result;

        }
    }
    static class OnValueAnswer implements Answer<Void> {
        private int argIdx;
        private String condition;
        private StructHandleResult newResult;
        private HandleResultAnswer handleResultAnswer;

        public OnValueAnswer(HandleResultAnswer handleResultAnswer, String condition, StructHandleResult newResult, int argIdx) {
            this.handleResultAnswer = handleResultAnswer;
            this.newResult = newResult;
            this.condition = condition;
            this.argIdx = argIdx;
        }

        @Override
        public Void answer(InvocationOnMock invocation) throws Throwable {
            if (condition.equals(invocation.getArguments()[argIdx])) {
                handleResultAnswer.newResponseStatus = newResult;
            }
            return null;
        }
    }


    @FunctionalInterface
    private interface T0Consumer {
        void accept() throws Exception;
    }

    @FunctionalInterface
    private interface T1Consumer<T> {
        void accept(T val) throws Exception;

    }

    @FunctionalInterface
    private interface T1BiConsumer<T, TT> {
        void accept(T val1, TT val2) throws Exception;

    }
}
