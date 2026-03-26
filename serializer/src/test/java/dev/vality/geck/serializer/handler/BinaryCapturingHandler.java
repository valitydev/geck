package dev.vality.geck.serializer.handler;

public class BinaryCapturingHandler extends HandlerStub {

    private byte[] binaryValue;

    @Override
    public void value(byte[] value) {
        if (binaryValue == null) {
            binaryValue = value;
        }
    }

    @Override
    public byte[] getResult() {
        return binaryValue;
    }
}
