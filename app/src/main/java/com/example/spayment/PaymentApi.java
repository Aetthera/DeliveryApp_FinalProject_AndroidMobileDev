package com.example.spayment;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
public interface PaymentApi {
    @Headers("Content-Type: application/json")
    @POST("create-payment-intent")
    Call<PaymentResponse> createPaymentIntent(@Body PaymentRequest request);
}
