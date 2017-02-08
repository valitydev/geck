package com.rbkmoney.kebab.kit.msgpack;

/**
 * Created by vpankrashkin on 07.02.17.
 */
public class MsgPackFlags {
     static final byte startList = 3;
     static final byte nop = 0;
     static final byte startStruct = 1;
     static final byte endStruct = 2;
     static final byte endList = 4;
     static final byte startMap = 5;
     static final byte endMap = 6;
     static final byte startMapKey = 7;
     static final byte endMapKey = 8;
     static final byte startMapValue = 9;
     static final byte endMapValue = 10;
     static final byte pointName = 11;
     static final byte pointValue = 12;
     static final byte pointDictionary = 13;
     static final byte pointDictionaryRef = 14;
}
