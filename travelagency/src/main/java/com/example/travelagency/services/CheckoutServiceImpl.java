package com.example.travelagency.services;

import com.example.travelagency.dao.CartRepository;
import com.example.travelagency.dao.CustomerRepository;
import com.example.travelagency.dao.ExcursionRepository;
import com.example.travelagency.dao.VacationRepository;
import com.example.travelagency.entities.Cart;
import com.example.travelagency.entities.CartItem;
import com.example.travelagency.entities.Customer;
import com.example.travelagency.entities.Excursion;
import com.example.travelagency.entities.StatusType;
import com.example.travelagency.entities.Vacation;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class CheckoutServiceImpl implements CheckoutService {
    private final CustomerRepository customerRepository;
    private final CartRepository cartRepository;
    private final ExcursionRepository excursionRepository;
    private final VacationRepository vacationRepository;
    private final ObjectMapper objectMapper;

    public CheckoutServiceImpl(CustomerRepository customerRepository, CartRepository cartRepository,
                               ExcursionRepository excursionRepository, VacationRepository vacationRepository) {
        this.customerRepository = customerRepository;
        this.cartRepository = cartRepository;
        this.excursionRepository = excursionRepository;
        this.vacationRepository = vacationRepository;
        this.objectMapper = new ObjectMapper();
    }

    @Override
    @Transactional
    public PurchaseResponse placeOrder(Purchase purchase) {
        System.out.println("Received purchase: " + purchase);

        if (purchase == null) {
            throw new IllegalArgumentException("Purchase object cannot be null");
        }

        Customer customer = purchase.getCustomer();
        if (customer == null || customer.getId() == null) {
            throw new IllegalArgumentException("Customer is required. Received customer: " + customer);
        }

        Cart receivedCart = purchase.getCustomerCart();
        if (receivedCart == null) {
            throw new IllegalArgumentException("Cart is required");
        }

        JsonNode cartItemsJson = purchase.getCartItems();
        if (cartItemsJson == null || !cartItemsJson.isArray() || cartItemsJson.size() == 0) {
            throw new IllegalArgumentException("At least one cart item is required");
        }

        // Verify the customer exists in the database
        Customer finalCustomer = customer;
        customer = customerRepository.findById(customer.getId())
                .orElseThrow(() -> new IllegalArgumentException("Customer not found with ID: " + finalCustomer.getId()));

        // Create a new Cart instance (ignore the ID from the payload)
        Cart cart = new Cart();
        cart.setCustomer(customer);
        cart.setPackage_price(receivedCart.getPackage_price());
        cart.setParty_size(receivedCart.getParty_size());
        cart.setCreate_date(new Date());
        cart.setLast_update(new Date());
        cart.setStatus(StatusType.ordered);

        // Generate an order tracking number
        String orderTrackingNumber = UUID.randomUUID().toString();
        cart.setOrderTrackingNumber(orderTrackingNumber);

        // Process cartItems JSON
        Set<CartItem> cartItems = new HashSet<>();
        for (JsonNode item : cartItemsJson) {
            JsonNode vacationNode = item.get("vacation");
            if (vacationNode == null || !vacationNode.has("id")) {
                throw new IllegalArgumentException("Cart item must have a valid vacation. Received: " + item);
            }

            Long vacationId = vacationNode.get("id").asLong();
            Vacation vacation = vacationRepository.findById(vacationId)
                    .orElseThrow(() -> new IllegalArgumentException("Vacation not found with ID: " + vacationId));

            List<Excursion> excursions = excursionRepository.findByVacationId(vacationId);
            if (excursions.isEmpty()) {
                throw new IllegalArgumentException("No excursions found for vacation ID: " + vacationId);
            }

            // Create a single CartItem for all excursions
            CartItem cartItem = new CartItem();
            cartItem.setCart(cart);
            cartItem.setVacation(vacation);
            cartItem.setCreate_date(new Date());
            cartItem.setLast_update(new Date());
            
            // Add all excursions to the cart item
            for (Excursion excursion : excursions) {
                cartItem.setExcursion(excursion);
            }
            
            cartItems.add(cartItem);
            System.out.println("Created CartItem with excursions: " + excursions + " and vacation: " + vacation);
        }

        cart.setCartItems(cartItems);

        // Log and save
        System.out.println("Saving cart: " + cart);
        cartRepository.save(cart);

        return new PurchaseResponse(orderTrackingNumber);
    }
}