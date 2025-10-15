package br.edu.fema.gympro.dto.entradasaida;

import java.time.LocalDate;

public record EntradasPorDiaDTO(LocalDate data, Long quantidadeEntradas) {
}
