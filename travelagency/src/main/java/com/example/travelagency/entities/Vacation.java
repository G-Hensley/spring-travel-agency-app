package com.example.travelagency.entities;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "vacations")
@Getter
@Setter
public class Vacation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vacation_id")
    private Long id;

    @Column(name = "vacation_title")
    @NotBlank(message = "Vacation title is required")
    @Size(min = 2, max = 100, message = "Vacation title must be between 2 and 100 characters")
    private String vacation_title;

    @Column(name = "description")
    @NotBlank(message = "Description is required")
    @Size(min = 10, max = 255, message = "Description must be between 10 and 255 characters")
    private String description;

    @Column(name = "travel_fare_price")
    @NotNull(message = "Travel fare price is required")
    @Min(value = 0, message = "Travel fare price must be a positive number")
    private BigDecimal travel_price;

    @Column(name = "image_url")
    @NotBlank(message = "Image URL is required")
    @Size(min = 10, max = 255, message = "Image URL must be between 10 and 255 characters")
    private String image_URL;

    @Column(name = "create_date")
    private Date create_date;

    @Column(name = "last_update")
    private Date last_update;

    @OneToMany(mappedBy = "vacation", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CartItem> cartItems = new HashSet<>();

    @OneToMany(mappedBy = "vacation", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Excursion> excursions = new HashSet<>();

    public Vacation() {
    }
}