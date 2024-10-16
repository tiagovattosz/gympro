package br.edu.fema.gympro.service.impl;

import br.edu.fema.gympro.domain.Plano;
import br.edu.fema.gympro.dto.plano.PlanoRequestDTO;
import br.edu.fema.gympro.dto.plano.PlanoResponseDTO;
import br.edu.fema.gympro.repository.PlanoRepository;
import br.edu.fema.gympro.service.PlanoService;
import br.edu.fema.gympro.exception.domain.ObjetoNaoEncontrado;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PlanoServiceImpl implements PlanoService {
    private final PlanoRepository planoRepository;

    public PlanoServiceImpl(PlanoRepository planoRepository) {
        this.planoRepository = planoRepository;
    }

    @Override
    public List<PlanoResponseDTO> findAll() {
        return planoRepository.findAll().stream()
                .map(plano -> new PlanoResponseDTO(plano.getId(), plano.getDescricao(), plano.getValor(), plano.getMaximoInscricoes(), plano.getDetalhes()))
                .toList();
    }

    @Override
    public PlanoResponseDTO findById(Long id) {
        Plano plano = findPlanoOrThrow(id);
        return new PlanoResponseDTO(plano.getId(), plano.getDescricao(), plano.getValor(), plano.getMaximoInscricoes(), plano.getDetalhes());
    }

    @Transactional
    @Override
    public PlanoResponseDTO save(PlanoRequestDTO data) {
        Plano plano = new Plano();
        plano.setDescricao(data.descricao());
        plano.setValor(data.valor());
        plano.setMaximoInscricoes(data.maximoInscricoes());
        plano.setDuracaoEmMeses(data.duracaoEmMeses());
        plano.setDetalhes(data.detalhes());
        planoRepository.save(plano);
        return new PlanoResponseDTO(plano.getId(), plano.getDescricao(), plano.getValor(), plano.getMaximoInscricoes(), plano.getDetalhes());
    }

    @Transactional
    @Override
    public PlanoResponseDTO update(PlanoRequestDTO data, Long id) {
        Plano plano = findPlanoOrThrow(id);
        plano.setDescricao(data.descricao());
        plano.setValor(data.valor());
        plano.setMaximoInscricoes(data.maximoInscricoes());
        plano.setDuracaoEmMeses(data.duracaoEmMeses());
        plano.setDetalhes(data.detalhes());
        planoRepository.save(plano);
        return new PlanoResponseDTO(plano.getId(), plano.getDescricao(), plano.getValor(), plano.getMaximoInscricoes(), plano.getDetalhes());
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        if (!planoRepository.existsById(id)) {
            throw new ObjetoNaoEncontrado("Plano não encontrado!");
        }
        planoRepository.deleteById(id);
    }

    private Plano findPlanoOrThrow(Long id) {
        return planoRepository.findById(id)
                .orElseThrow(() -> new ObjetoNaoEncontrado("Plano não encontrado!"));
    }
}
