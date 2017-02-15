package com.rbkmoney.kebab.kit.tbase;

import com.rbkmoney.kebab.test.HandlerTest;
import com.rbkmoney.kebab.test.MapTest;
import com.rbkmoney.kebab.test.TUnionTest;
import org.junit.Test;

import java.io.IOException;

import static com.rbkmoney.kebab.kit.EventFlags.*;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Created by tolkonepiu on 14/02/2017.
 */
public class TBaseHandlerTest {

    @Test
    public void nameAndValueTest() throws IOException {
        TBaseHandler handler = new TBaseHandler(HandlerTest.class);
        handler.beginStruct(3);

        // when method 'name' has already been called
        handler.name("one");
        assertThatThrownBy(() -> handler.name("one"))
                .hasMessageContaining("incorrect state " + pointName);

        // when value has incorrect type
        assertThatThrownBy(() -> handler.value(true))
                .hasMessage("incorrect type of value: expected 'STRING', actual 'BOOLEAN'");

        handler.value("kek");

        // when method 'value' has already been called in struct
        assertThatThrownBy(() -> handler.value("kek?"))
                .hasMessageContaining("incorrect state " + startStruct);

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
                .hasMessageContaining("incorrect state " + startList);

        //check collection value
        assertThatThrownBy(() -> handler.value(Long.MAX_VALUE))
                .hasMessage("incorrect type of value: expected 'STRING', actual 'LONG'");
        assertThatThrownBy(() -> handler.beginStruct(2))
                .hasMessage("incorrect type of value: expected 'STRING', actual 'STRUCT'");
        assertThatThrownBy(() -> handler.beginList(3))
                .hasMessage("value expected 'STRING', actual collection");
        assertThatThrownBy(() -> handler.beginMap(4))
                .hasMessage("incorrect type of value: expected 'STRING', actual 'MAP'");

        handler.value("kek");

        handler.endList();

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
                .hasMessageContaining("incorrect state " + startMap);

        handler.beginKey();

        //end value after begin key
        assertThatThrownBy(() -> handler.endValue())
                .hasMessageContaining("incorrect state " + startMapKey);

        handler.value("TEST1");

        //double key data
        assertThatThrownBy(() -> handler.value("TEST2"))
                .hasMessageContaining("incorrect state " + endMapKey);

        handler.endKey();

        handler.beginValue();

        //end value after begin value
        assertThatThrownBy(() -> handler.endValue())
                .hasMessageContaining("incorrect state " + startMapValue);

        //end key after begin value
        assertThatThrownBy(() -> handler.endKey())
                .hasMessageContaining("incorrect state " + startMapValue);


        handler.value("TEST1");

        //double value data
        assertThatThrownBy(() -> handler.value("TEST2"))
                .hasMessageContaining("incorrect state " + endMapValue);

        handler.endValue();

        handler.endMap();

        //end map after end map
        assertThatThrownBy(() -> handler.endMap())
                .hasMessageContaining("incorrect state " + startStruct);


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
