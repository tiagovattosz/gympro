package br.edu.fema.gympro.cargo.service;

import br.edu.fema.gympro.cargo.dto.CargoRequestDTO;
import br.edu.fema.gympro.cargo.dto.CargoResponseDTO;

import java.util.List;

public interface CargoService {
    List<CargoResponseDTO> findAll();
    CargoResponseDTO findById(Long id);
    CargoResponseDTO save(CargoRequestDTO data);
    CargoResponseDTO update(CargoRequestDTO data, Long id);
    void deleteById(Long id);
}
