package com.rbkmoney.geck.serializer.kit.tbase;

import com.rbkmoney.damsel.domain.Failure;
import com.rbkmoney.damsel.domain.SubFailure;
import com.rbkmoney.damsel.payment_processing.errors.*;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import static com.rbkmoney.geck.serializer.kit.tbase.TErrorUtil.*;

/**
 * Created by vpankrashkin on 27.02.18.
 */
public class ErrorMappingTest {

    @Test
    public void testFailure() throws IOException {
        PaymentFailure failure = PaymentFailure.rejected_by_inspector(new GeneralFailure());
        Failure gFailure = toGeneral(failure);
        gFailure.setReason("test");
        Assert.assertEquals("rejected_by_inspector", TErrorUtil.toStringVal(gFailure));
        Assert.assertEquals("rejected_by_inspector", TErrorUtil.toStringVal(failure));
    }

    @Test
    public void testSubFailure() throws IOException {
        PaymentFailure failure = PaymentFailure.authorization_failed(AuthorizationFailure.payment_tool_rejected(PaymentToolReject.bank_card_rejected(BankCardReject.card_expired(new GeneralFailure()))));
        Assert.assertEquals("authorization_failed:payment_tool_rejected:bank_card_rejected:card_expired", TErrorUtil.toStringVal(toGeneral(failure)));
        Assert.assertEquals("authorization_failed:payment_tool_rejected:bank_card_rejected:card_expired", TErrorUtil.toStringVal(failure));
    }

    @Test
    public void testStringToFailure() {
        String strFailure = "rejected_by_inspector";
        Assert.assertEquals(strFailure, toStringVal(toGeneral(strFailure)));
    }

    @Test
    public void testStringToSubFailure() {
        String strFailure = "authorization_failed:payment_tool_rejected:bank_card_rejected:card_expired";
        Assert.assertEquals(strFailure, toStringVal(toGeneral(strFailure)));
    }

    @Test
    public void testWhenDelimiterInCode() {
        Failure failure = new Failure("delimiter:here");
        failure.setSub(new SubFailure("another:delimiter:here"));
        Assert.assertEquals("delimiter here:another delimiter here", TErrorUtil.toStringVal(failure));
    }
}
