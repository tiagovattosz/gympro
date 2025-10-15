package br.edu.fema.gympro.controller;

import br.edu.fema.gympro.dto.assinatura.AssinaturaRequestDTO;
import br.edu.fema.gympro.dto.assinatura.AssinaturaResponseDTO;
import br.edu.fema.gympro.dto.assinatura.ContagemAtivasVencidasDTO;
import br.edu.fema.gympro.dto.assinatura.RemoverAssinaturaRequestDTO;
import br.edu.fema.gympro.dto.cliente.ClienteResponseDTO;
import br.edu.fema.gympro.dto.cliente.ClientesPorPlanoDTO;
import br.edu.fema.gympro.service.AssinaturaService;
import br.edu.fema.gympro.service.ClienteService;
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

    @GetMapping("/contagem-ativas-vencidas")
    public ResponseEntity<List<ContagemAtivasVencidasDTO>> contarAssinaturasAtivasVencidas() {
        return ResponseEntity.status(HttpStatus.OK).body(assinaturaService.contarAssinaturasAtivasVencidas());
    }

    @GetMapping("/clientes-por-plano")
    public ResponseEntity<List<ClientesPorPlanoDTO>> contarClientesPorPlano() {
        return ResponseEntity.ok(assinaturaService.contarClientesPorPlano());
    }

    @PostMapping("/definir-plano")
    public ResponseEntity<AssinaturaResponseDTO> definirPlano(@RequestBody @Valid AssinaturaRequestDTO data) {
        AssinaturaResponseDTO responseDTO = assinaturaService.save(data);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @PostMapping("/remover-plano")
    public ResponseEntity<Void> removerPlano(@RequestBody RemoverAssinaturaRequestDTO data) {
        assinaturaService.removerAssinatura(data.clienteId());
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
}
