package br.edu.fema.gympro.service.impl;

import br.edu.fema.gympro.domain.Plano;
import br.edu.fema.gympro.dto.PlanoRequestDTO;
import br.edu.fema.gympro.dto.PlanoResponseDTO;
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
                .map(p -> new PlanoResponseDTO(p.getId(), p.getDescricao(), p.getMaximoInscricoes()))
                .toList();
    }

    @Override
    public PlanoResponseDTO findById(Long id) {
        Plano plano = findPlanoOrThrow(id);
        return new PlanoResponseDTO(plano.getId(), plano.getDescricao(), plano.getMaximoInscricoes());
    }

    @Transactional
    @Override
    public PlanoResponseDTO save(PlanoRequestDTO data) {
        Plano plano = new Plano();
        plano.setDescricao(data.descricao());
        plano.setMaximoInscricoes(data.maximoInscricoes());
        planoRepository.save(plano);
        return new PlanoResponseDTO(plano.getId(), plano.getDescricao(), plano.getMaximoInscricoes());
    }

    @Transactional
    @Override
    public PlanoResponseDTO update(PlanoRequestDTO data, Long id) {
        Plano plano = findPlanoOrThrow(id);
        plano.setDescricao(data.descricao());
        plano.setMaximoInscricoes(data.maximoInscricoes());
        planoRepository.save(plano);
        return new PlanoResponseDTO(plano.getId(), plano.getDescricao(), plano.getMaximoInscricoes());
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
