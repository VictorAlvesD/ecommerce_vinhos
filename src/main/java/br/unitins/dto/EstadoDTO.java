package br.unitins.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record EstadoDTO(
        @NotBlank(message = "O campo nome deve ser informado.") 
        String nome,
        
        @Size(min = 2, max = 2)
        @NotBlank(message = "O campo sigla deve ser informado com duas letras.") 
        String sigla) {

}
