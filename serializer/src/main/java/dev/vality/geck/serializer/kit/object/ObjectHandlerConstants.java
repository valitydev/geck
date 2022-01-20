package dev.vality.geck.serializer.kit.object;

import dev.vality.geck.serializer.kit.StructType;

class ObjectHandlerConstants {
    public static final char ESCAPE_CHAR = '\\';
    public static final char TYPE_DELIMITER = '@';
    public static final String MAP_MARK = TYPE_DELIMITER + StructType.MAP.getKey();
    public static final String SET_MARK = TYPE_DELIMITER + StructType.SET.getKey();
    public static final String MAP_VALUE = "val";
    public static final String MAP_KEY = "key";
    public static final String MAP_KEY_MAP = MAP_KEY + MAP_MARK;
    public static final String MAP_KEY_VALUE = MAP_KEY + MAP_MARK;

}
