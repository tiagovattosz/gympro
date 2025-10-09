package br.edu.fema.gympro.dto.cliente;

import br.edu.fema.gympro.dto.inscricaoaula.InscricaoAulaResponseDTO;

import java.util.List;

public record ClienteDetailsDTO(Long id,
                                String nome,
                                String cpf,
                                String celular,
                                String email,
                                String dataNascimento,
                                Integer numeroInscricoesAtivas,
                                Integer limiteDeInscricoes,
                                String plano,
                                String dataInicioAssinatura,
                                String dataTerminoAssinatura,
                                String matricula,
                                List<InscricaoAulaResponseDTO> inscricoes) {
}
