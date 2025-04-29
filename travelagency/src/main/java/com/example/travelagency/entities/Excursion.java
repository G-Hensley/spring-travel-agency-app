package com.example.travelagency.entities;

import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
public class Excursion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "excursion_title")
    private String excursionTitle;

    @Column(name = "excursion_price")
    private BigDecimal excursionPrice;

    @Column(name = "image_url")
    private String imageURL;

    @Column(name = "create_date")
    private Date createDate;

    @Column(name = "last_update")
    private Date lastUpdate;

    @ManyToMany(mappedBy = "excursions")
    private Set<Vacation> vacations = new HashSet<>();

    @ManyToMany(mappedBy = "excursions")
    private Set<CartItem> cartItems = new HashSet<>();

    public Excursion() {
    }
}