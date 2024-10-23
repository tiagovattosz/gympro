package br.edu.fema.gympro.util.mapper;

import br.edu.fema.gympro.domain.Aula;
import br.edu.fema.gympro.dto.aula.AulaResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class AulaMapper {
    public AulaResponseDTO toAulaResponseDTO(Aula aula) {
        return new AulaResponseDTO(
                aula.getId(),
                aula.getModalidade().getDescricao(),
                aula.getProfessor().getNome(),
                aula.getDiaDaSemana().toString(),
                aula.getHorario().toString()
        );
    }
}
