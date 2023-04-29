package br.unitins.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public record EstadoDTO(
        @NotBlank(message = "O campo nome deve ser informado.") String nome,
        @Size(min = 2, max = 2)
        @NotBlank(message = "O campo sigla deve ser informado.") String sigla) {

}
