package br.unitins.model;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum StatusPagamento {
    PENDENTE(1, "Pendente"),
    PAGO(2, "Pago"),
    CANCELADO(3, "Cancelado");

    private int id;
    private String label;

    StatusPagamento(int id, String label) {
        this.id = id;
        this.label = label;
    }

    public int getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public static StatusPagamento valueOf(Integer id) throws IllegalArgumentException {
        if (id == null)
            return null;
        for (StatusPagamento status : StatusPagamento.values()) {
            if (id.equals(status.getId()))
                return status;
        }
        throw new IllegalArgumentException("Id inválido: " + id);
    }
}

