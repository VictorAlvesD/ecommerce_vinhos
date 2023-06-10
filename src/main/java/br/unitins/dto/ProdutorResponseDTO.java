package br.unitins.dto;

import br.unitins.model.Produtor;

public record ProdutorResponseDTO(
    Long id,
    String nome,
    String pais
) {
    public ProdutorResponseDTO (Produtor produtor){
        this(produtor.getId(), produtor.getNome(), produtor.getPais());
    }
    public static ProdutorResponseDTO valueOf(Produtor produtor) {
        if (produtor == null)
            return null;

        return new ProdutorResponseDTO(
            produtor.getId(),
            produtor.getNome(),
            produtor.getPais()
        );
    }
}

