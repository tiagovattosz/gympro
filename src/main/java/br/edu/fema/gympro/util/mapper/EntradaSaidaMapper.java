package br.edu.fema.gympro.util.mapper;

import br.edu.fema.gympro.domain.EntradaSaida;
import br.edu.fema.gympro.dto.entradasaida.EntradaSaidaResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class EntradaSaidaMapper {
    public EntradaSaidaResponseDTO toEntradaSaidaResponseDTO(EntradaSaida entradaSaida) {
        return new EntradaSaidaResponseDTO(
                entradaSaida.getCliente().getNome(),
                entradaSaida.getDataHora().toLocalDate().toString(),
                entradaSaida.getDataHora().toLocalTime().toString(),
                entradaSaida.getTipoMovimento().toString()
        );
    }
}
