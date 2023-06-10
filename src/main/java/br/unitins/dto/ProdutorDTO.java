package br.unitins.dto;

import br.unitins.model.Produtor;
import jakarta.validation.constraints.NotBlank;

public record ProdutorDTO(
        @NotBlank(message = "O campo nome deve ser informado.") String nome,
        @NotBlank(message = "O campo nome deve ser informado.") String pais) {
        public static ProdutorDTO valueOf(Produtor produtor) {
        if (produtor == null)
            return null;

        return new ProdutorDTO(
            produtor.getNome(),
            produtor.getPais()
        );
    }


    
}
