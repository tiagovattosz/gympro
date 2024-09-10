package br.edu.fema.gympro.controller;

import br.edu.fema.gympro.dto.plano.PlanoRequestDTO;
import br.edu.fema.gympro.dto.plano.PlanoResponseDTO;
import br.edu.fema.gympro.service.PlanoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/planos")
public class PlanoController {
    private final PlanoService planoService;

    public PlanoController(PlanoService planoService) {
        this.planoService = planoService;
    }

    @GetMapping
    public ResponseEntity<List<PlanoResponseDTO>> findAll(){
        return ResponseEntity.ok().body(planoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlanoResponseDTO> findById(@PathVariable Long id){
        return ResponseEntity.ok().body(planoService.findById(id));
    }

    @PostMapping
    public ResponseEntity<PlanoResponseDTO> save(@RequestBody @Valid PlanoRequestDTO data) {
        PlanoResponseDTO response = planoService.save(data);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlanoResponseDTO> update(@RequestBody @Valid PlanoRequestDTO data, @PathVariable Long id){
        return ResponseEntity.ok().body(planoService.update(data, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        planoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
