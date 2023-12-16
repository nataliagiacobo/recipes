package com.ada.recipes.repository;

import com.ada.recipes.model.MeasuringUnit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MeasuringUnitRepository extends JpaRepository<MeasuringUnit, Integer> {
    @Override
    Page<MeasuringUnit> findAll(Pageable pageable);

    Optional<MeasuringUnit> findByDescriptionAndAbbreviation(String description, String abbreviation);
}
