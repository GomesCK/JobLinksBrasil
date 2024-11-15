package br.com.joblinks.controller;

import br.com.joblinks.model.Login;
import br.com.joblinks.model.Usuario;
import br.com.joblinks.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "http://localhost:4200")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody Login login) {
        Map<String, String> response = new HashMap<>();
        if (usuarioService.verificarCredenciais(login.getEmail(), login.getSenha())) {
            response.put("message", "Login bem-sucedido!");
            return ResponseEntity.ok(response);
        } else {
            response.put("message", "Credenciais inválidas!");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }

    @PostMapping("/cadastro")
    public ResponseEntity<Map<String, String>> cadastrar(@RequestBody Usuario usuario) {
        Map<String, String> response = new HashMap<>();
        usuarioService.cadastrarUsuario(usuario);
        response.put("message", "Usuário cadastrado com sucesso!");
        return ResponseEntity.ok(response);
    }
}
