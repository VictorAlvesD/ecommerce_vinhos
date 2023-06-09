package br.unitins.repository;

import java.util.List;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import br.unitins.model.Vinho;

@ApplicationScoped
public class VinhoRepository implements PanacheRepository<Vinho>{
    public List<Vinho> findByNome(String nome) {
        if (nome == null)
            return null;
        return find("UPPER(nome) LIKE ?1 ", "%" + nome.toUpperCase() + "%").list();
    }
}
