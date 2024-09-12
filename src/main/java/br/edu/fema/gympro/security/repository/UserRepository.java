package br.edu.fema.gympro.security.repository;

import br.edu.fema.gympro.security.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    UserDetails findByUsername(String username); // deve retornar UserDetails (método utilizado pelo AuthenticationService)

    boolean existsByUsername(String username);
}

