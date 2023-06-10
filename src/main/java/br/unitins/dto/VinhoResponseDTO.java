package br.unitins.dto;

import br.unitins.model.Produtor;
import br.unitins.model.TipoVinho;
import br.unitins.model.Vinho;

public record VinhoResponseDTO (
    String nome,
    Double preco,
    String teorAlcoolico,
    String tipoUva,
    TipoVinho tipoVinho
) {
    public VinhoResponseDTO (Vinho vinho) {
        this(
        vinho.getNome(),
        vinho.getPreco(),
        vinho.getTeorAlcoolico(),
        vinho.getTipoUva(),
        vinho.getTipoVinho()
    );
    }

    public static VinhoResponseDTO valueOf(Vinho vinho) {
        if (vinho == null)
            return null;


        return new VinhoResponseDTO(
            vinho.getNome(),
            vinho.getPreco(),
            vinho.getTeorAlcoolico(),
            vinho.getTipoUva(),
            vinho.getTipoVinho()
        );
    }

    public Object id() {
        return null;
    }
}

