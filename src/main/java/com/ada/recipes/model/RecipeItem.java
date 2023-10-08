package com.ada.recipes.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "recipeItems")
public class RecipeItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="recipe_id")
    private Recipe recipe;

    @ManyToOne
    private Ingredient ingredient;

    @Column(nullable = false)
    private Double quantity;

    @ManyToOne
    private MeasuringUnit measuringUnit;

}
