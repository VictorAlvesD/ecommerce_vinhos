package br.unitins.model;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum TipoPagamento {
    BOLETO(1, "Boleto"),
    PIX(2, "Pix"),
    CREDITO(3, "Crédito"),
    DEBITO(4, "Débito");

    private int id;
    private String label;

    TipoPagamento(int id, String label) {
        this.id = id;
        this.label = label;
    }

    public int getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public static TipoPagamento valueOf(Integer id) throws IllegalArgumentException {
        if (id == null)
            return null;
        for (TipoPagamento tipo : TipoPagamento.values()) {
            if (id.equals(tipo.getId()))
                return tipo;
        }
        throw new IllegalArgumentException("Id inválido: " + id);
    }
}
