package com.example.labxpert.model;

import com.example.labxpert.model.enums.StatusAnalyse;
import com.example.labxpert.model.enums.StatusResult;
import com.example.labxpert.model.enums.TypeAnalyse;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "analyses")
public class Analyse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User technicienResponsable;

    @Enumerated(EnumType.STRING)
    private StatusResult statusResult;

    @Enumerated(EnumType.STRING)
    private TypeAnalyse typeAnalyse;

    private LocalDate dateDebut;
    private LocalDate dateFin;

    private String commantaires;

    @Enumerated(EnumType.STRING)
    private StatusAnalyse statusAnalyse;

    @JsonIgnore
    @Column(name = "is_deleted")
    private Boolean deleted;

    @ToString.Exclude
    @OneToMany(mappedBy = "analyse", fetch = FetchType.EAGER)
    private List<SousAnalyse> sousAnalyses = new ArrayList<>();
}
