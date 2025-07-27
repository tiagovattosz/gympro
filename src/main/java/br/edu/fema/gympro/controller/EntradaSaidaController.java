package br.edu.fema.gympro.controller;

import br.edu.fema.gympro.dto.entradasaida.EntradaSaidaCreateDTO;
import br.edu.fema.gympro.dto.entradasaida.EntradaSaidaResponseDTO;
import br.edu.fema.gympro.service.EntradaSaidaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
public class EntradaSaidaController {
    private final EntradaSaidaService entradaSaidaService;

    public EntradaSaidaController(EntradaSaidaService entradaSaidaService) {
        this.entradaSaidaService = entradaSaidaService;
    }

    @GetMapping("/movimentos")
    public ResponseEntity<List<EntradaSaidaResponseDTO>> findByData(
            @RequestParam(value = "dataInicio", required = false) LocalDate dataInicio,
            @RequestParam(value = "dataFinal", required = false) LocalDate dataFinal,
            @RequestParam(value = "matricula", required = false) String matricula) {

        return ResponseEntity.ok().body(
                entradaSaidaService.findByData(dataInicio, dataFinal, matricula)
        );
    }


//    @GetMapping("/movimentos/cliente")
//    public ResponseEntity<List<EntradaSaidaResponseDTO>> findByCliente(@RequestParam(value = "idCliente") Long idCliente) {
//        return ResponseEntity.ok().body(entradaSaidaService.findByCliente(idCliente));
//    }

    @PostMapping("/entradas")
    public ResponseEntity<EntradaSaidaResponseDTO> registrarEntrada(@RequestBody EntradaSaidaCreateDTO entradaSaidaCreateDTO){
        return ResponseEntity.ok().body(entradaSaidaService.registrarEntrada(entradaSaidaCreateDTO));
    }

    @PostMapping("/saidas")
    public ResponseEntity<EntradaSaidaResponseDTO> registrarSaida(@RequestBody EntradaSaidaCreateDTO entradaSaidaCreateDTO){
        return ResponseEntity.ok().body(entradaSaidaService.registrarSaida(entradaSaidaCreateDTO));
    }
}
