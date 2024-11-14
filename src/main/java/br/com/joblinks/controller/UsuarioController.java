package br.com.joblinks.controller;

import br.com.joblinks.model.Usuario;
import br.com.joblinks.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/login")
    public String login(@RequestParam String email, @RequestParam String senha) {
        if (usuarioService.verificarCredenciais(email, senha)) {
            return "Login bem-sucedido!";
        } else {
            return "Credenciais inválidas!";
        }
    }

    @PostMapping("/cadastro")
    public String cadastrar(@RequestBody Usuario usuario) {
        usuarioService.cadastrarUsuario(usuario);
        return "Usuário cadastrado com sucesso!";
    }
}
