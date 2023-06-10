package br.unitins.dto;

import jakarta.validation.constraints.NotBlank;

public record PagamentoDTO(
    String statusPagamento,
    @NotBlank(message = "O campo tipo de pagamento deve ser informado.")
    String tipoPagamento,
    @NotBlank(message = "O campo quantidade de parcelas deve ser informado.")
    int quantidadeParcelas,
    float valorParcelas
) {
}

