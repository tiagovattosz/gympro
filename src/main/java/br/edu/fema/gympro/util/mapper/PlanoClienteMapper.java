package br.edu.fema.gympro.util.mapper;

import br.edu.fema.gympro.domain.PlanoCliente;
import br.edu.fema.gympro.dto.planocliente.PlanoClienteResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class PlanoClienteMapper {
    public PlanoClienteResponseDTO toPlanoClienteResponseDTO(PlanoCliente planoCliente) {
        return new PlanoClienteResponseDTO(
                planoCliente.getId(),
                planoCliente.getCliente().getNome(),
                planoCliente.getPlano().getDescricao(),
                planoCliente.getDataInicio().toString(),
                planoCliente.getDataTermino().toString()
        );
    }
}
