package br.edu.fema.gympro.util.mapper;

import br.edu.fema.gympro.domain.Cliente;
import br.edu.fema.gympro.dto.cliente.ClienteResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class ClienteMapper {
    public ClienteResponseDTO toClienteResponseDTO(Cliente cliente){
        return new ClienteResponseDTO(
                cliente.getId(),
                cliente.getNome(),
                cliente.getCelular(),
                cliente.getEmail(),
                cliente.getDataNascimento().toString(),
                cliente.getNumeroIncricoesAtivas(),
                cliente.getPlano() != null ? cliente.getPlano().getDescricao() : null
        );
    }
}
