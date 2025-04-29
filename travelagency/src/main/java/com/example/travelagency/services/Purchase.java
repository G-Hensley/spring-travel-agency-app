package com.example.travelagency.services;

import com.example.travelagency.entities.Cart;
import com.example.travelagency.entities.CartItem;
import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class Purchase {
    @NotNull(message = "Customer cart is required")
    private Cart customerCart;

    @NotEmpty(message = "At least one item is required")
    private Set<CartItem> cartItems = new HashSet<>();
}