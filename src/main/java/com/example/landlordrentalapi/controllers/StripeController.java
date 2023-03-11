package com.example.landlordrentalapi.controllers;

import com.example.landlordrentalapi.exceptions.InvalidPayloadException;
import com.example.landlordrentalapi.exceptions.SignatureVerificationFailureException;
import com.google.gson.JsonSyntaxException;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.Event;
import com.stripe.model.EventDataObjectDeserializer;
import com.stripe.model.StripeObject;
import com.stripe.net.Webhook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class StripeController {
    private final String signatureHeaderSecret;

    public StripeController(
            final @Value("${stripe.signatureHeaderSecret}") String signatureHeaderSecret) {
        this.signatureHeaderSecret = signatureHeaderSecret;
    }

    @PostMapping("/stripe/webhook")
    public ResponseEntity<String> stripeEventHandler(
            @RequestBody final String payload,
            @RequestHeader("Stripe-Signature") final String signatureHeader) {

        Event event;
        try {
            event = Webhook.constructEvent(payload, signatureHeader, signatureHeaderSecret);
        } catch (JsonSyntaxException e) {
            throw new InvalidPayloadException(e.getMessage());
        } catch (SignatureVerificationException e) {
            throw new SignatureVerificationFailureException(e.getMessage());
        }

        EventDataObjectDeserializer dataObjectDeserializer = event.getDataObjectDeserializer();
        StripeObject stripeObject = null;
        if (dataObjectDeserializer.getObject().isPresent()) {
            stripeObject = dataObjectDeserializer.getObject().get();
        } else {
            // Deserialization failed, probably due to an API version mismatch.
            // Refer to the Javadoc documentation on `EventDataObjectDeserializer` for
            // instructions on how to handle this case, or return an error here.
            System.out.println("Deserializer Failed");
        }


        switch (event.getType()) {
            case "payment_intent.succeeded":
                // ...
                System.out.println("PAYMENT SUCCEEDED!");
                break;
            case "payment_method.attached":
                // ...
                System.out.println("PAYMENT METHOD ATTACHED");
                break;
            // ... handle other event types
            default:
                // Unexpected event type
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(event.getType() + " is an invalid event type");
        }
        return ResponseEntity.ok("");
    }
}
