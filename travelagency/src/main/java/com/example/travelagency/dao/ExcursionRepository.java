package com.example.travelagency.dao;

import com.example.travelagency.entities.Excursion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@CrossOrigin(origins = "*")
public interface ExcursionRepository extends JpaRepository<Excursion, Long> {
    List<Excursion> findByVacationId(Long vacationId);
}