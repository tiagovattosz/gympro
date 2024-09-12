package br.edu.fema.gympro.security.dto;

public record RegisterDTO(String username,
                          String password,
                          String role) {
}
