package br.edu.fema.gympro.dto.equipamento;

public record EquipamentoUpdateDTO(
        String nome,
        String descricao,
        Boolean emManutencao
) {}
