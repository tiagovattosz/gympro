package br.edu.fema.gympro.cargo.controller;

import br.edu.fema.gympro.cargo.dto.CargoRequestDTO;
import br.edu.fema.gympro.cargo.dto.CargoResponseDTO;
import br.edu.fema.gympro.cargo.service.CargoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cargos")
public class CargoController {
    private final CargoService cargoService;

    public CargoController(CargoService cargoService) {
        this.cargoService = cargoService;
    }

    @GetMapping
    public ResponseEntity<List<CargoResponseDTO>> findAll(){
        return ResponseEntity.ok().body(cargoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CargoResponseDTO> findById(@PathVariable Long id){
        return ResponseEntity.ok().body(cargoService.findById(id));
    }

    @PostMapping
    public ResponseEntity<CargoResponseDTO> save(@RequestBody @Valid CargoRequestDTO data) {
        CargoResponseDTO response = cargoService.save(data);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CargoResponseDTO> update(@RequestBody @Valid CargoRequestDTO data, @PathVariable Long id){
        return ResponseEntity.ok().body(cargoService.update(data, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        cargoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
