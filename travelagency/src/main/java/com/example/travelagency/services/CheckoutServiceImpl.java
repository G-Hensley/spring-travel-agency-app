package com.example.travelagency.services;

import com.example.travelagency.dao.CartRepository;
import com.example.travelagency.dao.CustomerRepository;
import com.example.travelagency.entities.Cart;
import com.example.travelagency.entities.CartItem;
import com.example.travelagency.entities.StatusType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.UUID;

@Service
public class CheckoutServiceImpl implements CheckoutService {

    private final CustomerRepository customerRepository;
    private final CartRepository cartRepository;

    public CheckoutServiceImpl(CustomerRepository customerRepository, CartRepository cartRepository) {
        this.customerRepository = customerRepository;
        this.cartRepository = cartRepository;
    }

    @Override
    @Transactional
    public PurchaseResponse placeOrder(Purchase purchase) {
        // Retrieve the cart and cart items from the purchase
        Cart cart = purchase.getCustomerCart();
        Set<CartItem> cartItems = purchase.getCartItems();

        // Generate an order tracking number
        String orderTrackingNumber = UUID.randomUUID().toString();
        cart.setOrderTrackingNumber(orderTrackingNumber);

        // Set the status to ordered
        cart.setStatus(StatusType.ordered);

        // Associate cart items with the cart
        if (cartItems != null) {
            cartItems.forEach(cartItem -> cartItem.setCart(cart));
            cart.setCartItems(cartItems);
        }

        // Save the cart (this will cascade to cart items due to CascadeType.ALL)
        cartRepository.save(cart);

        // Return the response with the order tracking number
        return new PurchaseResponse(orderTrackingNumber);
    }
}
