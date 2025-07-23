package br.edu.fema.gympro.controller;

import br.edu.fema.gympro.dto.assinatura.AssinaturaRequestDTO;
import br.edu.fema.gympro.dto.assinatura.AssinaturaResponseDTO;
import br.edu.fema.gympro.dto.cliente.ClienteResponseDTO;
import br.edu.fema.gympro.service.AssinaturaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/assinaturas")
public class AssinaturaController {

    private final AssinaturaService assinaturaService;

    public AssinaturaController(AssinaturaService assinaturaService) {
        this.assinaturaService = assinaturaService;
    }

    @GetMapping("/assinaturas-ativas")
    public ResponseEntity<List<ClienteResponseDTO>> findAssinaturasAtivas() {
        return ResponseEntity.status(HttpStatus.OK).body(assinaturaService.findAssinaturasAtivas());
    }

    @GetMapping("/assinaturas-vencidas")
    public ResponseEntity<List<ClienteResponseDTO>> findAssinaturasVencidas() {
        return ResponseEntity.status(HttpStatus.OK).body(assinaturaService.findAssinaturasVencidas());
    }

    @PostMapping("/definir-plano")
    public ResponseEntity<AssinaturaResponseDTO> definirPlano(@RequestBody @Valid AssinaturaRequestDTO data) {
        AssinaturaResponseDTO responseDTO = assinaturaService.save(data);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

}
