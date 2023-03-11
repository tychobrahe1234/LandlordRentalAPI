package com.example.landlordrentalapi.util;

import com.stripe.net.Webhook;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class SharedUtilityMethods {
    public static String generateSigHeader(final String payload, final String key) throws NoSuchAlgorithmException, InvalidKeyException {
        final long timestamp = Webhook.Util.getTimeNow();
        final String payloadToSign = String.format("%d.%s", timestamp, payload);
        final String signature = Webhook.Util.computeHmacSha256(key, payloadToSign);

        return String.format("t=%d,%s=%s", timestamp, Webhook.Signature.EXPECTED_SCHEME, signature);
    }
}
