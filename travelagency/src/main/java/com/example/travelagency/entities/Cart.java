package com.example.travelagency.entities;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "carts")
@Getter
@Setter
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id")
    private Long id;

    @Column(name = "order_tracking_number")
    private String orderTrackingNumber;

    @Column(name = "package_price")
    @NotNull(message = "Package price is required")
    @Min(value = 0, message = "Package price must be a positive number")
    private BigDecimal package_price;

    @Column(name = "party_size")
    @NotNull(message = "Party size is required")
    @Min(value = 1, message = "Party size must be at least 1")
    private int party_size;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Status is required")
    private StatusType status;

    @Column(name = "create_date")
    private Date create_date;

    @Column(name = "last_update")
    private Date last_update;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    @NotNull(message = "Customer is required")
    private Customer customer;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CartItem> cartItems = new HashSet<>();

    public Cart() {
    }
}