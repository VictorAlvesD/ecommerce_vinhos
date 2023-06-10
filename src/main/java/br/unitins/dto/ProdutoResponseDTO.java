package br.unitins.dto;

import java.util.ArrayList;
import java.util.List;

import br.unitins.model.Produto;

public record ProdutoResponseDTO(
        Long id,
        String nome,
        String descricao,
        Double preco,
        Integer quantidadeEstoque) {
    public static List<ProdutoResponseDTO> valueOf(List<Produto> listaProdutos) {
        if (listaProdutos == null)
            return null;

        List<ProdutoResponseDTO> responseList = new ArrayList<>();

        for (Produto produto : listaProdutos) {
            ProdutoResponseDTO responseDTO = new ProdutoResponseDTO(
                    produto.getId(),
                    produto.getNome(),
                    produto.getDescricao(),
                    produto.getPreco(),
                    produto.getEstoque());
            responseList.add(responseDTO);
        }

        return responseList;
    }

}
