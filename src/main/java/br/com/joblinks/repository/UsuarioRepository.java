package br.com.joblinks.repository;

import br.com.joblinks.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    // Buscar um usuário pelo email/nome
    Usuario findByEmail(String email);
}
