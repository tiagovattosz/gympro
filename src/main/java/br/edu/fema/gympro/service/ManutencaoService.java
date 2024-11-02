package br.edu.fema.gympro.service;

import br.edu.fema.gympro.domain.Equipamento;
import br.edu.fema.gympro.domain.Funcionario;
import br.edu.fema.gympro.domain.Manutencao;
import br.edu.fema.gympro.domain.enums.Situacao;
import br.edu.fema.gympro.dto.manutencao.ManutencaoCreateDTO;
import br.edu.fema.gympro.dto.manutencao.ManutencaoResponseDTO;
import br.edu.fema.gympro.dto.manutencao.ManutencaoUpdateDTO;
import br.edu.fema.gympro.exception.domain.ObjetoNaoEncontrado;
import br.edu.fema.gympro.repository.ManutencaoRepository;
import br.edu.fema.gympro.util.mapper.ManutencaoMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        manutencao.setSituacao(Situacao.SOLICIDADA);
        manutencao.setRealizada(false);

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
}
