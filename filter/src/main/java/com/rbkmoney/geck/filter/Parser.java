package com.rbkmoney.geck.filter;

/**
 * Created by tolkonepiu on 16/03/2017.
 */
public interface Parser {

    String getItem(int item);

    String getItemPath(int item);

    Parser getSubParser(int from);

    int size();

}
