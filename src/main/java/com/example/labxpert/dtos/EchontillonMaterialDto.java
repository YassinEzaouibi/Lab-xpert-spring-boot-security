package com.example.labxpert.dtos;

import com.example.labxpert.model.EchontillonMaterial;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * DTO for {@link EchontillonMaterial}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EchontillonMaterialDto implements Serializable {

    Long id;

    @NotNull
    EchontillonDto echontillon;

    @NotNull
    MaterialDto material;

    @Min(1)
    int quantity;

    double priceTotal;

    @JsonIgnore
    @Builder.Default
    Boolean deleted = false;
}