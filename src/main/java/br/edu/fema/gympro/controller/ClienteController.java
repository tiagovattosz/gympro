package br.edu.fema.gympro.controller;

import br.edu.fema.gympro.dto.cliente.ClienteCreateDTO;
import br.edu.fema.gympro.dto.cliente.ClienteDetailsDTO;
import br.edu.fema.gympro.dto.cliente.ClienteResponseDTO;
import br.edu.fema.gympro.dto.cliente.ClienteUpdateDTO;
import br.edu.fema.gympro.service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteController {
    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping
    public ResponseEntity<List<ClienteResponseDTO>> findAll(){
        return ResponseEntity.ok().body(clienteService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteDetailsDTO> findById(@PathVariable Long id){
        return ResponseEntity.ok().body(clienteService.findById(id));
    }

    @GetMapping("/plano")
    public ResponseEntity<List<ClienteResponseDTO>> findByPlano(@RequestParam(name = "idPlano") Long idPlano){
        return ResponseEntity.ok().body(clienteService.findByPlano(idPlano));
    }

    @PostMapping
    public ResponseEntity<ClienteResponseDTO> save(@RequestBody @Valid ClienteCreateDTO data) {
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteService.save(data));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> update(@RequestBody @Valid ClienteUpdateDTO data, @PathVariable Long id){
        return ResponseEntity.ok().body(clienteService.update(data, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        clienteService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
