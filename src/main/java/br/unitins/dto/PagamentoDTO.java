package br.unitins.dto;

import br.unitins.model.Compra;
import jakarta.validation.constraints.NotBlank;

public record PagamentoDTO(
    @NotBlank
    String valor,
    @NotBlank
    Compra compra
) {
}

