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

        assertEquals("kek", parser.getItem(0));
        assertEquals("kek", parser.getItemPath(0));
        assertEquals("tsss", parser.getItem(1));
        assertEquals("kek.tsss", parser.getItemPath(1));
        assertEquals("qwe", parser.getItem(2));
        assertEquals("kek.tsss.qwe", parser.getItemPath(2));
        assertEquals(3, parser.size());

        parser = new PathParser("kek..kek...");
        assertEquals("kek", parser.getItem(1));
        assertEquals("kek.kek", parser.getItemPath(1));
        assertEquals(2, parser.size());
    }

    @Test
    public void subParserTest() {
        Parser parser = new PathParser("1.2.3.4.5");
        assertEquals(5, parser.size());
        parser = parser.getSubParser(2);
        assertEquals(3, parser.size());
        assertEquals("3.4.5", parser.getItemPath(parser.size() - 1));
    }

}
