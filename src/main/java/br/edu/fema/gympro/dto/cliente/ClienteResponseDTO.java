package br.edu.fema.gympro.dto.cliente;

public record ClienteResponseDTO(Long id,
                                 String nome,
                                 String celular,
                                 String email,
                                 String dataNascimento,
                                 Integer numeroInscricoesAtivas,
                                 String plano) {
}
