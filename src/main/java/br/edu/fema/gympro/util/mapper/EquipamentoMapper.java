package br.edu.fema.gympro.util.mapper;

import br.edu.fema.gympro.domain.Equipamento;
import br.edu.fema.gympro.dto.equipamento.EquipamentoResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class EquipamentoMapper {
    public EquipamentoResponseDTO toEquipamentoResponseDTO(Equipamento equipamento) {
        return new EquipamentoResponseDTO(
                equipamento.getId(),
                equipamento.getNome(),
                equipamento.getDescricao(),
                equipamento.getEmManutencao());
    }
}
