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
public class Vacation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "vacation_title")
    private String vacationTitle;

    private String description;

    @Column(name = "travel_price")
    private BigDecimal travelPrice;

    @Column(name = "image_url")
    private String imageURL;

    @Column(name = "create_date")
    private Date createDate;

    @Column(name = "last_update")
    private Date lastUpdate;

    @OneToMany(mappedBy = "vacation", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CartItem> cartItems = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "vacation_excursion",
            joinColumns = @JoinColumn(name = "vacation_id"),
            inverseJoinColumns = @JoinColumn(name = "excursion_id")
    )
    private Set<Excursion> excursions = new HashSet<>();

    public Vacation() {
    }
}