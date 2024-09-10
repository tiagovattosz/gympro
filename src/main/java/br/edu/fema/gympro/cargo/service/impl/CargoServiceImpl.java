package br.edu.fema.gympro.cargo.service.impl;

import br.edu.fema.gympro.cargo.domain.Cargo;
import br.edu.fema.gympro.cargo.dto.CargoRequestDTO;
import br.edu.fema.gympro.cargo.dto.CargoResponseDTO;
import br.edu.fema.gympro.cargo.repository.CargoRepository;
import br.edu.fema.gympro.cargo.service.CargoService;
import br.edu.fema.gympro.exception.domain.ObjetoNaoEncontrado;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CargoServiceImpl implements CargoService {
    private final CargoRepository cargoRepository;

    public CargoServiceImpl(CargoRepository cargoRepository) {
        this.cargoRepository = cargoRepository;
    }

    @Override
    public List<CargoResponseDTO> findAll() {
        return cargoRepository.findAll().stream()
                .map(c -> new CargoResponseDTO(c.getId(), c.getDescricao()))
                .toList();
    }

    @Override
    public CargoResponseDTO findById(Long id) {
        Cargo cargo = findCargoOrThrow(id);
        return new CargoResponseDTO(cargo.getId(), cargo.getDescricao());
    }

    @Transactional
    @Override
    public CargoResponseDTO save(CargoRequestDTO data) {
        Cargo cargo = new Cargo();
        cargo.setDescricao(data.descricao());
        cargoRepository.save(cargo);
        return new CargoResponseDTO(cargo.getId(), cargo.getDescricao());
    }

    @Transactional
    @Override
    public CargoResponseDTO update(CargoRequestDTO data, Long id) {
        Cargo cargo = findCargoOrThrow(id);
        cargo.setDescricao(data.descricao());
        cargoRepository.save(cargo);
        return new CargoResponseDTO(cargo.getId(), cargo.getDescricao());
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        if (!cargoRepository.existsById(id)) {
            throw new ObjetoNaoEncontrado("Cargo não encontrado!");
        }
        cargoRepository.deleteById(id);
    }

    private Cargo findCargoOrThrow(Long id) {
        return cargoRepository.findById(id)
                .orElseThrow(() -> new ObjetoNaoEncontrado("Cargo não encontrado!"));
    }
}
