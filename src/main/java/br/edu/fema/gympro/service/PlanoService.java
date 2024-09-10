package br.edu.fema.gympro.service;

import br.edu.fema.gympro.dto.plano.PlanoRequestDTO;
import br.edu.fema.gympro.dto.plano.PlanoResponseDTO;

import java.util.List;

public interface PlanoService {
    List<PlanoResponseDTO> findAll();
    PlanoResponseDTO findById(Long id);
    PlanoResponseDTO save(PlanoRequestDTO data);
    PlanoResponseDTO update(PlanoRequestDTO data, Long id);
    void deleteById(Long id);
}
