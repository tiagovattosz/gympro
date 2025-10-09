package br.edu.fema.gympro.dto.cliente;

import br.edu.fema.gympro.dto.inscricaoaula.InscricaoAulaResponseDTO;

import java.util.List;

public record AreaDoClienteDTO (Long id,
                               String nome,
                               Integer numeroInscricoesAtivas,
                               Integer limiteDeInscricoes,
                               String plano,
                               String dataInicioAssinatura,
                               String dataTerminoAssinatura,
                               String matricula,
                               List<InscricaoAulaResponseDTO> inscricoes){
}
