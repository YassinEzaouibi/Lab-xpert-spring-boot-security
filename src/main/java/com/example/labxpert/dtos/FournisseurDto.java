package com.example.labxpert.dtos;

import com.example.labxpert.model.Fournisseur;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;

/**
 * DTO for {@link Fournisseur}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FournisseurDto implements Serializable {
    Long id;

    @NotBlank
    String nom;

    @NotBlank
    String societeName;

    @JsonIgnore
    @Builder.Default
    Boolean deleted = false;

    @JsonIgnoreProperties(value = "fournisseurs")
    List<ReactifDto> reactifs;
}