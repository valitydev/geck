package com.rbkmoney.geck.filter.kit.msgpack;

import com.rbkmoney.geck.filter.Rule;
import com.rbkmoney.geck.serializer.StructHandleResult;
import com.rbkmoney.geck.serializer.StructHandler;

import java.io.IOException;
import java.util.List;

import static com.rbkmoney.geck.serializer.kit.EventFlags.*;

public class FilteringHandler implements StructHandler<List<Rule>>{
    private final StructVisitor visitor;
    private StructHandleResult handleResult;

    public FilteringHandler(StructVisitor visitor) {
        this.visitor = visitor;
        visitor.init();
    }

    @Override
    public void beginStruct(int size) throws IOException {
        handleResult = visitor.visit(startStruct, size);
    }

    @Override
    public void endStruct() throws IOException {
        handleResult = visitor.visit(endStruct, null);
    }

    @Override
    public void beginList(int size) throws IOException {
        handleResult = visitor.visit(startList, size);
    }

    @Override
    public void endList() throws IOException {
        handleResult = visitor.visit(endList, null);
    }

    @Override
    public void beginSet(int size) throws IOException {
        handleResult = visitor.visit(startSet, size);
    }

    @Override
    public void endSet() throws IOException {
        handleResult = visitor.visit(endSet, null);
    }

    @Override
    public void beginMap(int size) throws IOException {
        handleResult = visitor.visit(startMap, size);
    }

    @Override
    public void endMap() throws IOException {
        handleResult = visitor.visit(endMap, null);
    }

    @Override
    public void beginKey() throws IOException {
        handleResult = visitor.visit(startMapKey, null);
    }

    @Override
    public void endKey() throws IOException {
        handleResult = visitor.visit(endMapKey, null);
    }

    @Override
    public void beginValue() throws IOException {
        handleResult = visitor.visit(startMapValue, null);
    }

    @Override
    public void endValue() throws IOException {
        handleResult = visitor.visit(endMapValue, null);
    }

    @Override
    public void name(String name) throws IOException {
        handleResult = visitor.visit(pointName, name);
    }

    @Override
    public void value(boolean value) throws IOException {
        handleResult = visitor.visit(pointValue, value);
    }

    @Override
    public void value(String value) throws IOException {
        handleResult = visitor.visit(pointValue, value);
    }

    @Override
    public void value(double value) throws IOException {
        handleResult = visitor.visit(pointValue, value);
    }

    @Override
    public void value(long value) throws IOException {
        handleResult = visitor.visit(pointValue, value);
    }

    @Override
    public void value(byte[] value) throws IOException {
        handleResult = visitor.visit(pointValue, value);
    }

    @Override
    public void nullValue() throws IOException {
        handleResult = visitor.visit(pointValue, null);
    }

    @Override
    public List<Rule> getResult() throws IOException {
        List<Rule> selected = visitor.getSelected();
        visitor.init();
        return selected;
    }

    @Override
    public StructHandleResult getLastHandleResult() {
        return handleResult;
    }
}
