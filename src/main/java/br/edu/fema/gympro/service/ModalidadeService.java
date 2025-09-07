package br.edu.fema.gympro.service;

import br.edu.fema.gympro.domain.Aula;
import br.edu.fema.gympro.domain.Modalidade;
import br.edu.fema.gympro.dto.modalidade.ModalidadeCreateDTO;
import br.edu.fema.gympro.dto.modalidade.ModalidadeResponseDTO;
import br.edu.fema.gympro.dto.modalidade.ModalidadeUpdateDTO;
import br.edu.fema.gympro.repository.AulaRepository;
import br.edu.fema.gympro.repository.ModalidadeRepository;
import br.edu.fema.gympro.exception.domain.ObjetoNaoEncontrado;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ModalidadeService {

    private final ModalidadeRepository modalidadeRepository;
    private final AulaRepository aulaRepository;

    public ModalidadeService(ModalidadeRepository modalidadeRepository, AulaRepository aulaRepository) {
        this.modalidadeRepository = modalidadeRepository;
        this.aulaRepository = aulaRepository;
    }

    public List<ModalidadeResponseDTO> findAll() {
        return modalidadeRepository.findAll().stream()
                .map(m -> new ModalidadeResponseDTO(m.getId(), m.getDescricao()))
                .toList();
    }

    public ModalidadeResponseDTO findById(Long id) {
        Modalidade modalidade = findModalidadeOrThrow(id);
        return new ModalidadeResponseDTO(modalidade.getId(), modalidade.getDescricao());
    }

    @Transactional
    public ModalidadeResponseDTO save(ModalidadeCreateDTO data) {
        Modalidade modalidade = new Modalidade();
        modalidade.setDescricao(data.descricao());
        modalidadeRepository.save(modalidade);
        return new ModalidadeResponseDTO(modalidade.getId(), modalidade.getDescricao());
    }

    @Transactional
    public ModalidadeResponseDTO update(ModalidadeUpdateDTO data, Long id) {
        Modalidade modalidade = findModalidadeOrThrow(id);
        modalidade.setDescricao(data.descricao());
        modalidadeRepository.save(modalidade);
        return new ModalidadeResponseDTO(modalidade.getId(), modalidade.getDescricao());
    }

    @Transactional
    public void deleteById(Long id) {
        Optional<Modalidade> modalidadeOptional = modalidadeRepository.findById(id);
        if (modalidadeOptional.isEmpty()) {
            throw new ObjetoNaoEncontrado("Modalidade não encontrada!");
        }

        List<Aula> aulasDaModalidade = aulaRepository.findByModalidade(modalidadeOptional.get());
        for(Aula aula : aulasDaModalidade) {
            aula.setModalidade(null);
            aulaRepository.save(aula);
        }

        modalidadeRepository.deleteById(id);
    }

    public Modalidade findModalidadeOrThrow(Long id) {
        return modalidadeRepository.findById(id)
                .orElseThrow(() -> new ObjetoNaoEncontrado("Modalidade não encontrada!"));
    }
}
