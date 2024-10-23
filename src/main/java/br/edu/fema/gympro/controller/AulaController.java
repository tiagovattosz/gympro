package br.edu.fema.gympro.controller;

import br.edu.fema.gympro.dto.aula.AulaCreateDTO;
import br.edu.fema.gympro.dto.aula.AulaResponseDTO;
import br.edu.fema.gympro.service.AulaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/aulas")
public class AulaController {
    private final AulaService aulaService;

    public AulaController(AulaService aulaService) {
        this.aulaService = aulaService;
    }

    @GetMapping
    public ResponseEntity<List<AulaResponseDTO>> findAll() {
        return ResponseEntity.ok().body(aulaService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AulaResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok().body(aulaService.findById(id));
    }

    @PostMapping
    public ResponseEntity<AulaResponseDTO> save(@RequestBody @Valid AulaCreateDTO data) {
        return ResponseEntity.status(HttpStatus.CREATED).body(aulaService.save(data));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AulaResponseDTO> update(@RequestBody @Valid AulaCreateDTO data, @PathVariable Long id) {
        return ResponseEntity.ok().body(aulaService.update(data, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        aulaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
