package br.edu.fema.gympro.service;

import br.edu.fema.gympro.domain.*;
import br.edu.fema.gympro.dto.aula.AulaCreateDTO;
import br.edu.fema.gympro.dto.aula.AulaDetailsDTO;
import br.edu.fema.gympro.dto.aula.AulaResponseDTO;
import br.edu.fema.gympro.dto.aula.AulaUpdateDTO;
import br.edu.fema.gympro.exception.domain.InscricoesExcedidasException;
import br.edu.fema.gympro.exception.domain.ObjetoNaoEncontrado;
import br.edu.fema.gympro.repository.AulaRepository;
import br.edu.fema.gympro.repository.ClienteRepository;
import br.edu.fema.gympro.repository.InscricaoAulaRepository;
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
    private final InscricaoAulaRepository inscricaoAulaRepository;
    private final ClienteRepository clienteRepository;

    public AulaService(AulaRepository aulaRepository,
                       AulaMapper aulaMapper,
                       ModalidadeService modalidadeService,
                       FuncionarioService funcionarioService, InscricaoAulaRepository inscricaoAulaRepository, ClienteRepository clienteRepository) {
        this.aulaRepository = aulaRepository;
        this.aulaMapper = aulaMapper;
        this.modalidadeService = modalidadeService;
        this.funcionarioService = funcionarioService;
        this.inscricaoAulaRepository = inscricaoAulaRepository;
        this.clienteRepository = clienteRepository;
    }

    public List<AulaResponseDTO> findAll() {
        return aulaRepository.findAll().stream()
                .map(aulaMapper::toAulaResponseDTO)
                .toList();
    }

    public List<AulaResponseDTO> findAulasComVagas() {
        return aulaRepository.findAulasComVagas().stream()
                .map(aulaMapper::toAulaResponseDTO)
                .toList();
    }

    public AulaDetailsDTO findById(Long id) {
        Aula aula = findAulaOrThrow(id);
        List<String> alunosInscritos = inscricaoAulaRepository.listarAlunosInscritos(id);
        return new AulaDetailsDTO(aula.getId(),
                aula.getModalidade().getDescricao(),
                aula.getProfessor().getNome(),
                aula.getDiaDaSemana().toString(),
                aula.getHorario().toString(),
                aula.getNumeroInscricoes(),
                aula.getMaximoInscricoes(),
                alunosInscritos);
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
        aula.setMaximoInscricoes(data.maximoInscricoes());
        aula.setNumeroInscricoes(0);

        aulaRepository.save(aula);
        return aulaMapper.toAulaResponseDTO(aula);
    }

    @Transactional
    public AulaResponseDTO update(AulaUpdateDTO data, Long id) {
        Aula aula = findAulaOrThrow(id);

        Modalidade modalidade = modalidadeService.findModalidadeOrThrow(data.modalidadeId());
        Funcionario professor = funcionarioService.findFuncionarioOrThrow(data.professorId());

        aula.setModalidade(modalidade);
        aula.setProfessor(professor);
        aula.setDiaDaSemana(data.diaDaSemana());
        aula.setHorario(LocalTime.parse(data.horario()));
        if(data.maximoInscricoes() < aula.getNumeroInscricoes()){
            throw new InscricoesExcedidasException("Numero de inscrições maior que o limite definido!");
        }
        aula.setMaximoInscricoes(data.maximoInscricoes());

        aulaRepository.save(aula);
        return aulaMapper.toAulaResponseDTO(aula);
    }

    @Transactional
    public void deleteById(Long id) {
        if (!aulaRepository.existsById(id)) {
            throw new ObjetoNaoEncontrado("Aula não encontrada!");
        }
        Aula aula = findAulaOrThrow(id);
        List<InscricaoAula> inscricoes = inscricaoAulaRepository.findInscricaoAulaByAula(aula);
        for(InscricaoAula inscricao : inscricoes) {
            Cliente cliente = inscricao.getCliente();
            cliente.setNumeroIncricoesAtivas(cliente.getNumeroIncricoesAtivas() - 1);
            clienteRepository.save(cliente);
        }
        inscricaoAulaRepository.deleteAll(inscricoes);
        aulaRepository.deleteById(id);
    }

    public Aula findAulaOrThrow(Long id) {
        return aulaRepository.findById(id)
                .orElseThrow(() -> new ObjetoNaoEncontrado("Aula não encontrada!"));
    }
}
