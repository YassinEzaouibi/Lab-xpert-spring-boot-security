package com.example.labxpert.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "reactifs")
public class Reactif {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private String description;
    private int quantityStock;
    private LocalDate dateExp;


    @ManyToMany(fetch = FetchType.LAZY)
    private List<Fournisseur> fournisseurs = new ArrayList<>();

    @JsonIgnore
    @Column(name = "is_deleted")
    private Boolean deleted;
}
