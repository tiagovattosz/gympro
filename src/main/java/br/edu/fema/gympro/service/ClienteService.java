package br.edu.fema.gympro.service;

import br.edu.fema.gympro.dto.cliente.ClienteCreateDTO;
import br.edu.fema.gympro.dto.cliente.ClienteResponseDTO;
import br.edu.fema.gympro.dto.cliente.ClienteUpdateDTO;

import java.util.List;

public interface ClienteService {
    List<ClienteResponseDTO> findAll();
    ClienteResponseDTO findById(Long id);
    ClienteResponseDTO save(ClienteCreateDTO data);
    ClienteResponseDTO update(ClienteUpdateDTO data, Long id);
    void deleteById(Long id);
}
