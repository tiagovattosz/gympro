package br.edu.fema.gympro.controller;

import br.edu.fema.gympro.dto.inscricaoaula.InscricaoAulaCreateDTO;
import br.edu.fema.gympro.dto.inscricaoaula.InscricaoAulaResponseDTO;
import br.edu.fema.gympro.dto.inscricaoaula.InscricaoAulaUpdateDTO;
import br.edu.fema.gympro.service.InscricaoAulaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inscricoes-aula")
public class InscricaoAulaController {
    private final InscricaoAulaService inscricaoAulaService;

    public InscricaoAulaController(InscricaoAulaService inscricaoAulaService) {
        this.inscricaoAulaService = inscricaoAulaService;
    }

    @GetMapping
    public ResponseEntity<List<InscricaoAulaResponseDTO>> findAll() {
        return ResponseEntity.ok().body(inscricaoAulaService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<InscricaoAulaResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok().body(inscricaoAulaService.findById(id));
    }

    @PostMapping
    public ResponseEntity<InscricaoAulaResponseDTO> save(@RequestBody @Valid InscricaoAulaCreateDTO data) {
        return ResponseEntity.status(HttpStatus.CREATED).body(inscricaoAulaService.save(data));
    }

    @PutMapping("/{id}")
    public ResponseEntity<InscricaoAulaResponseDTO> update(@RequestBody @Valid InscricaoAulaUpdateDTO data, @PathVariable Long id) {
        return ResponseEntity.ok().body(inscricaoAulaService.update(data, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        inscricaoAulaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
