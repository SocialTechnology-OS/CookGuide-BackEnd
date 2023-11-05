package com.cookguide.database.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Health_info {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long uid;

    @Column(name = "imc", nullable = false)
    private int imc;

    @Column(name = "weight", nullable = false)
    private float weight;

    @Column(name = "height", nullable = false)
    private float height;
}
