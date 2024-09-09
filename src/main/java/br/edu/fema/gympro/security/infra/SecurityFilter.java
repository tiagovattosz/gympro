package br.edu.fema.gympro.security.infra;

import br.edu.fema.gympro.security.repository.UserRepository;
import br.edu.fema.gympro.security.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final UserRepository userRepository;

    public SecurityFilter(TokenService tokenService, UserRepository userRepository) {
        this.tokenService = tokenService;
        this.userRepository = userRepository;
    }

    // filtro do token
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var token = this.recoverToken(request);
        if (token != null) {
            var subject = tokenService.validateToken(token);  // valida token
            UserDetails user = userRepository.findByUsername(subject); // recupera usuário a partir do subject (username)

            var authentication = new UsernamePasswordAuthenticationToken(subject, null, user.getAuthorities()); // gera autenticação para o próximo filtro (username e password authentication token)
            SecurityContextHolder.getContext().setAuthentication(authentication);  // segura o token no contexto da aplicação para o próximo filtro
        }
        filterChain.doFilter(request, response); // chama o próximo filtro
    }

    //recupera token da requisição
    private String recoverToken(HttpServletRequest request) {
        var authHeader = request.getHeader("Authorization");
        if (authHeader == null) {
            return null;
        }
        return authHeader.replace("Bearer ", "");
    }
}

