package br.edu.fema.gympro.controller;

import br.edu.fema.gympro.dto.modalidade.ModalidadeCreateDTO;
import br.edu.fema.gympro.dto.modalidade.ModalidadeResponseDTO;
import br.edu.fema.gympro.dto.modalidade.ModalidadeUpdateDTO;
import br.edu.fema.gympro.service.ModalidadeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/modalidades")
public class ModalidadeController {

    private final ModalidadeService modalidadeService;

    public ModalidadeController(ModalidadeService modalidadeService) {
        this.modalidadeService = modalidadeService;
    }

    @GetMapping
    public ResponseEntity<List<ModalidadeResponseDTO>> findAll() {
        return ResponseEntity.ok().body(modalidadeService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ModalidadeResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok().body(modalidadeService.findById(id));
    }

    @PostMapping
    public ResponseEntity<ModalidadeResponseDTO> save(@RequestBody @Valid ModalidadeCreateDTO data) {
        return ResponseEntity.status(HttpStatus.CREATED).body(modalidadeService.save(data));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ModalidadeResponseDTO> update(@RequestBody @Valid ModalidadeUpdateDTO data, @PathVariable Long id) {
        return ResponseEntity.ok().body(modalidadeService.update(data, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        modalidadeService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
