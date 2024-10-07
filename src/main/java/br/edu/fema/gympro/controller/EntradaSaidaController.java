package br.edu.fema.gympro.controller;

import br.edu.fema.gympro.dto.entradasaida.EntradaSaidaCreateDTO;
import br.edu.fema.gympro.dto.entradasaida.EntradaSaidaResponseDTO;
import br.edu.fema.gympro.service.EntradaSaidaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class EntradaSaidaController {
    private final EntradaSaidaService entradaSaidaService;

    public EntradaSaidaController(EntradaSaidaService entradaSaidaService) {
        this.entradaSaidaService = entradaSaidaService;
    }

    @GetMapping("/movimentos")
    public ResponseEntity<List<EntradaSaidaResponseDTO>> findAll() {
        return ResponseEntity.ok().body(entradaSaidaService.findAll());
    }

    @PostMapping("/entradas")
    public ResponseEntity<EntradaSaidaResponseDTO> registrarEntrada(@RequestBody EntradaSaidaCreateDTO entradaSaidaCreateDTO){
        return ResponseEntity.ok().body(entradaSaidaService.registrarEntrada(entradaSaidaCreateDTO));
    }

    @PostMapping("/saidas")
    public ResponseEntity<EntradaSaidaResponseDTO> registrarSaida(@RequestBody EntradaSaidaCreateDTO entradaSaidaCreateDTO){
        return ResponseEntity.ok().body(entradaSaidaService.registrarSaida(entradaSaidaCreateDTO));
    }
}
