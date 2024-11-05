package br.edu.fema.gympro.controller;

import br.edu.fema.gympro.dto.funcionario.FuncionarioCreateDTO;
import br.edu.fema.gympro.dto.funcionario.FuncionarioResponseDTO;
import br.edu.fema.gympro.dto.funcionario.FuncionarioUpdateDTO;
import br.edu.fema.gympro.service.FuncionarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/funcionarios")
public class FuncionarioController {

    private final FuncionarioService funcionarioService;

    public FuncionarioController(FuncionarioService funcionarioService) {
        this.funcionarioService = funcionarioService;
    }

    @GetMapping
    public ResponseEntity<List<FuncionarioResponseDTO>> findAll() {
        return ResponseEntity.ok().body(funcionarioService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FuncionarioResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok().body(funcionarioService.findById(id));
    }

    @PostMapping
    public ResponseEntity<FuncionarioResponseDTO> save(@RequestBody @Valid FuncionarioCreateDTO data) {
        return ResponseEntity.status(HttpStatus.CREATED).body(funcionarioService.save(data));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FuncionarioResponseDTO> update(@RequestBody @Valid FuncionarioUpdateDTO data, @PathVariable Long id) {
        return ResponseEntity.ok().body(funcionarioService.update(data, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        funcionarioService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/professores")
    public ResponseEntity<List<FuncionarioResponseDTO>> findProfessores(){
        return ResponseEntity.ok().body(funcionarioService.findProfessores());
    }
}
