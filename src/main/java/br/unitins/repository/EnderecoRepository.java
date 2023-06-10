package br.unitins.repository;

import java.util.List;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import br.unitins.model.Endereco;

@ApplicationScoped
public class EnderecoRepository implements PanacheRepository<Endereco> {
    public List<Endereco> findByCep(String cep) {
        if (cep == null)
            return null;
        return find("UPPER(cep) LIKE ?1 ", "%" + cep.toUpperCase() + "%").list();
    }

    public List<Endereco> findById(Integer id) {
        if (id == null)
            return null;
        return find("id = ?1", id).firstResult();
    }
}
