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
@Table(name = "excursions")
@Getter
@Setter
public class Excursion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "excursion_id")
    private Long id;

    @Column(name = "excursion_title")
    @NotBlank(message = "Excursion title is required")
    @Size(min = 2, max = 100, message = "Excursion title must be between 2 and 100 characters")
    private String excursion_title;

    @Column(name = "excursion_price")
    @NotNull(message = "Excursion price is required")
    @Min(value = 0, message = "Excursion price must be a positive number")
    private BigDecimal excursion_price;

    @Column(name = "image_url")
    @NotBlank(message = "Image URL is required")
    @Size(min = 10, max = 255, message = "Image URL must be between 10 and 255 characters")
    private String image_URL;

    @Column(name = "create_date")
    @Temporal(TemporalType.DATE)
    private Date create_date;

    @Column(name = "last_update")
    @Temporal(TemporalType.DATE)
    private Date last_update;

    @ManyToOne
    @JoinColumn(name = "vacation_id")
    @NotNull(message = "Vacation is required")
    private Vacation vacation;

    @ManyToMany(mappedBy = "excursions")
    private Set<CartItem> cartItems = new HashSet<>();

    public Excursion() {
    }
}