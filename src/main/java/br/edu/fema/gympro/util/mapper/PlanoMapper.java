package br.edu.fema.gympro.util.mapper;

import br.edu.fema.gympro.domain.Plano;
import br.edu.fema.gympro.dto.plano.PlanoResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class PlanoMapper {
    public PlanoResponseDTO toPlanoResponseDTO(Plano plano) {
        return new PlanoResponseDTO(
                plano.getId(),
                plano.getDescricao(),
                plano.getValor(),
                plano.getMaximoInscricoes(),
                plano.getDuracaoEmMeses(),
                plano.getDetalhes());
    }
}
