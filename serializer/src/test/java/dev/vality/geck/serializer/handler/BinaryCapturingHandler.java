package dev.vality.geck.serializer.handler;

/**
 * Тестовый handler, который запоминает первое бинарное значение.
 * Нужен, чтобы проверить, какие байты процессор реально отдал в результат.
 */
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
