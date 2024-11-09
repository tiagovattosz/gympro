package br.edu.fema.gympro.controller;

import br.edu.fema.gympro.dto.manutencao.ManutencaoCreateDTO;
import br.edu.fema.gympro.dto.manutencao.ManutencaoResponseDTO;
import br.edu.fema.gympro.dto.manutencao.ManutencaoUpdateDTO;
import br.edu.fema.gympro.service.ManutencaoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/manutencoes")
public class ManutencaoController {
    private final ManutencaoService manutencaoService;

    public ManutencaoController(ManutencaoService manutencaoService) {
        this.manutencaoService = manutencaoService;
    }

    @GetMapping
    public ResponseEntity<List<ManutencaoResponseDTO>> findAll() {
        return ResponseEntity.ok().body(manutencaoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ManutencaoResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok().body(manutencaoService.findById(id));
    }

    @PostMapping
    public ResponseEntity<ManutencaoResponseDTO> save(@RequestBody @Valid ManutencaoCreateDTO data) {
        return ResponseEntity.status(HttpStatus.CREATED).body(manutencaoService.save(data));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ManutencaoResponseDTO> update(@RequestBody @Valid ManutencaoUpdateDTO data, @PathVariable Long id) {
        return ResponseEntity.ok().body(manutencaoService.update(data, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        manutencaoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/aceitar-manutencao/{id}")
    public ResponseEntity<ManutencaoResponseDTO> aceitarManutencao(@PathVariable Long id) {
        return ResponseEntity.ok().body(manutencaoService.aceitarManutencao(id));
    }

    @PutMapping("/rejeitar-manutencao/{id}")
    public ResponseEntity<ManutencaoResponseDTO> rejeitarManutencao(@PathVariable Long id) {
        return ResponseEntity.ok().body(manutencaoService.rejeitarManutencao(id));
    }

    @PutMapping("/cancelar-manutencao/{id}")
    public ResponseEntity<ManutencaoResponseDTO> cancelarManutencao(@PathVariable Long id) {
        return ResponseEntity.ok().body(manutencaoService.cancelarManutencao(id));
    }

    @PutMapping("/realizar-manutencao/{id}")
    public ResponseEntity<ManutencaoResponseDTO> realizarManutencao(@PathVariable Long id) {
        return ResponseEntity.ok().body(manutencaoService.realizarManutencao(id));
    }

    @GetMapping("/solicitacoes")
    public ResponseEntity<List<ManutencaoResponseDTO>> findSolicitadas() {
        return ResponseEntity.ok().body(manutencaoService.findSolicitadas());
    }

    @GetMapping("/em-realizacao")
    public ResponseEntity<List<ManutencaoResponseDTO>> findEmRealizacao() {
        return ResponseEntity.ok().body(manutencaoService.findEmRealizacao());
    }

    @GetMapping("/realizadas")
    public ResponseEntity<List<ManutencaoResponseDTO>> findRealizadas() {
        return ResponseEntity.ok().body(manutencaoService.findRealizadas());
    }
}
