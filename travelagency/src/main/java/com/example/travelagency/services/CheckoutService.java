package com.example.travelagency.services;

import jakarta.validation.Valid;

public interface CheckoutService {
    PurchaseResponse placeOrder(@Valid Purchase purchase);
}