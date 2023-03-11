To run and develop locally:

1. [Install the Stripe CLI](https://stripe.com/docs/stripe-cli)
   2. Ask Sam to be added to the Stripe development team if you need access
2. Send all stripe events to the local webhook endpoint: ``stripe listen --forward-to localhost:8080/stripe/webhook``. Set the `stripe.signatureHeaderSecret` in `application.properties` to the webhook signing secret
3. Trigger events like so: ``stripe trigger payment_intent.succeeded``