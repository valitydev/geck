package com.rbkmoney.kebab;

import org.apache.thrift.TBase;

/**
 * Created by tolkonepiu on 24/01/2017.
 */
public class Kebab<T extends TBase> {

    public String toJson(T src) {
        throw new UnsupportedOperationException("under contruction");
    }

    public byte[] toMsgPack(T src) {
        throw new UnsupportedOperationException("under contruction");
    }


}
