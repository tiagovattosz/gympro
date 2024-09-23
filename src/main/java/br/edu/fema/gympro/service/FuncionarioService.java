package br.edu.fema.gympro.service;

import br.edu.fema.gympro.dto.funcionario.FuncionarioCreateDTO;
import br.edu.fema.gympro.dto.funcionario.FuncionarioResponseDTO;
import br.edu.fema.gympro.dto.funcionario.FuncionarioUpdateDTO;

import java.util.List;

public interface FuncionarioService {
    List<FuncionarioResponseDTO> findAll();
    FuncionarioResponseDTO findById(Long id);
    FuncionarioResponseDTO save(FuncionarioCreateDTO data);
    FuncionarioResponseDTO update(FuncionarioUpdateDTO data, Long id);
    void deleteById(Long id);
}
