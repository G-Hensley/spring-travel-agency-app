package com.example.travelagency.services;

import com.example.travelagency.entities.Cart;
import com.example.travelagency.entities.CartItem;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class Purchase {
    private Cart customerCart;
    private Set<CartItem> cartItems = new HashSet<>();
}