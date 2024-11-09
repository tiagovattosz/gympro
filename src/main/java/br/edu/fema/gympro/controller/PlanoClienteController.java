package br.edu.fema.gympro.controller;

import br.edu.fema.gympro.dto.planocliente.PlanoClienteCreateDTO;
import br.edu.fema.gympro.dto.planocliente.PlanoClienteResponseDTO;
import br.edu.fema.gympro.service.PlanoClienteService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/assinaturas")
public class PlanoClienteController {

    private final PlanoClienteService planoClienteService;

    public PlanoClienteController(PlanoClienteService planoClienteService) {
        this.planoClienteService = planoClienteService;
    }

    @GetMapping("/assinaturas-ativas")
    public ResponseEntity<List<PlanoClienteResponseDTO>> findAssinaturasAtivas() {
        return ResponseEntity.status(HttpStatus.OK).body(planoClienteService.findAssinaturasAtivas());
    }

    @GetMapping("/assinaturas-vencidas")
    public ResponseEntity<List<PlanoClienteResponseDTO>> findAssinaturasVencidas() {
        return ResponseEntity.status(HttpStatus.OK).body(planoClienteService.findAssinaturasVencidas());
    }

    @PostMapping("/definir-plano")
    public ResponseEntity<PlanoClienteResponseDTO> definirPlano(@RequestBody @Valid PlanoClienteCreateDTO data) {
        PlanoClienteResponseDTO responseDTO = planoClienteService.save(data);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @PutMapping("/atualizar-plano")
    public ResponseEntity<PlanoClienteResponseDTO> atualizarPlano(@RequestBody @Valid PlanoClienteCreateDTO data) {
        PlanoClienteResponseDTO responseDTO = planoClienteService.update(data);
        return ResponseEntity.ok().body(responseDTO);
    }
}
