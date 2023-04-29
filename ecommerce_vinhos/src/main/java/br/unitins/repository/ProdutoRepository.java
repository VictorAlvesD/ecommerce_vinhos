package br.unitins.repository;
import java.util.List;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import javax.enterprise.context.ApplicationScoped;
import br.unitins.model.Produto;

@ApplicationScoped
public class ProdutoRepository implements PanacheRepository<Produto>{
    public List<Produto> findByNome(String nome) {
        if (nome == null)
            return null;
        return find("UPPER(nome) LIKE ?1 ", "%" + nome.toUpperCase() + "%").list();
    }
    public Produto findByID(Integer id) {
        if (id == null)
            return null;
        return  find("id = ?1", id).firstResult();
    }
}
