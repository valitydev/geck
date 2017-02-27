package com.rbkmoney.geck.serializer.kit.tbase;

import com.rbkmoney.geck.serializer.kit.EventFlags;
import com.rbkmoney.geck.serializer.test.HandlerTest;
import com.rbkmoney.geck.serializer.test.MapTest;
import com.rbkmoney.geck.serializer.test.SetTest;
import com.rbkmoney.geck.serializer.test.TUnionTest;
import org.junit.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Created by tolkonepiu on 14/02/2017.
 */
public class TBaseHandlerTest {

    @Test
    public void nameAndValueTest() throws IOException {
        TBaseHandler handler = new TBaseHandler<>(HandlerTest.class);
        handler.beginStruct(3);

        // when method 'name' has already been called
        handler.name("one");
        assertThatThrownBy(() -> handler.name("one"))
                .hasMessageContaining("incorrect state " + EventFlags.pointName);

        // when value has incorrect type
        assertThatThrownBy(() -> handler.value(true))
                .hasMessage("incorrect type of value: expected 'STRING', actual 'BOOLEAN'");

        handler.value("kek");

        // when method 'value' has already been called in struct
        assertThatThrownBy(() -> handler.value("kek?"))
                .hasMessageContaining("incorrect state " + EventFlags.startStruct);

        // when 'name' does not exist
        assertThatThrownBy(() -> handler.name("qwe"))
                .hasMessage("Field 'qwe' not found");

        handler.name("two");

        //when long > int
        assertThatThrownBy(() -> handler.value(Long.MAX_VALUE))
                .hasMessage("integer overflow");

        //when is not a map
        assertThatThrownBy(() -> handler.beginMap(2))
                .hasMessage("incorrect type of value: expected 'INTEGER', actual 'MAP'");

        //when is not a struct
        assertThatThrownBy(() -> handler.beginStruct(2))
                .hasMessage("incorrect type of value: expected 'INTEGER', actual 'STRUCT'");

        handler.value(5);

        handler.name("three");

        handler.beginList(1);

        assertThatThrownBy(() -> handler.name("three"))
                .hasMessageContaining("incorrect state " + EventFlags.startList);

        //check collection value
        assertThatThrownBy(() -> handler.value(Long.MAX_VALUE))
                .hasMessage("incorrect type of value: expected 'STRING', actual 'LONG'");
        assertThatThrownBy(() -> handler.beginStruct(2))
                .hasMessage("incorrect type of value: expected 'STRING', actual 'STRUCT'");
        assertThatThrownBy(() -> handler.beginList(3))
                .hasMessage("incorrect type of value: expected 'STRING', actual 'LIST'");
        assertThatThrownBy(() -> handler.beginMap(4))
                .hasMessage("incorrect type of value: expected 'STRING', actual 'MAP'");

        handler.value("kek");

        handler.endList();

        handler.endStruct();

        handler.getResult();
    }

    @Test
    public void setAndNumbersTest() throws IOException {
        TBaseHandler handler = new TBaseHandler<>(SetTest.class);

        handler.beginStruct(1);

        handler.name("idsSet");

        assertThatThrownBy(() -> handler.beginList(2))
                .hasMessage("incorrect type of value: expected 'SET', actual 'LIST'");

        handler.beginSet(2);

        handler.beginStruct(5);

        handler.name("micro_id");

        assertThatThrownBy(() -> handler.value(Short.MIN_VALUE))
                .hasMessage("byte overflow");

        handler.value(Byte.MAX_VALUE);

        handler.name("mini_id");

        assertThatThrownBy(() -> handler.value(Short.MAX_VALUE + 1))
                .hasMessage("short overflow");

        handler.value(Short.MIN_VALUE);

        handler.name("id");

        assertThatThrownBy(() -> handler.value(Long.MIN_VALUE))
                .hasMessage("integer overflow");

        handler.value(Integer.MAX_VALUE);

        handler.name("big_id");

        handler.value(Long.MIN_VALUE);

        handler.endStruct();

        assertThatThrownBy(() -> handler.endList())
                .hasMessageContaining("incorrect state " + EventFlags.startSet);

        handler.endSet();

        handler.endStruct();

        handler.getResult();
    }

    @Test
    public void mapTest() throws IOException {
        TBaseHandler handler = new TBaseHandler(MapTest.class);

        handler.beginStruct(1);

        handler.name("enumMap");

        handler.beginMap(2);

        //begin map after begin map
        assertThatThrownBy(() -> handler.beginMap(2))
                .hasMessageContaining("incorrect state " + EventFlags.startMap);

        handler.beginKey();

        //end value after begin key
        assertThatThrownBy(() -> handler.endValue())
                .hasMessageContaining("incorrect state " + EventFlags.startMapKey);

        handler.value("TEST1");

        //double key data
        assertThatThrownBy(() -> handler.value("TEST2"))
                .hasMessageContaining("incorrect state " + EventFlags.endMapKey);

        handler.endKey();

        handler.beginValue();

        //end value after begin value
        assertThatThrownBy(() -> handler.endValue())
                .hasMessageContaining("incorrect state " + EventFlags.startMapValue);

        //end key after begin value
        assertThatThrownBy(() -> handler.endKey())
                .hasMessageContaining("incorrect state " + EventFlags.startMapValue);


        handler.value("TEST1");

        //double value data
        assertThatThrownBy(() -> handler.value("TEST2"))
                .hasMessageContaining("incorrect state " + EventFlags.endMapValue);

        handler.endValue();

        handler.endMap();

        //end map after end map
        assertThatThrownBy(() -> handler.endMap())
                .hasMessageContaining("incorrect state " + EventFlags.startStruct);


        handler.endStruct();

        handler.getResult();
    }

    @Test
    public void tUnionTest() throws IOException {
        TBaseHandler handler = new TBaseHandler(TUnionTest.class);

        handler.beginStruct(1);

        assertThatThrownBy(() -> handler.endStruct())
                .hasMessage("field 'status' is required and must not be null");

        handler.name("status");

        handler.beginStruct(1);

        assertThatThrownBy(() -> handler.endStruct())
                .hasMessage("one of fields in union 'Status' must be set");

        handler.name("ok");

        handler.beginStruct(1);

        handler.endStruct();

        handler.endStruct();

        handler.getResult();

    }

}
