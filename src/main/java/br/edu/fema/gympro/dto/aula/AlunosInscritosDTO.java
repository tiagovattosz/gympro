package br.edu.fema.gympro.dto.aula;

import java.time.LocalDate;

public record AlunosInscritosDTO(
        Long idInscricao, Long id, String nome, LocalDate dataDaInscricao
) {
}
