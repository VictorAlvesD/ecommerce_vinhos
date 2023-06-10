package br.unitins.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import br.unitins.model.Compra;
import br.unitins.model.StatusPagamento;

public record CompraResponseDTO(
        ItemCompraResponseDTO itemCompra,
        PagamentoResponseDTO pagamento,
        StatusPagamento statusPagamento) {
    
    public static CompraResponseDTO valueOf(Compra compra) {
        if (compra == null)
            return null;

        ItemCompraResponseDTO itemCompra = ItemCompraResponseDTO.valueOf(compra.getItens());
        PagamentoResponseDTO pagamento = PagamentoResponseDTO.valueOf(compra.getPagamento());

        return new CompraResponseDTO(itemCompra, pagamento, compra.getStatus());
    }

    public Object id() {
        return null;
    }
}
