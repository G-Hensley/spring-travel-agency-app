package com.example.travelagency.services;

public interface CheckoutService {
    PurchaseResponse placeOrder(Purchase purchase);
}