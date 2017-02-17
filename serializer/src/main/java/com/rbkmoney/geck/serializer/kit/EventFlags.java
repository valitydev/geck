package com.rbkmoney.geck.serializer.kit;

/**
 * Created by vpankrashkin on 07.02.17.
 */
public class EventFlags {
     public static final byte nop = 0;
     public static final byte startStruct = 1;
     public static final byte endStruct = 2;
     public static final byte startList = 3;
     public static final byte endList = 4;
     public static final byte startMap = 5;
     public static final byte endMap = 6;
     public static final byte startMapKey = 7;
     public static final byte endMapKey = 8;
     public static final byte startMapValue = 9;
     public static final byte endMapValue = 10;
     public static final byte pointName = 11;
     public static final byte pointValue = 12;
     public static final byte pointDictionary = 13;
     public static final byte pointDictionaryRef = 14;
}
