package dev.vality.geck.filter;

public interface Parser {

    String getItem(int item);

    String[] getItems();

    String getItemPath(int item);

    Parser getSubParser(int from);

    int size();

}
