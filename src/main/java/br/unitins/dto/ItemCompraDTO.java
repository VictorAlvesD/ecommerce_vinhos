package br.unitins.dto;

import java.util.List;

import br.unitins.model.Produto;
import io.smallrye.common.constraint.NotNull;

public record ItemCompraDTO(
    @NotNull
    Long produto,
    int quantidade
) {
}

