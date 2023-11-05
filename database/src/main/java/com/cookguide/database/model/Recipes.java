package com.cookguide.database.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "recipes")
public class Recipes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int uid;

    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @Column(name = "image", nullable = false)
    private String image;

    @Column(name = "ingredients", nullable = false)
    private String ingredients;

    @Column (name = "preparation", nullable = false)
    private String preparation;

    @Column (name = "time", nullable = false)
    private String time;

    @Column (name = "servings", nullable = false)
    private String servings;

}
