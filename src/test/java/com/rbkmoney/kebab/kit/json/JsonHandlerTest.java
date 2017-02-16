package com.rbkmoney.kebab.kit.json;

import com.rbkmoney.kebab.kit.tbase.TBaseProcessor;
import com.rbkmoney.kebab.test.TestObject;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import java.io.IOException;

import static com.rbkmoney.kebab.KebabUtil.getTestObject;

/**
 * Created by tolkonepiu on 15/02/2017.
 */
public class JsonHandlerTest {

    @Test
    public void jsonTest() throws JSONException, IOException {
        TestObject testObject = getTestObject();
        String json = new TBaseProcessor().process(testObject, new JsonHandler()).toString();
        System.out.println(json);
        new JSONObject(json);
    }

    @Test
    public void incorrectCharactersTest() throws IOException {
        JsonHandler jsonHandler = new JsonHandler();
        jsonHandler.beginStruct(2);
        jsonHandler.name("ke\"k4 2\nqw\\eqw/eqw\nas\be");


        char[] chars = new char['\u00A0'];
        for (int i = 0; i < '\u00A0'; i++) {
            chars[i] = (char) i;
        }

        jsonHandler.value(new String(chars));

        jsonHandler.endStruct();
        String json = jsonHandler.getResult().toString();
        System.out.println(json);
        new JSONObject(json);
    }

}
