package br.edu.fema.gympro.controller;

import br.edu.fema.gympro.dto.assinatura.ContagemAtivasVencidasDTO;
import br.edu.fema.gympro.dto.cliente.ClientesPorPlanoDTO;
import br.edu.fema.gympro.dto.entradasaida.EntradasPorDiaDTO;
import br.edu.fema.gympro.dto.modalidade.ModalidadeMaisPopularDTO;
import br.edu.fema.gympro.service.AssinaturaService;
import br.edu.fema.gympro.service.EntradaSaidaService;
import br.edu.fema.gympro.service.ModalidadeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {

    private final EntradaSaidaService entradaSaidaService;
    private final AssinaturaService assinaturaService;
    private final ModalidadeService modalidadeService;

    public DashboardController(EntradaSaidaService entradaSaidaService, AssinaturaService assinaturaService, ModalidadeService modalidadeService) {
        this.entradaSaidaService = entradaSaidaService;
        this.assinaturaService = assinaturaService;
        this.modalidadeService = modalidadeService;
    }

    @GetMapping("/entradas-por-dia")
    public ResponseEntity<List<EntradasPorDiaDTO>> listarEntradasPorDia(
            @RequestParam LocalDate inicio,
            @RequestParam LocalDate fim
    ) {
        List<EntradasPorDiaDTO> resultado = entradaSaidaService.getEntradasPorPeriodo(inicio, fim);
        return ResponseEntity.ok(resultado);
    }

    @GetMapping("/contagem-ativas-vencidas")
    public ResponseEntity<List<ContagemAtivasVencidasDTO>> contarAssinaturasAtivasVencidas() {
        return ResponseEntity.ok(assinaturaService.contarAssinaturasAtivasVencidas());
    }

    @GetMapping("/clientes-por-plano")
    public ResponseEntity<List<ClientesPorPlanoDTO>> contarClientesPorPlano() {
        return ResponseEntity.ok(assinaturaService.contarClientesPorPlano());
    }

    @GetMapping("/modalidades-mais-populares")
    public ResponseEntity<List<ModalidadeMaisPopularDTO>> contarModalidadesMaisPopulares() {
        return ResponseEntity.ok(modalidadeService.contarModalidadesMaisPopulares());
    }

}
