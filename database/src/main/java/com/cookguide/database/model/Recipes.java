package com.cookguide.database.model;

import com.fasterxml.jackson.annotation.JsonProperty;
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
public class Recipes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int uid;

    @Column(name = "category", length = 50, nullable = false)
    private String category;

    @Column(name = "numPortions", nullable = false)
    private int numPortions;

    @Column(name = "description", nullable = false)
    private String description;

    @ManyToOne
    @JoinColumn(name = "accountId", nullable = false
            , foreignKey = @ForeignKey(name = "fkAccountId"))
    @JsonProperty (access = JsonProperty.Access.WRITE_ONLY)
    private Account account;
}
