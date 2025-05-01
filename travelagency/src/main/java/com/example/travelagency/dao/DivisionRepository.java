package com.example.travelagency.dao;

import com.example.travelagency.entities.Division;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "*")
public interface DivisionRepository extends JpaRepository<Division, Long> {
}