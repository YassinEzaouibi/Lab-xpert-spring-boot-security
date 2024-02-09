package com.example.labxpert.dtos;

import com.example.labxpert.model.enums.StatusResult;
import com.example.labxpert.model.SousAnalyse;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * DTO for {@link SousAnalyse}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SousAnalyseDto implements Serializable {

    Long id;

    @NotBlank
    String title;

    @NotNull
    double etatNormalMax;

    @NotNull
    double etatNormalMin;

    @JsonIgnoreProperties(value = "sousAnalyses")
    @NotNull
    AnalyseDto analyse;

    @NotNull
    ReactifDto reactif;

    StatusResult statusResult;

    @JsonIgnore
    @Builder.Default
    Boolean deleted = false;
}