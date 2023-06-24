package br.unitins.dto;

import java.time.LocalDate;

import br.unitins.model.ItemCompra;

public record ItemCompraResponseDTO(
    Long id,
    int quantidade,
    LocalDate dataCompra
) {
    public  ItemCompraResponseDTO (ItemCompra itemCompra) {
        this(itemCompra.getId(), itemCompra.getQuantidade(), itemCompra.getDataCompra());
    }
}


