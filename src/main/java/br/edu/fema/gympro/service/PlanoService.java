package br.edu.fema.gympro.service;

import br.edu.fema.gympro.domain.Plano;
import br.edu.fema.gympro.dto.plano.PlanoRequestDTO;
import br.edu.fema.gympro.dto.plano.PlanoResponseDTO;
import br.edu.fema.gympro.repository.ClienteRepository;
import br.edu.fema.gympro.repository.PlanoRepository;
import br.edu.fema.gympro.exception.domain.ObjetoNaoEncontrado;
import br.edu.fema.gympro.util.mapper.PlanoMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PlanoService {
    private final PlanoRepository planoRepository;
    private final PlanoMapper planoMapper;
    private final ClienteRepository clienteRepository;

    public PlanoService(PlanoRepository planoRepository, PlanoMapper planoMapper, ClienteRepository clienteRepository) {
        this.planoRepository = planoRepository;
        this.planoMapper = planoMapper;
        this.clienteRepository = clienteRepository;
    }

    public List<PlanoResponseDTO> findAll() {
        return planoRepository.findAll().stream()
                .map(planoMapper::toPlanoResponseDTO)
                .toList();
    }

    public PlanoResponseDTO findById(Long id) {
        Plano plano = findPlanoOrThrow(id);
        return planoMapper.toPlanoResponseDTO(plano);
    }

    @Transactional
    public PlanoResponseDTO save(PlanoRequestDTO data) {
        Plano plano = new Plano();
        plano.setDescricao(data.descricao());
        plano.setValor(data.valor());
        plano.setMaximoInscricoes(data.maximoInscricoes());
        plano.setDuracaoEmMeses(data.duracaoEmMeses());
        plano.setDetalhes(data.detalhes());
        planoRepository.save(plano);
        return planoMapper.toPlanoResponseDTO(plano);
    }

    @Transactional
    public PlanoResponseDTO update(PlanoRequestDTO data, Long id) {
        Plano plano = findPlanoOrThrow(id);
        plano.setDescricao(data.descricao());
        plano.setValor(data.valor());
        plano.setMaximoInscricoes(data.maximoInscricoes());
        plano.setDuracaoEmMeses(data.duracaoEmMeses());
        plano.setDetalhes(data.detalhes());
        planoRepository.save(plano);
        return planoMapper.toPlanoResponseDTO(plano);
    }

    @Transactional
    public void deleteById(Long id) {
        if (!planoRepository.existsById(id)) {
            throw new ObjetoNaoEncontrado("Plano não encontrado!");
        }
        Plano plano = findPlanoOrThrow(id);
        if(clienteRepository.existsByPlano(plano)) {
            throw new DataIntegrityViolationException("O plano possui clientes inscritos.");
        }
        planoRepository.deleteById(id);
    }

    public Plano findPlanoOrThrow(Long id) {
        return planoRepository.findById(id)
                .orElseThrow(() -> new ObjetoNaoEncontrado("Plano não encontrado!"));
    }
}
