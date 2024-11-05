package br.edu.fema.gympro.service;

import br.edu.fema.gympro.domain.Equipamento;
import br.edu.fema.gympro.domain.Funcionario;
import br.edu.fema.gympro.domain.Manutencao;
import br.edu.fema.gympro.domain.enums.Situacao;
import br.edu.fema.gympro.dto.manutencao.ManutencaoCreateDTO;
import br.edu.fema.gympro.dto.manutencao.ManutencaoResponseDTO;
import br.edu.fema.gympro.dto.manutencao.ManutencaoUpdateDTO;
import br.edu.fema.gympro.exception.domain.ManutencaoNaoAceitaException;
import br.edu.fema.gympro.exception.domain.ObjetoNaoEncontrado;
import br.edu.fema.gympro.repository.ManutencaoRepository;
import br.edu.fema.gympro.util.mapper.ManutencaoMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ManutencaoService {
    private final ManutencaoRepository manutencaoRepository;
    private final ManutencaoMapper manutencaoMapper;
    private final FuncionarioService funcionarioService;
    private final EquipamentoService equipamentoService;

    public ManutencaoService(ManutencaoRepository manutencaoRepository,
                             ManutencaoMapper manutencaoMapper,
                             FuncionarioService funcionarioService,
                             EquipamentoService equipamentoService) {
        this.manutencaoRepository = manutencaoRepository;
        this.manutencaoMapper = manutencaoMapper;
        this.funcionarioService = funcionarioService;
        this.equipamentoService = equipamentoService;
    }

    public List<ManutencaoResponseDTO> findAll() {
        return manutencaoRepository.findAll().stream()
                .map(manutencaoMapper::toManutencaoResponseDTO)
                .toList();
    }

    public ManutencaoResponseDTO findById(Long id) {
        Manutencao manutencao = findManutencaoOrThrow(id);
        return manutencaoMapper.toManutencaoResponseDTO(manutencao);
    }

    @Transactional
    public ManutencaoResponseDTO save(ManutencaoCreateDTO data) {
        Funcionario funcionario = funcionarioService.findFuncionarioOrThrow(data.funcionarioId());
        Equipamento equipamento = equipamentoService.findEquipamentoOrThrow(data.equipamentoId());

        Manutencao manutencao = new Manutencao();
        manutencao.setFuncionario(funcionario);
        manutencao.setEquipamento(equipamento);
        manutencao.setDescricao(data.descricao());
        manutencao.setSituacao(Situacao.SOLICITADA);

        manutencaoRepository.save(manutencao);
        return manutencaoMapper.toManutencaoResponseDTO(manutencao);
    }

    @Transactional
    public ManutencaoResponseDTO update(ManutencaoUpdateDTO data, Long id) {
        Manutencao manutencao = findManutencaoOrThrow(id);

        Funcionario funcionario = funcionarioService.findFuncionarioOrThrow(data.funcionarioId());
        Equipamento equipamento = equipamentoService.findEquipamentoOrThrow(data.equipamentoId());

        manutencao.setFuncionario(funcionario);
        manutencao.setEquipamento(equipamento);
        manutencao.setDescricao(data.descricao());
        manutencao.setSituacao(data.situacao());
        manutencao.setRealizada(data.realizada());

        manutencaoRepository.save(manutencao);
        return manutencaoMapper.toManutencaoResponseDTO(manutencao);
    }

    @Transactional
    public void deleteById(Long id) {
        if (!manutencaoRepository.existsById(id)) {
            throw new ObjetoNaoEncontrado("Manutenção não encontrada!");
        }
        manutencaoRepository.deleteById(id);
    }

    public Manutencao findManutencaoOrThrow(Long id) {
        return manutencaoRepository.findById(id)
                .orElseThrow(() -> new ObjetoNaoEncontrado("Manutenção não encontrada!"));
    }

    public ManutencaoResponseDTO aceitarManutencao(Long id) {
        Manutencao manutencao = findManutencaoOrThrow(id);
        manutencao.setSituacao(Situacao.ACEITA);
        manutencao.setDataResposta(LocalDateTime.now());
        manutencaoRepository.save(manutencao);
        return manutencaoMapper.toManutencaoResponseDTO(manutencao);
    }

    public ManutencaoResponseDTO rejeitarManutencao(Long id) {
        Manutencao manutencao = findManutencaoOrThrow(id);
        manutencao.setSituacao(Situacao.RECUSADA);
        manutencao.setDataResposta(LocalDateTime.now());
        manutencaoRepository.save(manutencao);
        return manutencaoMapper.toManutencaoResponseDTO(manutencao);
    }

    public ManutencaoResponseDTO realizarManutencao(Long id) {
        Manutencao manutencao = findManutencaoOrThrow(id);
        if(manutencao.getSituacao() != Situacao.ACEITA) {
            throw new ManutencaoNaoAceitaException("Manutenção deve ser aceita primeiro.");
        }
        manutencao.setDataRealizacao(LocalDateTime.now());
        manutencao.setRealizada(true);
        manutencaoRepository.save(manutencao);
        return manutencaoMapper.toManutencaoResponseDTO(manutencao);
    }

    public List<ManutencaoResponseDTO> findSolicitadas() {
        return manutencaoRepository.findSolicitadas().stream()
                .map(manutencaoMapper::toManutencaoResponseDTO)
                .toList();
    }

    public List<ManutencaoResponseDTO> findEmRealizacao() {
        return manutencaoRepository.findEmRealizacao().stream()
                .map(manutencaoMapper::toManutencaoResponseDTO)
                .toList();
    }

    public List<ManutencaoResponseDTO> findRealizadas() {
        return manutencaoRepository.findRealizadas().stream()
                .map(manutencaoMapper::toManutencaoResponseDTO)
                .toList();
    }
}
