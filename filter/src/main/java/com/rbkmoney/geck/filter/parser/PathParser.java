package com.rbkmoney.geck.filter.parser;

import com.rbkmoney.geck.filter.Parser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tolkonepiu on 16/03/2017.
 */
public class PathParser implements Parser {

    private String fieldPath;
    private List<String> items;
    private List<String> forNameItems;

    public static final String DEFAULT_DELIMITER = ".";
    private String delimiter;

    public PathParser(String fieldPath) {
        this(fieldPath, DEFAULT_DELIMITER);
    }

    public PathParser(String fieldPath, String delimiter) {
        this.fieldPath = fieldPath;
        this.delimiter = delimiter;
        init();
    }

    private void init() {
        List<String> items = new ArrayList<>();
        List<String> forNameItems = new ArrayList<>();
        int last = 0;
        int next;
        while ((next = fieldPath.indexOf(delimiter, last)) != -1) {
            items.add(fieldPath.substring(last, next));
            forNameItems.add(fieldPath.substring(0, next));
            last = next + delimiter.length();
        }
        forNameItems.add(fieldPath.substring(0, fieldPath.length()));
        items.add(fieldPath.substring(last, fieldPath.length()));

        this.items = items;
        this.forNameItems = forNameItems;
    }

    @Override
    public String getItem(int item) {
        return items.get(item);
    }

    @Override
    public String getItemPath(int item) {
        return forNameItems.get(item);
    }

    @Override
    public int size() {
        return items.size();
    }

}
