package com.example.landlordrentalapi.controllers;


import com.example.landlordrentalapi.util.SharedTestConstants;
import com.example.landlordrentalapi.exceptions.InvalidPayloadException;
import com.example.landlordrentalapi.exceptions.SignatureVerificationFailureException;
import com.example.landlordrentalapi.util.SharedUtilityMethods;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class StripeControllerTests {
    @Autowired
    private StripeController stripeController;
    @Value("${stripe.signatureHeaderSecret}")
    private String signatureHeaderSecret;

    @Test
    public void testSuccessfulWebhookCall() throws NoSuchAlgorithmException, InvalidKeyException {
        this.stripeController.stripeEventHandler(
                SharedTestConstants.VALID_WEBHOOK_PAYLOAD,
                SharedUtilityMethods.generateSigHeader(SharedTestConstants.VALID_WEBHOOK_PAYLOAD, this.signatureHeaderSecret)
        );
    }

    @Test
    public void testWebhookInvalidPayload() {
        assertThrows(InvalidPayloadException.class, () ->
                this.stripeController.stripeEventHandler(
                        SharedTestConstants.INVALID_WEBHOOK_PAYLOAD,
                        SharedUtilityMethods.generateSigHeader(SharedTestConstants.INVALID_WEBHOOK_PAYLOAD, this.signatureHeaderSecret)
                ));
    }

    @Test
    public void testWebhookSignatureVerificationFailed() {
        assertThrows(SignatureVerificationFailureException.class, () ->
                this.stripeController.stripeEventHandler(
                        SharedTestConstants.VALID_WEBHOOK_PAYLOAD,
                        SharedUtilityMethods.generateSigHeader(SharedTestConstants.VALID_WEBHOOK_PAYLOAD, SharedTestConstants.INVALID_WEBHOOK_KEY)
                ));
    }
}
