package com.ada.recipes.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "measures")
public class MeasuringUnit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String abbreviation;

    public MeasuringUnit(String description, String abbreviation){
        this.description = description;
        this.abbreviation = abbreviation;
    }
}
