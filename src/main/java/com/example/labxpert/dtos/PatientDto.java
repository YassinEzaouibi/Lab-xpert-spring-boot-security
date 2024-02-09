package com.example.labxpert.dtos;

import com.example.labxpert.model.enums.Sexe;
import com.example.labxpert.model.Patient;
import com.fasterxml.jackson.annotation.*;
import lombok.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import javax.validation.constraints.*;

/**
 * DTO for {@link Patient}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PatientDto implements Serializable {
    Long id;

    @NotNull
    String nom;

    @NotNull
    String prenom;

    @NotNull
    @Size(min = 5)
    String address;

    @NotNull
    @Pattern(regexp = "^\\+?[0-9. ()-]{8,}$")
    String tel;

    @NotNull
    String ville;

    @NotNull
    Sexe sexe;

    @Past
    LocalDate dateNaissance;

    @Min(18)
    @Max(80)
    double age;

    @JsonIgnore
    @Builder.Default
    Boolean deleted = false;

    @JsonIgnoreProperties(value = "patient")
    List<EchontillonDto> echontillons;
}