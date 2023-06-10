package br.unitins.dto;

import java.util.List;

import br.unitins.model.Produto;
import jakarta.validation.constraints.NotBlank;

public record ItemCompraDTO(
    @NotBlank(message = "O campo produto deve ser informado.")
    Produto produto,
    int quantidade
) {
}

