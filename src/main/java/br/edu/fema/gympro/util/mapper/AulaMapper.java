package br.edu.fema.gympro.util.mapper;

import br.edu.fema.gympro.domain.Aula;
import br.edu.fema.gympro.dto.aula.AulaResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class AulaMapper {
    public AulaResponseDTO toAulaResponseDTO(Aula aula) {
        return new AulaResponseDTO(
                aula.getId(),
                aula.getModalidade() == null ? null : aula.getModalidade().getId(),
                aula.getModalidade() == null ? null : aula.getModalidade().getDescricao(),
                aula.getProfessor() == null ? null : aula.getProfessor().getId(),
                aula.getProfessor() == null ? null : aula.getProfessor().getNome(),
                aula.getDiaDaSemana().toString(),
                aula.getHorario().toString(),
                aula.getNumeroInscricoes(),
                aula.getMaximoInscricoes()
        );
    }
}
