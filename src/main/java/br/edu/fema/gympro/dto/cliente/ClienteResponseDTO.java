package br.edu.fema.gympro.dto.cliente;

public record ClienteResponseDTO(Long id,
                                 String nome,
                                 String cpf,
                                 String celular,
                                 String email,
                                 String dataNascimento,
                                 Integer numeroInscricoesAtivas,
                                 String plano,
                                 String dataInicioAssinatura,
                                 String dataTerminoAssinatura
                                 ) {
}
