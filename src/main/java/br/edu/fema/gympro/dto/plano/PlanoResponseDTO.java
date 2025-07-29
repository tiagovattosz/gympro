package br.edu.fema.gympro.dto.plano;

import java.math.BigDecimal;

public record PlanoResponseDTO (Long id,
                                String descricao,
                                BigDecimal valor,
                                Integer maximoInscricoes,
                                Integer duracaoEmMeses,
                                String detalhes
){}
