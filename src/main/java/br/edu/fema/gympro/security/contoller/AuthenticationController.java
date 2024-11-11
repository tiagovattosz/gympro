package br.edu.fema.gympro.security.contoller;

import br.edu.fema.gympro.exception.domain.ObjetoNaoEncontrado;
import br.edu.fema.gympro.repository.FuncionarioRepository;
import br.edu.fema.gympro.security.domain.user.User;
import br.edu.fema.gympro.security.domain.user.UserRole;
import br.edu.fema.gympro.security.dto.*;
import br.edu.fema.gympro.security.repository.UserRepository;
import br.edu.fema.gympro.security.service.AuthenticationService;
import br.edu.fema.gympro.security.service.TokenService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping
public class AuthenticationController {
    private final AuthenticationManager authenticationManager;
    private final AuthenticationService authenticationService;
    private final TokenService tokenService;
    private final UserRepository userRepository;
    private final FuncionarioRepository funcionarioRepository;

    public AuthenticationController(AuthenticationManager authenticationManager, AuthenticationService authenticationService, TokenService tokenService, UserRepository userRepository, FuncionarioRepository funcionarioRepository) {
        this.authenticationManager = authenticationManager;
        this.authenticationService = authenticationService;
        this.tokenService = tokenService;
        this.userRepository = userRepository;
        this.funcionarioRepository = funcionarioRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid LoginRequestDTO data) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.username(), data.password());
        var auth = authenticationManager.authenticate(usernamePassword);
        User user = (User) auth.getPrincipal();
        var token = tokenService.generateToken(user);
        return ResponseEntity.ok().body(new LoginResponseDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody @Valid RegisterDTO data) {
        authenticationService.register(data);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/usuarios")
        ResponseEntity<List<UsuarioDTO>> listarUsuarios() {
        List<User> usuarios = userRepository.findUsuarios();
        List<UsuarioDTO> resposta = new ArrayList<>();
        usuarios.forEach(user -> resposta.add(new UsuarioDTO(user.getId(), user.getUsername(), user.getRole().toString())));
        return ResponseEntity.ok(resposta);
    }

    @GetMapping("/login/dados")
    public ResponseEntity<PessoaDTO> buscarPessoaPorToken(@RequestBody String token) throws Exception {
        String[] parts = token.split("\\.");
        String payload = parts[1];
        String decodedPayload = new String(Base64.getUrlDecoder().decode(payload));
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> claims = mapper.readValue(decodedPayload, Map.class);
        String username = (String) claims.get("sub");

        if (username == null || username.isBlank()) {
            throw new ObjetoNaoEncontrado("Usuario não encontrado!");
        }

        User usuarioLogado = userRepository.findUsuarioGymPro(username);
        if (usuarioLogado.getRole() == UserRole.ADMIN) {
            return ResponseEntity.ok(new PessoaDTO(usuarioLogado.getId(), usuarioLogado.getUsername(), usuarioLogado.getRole().toString()));
        }

        String nome = funcionarioRepository.findByUser(usuarioLogado).getNome();
        return ResponseEntity.ok(new PessoaDTO(usuarioLogado.getId(), nome, usuarioLogado.getRole().toString()));
    }
}
