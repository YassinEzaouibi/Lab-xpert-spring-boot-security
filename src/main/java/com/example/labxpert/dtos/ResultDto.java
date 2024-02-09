package com.example.labxpert.dtos;

import com.example.labxpert.model.Result;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * DTO for {@link Result}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResultDto implements Serializable {
    Long id;

    @NotNull
    double valeurResult;

    @NotBlank
    String uniteMesure;

    @NotNull
    SousAnalyseDto sousAnalyse;

    @JsonIgnore
    @Builder.Default
    Boolean deleted = false;
}