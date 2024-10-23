package br.edu.fema.gympro.service;

import br.edu.fema.gympro.domain.Aula;
import br.edu.fema.gympro.domain.Funcionario;
import br.edu.fema.gympro.domain.Modalidade;
import br.edu.fema.gympro.dto.aula.AulaCreateDTO;
import br.edu.fema.gympro.dto.aula.AulaResponseDTO;
import br.edu.fema.gympro.exception.domain.ObjetoNaoEncontrado;
import br.edu.fema.gympro.repository.AulaRepository;
import br.edu.fema.gympro.util.mapper.AulaMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.List;

@Service
public class AulaService {
    private final AulaRepository aulaRepository;
    private final AulaMapper aulaMapper;
    private final ModalidadeService modalidadeService;
    private final FuncionarioService funcionarioService;

    public AulaService(AulaRepository aulaRepository,
                       AulaMapper aulaMapper,
                       ModalidadeService modalidadeService,
                       FuncionarioService funcionarioService) {
        this.aulaRepository = aulaRepository;
        this.aulaMapper = aulaMapper;
        this.modalidadeService = modalidadeService;
        this.funcionarioService = funcionarioService;
    }

    public List<AulaResponseDTO> findAll() {
        return aulaRepository.findAll().stream()
                .map(aulaMapper::toAulaResponseDTO)
                .toList();
    }

    public AulaResponseDTO findById(Long id) {
        Aula aula = findAulaOrThrow(id);
        return aulaMapper.toAulaResponseDTO(aula);
    }

    @Transactional
    public AulaResponseDTO save(AulaCreateDTO data) {
        Modalidade modalidade = modalidadeService.findModalidadeOrThrow(data.modalidadeId());
        Funcionario professor = funcionarioService.findFuncionarioOrThrow(data.professorId());

        Aula aula = new Aula();
        aula.setModalidade(modalidade);
        aula.setProfessor(professor);
        aula.setDiaDaSemana(data.diaDaSemana());
        aula.setHorario(LocalTime.parse(data.horario()));

        aulaRepository.save(aula);
        return aulaMapper.toAulaResponseDTO(aula);
    }

    @Transactional
    public AulaResponseDTO update(AulaCreateDTO data, Long id) {
        Aula aula = findAulaOrThrow(id);

        Modalidade modalidade = modalidadeService.findModalidadeOrThrow(data.modalidadeId());
        Funcionario professor = funcionarioService.findFuncionarioOrThrow(data.professorId());

        aula.setModalidade(modalidade);
        aula.setProfessor(professor);
        aula.setDiaDaSemana(data.diaDaSemana());
        aula.setHorario(LocalTime.parse(data.horario()));

        aulaRepository.save(aula);
        return aulaMapper.toAulaResponseDTO(aula);
    }

    @Transactional
    public void deleteById(Long id) {
        if (!aulaRepository.existsById(id)) {
            throw new ObjetoNaoEncontrado("Aula não encontrada!");
        }
        aulaRepository.deleteById(id);
    }

    public Aula findAulaOrThrow(Long id) {
        return aulaRepository.findById(id)
                .orElseThrow(() -> new ObjetoNaoEncontrado("Aula não encontrada!"));
    }
}
