package br.edu.fema.gympro.service;

import br.edu.fema.gympro.domain.Cargo;
import br.edu.fema.gympro.domain.Funcionario;
import br.edu.fema.gympro.dto.cargo.CargoRequestDTO;
import br.edu.fema.gympro.dto.cargo.CargoResponseDTO;
import br.edu.fema.gympro.repository.CargoRepository;
import br.edu.fema.gympro.exception.domain.ObjetoNaoEncontrado;
import br.edu.fema.gympro.repository.FuncionarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CargoService {
    private final CargoRepository cargoRepository;
    private final FuncionarioRepository funcionarioRepository;

    public CargoService(CargoRepository cargoRepository, FuncionarioRepository funcionarioRepository) {
        this.cargoRepository = cargoRepository;
        this.funcionarioRepository = funcionarioRepository;
    }

    public List<CargoResponseDTO> findAll() {
        return cargoRepository.findAll().stream()
                .map(c -> new CargoResponseDTO(c.getId(), c.getDescricao()))
                .toList();
    }

    public CargoResponseDTO findById(Long id) {
        Cargo cargo = findCargoOrThrow(id);
        return new CargoResponseDTO(cargo.getId(), cargo.getDescricao());
    }

    @Transactional
    public CargoResponseDTO save(CargoRequestDTO data) {
        Cargo cargo = new Cargo();
        cargo.setDescricao(data.descricao());
        cargoRepository.save(cargo);
        return new CargoResponseDTO(cargo.getId(), cargo.getDescricao());
    }

    @Transactional
    public CargoResponseDTO update(CargoRequestDTO data, Long id) {
        Cargo cargo = findCargoOrThrow(id);
        cargo.setDescricao(data.descricao());
        cargoRepository.save(cargo);
        return new CargoResponseDTO(cargo.getId(), cargo.getDescricao());
    }

    @Transactional
    public void deleteById(Long id) {
        if (!cargoRepository.existsById(id)) {
            throw new ObjetoNaoEncontrado("Cargo não encontrado!");
        }
        Cargo cargo = findCargoOrThrow(id);
        List<Funcionario> funcionarios = funcionarioRepository.findByCargo(cargo);
        for(Funcionario funcionario : funcionarios) {
            funcionario.setCargo(null);
            funcionarioRepository.save(funcionario);
        }
        cargoRepository.deleteById(id);
    }

    public Cargo findCargoOrThrow(Long id) {
        return cargoRepository.findById(id)
                .orElseThrow(() -> new ObjetoNaoEncontrado("Cargo não encontrado!"));
    }
}
