package br.edu.fema.gympro.controller;

import br.edu.fema.gympro.dto.planocliente.PlanoClienteCreateDTO;
import br.edu.fema.gympro.dto.planocliente.PlanoClienteResponseDTO;
import br.edu.fema.gympro.service.PlanoClienteService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/plano-cliente")
public class PlanoClienteController {

    private final PlanoClienteService planoClienteService;

    public PlanoClienteController(PlanoClienteService planoClienteService) {
        this.planoClienteService = planoClienteService;
    }

    // Rota para definir o plano do cliente
    @PostMapping("/definir-plano")
    public ResponseEntity<PlanoClienteResponseDTO> definirPlano(@RequestBody @Valid PlanoClienteCreateDTO data) {
        PlanoClienteResponseDTO responseDTO = planoClienteService.save(data);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    // Rota para atualizar o plano do cliente existente
    @PutMapping("/atualizar-plano/{idCliente}")
    public ResponseEntity<PlanoClienteResponseDTO> atualizarPlano(@RequestBody @Valid PlanoClienteCreateDTO data, @PathVariable Long idCliente) {
        PlanoClienteResponseDTO responseDTO = planoClienteService.update(data, idCliente);
        return ResponseEntity.ok().body(responseDTO);
    }
}
