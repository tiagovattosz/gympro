package br.edu.fema.gympro.util.mapper;

import br.edu.fema.gympro.domain.Manutencao;
import br.edu.fema.gympro.dto.manutencao.ManutencaoResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class ManutencaoMapper {
    public ManutencaoResponseDTO toManutencaoResponseDTO(Manutencao manutencao) {
        return new ManutencaoResponseDTO(
                manutencao.getId(),
                manutencao.getFuncionario().getNome(),
                manutencao.getEquipamento().getNome(),
                manutencao.getDescricao(),
                manutencao.getSituacao(),
                manutencao.getRealizada()
        );
    }
}
