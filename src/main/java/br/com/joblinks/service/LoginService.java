package br.com.joblinks.service;

import br.com.joblinks.dto.EpisodioDTO;
import br.com.joblinks.dto.LoginDTO;
import br.com.joblinks.model.Categoria;
import br.com.joblinks.model.Cadastros;
import br.com.joblinks.repository.LoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LoginService {

    @Autowired
    private LoginRepository repositorio;

    public List<LoginDTO> obterTodasAsSeries() {
        return converteDados(repositorio.findAll());
    }

    public List<LoginDTO> obterTop5Series() {
        return converteDados(repositorio.findTop5ByOrderByAvaliacaoDesc());
    }

    private List<LoginDTO> converteDados(List<Cadastros> series) {
      return series.stream()
              .map(s -> new LoginDTO(s.getId(), s.getNome(), s.getEmail(), s.getSenha(), s.getConfirmarSenha()))
              .collect(Collectors.toList());
    }

    public List<LoginDTO> obterLancamentos() {
        return converteDados(repositorio.lancamentosMaisRecentes());
    }

    public LoginDTO obterPorId(Long id) {
        Optional<Cadastros> serie = repositorio.findById(id);

        if (serie.isPresent()) {
            Cadastros s = serie.get();
            return new LoginDTO(s.getId(), s.getNome(),  s.getEmail(), s.getSenha(), s.getConfirmarSenha());
        }
        return null;
    }

//    public List<EpisodioDTO> obterTodasTemporadas(Long id) {
//        Optional<Cadastros> serie = repositorio.findById(id);
//
//        if (serie.isPresent()) {
//            Cadastros s = serie.get();
//            return s.getEpisodios().stream()
//                    .map(e -> new EpisodioDTO(e.getTemporada(), e.getNumeroEpisodio(), e.getTitulo()))
//                    .collect(Collectors.toList());
//        }
//        return null;
//    }

//    public List<EpisodioDTO> obterTemporadasPorNumero(Long id, Long numero) {
//        return repositorio.obterEpisodiosPorTemporada(id, numero)
//                .stream()
//                .map(e -> new EpisodioDTO(e.getTemporada(), e.getNumeroEpisodio(), e.getTitulo()))
//                .collect(Collectors.toList());
//    }

    public List<LoginDTO> obterSeriesPorCategoria(String nomeGenero) {
        Categoria categoria = Categoria.fromPortugues(nomeGenero);
        return converteDados(repositorio.findByGenero(categoria));
    }

//    public List<EpisodioDTO> obterTopEpisodios(Long id) {
//        var serie = repositorio.findById(id).get();
//        return repositorio.topEpisodiosPorSerie(serie)
//                .stream()
//                .map(e -> new EpisodioDTO(e.getTemporada(), e.getNumeroEpisodio(), e.getTitulo()))
//                .collect(Collectors.toList());
//    }
}
