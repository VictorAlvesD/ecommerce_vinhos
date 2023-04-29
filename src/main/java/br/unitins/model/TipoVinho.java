package br.unitins.model;
import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum TipoVinho {
    TINTO(1, "Tinto"),
    BRANCO(2, "Branco"),
    ROSE(3, "Rose"),
    ESPUMANTE(4, "Espumante"),
    LICOROSO(5, "Licoroso");

    private int id;
    private String label;

    TipoVinho(int id, String label) {
        this.id = id;
        this.label = label;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public static TipoVinho valueOf(Integer id) throws IllegalArgumentException {
        if (id == null)
            return null;
        for(TipoVinho vinho : TipoVinho.values()) {
            if (id.equals(vinho.getId()))
                return vinho;
        } 
        throw new IllegalArgumentException("Id inválido:" + id);
    }

    public static TipoVinho valueOf(TipoVinho tipoVinho) {
        return null;
    }

}
