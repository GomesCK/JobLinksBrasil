package br.com.joblinks.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;

@Entity
@Table(name = "cadastros")
public class Cadastros {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private Integer totalTemporadas;
    private Double avaliacao;
    @Enumerated(EnumType.STRING)
    private Categoria genero;
    private String email;
    private String senha;
    private String confirmarSenha;

//    @OneToMany(mappedBy = "serie", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    private List<Episodio> episodios = new ArrayList<>();

    public Cadastros() {}

    public Cadastros(DadosLogin dadosLogin){
        this.nome = dadosLogin.nome();
        this.totalTemporadas = dadosLogin.totalTemporadas();
        this.avaliacao = OptionalDouble.of(Double.valueOf(dadosLogin.avaliacao())).orElse(0);
        this.genero = Categoria.fromString(dadosLogin.genero().split(",")[0].trim());
        this.email = dadosLogin.email();
        this.senha = dadosLogin.senha();
        this.confirmarSenha = dadosLogin.senha();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

//    public List<Episodio> getEpisodios() {
//        return episodios;
//    }

//    public void setEpisodios(List<Episodio> episodios) {
//        episodios.forEach(e -> e.setSerie(this));
//        this.episodios = episodios;
//    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getTotalTemporadas() {
        return totalTemporadas;
    }

    public void setTotalTemporadas(Integer totalTemporadas) {
        this.totalTemporadas = totalTemporadas;
    }

    public Double getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(Double avaliacao) {
        this.avaliacao = avaliacao;
    }

    public Categoria getGenero() {
        return genero;
    }

    public void setGenero(Categoria genero) {
        this.genero = genero;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getConfirmarSenha() {
        return confirmarSenha;
    }

    public void setConfirmarSenha(String confirmarSenha) {
        this.confirmarSenha = confirmarSenha;
    }

    @Override
    public String toString() {
        return
                "genero=" + genero +
                        ", nome='" + nome + '\'' +
                        ", totalTemporadas=" + totalTemporadas +
                        ", avaliacao=" + avaliacao +
                        ", email='" + email + '\'' +
                        ", senha='" + senha + '\'' +
                        ", confirmarSenha='" + confirmarSenha + '\'';
//                        +
//                        ", episodios='" + episodios + '\'';
    }
}
