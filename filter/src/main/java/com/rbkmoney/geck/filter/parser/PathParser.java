package com.rbkmoney.geck.filter.parser;

import com.rbkmoney.geck.common.util.StringUtil;
import com.rbkmoney.geck.filter.Parser;

import java.util.Arrays;

/**
 * Created by tolkonepiu on 16/03/2017.
 */
public class PathParser implements Parser {

    private final String[] items;
    private final String delimiter;

    public static final String DEFAULT_DELIMITER = ".";

    public PathParser(String fieldPath) {
        this(fieldPath, DEFAULT_DELIMITER);
    }

    public PathParser(String fieldPath, String delimiter) {
        this(StringUtil.split(fieldPath, delimiter), delimiter);
    }

    public PathParser(String[] items, String delimiter) {
        this.items = items;
        this.delimiter = delimiter;
    }

    @Override
    public String getItem(int item) {
        return items[item];
    }

    @Override
    public String[] getItems() {
        return Arrays.copyOf(items, items.length);
    }

    @Override
    public String getItemPath(int item) {
        return String.join(delimiter, Arrays.copyOfRange(items, 0, item + 1));
    }

    @Override
    public Parser getSubParser(int from) {
        return new PathParser(Arrays.copyOfRange(items, from, items.length), delimiter);
    }

    @Override
    public int size() {
        return items.length;
    }

}
