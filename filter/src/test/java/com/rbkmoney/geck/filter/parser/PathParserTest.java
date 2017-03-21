package com.rbkmoney.geck.filter.parser;

import com.rbkmoney.geck.filter.Parser;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by tolkonepiu on 16/03/2017.
 */
public class PathParserTest {

    @Test
    public void parserInitTest() {
        Parser parser = new PathParser("kek.tsss.qwe");

        assertEquals(parser.getItem(0), "kek");
        assertEquals(parser.getItemPath(0), "kek");
        assertEquals(parser.getItem(1), "tsss");
        assertEquals(parser.getItemPath(1), "kek.tsss");
        assertEquals(parser.getItem(2), "qwe");
        assertEquals(parser.getItemPath(2), "kek.tsss.qwe");
        assertEquals(parser.size(), 3);

        parser = new PathParser("kek..kek...");
        assertEquals(parser.getItem(1), "");
        assertEquals(parser.getItemPath(1), "kek.");
        assertEquals(parser.size(), 6);
    }

}
