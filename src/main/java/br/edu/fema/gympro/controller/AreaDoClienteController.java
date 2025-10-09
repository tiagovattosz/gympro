package br.edu.fema.gympro.controller;

import br.edu.fema.gympro.dto.cliente.AreaDoClienteDTO;
import br.edu.fema.gympro.service.ClienteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/area-do-cliente")
public class AreaDoClienteController {
    private final ClienteService clienteService;

    public AreaDoClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping("/{matricula}")
    public ResponseEntity<AreaDoClienteDTO> findById(@PathVariable String matricula){
        return ResponseEntity.ok().body(clienteService.areaDoCliente(matricula));
    }


}
