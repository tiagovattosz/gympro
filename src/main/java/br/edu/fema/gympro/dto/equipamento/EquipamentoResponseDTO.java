package br.edu.fema.gympro.dto.equipamento;

public record EquipamentoResponseDTO(
        Long id,
        String nome,
        String descricao,
        Boolean emManutencao
) {}
