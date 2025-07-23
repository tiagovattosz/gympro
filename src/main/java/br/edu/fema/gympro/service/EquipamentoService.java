package br.edu.fema.gympro.service;

import br.edu.fema.gympro.domain.Equipamento;
import br.edu.fema.gympro.dto.equipamento.EquipamentoCreateDTO;
import br.edu.fema.gympro.dto.equipamento.EquipamentoResponseDTO;
import br.edu.fema.gympro.dto.equipamento.EquipamentoUpdateDTO;
import br.edu.fema.gympro.exception.domain.ObjetoNaoEncontrado;
import br.edu.fema.gympro.repository.EquipamentoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EquipamentoService {
    private final EquipamentoRepository equipamentoRepository;

    public EquipamentoService(EquipamentoRepository equipamentoRepository) {
        this.equipamentoRepository = equipamentoRepository;
    }

    public List<EquipamentoResponseDTO> findAll() {
        return equipamentoRepository.findAll().stream()
                .map(e -> new EquipamentoResponseDTO(e.getId(), e.getNome(), e.getDescricao(), e.getEmManutencao()))
                .toList();
    }

    public List<EquipamentoResponseDTO> findEquipamentosEmManutencao() {
        return equipamentoRepository.findEquipamentosEmManutencao().stream()
                .map(e -> new EquipamentoResponseDTO(e.getId(), e.getNome(), e.getDescricao(), e.getEmManutencao()))
                .toList();
    }

    public List<EquipamentoResponseDTO> findEquipamentosDisponiveis() {
        return equipamentoRepository.findEquipamentosDisponiveis().stream()
                .map(e -> new EquipamentoResponseDTO(e.getId(), e.getNome(), e.getDescricao(), e.getEmManutencao()))
                .toList();
    }

    public EquipamentoResponseDTO findById(Long id) {
        Equipamento equipamento = findEquipamentoOrThrow(id);
        return new EquipamentoResponseDTO(equipamento.getId(), equipamento.getNome(), equipamento.getDescricao(), equipamento.getEmManutencao());
    }

    @Transactional
    public EquipamentoResponseDTO save(EquipamentoCreateDTO data) {
        Equipamento equipamento = new Equipamento();
        equipamento.setNome(data.nome());
        equipamento.setDescricao(data.descricao());
        equipamento.setEmManutencao(false);
        equipamentoRepository.save(equipamento);
        return new EquipamentoResponseDTO(equipamento.getId(), equipamento.getNome(), equipamento.getDescricao(), equipamento.getEmManutencao());
    }

    @Transactional
    public EquipamentoResponseDTO update(EquipamentoUpdateDTO data, Long id) {
        Equipamento equipamento = findEquipamentoOrThrow(id);
        equipamento.setNome(data.nome());
        equipamento.setDescricao(data.descricao());
        equipamento.setEmManutencao(data.emManutencao());
        equipamentoRepository.save(equipamento);
        return new EquipamentoResponseDTO(equipamento.getId(), equipamento.getNome(), equipamento.getDescricao(), equipamento.getEmManutencao());
    }

    @Transactional
    public void deleteById(Long id) {
        if (!equipamentoRepository.existsById(id)) {
            throw new ObjetoNaoEncontrado("Equipamento não encontrado!");
        }
        equipamentoRepository.deleteById(id);
    }

    public Equipamento findEquipamentoOrThrow(Long id) {
        return equipamentoRepository.findById(id)
                .orElseThrow(() -> new ObjetoNaoEncontrado("Equipamento não encontrado!"));
    }
}
