package br.com.joblinks.service;

import br.com.joblinks.model.Usuario;
import br.com.joblinks.repository.UsuarioRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Usuario cadastrarUsuario(Usuario usuario) {
        // Criptografar a senha antes de salvar
        String senhaCriptografada = BCrypt.hashpw(usuario.getSenha(), BCrypt.gensalt());
        usuario.setSenha(senhaCriptografada);
        return usuarioRepository.save(usuario);
    }

    public boolean verificarCredenciais(String email, String senha) {
        Usuario usuario = usuarioRepository.findByEmail(email);
        if (usuario != null && BCrypt.checkpw(senha, usuario.getSenha())) {
            return true; // Credenciais válidas
        }
        return false; // Credenciais inválidas
    }
}
