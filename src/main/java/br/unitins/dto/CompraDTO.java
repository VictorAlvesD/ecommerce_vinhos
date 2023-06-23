package br.unitins.dto;

import jakarta.validation.constraints.NotBlank;

public record CompraDTO(
        @NotBlank(message = "O campo itemCompra deve ser informado.") ItemCompraDTO itemCompra,
        @NotBlank(message = "O campo pagamento deve ser informado.") PagamentoDTO pagamento,
        EnderecoDTO endereco,
        @NotBlank(message = "O campo usuario deve ser informado.") UsuarioDTO usuario,
        Integer statusPagamento,
        @NotBlank(message = "O campo tipoPagamento deve ser informado.") Integer tipoPagamento) {
}
