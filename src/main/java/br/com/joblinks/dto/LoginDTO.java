package br.com.joblinks.dto;

public record LoginDTO(Long id,
                       String nome,
//                       Categoria genero,
                       String email,
                       String senha,
                       String confirmarSenha) {
}
