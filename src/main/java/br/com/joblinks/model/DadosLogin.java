package br.com.joblinks.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosLogin(String nome,
                         Integer totalTemporadas,
                         String avaliacao,
                         String genero,
                         String email,
                         String senha,
                         String confirmarSenha) {
}
