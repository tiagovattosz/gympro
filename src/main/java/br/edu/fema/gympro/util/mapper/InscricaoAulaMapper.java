package br.edu.fema.gympro.util.mapper;

import br.edu.fema.gympro.domain.InscricaoAula;
import br.edu.fema.gympro.dto.inscricaoaula.InscricaoAulaResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class InscricaoAulaMapper {
    public InscricaoAulaResponseDTO toInscricaoAulaResponseDTO(InscricaoAula inscricaoAula) {
        return new InscricaoAulaResponseDTO(
                inscricaoAula.getId(),
                inscricaoAula.getCliente().getNome(),
                inscricaoAula.getAula().getProfessor().getNome(),
                inscricaoAula.getAula().getModalidade().getDescricao(),
                inscricaoAula.getDataInscricao().toString(),
                inscricaoAula.getAula().getDiaDaSemana().toString(),
                inscricaoAula.getAula().getHorario().toString()
        );
    }
}
