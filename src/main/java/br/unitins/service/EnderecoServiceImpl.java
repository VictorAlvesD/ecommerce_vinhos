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

import br.unitins.dto.EnderecoDTO;
import br.unitins.dto.EnderecoResponseDTO;
import br.unitins.model.Endereco;
import br.unitins.repository.EnderecoRepository;
import br.unitins.repository.EstadoRepository;

@ApplicationScoped
public class EnderecoServiceImpl implements EnderecoService{
    @Inject
    EnderecoRepository enderecoRepository;
    @Inject
    EstadoRepository cidadeRepository;
    @Inject
    Validator validator;

    @Override
    public List<EnderecoResponseDTO> getAll() {
        List<Endereco> list = enderecoRepository.listAll();
        return list.stream().map(EnderecoResponseDTO::new).collect(Collectors.toList());
    }

    @Override
    public EnderecoResponseDTO findById(Long id) {
        Endereco endereco = enderecoRepository.findById(id);
        if (endereco == null)
            throw new NotFoundException("Endereco não encontrado!");
        return new EnderecoResponseDTO(endereco);
    }

    private void validarId(Endereco endereco) throws ConstraintViolationException {
        if (endereco.getId() == null)
            throw new NullPointerException("Id inválido");
    }
    @Override
    @Transactional
    public EnderecoResponseDTO insert(EnderecoDTO enderecoDTO) throws ConstraintViolationException {
        validar(enderecoDTO);

        Endereco entity = new Endereco();
        entity.setNumero(enderecoDTO.numero());
        entity.setRua(enderecoDTO.rua());
        entity.setBairro(enderecoDTO.bairro());
        entity.setCep(enderecoDTO.cep());
        entity.setComplemento(enderecoDTO.complemento());
        

        enderecoRepository.persist(entity);

        return new EnderecoResponseDTO(entity);
    }

    private void validar(EnderecoDTO enderecoDTO) throws ConstraintViolationException {
        Set<ConstraintViolation<EnderecoDTO>> violations = validator.validate(enderecoDTO);
        if (!violations.isEmpty())
            throw new ConstraintViolationException(violations);
    }

    @Override
    @Transactional
    public EnderecoResponseDTO update(Long id, EnderecoDTO enderecoDTO) throws ConstraintViolationException {
        validar(enderecoDTO);

        Endereco entity = enderecoRepository.findById(id);
        validarId(entity);
        entity.setNumero(enderecoDTO.numero());
        entity.setRua(enderecoDTO.rua());
        entity.setBairro(enderecoDTO.bairro());
        entity.setCep(enderecoDTO.cep());
        entity.setComplemento(enderecoDTO.complemento());

        entity.setCidade(cidadeRepository.findById(enderecoDTO.cidade()));

        return new EnderecoResponseDTO(entity);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        validarId(enderecoRepository.findById(id));
        enderecoRepository.deleteById(id);
        
    }

    @Override
    public List<EnderecoResponseDTO> findByCep(String cep) {
        List<Endereco> list = enderecoRepository.findByCep(cep);
        return list.stream().map(EnderecoResponseDTO::new).collect(Collectors.toList());
    }

    @Override
    public long count() {
        return enderecoRepository.count();
    }

}
