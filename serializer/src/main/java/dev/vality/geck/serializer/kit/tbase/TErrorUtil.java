package dev.vality.geck.serializer.kit.tbase;

import dev.vality.damsel.domain.Failure;
import dev.vality.damsel.payment_processing.errors.PaymentFailure;

import java.io.IOException;

public class TErrorUtil {
    public static Failure toGeneral(PaymentFailure failure) {
        try {
            return new TBaseProcessor().process(failure, new TTypedToDomainErrorHandler());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Failure toGeneral(String failure) {
        try {
            return new TTypedStringToDomainErrorProcessor().process(failure, new TTypedToDomainErrorHandler());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String toStringVal(Failure failure) {
        try {
            return new TBaseProcessor().process(failure, new TDomainToStringErrorHandler());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String toStringVal(PaymentFailure failure) {
        try {
            return new TBaseProcessor().process(failure, new TTypedToStringErrorHandler());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
