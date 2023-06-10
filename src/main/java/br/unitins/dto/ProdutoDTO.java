package br.unitins.dto;

import br.unitins.model.Produto;
import jakarta.validation.constraints.NotBlank;

public record ProdutoDTO(
    @NotBlank(message = "O campo nome deve ser informado.")
    String nome,
    String descricao,
    @NotBlank(message = "O campo preco deve ser informado.")
    Double preco,
    @NotBlank(message = "O campo quantidade em estoque deve ser informado.")
    Integer quantidadeEstoque
) {
   
}

