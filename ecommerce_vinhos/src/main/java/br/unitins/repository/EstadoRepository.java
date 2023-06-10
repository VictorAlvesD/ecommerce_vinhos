package br.unitins.repository;

import java.util.List;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import br.unitins.model.Cidade;
import br.unitins.model.Estado;

@ApplicationScoped
public class EstadoRepository implements PanacheRepository<Estado> {
    public List<Estado> findBySigla(String sigla) {
        if (sigla == null)
            return null;
        return find("UPPER(nome) LIKE ?1 ", "%" + sigla.toUpperCase() + "%").list();
    }
    public Estado findByID(Long integer) {
        if (integer == null)
            return null;
        return  find("id = ?1", integer).firstResult();
    }
    public Cidade findById(Integer cidade) {
        return null;
    }
}
