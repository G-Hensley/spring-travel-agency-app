package com.example.travelagency.dao;

import com.example.travelagency.entities.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "*")
public interface CountryRepository extends JpaRepository<Country, Long> {
}