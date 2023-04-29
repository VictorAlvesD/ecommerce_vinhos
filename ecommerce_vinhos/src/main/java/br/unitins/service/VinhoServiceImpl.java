package br.unitins.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import javax.ws.rs.NotFoundException;

import br.unitins.dto.VinhoDTO;
import br.unitins.dto.VinhoResponseDTO;
import br.unitins.model.Vinho;
import br.unitins.model.TipoVinho;
import br.unitins.repository.ProdutorRepository;
import br.unitins.repository.VinhoRepository;

@ApplicationScoped
public class VinhoServiceImpl implements VinhoService{
    @Inject
    VinhoRepository vinhoRepository;

    @Inject
    ProdutorRepository produtorRepository;

    @Inject
    Validator validator;

    @Override
    public List<VinhoResponseDTO> getAll() {
        List<Vinho> list = vinhoRepository.listAll();
        return list.stream().map(VinhoResponseDTO::new).collect(Collectors.toList());
    }

    @Override
    public VinhoResponseDTO findById(Long id) {
        Vinho pessoafisica = vinhoRepository.findById(id);
        if (pessoafisica == null)
            throw new NotFoundException("Vinho não encontrado!");
        return new VinhoResponseDTO(pessoafisica);
    }

    @Override
    @Transactional
    public VinhoResponseDTO insert(VinhoDTO vinhoDTO) throws ConstraintViolationException {
        validar(vinhoDTO);

        Vinho entity = new Vinho();
        entity.setNome(vinhoDTO.nome());
        entity.setPreco(vinhoDTO.preco());
        entity.setEstoque(vinhoDTO.estoque());
        entity.setTeorAlcoolico(vinhoDTO.teorAlcoolico());
        entity.setTipoUva(vinhoDTO.tipoUva());
        entity.setDescricao(vinhoDTO.descricao());
        entity.setTipoVinho(TipoVinho.valueOf(vinhoDTO.tipoVinho()));
        entity.setProdutor(produtorRepository.findById(vinhoDTO.produtor().longValue()));

        vinhoRepository.persist(entity);

        return new VinhoResponseDTO(entity);
    }

    @Override
    @Transactional
    public VinhoResponseDTO update(Long id, VinhoDTO vinhoDTO) throws ConstraintViolationException{
        validar(vinhoDTO);
   
        Vinho entity = vinhoRepository.findById(id);
        entity.setNome(vinhoDTO.nome());
        entity.setPreco(vinhoDTO.preco());
        entity.setEstoque(vinhoDTO.estoque());
        entity.setTeorAlcoolico(vinhoDTO.teorAlcoolico());
        entity.setTipoUva(vinhoDTO.tipoUva());
        entity.setDescricao(vinhoDTO.descricao());
        entity.setTipoVinho(TipoVinho.valueOf(vinhoDTO.tipoVinho()));
        entity.setProdutor(produtorRepository.findById(vinhoDTO.produtor().longValue()));

        return new VinhoResponseDTO(entity);
    }

    private void validar(VinhoDTO vinhoDTO) throws ConstraintViolationException {
        Set<ConstraintViolation<VinhoDTO>> violations = validator.validate(vinhoDTO);
        if (!violations.isEmpty())
            throw new ConstraintViolationException(violations);


    }

    @Override
    @Transactional
    public void delete(Long id) {
        vinhoRepository.deleteById(id);
    }

    @Override
    public List<VinhoResponseDTO> findByNome(String nome) {
        List<Vinho> list = vinhoRepository.findByNome(nome);
        return list.stream().map(VinhoResponseDTO::new).collect(Collectors.toList());
    }

    @Override
    public long count() {
        return vinhoRepository.count();
    }

}
