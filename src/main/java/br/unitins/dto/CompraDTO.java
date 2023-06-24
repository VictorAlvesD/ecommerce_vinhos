package br.unitins.dto;

import java.time.LocalDate;

import io.smallrye.common.constraint.NotNull;

public record CompraDTO(
        @NotNull
        LocalDate data,
        Double totalCompra,
        @NotNull
        Long itemcompra,
        @NotNull
        Long usuario)  {
}
