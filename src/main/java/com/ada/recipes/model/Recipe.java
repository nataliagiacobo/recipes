package com.ada.recipes.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "recipes")
@Where(clause = "active is true")
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String description;

    @ManyToOne
    private RecipeCategory category;

    @Column(nullable = false)
    private Integer preparationTimeMin;

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL)
    private List<RecipeItem> items;

    @ElementCollection
    private List<String> preparationSteps = new ArrayList<>();

    @ManyToOne
    private User user;

    @Column(nullable = false)
    private Boolean active;

}
