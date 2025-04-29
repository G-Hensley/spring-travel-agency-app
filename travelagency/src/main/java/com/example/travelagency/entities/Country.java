package com.example.travelagency.entities;

import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    @Column(name = "country_name")
    private String countryName;

    @Column(name = "creat_date")
    private Date createDate;

    @Column(name = "last_update")
    private Date lastUpdate;

    @OneToMany(mappedBy = "country", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Division> divisions = new HashSet<>();

    public Country() {

    }
}
