package br.com.joblinks.controller;

import br.com.joblinks.dto.EpisodioDTO;
import br.com.joblinks.dto.LoginDTO;
import br.com.joblinks.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/users")
public class LoginController {

   @Autowired
   private LoginService servico;

    @GetMapping
    public List<LoginDTO> obterSeries() {
       return servico.obterTodasAsSeries();
    }

    @GetMapping("/email")
    public List<LoginDTO> obterEmail() {
        return servico.obterTop5Series();
    }

    @GetMapping("/senha")
    public List<LoginDTO> obterLancamentos() {
        return servico.obterLancamentos();
    }

    @PutMapping("/email/register")
    public List<LoginDTO> cadastrarEmail() {
        return servico.obterTop5Series();
    }

    @GetMapping("/{id}")
    public LoginDTO obterPorId(@PathVariable Long id) {
        return servico.obterPorId(id);
    }

//    @GetMapping("/{id}/password")
//    public List<EpisodioDTO> obterTodasTemporadas(@PathVariable Long id){
//        return servico.obterTodasTemporadas(id);
//    }
//
//    @GetMapping("/{id}/temporadas/{numero}")
//    public List<EpisodioDTO> obterTemporadasPorNumero(@PathVariable Long id, @PathVariable Long numero){
//        return servico.obterTemporadasPorNumero(id, numero);
//    }
//
//    @GetMapping("/{id}/temporadas/top")
//    public List<EpisodioDTO> obterTopEpisodios(@PathVariable Long id){
//        return servico.obterTopEpisodios(id);
//    }

    @GetMapping("/categoria/{nomeGenero}")
    public List<LoginDTO> obterSeriesPorCategoria(@PathVariable String nomeGenero){
        return servico.obterSeriesPorCategoria(nomeGenero);
    }
}
