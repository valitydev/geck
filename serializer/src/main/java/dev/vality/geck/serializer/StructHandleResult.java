package dev.vality.geck.serializer;

public enum StructHandleResult {
    CONTINUE,
    SKIP_SIBLINGS,
    SKIP_SUBTREE,
    JUMP_VALUE,
    TERMINATE
}
