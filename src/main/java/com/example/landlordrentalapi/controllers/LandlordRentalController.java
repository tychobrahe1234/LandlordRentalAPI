package com.example.landlordrentalapi.controllers;

import com.example.landlordrentalapi.exceptions.InvalidPayloadException;
import com.google.gson.JsonSyntaxException;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.Event;
import com.stripe.model.EventDataObjectDeserializer;
import com.stripe.model.StripeObject;
import com.stripe.net.Webhook;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class LandlordRentalController {

    @GetMapping("/")
    public String helloWorld(@RequestParam(name = "statement", required = false) Optional<String> statement) {
        return statement.orElse("Hello World!");
    }

    @PostMapping("/webhook")
    public ResponseEntity<String> stripeEventHandler(
            @RequestBody String payload,
            @RequestHeader("Stripe-Signature") String sigHeader) {

        Event event;
        try {
            event = Webhook.constructEvent(payload, sigHeader, "whsec_3d03c7fa8588f57462d879ec619fcd74758af2406408af1387a94afb1db03006");
        } catch (JsonSyntaxException e) {
            throw new InvalidPayloadException(e.getMessage());
        } catch (SignatureVerificationException e) {
            System.out.println("Failed signature verification");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed signature verification");
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
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }
}
