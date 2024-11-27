from flask import Flask, request, jsonify
import stripe

app = Flask(__name__)

# Set your Stripe secret key
# stripe.api_key = "sk_test_51QLRum029qv9sJ0BbOQXzc2dGpPN7EEpM3xMKNUq2Ok5ER9dX9Gsy2lj6E8K5AJoluSp4kFoupn3Bwl8DAcwTBsn001iNZf2zo"

# Route for the home page
@app.route('/')
def home():
    return "Welcome to the Flask Stripe API!"

# Route for the favicon (to prevent 404 errors)
@app.route('/favicon.ico')
def favicon():
    return "", 204  # No content

# Route to create a PaymentIntent
@app.route('/create-payment-intent', methods=['POST'])
def create_payment_intent():
    try:
        # Get payment details from the client
        data = request.json
        amount = data['amount']  # Amount in smallest currency unit (e.g., cents)

        # Set currency to CAD (Canadian dollars)
        currency = "cad"

        # Create a customer
        customer = stripe.Customer.create()

        # Create an ephemeral key for the customer
        ephemeral_key = stripe.EphemeralKey.create(
            customer=customer['id'],
            stripe_version='2022-11-15'
        )

        # Create a PaymentIntent for the customer
        payment_intent = stripe.PaymentIntent.create(
            amount=amount,
            currency=currency,
            customer=customer['id'],
            payment_method_types=['card']
        )

        # Return the client secret, customer ID, and ephemeral key
        return jsonify({
            'clientSecret': payment_intent['client_secret'],
            'customer': customer['id'],
            'ephemeralKey': ephemeral_key['secret']
        })
    except Exception as e:
        # Handle errors gracefully
        return jsonify({'error': str(e)}), 400

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=4242)
