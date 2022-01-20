package dev.vality.geck.serializer.kit;

public class EventFlags {
     public static final byte nop = 0;
     public static final byte startStruct = 1;
     public static final byte endStruct = 2;
     public static final byte startList = 3;
     public static final byte endList = 4;
     public static final byte startSet = 5;
     public static final byte endSet = 6;
     public static final byte startMap = 7;
     public static final byte endMap = 8;
     public static final byte startMapKey = 9;
     public static final byte endMapKey = 10;
     public static final byte startMapValue = 11;
     public static final byte endMapValue = 12;
     public static final byte pointName = 13;
     public static final byte pointValue = 14;
     public static final byte pointDictionary = 15;
     public static final byte pointDictionaryRef = 16;
}
