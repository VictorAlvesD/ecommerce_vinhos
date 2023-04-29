package br.unitins.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public record TelefoneDTO(
    @NotBlank
    String codigoArea,
    @NotBlank
    @Pattern(regexp = "\\d{5}-\\d{4}", message = "O Número deve estar no formato 99999-9999")
    String numero
) {
    
}
