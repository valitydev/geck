package com.rbkmoney.geck.filter.kit.msgpack;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rbkmoney.geck.filter.Rule;
import com.rbkmoney.geck.filter.condition.EqualsCondition;
import com.rbkmoney.geck.filter.condition.IsNullCondition;
import com.rbkmoney.geck.filter.rule.ConditionRule;
import com.rbkmoney.geck.filter.test.filter.IData;
import com.rbkmoney.geck.filter.test.filter.IDataCollection;
import com.rbkmoney.geck.filter.test.filter.ILvl2Data;
import com.rbkmoney.geck.filter.test.filter.ILvlData;
import com.rbkmoney.geck.filter.test.filter.IStatusCanceled;
import com.rbkmoney.geck.filter.test.filter.IStatusPaid;
import com.rbkmoney.geck.filter.test.filter.Invoice;
import com.rbkmoney.geck.filter.test.filter.InvoiceStatus;
import com.rbkmoney.geck.filter.test.filter.MapTest;
import com.rbkmoney.geck.serializer.kit.json.JsonHandler;
import com.rbkmoney.geck.serializer.kit.msgpack.MsgPackHandler;
import com.rbkmoney.geck.serializer.kit.msgpack.MsgPackProcessor;
import com.rbkmoney.geck.serializer.kit.tbase.TBaseProcessor;
import org.apache.thrift.TBase;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;

public class SelectorParserTest {

    private static Invoice createInvoice(int invId, String details, String dataVal, InvoiceStatus invoiceStatus) {
        Invoice invoice = new Invoice(invId, invoiceStatus,
                new IData(dataVal) {{
                    setDataOptVal("opt_data");
                }},
                new ILvlData() {{
                    setLvl2Data1(new ILvl2Data("lvl21val1", "lvl21val2"));
                    setLvl2Data2(new ILvl2Data("lvl22val1", "lvl22val2"));
                }}
        );
        invoice.setIDetails(details);

        return invoice;
    }

    private static InvoiceStatus iPaidStatus(String date) {
        return InvoiceStatus.paid(new IStatusPaid(date) {
            {
                setValue("pvalue");
            }
        });
    }

    private static InvoiceStatus iCanceledStatus(String details) {
        return InvoiceStatus.canceled(new IStatusCanceled(details) {
            {
                setValue("cvalue");
            }
        });
    }

    @Test
    public void test1LvlParser() throws IOException {
        List<Rule> rules = applyRules(new SelectorParser().parse("id", new ConditionRule(new EqualsCondition(1L))),
                preparePaidSample1(""));
        assertEquals(1, rules.size());
    }

    @Test
    public void test2LvlNotNullParserParser() throws IOException {
        List<Rule> rules =
                applyRules(new SelectorParser().parse("status.paid", new ConditionRule(new IsNullCondition().not())),
                        preparePaidSample1(""));
        assertEquals(1, rules.size());
    }

    @Test
    public void test3LvlParser() throws IOException {
        List<Rule> rules = applyRules(
                new SelectorParser().parse("status.canceled.details", new ConditionRule(new EqualsCondition("fail"))),
                prepareCanceledSample1("fail", ""));
        assertEquals(1, rules.size());
    }

    @Test
    public void testAnyNameParser() throws IOException {
        List<Rule> rules =
                applyRules(new SelectorParser().parse("*", new ConditionRule(new EqualsCondition("i_details_p"))),
                        preparePaidSample1(""));
        assertEquals(1, rules.size());
    }

    @Test
    public void test3LvlMiddleAnyNameParser() throws IOException {
        List<Rule> rules = applyRules(
                new SelectorParser().parse("status.*.value", new ConditionRule(new EqualsCondition("pvalue"))),
                preparePaidSample1(""));
        assertEquals(1, rules.size());
    }

    @Test
    public void test3LvlTailAnyNameParser() throws IOException {
        List<Rule> rules =
                applyRules(new SelectorParser().parse("data.*", new ConditionRule(new EqualsCondition("data_val"))),
                        preparePaidSample1("data_val"));
        assertEquals(1, rules.size());
    }

    @Test
    public void test2LvlAnyNameParser() throws IOException {
        List<Rule> rules = applyRules(
                new SelectorParser().parse("lvl_data.*.*", new ConditionRule(new EqualsCondition("lvl22val2"))),
                preparePaidSample1("data_val"));
        assertEquals(1, rules.size());
    }

    @Test
    public void testAnyElemParser() throws IOException {
        List<Rule> rules = applyRules(new SelectorParser()
                        .parse("data_coll.data_list.[*].data_val", new ConditionRule(new EqualsCondition("1"))),
                preparePaidSample2("value", true, 2));
        assertEquals(1, rules.size());
    }

    @Test
    public void testArrayNotExistsParser() throws IOException {
        Invoice invoice = preparePaidInvoice2("value", true, 2);
        List<Rule> rules = applyRules(new SelectorParser().parse("i_list.[]", new ConditionRule(obj -> true)),
                prepareSample(invoice));
        assertEquals(0, rules.size());
    }

    @Test
    public void testArrayExistsParser() throws IOException {
        Invoice invoice = preparePaidInvoice2("value", true, 2);
        invoice.setIList(new ArrayList<>());
        List<Rule> rules = applyRules(new SelectorParser().parse("i_list.[]", new ConditionRule(obj -> true)),
                prepareSample(invoice));
        assertEquals(1, rules.size());
    }

    @Test
    public void testAnyElemValueParser() throws IOException {
        Invoice invoice = createInvoice(1, "", "", InvoiceStatus.paid(new IStatusPaid()));
        invoice.setIList(Arrays.asList(0, 1, 2, 3));

        List<Rule> rules =
                applyRules(new SelectorParser().parse("i_list.[*]", new ConditionRule(new EqualsCondition(2L))),
                        prepareSample(invoice));
        assertEquals(1, rules.size());

        invoice.setIList(Arrays.asList());
        rules = applyRules(new SelectorParser().parse("i_list.[*]", new ConditionRule(new EqualsCondition(2L))),
                prepareSample(invoice));
        assertEquals(0, rules.size());
    }

    @Test
    public void testMapExistsParser() throws IOException {
        Invoice invoice = preparePaidInvoice1("value");
        invoice.setIMap(new LinkedHashMap<Integer, IData>() {
            {
            }
        });
        List<Rule> rules = applyRules(new SelectorParser().parse("i_map.{}", new ConditionRule(obj -> true)),
                prepareSample(invoice));
        assertEquals(1, rules.size());
    }

    @Test
    public void testMapKeyParser() throws IOException {
        Invoice invoice = preparePaidInvoice1("value");
        invoice.setIMap(new LinkedHashMap<Integer, IData>() {
            {
                put(1, new IData("val1"));
                put(2, new IData("val2"));
            }
        });
        List<Rule> rules = applyRules(
                new SelectorParser().parse("i_map.{*}.data_val", new ConditionRule(new EqualsCondition("val2"))),
                prepareSample(invoice));
        assertEquals(1, rules.size());
    }

    @Test
    public void testEmptyMapKeyParser() throws IOException {
        Invoice invoice = preparePaidInvoice1("value");
        invoice.setIMap(new LinkedHashMap<Integer, IData>() {
            {
            }
        });
        List<Rule> rules = applyRules(
                new SelectorParser().parse("i_map.{*}.data_val", new ConditionRule(new EqualsCondition("val2"))),
                prepareSample(invoice));
        assertEquals(0, rules.size());
    }

    @Test
    public void testNullMapKeyParser() throws IOException {
        Invoice invoice = preparePaidInvoice1("value");
        List<Rule> rules = applyRules(
                new SelectorParser().parse("i_map.{*}.data_val", new ConditionRule(new EqualsCondition("val2"))),
                prepareSample(invoice));
        assertEquals(0, rules.size());
    }

    @Test
    public void testObjMapKeyParser() throws IOException {
        Invoice invoice = preparePaidInvoice1("value");
        invoice.setObjMap(new LinkedHashMap() {
            {
                put(iPaidStatus("p"), new IData("val1"));
                put(iCanceledStatus("c"), new IData("val2"));
            }
        });
        List<Rule> rules = applyRules(
                new SelectorParser().parse("obj_map.{*}.data_val", new ConditionRule(new EqualsCondition("val3"))),
                prepareSample(invoice));
        assertEquals(0, rules.size());
    }

    @Test
    public void test2ObjMapKeyParser() throws IOException {
        Invoice invoice = preparePaidInvoice1("value");
        invoice.setObjMap(new LinkedHashMap() {
            {
                put(iPaidStatus("p"), new IData("val1"));
                put(iCanceledStatus("c"), new IData("val2"));
            }
        });
        List<Rule> rules = applyRules(
                new SelectorParser().parse("obj_map.{*}.data_val", new ConditionRule(new EqualsCondition("val3"))),
                prepareSample(invoice));
        assertEquals(0, rules.size());
    }

    /*@Test(expected = IllegalArgumentException.class)
    public void testBadFormat1MapExistsParser() throws IOException {
        List<Rule> rules = applyRules(new SelectorParser().parse("i_map.{*}", new ConditionRule(obj -> true)), preparePaidSample2("value", true, 2));
        assertEquals(1, rules.size());
    }*/

    @Test
    public void test3ObjMapKeyParser() throws IOException {
        Invoice invoice = preparePaidInvoice1("value");
        invoice.setObjMap(new LinkedHashMap() {
            {
                put(iPaidStatus("p"), new IData("val1"));
                put(iCanceledStatus("c"), new IData("val2"));
            }
        });
        List<Rule> rules = applyRules(new SelectorParser()
                        .parse("obj_map.{canceled}.data_val", new ConditionRule(new EqualsCondition("val2"))),
                prepareSample(invoice));
        assertEquals(1, rules.size());
        rules = applyRules(new SelectorParser()
                        .parse("obj_map.{canceled}.data_val", new ConditionRule(new EqualsCondition("val3"))),
                prepareSample(invoice));
        assertEquals(0, rules.size());
    }

    /*@Test(expected = IllegalArgumentException.class)
    public void testBadFormat1ArrayExistsParser() throws IOException {
        List<Rule> rules = applyRules(new SelectorParser().parse("i_list.[*]", new ConditionRule(obj -> true)), preparePaidSample2("value", true, 2));
        assertEquals(1, rules.size());
    }*/

    @Test
    public void testListMapKeyParser() throws IOException {
        MapTest mapTest = new MapTest();
        mapTest.setLstKeyMap(new HashMap<List<IData>, Integer>() {
            {
                put(Arrays.asList(), -1);
            }
        });

        List<Rule> rules = null;/*applyRules(new SelectorParser().parse("lst_key_map.{[]}", new ConditionRule(new EqualsCondition(-1L))), prepareSample(mapTest));
        assertEquals(1, rules.size());*/

        rules = applyRules(new SelectorParser().parse("lst_key_map.{*}", new ConditionRule(new EqualsCondition(-1L))),
                prepareSample(mapTest));
        assertEquals(1, rules.size());


        mapTest.setLstKeyMap(new HashMap<List<IData>, Integer>() {
            {
                put(Arrays.asList(), 0);
            }
        });

        rules = applyRules(new SelectorParser().parse("lst_key_map.{[]}", new ConditionRule(new EqualsCondition(-1L))),
                prepareSample(mapTest));
        assertEquals(0, rules.size());

        rules = applyRules(new SelectorParser().parse("lst_key_map.{*}", new ConditionRule(new EqualsCondition(-1L))),
                prepareSample(mapTest));
        assertEquals(0, rules.size());

    }

    @Test
    public void testMapNotExistsParser() throws IOException {
        Invoice invoice = preparePaidInvoice1("value");
        List<Rule> rules = applyRules(new SelectorParser().parse("i_map.{}", new ConditionRule(obj -> true)),
                prepareSample(invoice));
        assertEquals(0, rules.size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBadFormat2MapExistsParser() throws IOException {
        List<Rule> rules = applyRules(new SelectorParser().parse("i_map.{}.something", new ConditionRule(obj -> true)),
                preparePaidSample2("value", true, 2));
        assertEquals(1, rules.size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBadFormat2ArrayExistsParser() throws IOException {
        List<Rule> rules = applyRules(new SelectorParser().parse("i_list.[].something", new ConditionRule(obj -> true)),
                preparePaidSample2("value", true, 2));
        assertEquals(1, rules.size());
    }

    List<Rule> applyRules(Selector.Config[] configs, byte[] data) throws IOException {
        StructVisitor visitor = new StructVisitor(() -> configs);
        FilteringHandler handler = new FilteringHandler(visitor);

        List<Rule> rules = MsgPackProcessor.newBinaryInstance().process(data, handler);
        return rules;
    }

    byte[] prepareSample(TBase tBase) throws IOException {
        printJson(tBase);
        return new TBaseProcessor().process(tBase, MsgPackHandler.newBufferedInstance());
    }

    byte[] preparePaidSample1(String dataVal) throws IOException {
        Invoice invoice = preparePaidInvoice1(dataVal);
        return new TBaseProcessor().process(invoice, MsgPackHandler.newBufferedInstance());
    }

    Invoice preparePaidInvoice1(String dataVal) throws IOException {
        Invoice invoice = createInvoice(1, "i_details_p", dataVal, iPaidStatus("123"));
        return invoice;
    }

    byte[] preparePaidSample2(String dataVal, boolean useList, int collCount) throws IOException {
        Invoice invoice = preparePaidInvoice2(dataVal, useList, collCount);
        return new TBaseProcessor().process(invoice, MsgPackHandler.newBufferedInstance());
    }

    Invoice preparePaidInvoice2(String dataVal, boolean useList, int collCount) throws IOException {
        Invoice invoice = createInvoice(1, "i_details_p", dataVal, iPaidStatus("123"));
        setIDataCollection(invoice, useList, collCount);
        printJson(invoice);
        return invoice;
    }

    byte[] prepareCanceledSample1(String details, String dataVal) throws IOException {
        Invoice invoice = createInvoice(1, "i_details_c", dataVal, iCanceledStatus(details));
        printJson(invoice);
        return new TBaseProcessor().process(invoice, MsgPackHandler.newBufferedInstance());
    }

    private void printJson(TBase object) throws IOException {
        JsonNode node = new TBaseProcessor().process(object, new JsonHandler(true));

        System.out.printf("%nData: %n %s %n =========================================================%n",
                new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(node));
    }

    private void setIDataCollection(Invoice invoice, boolean useList, int elemCount) {
        Collection<IData> collection = useList ? new ArrayList<>() : new HashSet<>();
        IntStream.range(0, elemCount).forEach(i -> collection.add(new IData("" + i)));
        invoice.setDataColl(useList ? IDataCollection.data_list((List<IData>) collection) :
                IDataCollection.data_set((Set<IData>) collection));
    }

}
