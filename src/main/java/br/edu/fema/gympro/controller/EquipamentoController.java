package br.edu.fema.gympro.controller;

import br.edu.fema.gympro.dto.equipamento.EquipamentoCreateDTO;
import br.edu.fema.gympro.dto.equipamento.EquipamentoResponseDTO;
import br.edu.fema.gympro.dto.equipamento.EquipamentoUpdateDTO;
import br.edu.fema.gympro.service.EquipamentoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/equipamentos")
public class EquipamentoController {
    private final EquipamentoService equipamentoService;

    public EquipamentoController(EquipamentoService equipamentoService) {
        this.equipamentoService = equipamentoService;
    }

    @GetMapping
    public ResponseEntity<List<EquipamentoResponseDTO>> findAll() {
        return ResponseEntity.ok().body(equipamentoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EquipamentoResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok().body(equipamentoService.findById(id));
    }

    @PostMapping
    public ResponseEntity<EquipamentoResponseDTO> save(@RequestBody @Valid EquipamentoCreateDTO data) {
        return ResponseEntity.status(HttpStatus.CREATED).body(equipamentoService.save(data));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EquipamentoResponseDTO> update(@RequestBody @Valid EquipamentoUpdateDTO data, @PathVariable Long id) {
        return ResponseEntity.ok().body(equipamentoService.update(data, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        equipamentoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
