package com.rbkmoney.geck.serializer.kit.tbase;

import com.rbkmoney.damsel.domain.Failure;
import com.rbkmoney.damsel.payment_processing.errors.PaymentFailure;

import java.io.IOException;

/**
 * Created by vpankrashkin on 27.02.18.
 */
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
