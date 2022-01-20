package dev.vality.geck.serializer.kit.tbase;

import dev.vality.damsel.domain.Failure;
import dev.vality.damsel.domain.SubFailure;
import dev.vality.damsel.payment_processing.errors.*;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class ErrorMappingTest {

    @Test
    public void testFailure() throws IOException {
        PaymentFailure failure = PaymentFailure.rejected_by_inspector(new GeneralFailure());
        Failure gFailure = TErrorUtil.toGeneral(failure);
        gFailure.setReason("test");
        Assert.assertEquals("rejected_by_inspector", TErrorUtil.toStringVal(gFailure));
        Assert.assertEquals("rejected_by_inspector", TErrorUtil.toStringVal(failure));
    }

    @Test
    public void testSubFailure() throws IOException {
        PaymentFailure failure = PaymentFailure.authorization_failed(AuthorizationFailure.payment_tool_rejected(PaymentToolReject.bank_card_rejected(BankCardReject.card_expired(new GeneralFailure()))));
        Assert.assertEquals("authorization_failed:payment_tool_rejected:bank_card_rejected:card_expired", TErrorUtil.toStringVal(TErrorUtil.toGeneral(failure)));
        Assert.assertEquals("authorization_failed:payment_tool_rejected:bank_card_rejected:card_expired", TErrorUtil.toStringVal(failure));
    }

    @Test
    public void testStringToFailure() {
        String strFailure = "rejected_by_inspector";
        Assert.assertEquals(strFailure, TErrorUtil.toStringVal(TErrorUtil.toGeneral(strFailure)));
    }

    @Test
    public void testStringToSubFailure() {
        String strFailure = "authorization_failed:payment_tool_rejected:bank_card_rejected:card_expired";
        Assert.assertEquals(strFailure, TErrorUtil.toStringVal(TErrorUtil.toGeneral(strFailure)));
    }

    @Test
    public void testWhenDelimiterInCode() {
        Failure failure = new Failure("delimiter:here");
        failure.setSub(new SubFailure("another:delimiter:here"));
        Assert.assertEquals("delimiter here:another delimiter here", TErrorUtil.toStringVal(failure));
    }
}
