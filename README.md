**LandlordRentalAPI**

_Database setup_:
Change values in `application.properties` to reflect what is on your local machine. Clone the database repository and run the setup scripts on your machine to receive most up to date schema. 

_To run and develop locally:_

1. [Install the Stripe CLI](https://stripe.com/docs/stripe-cli)
   2. Ask Sam to be added to the Stripe development team if you haven't already
2. Send all stripe events to the local webhook endpoint: ``stripe listen --forward-to localhost:8080/webhook_events/stripe``. Set the `stripe.signatureHeaderSecret` in `application.properties` to the webhook signing secret
3. Trigger events like so: ``stripe trigger payment_intent.succeeded``