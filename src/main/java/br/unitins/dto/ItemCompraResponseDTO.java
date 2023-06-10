package br.unitins.dto;

import java.time.LocalDate;

import br.unitins.model.ItemCompra;

public record ItemCompraResponseDTO(
    int quantidade,
    LocalDate dataCompra
) {
    public static ItemCompraResponseDTO valueOf(ItemCompra itemCompra) {
        if (itemCompra == null)
            return null;

        return new ItemCompraResponseDTO(
            itemCompra.getQuantidade(),
            itemCompra.getDataCompra()
        );
    }
}


