package com.example.travelagency.dao;

import com.example.travelagency.entities.Vacation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "*")
public interface VacationRepository extends JpaRepository<Vacation, Long> {
}