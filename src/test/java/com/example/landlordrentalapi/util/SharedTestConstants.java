package com.example.landlordrentalapi.util;

public class SharedTestConstants {
    public static final String VALID_WEBHOOK_PAYLOAD = """
            {
              "id": "evt_1MkIXpFpw6zLLOxOhwrO9K61",
              "object": "event",
              "api_version": "2022-11-15",
              "created": 1678503149,
              "data": {
                "object": {
                  "id": "pm_1MkIXpFpw6zLLOxONzepJV4l",
                  "object": "payment_method",
                  "billing_details": {
                    "address": {
                      "city": null,
                      "country": null,
                      "line1": null,
                      "line2": null,
                      "postal_code": null,
                      "state": null
                    },
                    "email": null,
                    "name": null,
                    "phone": null
                  },
                  "card": {
                    "brand": "visa",
                    "checks": {
                      "address_line1_check": null,
                      "address_postal_code_check": null,
                      "cvc_check": null
                    },
                    "country": "US",
                    "exp_month": 3,
                    "exp_year": 2024,
                    "fingerprint": "rJAq2WGChpPqApyI",
                    "funding": "credit",
                    "generated_from": null,
                    "last4": "4242",
                    "networks": {
                      "available": [
                        "visa"
                      ],
                      "preferred": null
                    },
                    "three_d_secure_usage": {
                      "supported": true
                    },
                    "wallet": null
                  },
                  "created": 1678503149,
                  "customer": "cus_NVJACfcsDSxIhj",
                  "livemode": false,
                  "metadata": {},
                  "type": "card"
                }
              },
              "livemode": false,
              "pending_webhooks": 0,
              "request": {
                "id": "req_DRPBscwKXPwJqL",
                "idempotency_key": "28ef79ca-ef4e-4f19-a023-8ecc44f17e15"
              },
              "type": "payment_method.attached"
            }
            """;
    public static final String INVALID_WEBHOOK_PAYLOAD = "invalid webhook payload";
    public static final String INVALID_WEBHOOK_KEY = "invalid webhook key";


}
