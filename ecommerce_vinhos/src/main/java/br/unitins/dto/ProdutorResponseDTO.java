package br.unitins.dto;

import br.unitins.model.Produtor;

public record ProdutorResponseDTO(
    String nome,
    String pais) {
        public ProdutorResponseDTO(Produtor produtor) {
            this(produtor.getNome(),produtor.getPais()); 
        }
}
