package br.unitins.dto;

import br.unitins.model.Pagamento;
import br.unitins.model.StatusPagamento;
import br.unitins.model.TipoPagamento;

public record PagamentoResponseDTO(
    StatusPagamento statusPagamento,
    TipoPagamento tipoPagamento,
    int quantidadeParcelas,
    float valorParcelas
) {
    public PagamentoResponseDTO(Pagamento pagamento) {
        this(pagamento.getStatusPagamento(),pagamento.getTipoPagamento(),pagamento.getQuantidadeParcelas(),pagamento.getQuantidadeParcelas());
    }

    public static PagamentoResponseDTO valueOf(Pagamento pagamento) {
    if (pagamento == null)
        return null;

    return new PagamentoResponseDTO(
        pagamento.getStatusPagamento(),
        pagamento.getTipoPagamento(),
        pagamento.getQuantidadeParcelas(),
        pagamento.getValorParcelas()
    );
}
}
