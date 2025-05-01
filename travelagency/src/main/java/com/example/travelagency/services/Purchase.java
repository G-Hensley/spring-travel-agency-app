package com.example.travelagency.services;

import com.example.travelagency.entities.Cart;
import com.example.travelagency.entities.CartItem;
import com.example.travelagency.entities.Customer;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class Purchase {
    @NotNull(message = "Customer is required")
    private Customer customer;

    @NotNull(message = "Customer cart is required")
    @JsonProperty("cart")
    private Cart customerCart;

    @NotEmpty(message = "At least one item is required")
    @JsonProperty("cartItems")
    private JsonNode cartItems;

    private Integer nextIndex;
}