package br.edu.fema.gympro.service;

import br.edu.fema.gympro.dto.PlanoRequestDTO;
import br.edu.fema.gympro.dto.PlanoResponseDTO;

import java.util.List;

public interface PlanoService {
    List<PlanoResponseDTO> findAll();
    PlanoResponseDTO findById(Long id);
    PlanoResponseDTO save(PlanoRequestDTO data);
    PlanoResponseDTO update(PlanoRequestDTO data, Long id);
    void deleteById(Long id);
}
