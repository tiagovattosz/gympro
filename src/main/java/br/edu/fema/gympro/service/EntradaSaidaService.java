package br.edu.fema.gympro.service;

import br.edu.fema.gympro.dto.entradasaida.EntradaSaidaCreateDTO;
import br.edu.fema.gympro.dto.entradasaida.EntradaSaidaResponseDTO;

import java.util.List;

public interface EntradaSaidaService {
    List<EntradaSaidaResponseDTO> findAll();
    EntradaSaidaResponseDTO registrarEntrada(EntradaSaidaCreateDTO data);
    EntradaSaidaResponseDTO registrarSaida(EntradaSaidaCreateDTO data);
}
