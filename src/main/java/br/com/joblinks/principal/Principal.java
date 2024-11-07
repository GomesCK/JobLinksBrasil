package br.com.joblinks.principal;

import br.com.joblinks.model.*;
import br.com.joblinks.repository.LoginRepository;
import br.com.joblinks.service.ConsumoApi;
import br.com.joblinks.service.ConverteDados;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Principal {

    private Scanner leitura = new Scanner(System.in);
    private ConsumoApi consumo = new ConsumoApi();
    private ConverteDados conversor = new ConverteDados();
    private final String ENDERECO = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=6585022c";
    private List<DadosLogin> dadosSeries = new ArrayList<>();

    private LoginRepository repositorio;
    private List<Cadastros> series = new ArrayList<>();
    private Optional<Cadastros> serieBusca;

    public Principal(LoginRepository repositorio) {
        this.repositorio = repositorio;
    }

    public void exibeMenu() {
        var opcao = -1;
        while(opcao != 0) {
            var menu = """
                    1 - Buscar séries
                    2 - Buscar episódios
                    3 - Listar séries buscadas
                    4 - Buscar série por título
                    5 - Buscar séries por ator
                    6 - Top 5 Séries
                    7 - Buscar séries por categoria
                    8 - Filtrar séries
                    9 - Buscar episódios por trecho
                    10 - Top 5 episódios por série
                    11 - Buscar episódios a partir de uma data 
                                    
                    0 - Sair                                 
                    """;

            System.out.println(menu);
            opcao = leitura.nextInt();
            leitura.nextLine();

            switch (opcao) {
                case 1:
                    buscarSerieWeb();
                    break;
                case 2:
                    buscarEpisodioPorSerie();
                    break;
                case 3:
                    listarSeriesBuscadas();
                    break;
                case 4:
                    buscarSeriePorTitulo();
                    break;
                case 5:
                    buscarSeriesPorAtor();
                    break;
                case 6:
                    buscarTop5Series();
                    break;
                case 7:
                    buscarSeriesPorCategoria();
                    break;
                case 8:
                    filtrarSeriesPorTemporadaEAvaliacao();
                    break;
                case 9:
                    buscarEpisodioPorTrecho();
                    break;
                case 10:
                    topEpisodiosPorSerie();
                    break;
                case 11:
                    buscarEpisodiosDepoisDeUmaData();
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida");
            }
        }
    }

    private void buscarSerieWeb() {
        DadosLogin dados = getDadosSerie();
        Cadastros cadastros = new Cadastros(dados);
        //dadosSeries.add(dados);
        repositorio.save(cadastros);
        System.out.println(dados);
    }

    private DadosLogin getDadosSerie() {
        System.out.println("Digite o nome da série para busca");
        var nomeSerie = leitura.nextLine();
        var json = consumo.obterDados(ENDERECO + nomeSerie.replace(" ", "+") + API_KEY);
        DadosLogin dados = conversor.obterDados(json, DadosLogin.class);
        return dados;
    }

    private void buscarEpisodioPorSerie(){
        listarSeriesBuscadas();
        System.out.println("Escolha uma série pelo nome");
        var nomeSerie = leitura.nextLine();

        Optional<Cadastros> serie = repositorio.findByEmailContainingIgnoreCase(nomeSerie);

        if(serie.isPresent()) {

            var serieEncontrada = serie.get();
            List<DadosTemporada> temporadas = new ArrayList<>();

            for (int i = 1; i <= serieEncontrada.getTotalTemporadas(); i++) {
                var json = consumo.obterDados(ENDERECO + serieEncontrada.getNome().replace(" ", "+") + "&season=" + i + API_KEY);
                DadosTemporada dadosTemporada = conversor.obterDados(json, DadosTemporada.class);
                temporadas.add(dadosTemporada);
            }
            temporadas.forEach(System.out::println);

//            List<Episodio> episodios = temporadas.stream()
//                    .flatMap(d -> d.episodios().stream()
//                            .map(e -> new Episodio(d.numero(), e)))
//                    .collect(Collectors.toList());
//
//            serieEncontrada.setEpisodios(episodios);
//            repositorio.save(serieEncontrada);
        } else {
            System.out.println("Série não encontrada!");
        }
    }

    private void listarSeriesBuscadas(){
        series = repositorio.findAll();
        series.stream()
                .sorted(Comparator.comparing(Cadastros::getGenero))
                .forEach(System.out::println);
    }

    private void buscarSeriePorTitulo() {
        System.out.println("Escolha um série pelo nome: ");
        var nomeSerie = leitura.nextLine();
        serieBusca = repositorio.findByEmailContainingIgnoreCase(nomeSerie);

        if (serieBusca.isPresent()) {
            System.out.println("Dados da série: " + serieBusca.get());

        } else {
            System.out.println("Série não encontrada!");
        }

    }

    private void buscarSeriesPorAtor() {
        System.out.println("Qual o nome para busca?");
        var nomeAtor = leitura.nextLine();
        System.out.println("Avaliações a partir de que valor? ");
        var senha = leitura.nextLine();
        List<Cadastros> seriesEncontradas = repositorio.findByEmailContainingIgnoreCaseAndSenhaEqual(nomeAtor, senha);
        System.out.println("Séries em que " + nomeAtor + " trabalhou: ");
        seriesEncontradas.forEach(s ->
                System.out.println(s.getNome() + " avaliação: " + s.getAvaliacao()));
    }

    private void buscarTop5Series() {
        List<Cadastros> cadastrosTop = repositorio.findTop5ByOrderByAvaliacaoDesc();
        cadastrosTop.forEach(s ->
                System.out.println(s.getNome() + " avaliação: " + s.getAvaliacao()));
    }

    private void buscarSeriesPorCategoria() {
        System.out.println("Deseja buscar séries de que categoria/gênero? ");
        var nomeGenero = leitura.nextLine();
        Categoria categoria = Categoria.fromPortugues(nomeGenero);
        List<Cadastros> seriesPorCategoria = repositorio.findByGenero(categoria);
        System.out.println("Séries da categoria " + nomeGenero);
        seriesPorCategoria.forEach(System.out::println);
    }

    private void filtrarSeriesPorTemporadaEAvaliacao(){
        System.out.println("Filtrar séries até quantas temporadas? ");
        var totalTemporadas = leitura.nextInt();
        leitura.nextLine();
        System.out.println("Com avaliação a partir de que valor? ");
        var avaliacao = leitura.nextDouble();
        leitura.nextLine();
        List<Cadastros> filtroSeries = repositorio.seriesPorTemporadaEAValiacao(totalTemporadas, avaliacao);
        System.out.println("*** Séries filtradas ***");
        filtroSeries.forEach(s ->
                System.out.println(s.getNome() + "  - avaliação: " + s.getAvaliacao()));
    }

    private void buscarEpisodioPorTrecho(){
//        System.out.println("Qual o nome do episódio para busca?");
//        var trechoEpisodio = leitura.nextLine();
//        List<Episodio> episodiosEncontrados = repositorio.episodiosPorTrecho(trechoEpisodio);
//        episodiosEncontrados.forEach(e ->
//                System.out.printf("Série: %s Temporada %s - Episódio %s - %s\n",
//                        e.getSerie().getNome(), e.getTemporada(),
//                        e.getNumeroEpisodio(), e.getTitulo()));
    }

    private void topEpisodiosPorSerie(){
//        buscarSeriePorTitulo();
//        if(serieBusca.isPresent()){
//            Cadastros cadastros = serieBusca.get();
//            List<Episodio> topEpisodios = repositorio.topEpisodiosPorSerie(cadastros);
//            topEpisodios.forEach(e ->
//                    System.out.printf("Série: %s Temporada %s - Episódio %s - %s Avaliação %s\n",
//                            e.getSerie().getNome(), e.getTemporada(),
//                            e.getNumeroEpisodio(), e.getTitulo(), e.getAvaliacao()));
//        }
    }
    private void buscarEpisodiosDepoisDeUmaData(){
        buscarSeriePorTitulo();
        if(serieBusca.isPresent()){
            Cadastros cadastros = serieBusca.get();
            System.out.println("Digite o ano limite de lançamento");
            var anoLancamento = leitura.nextInt();
            leitura.nextLine();

            List<Episodio> episodiosAno = repositorio.episodiosPorSerieEAno(cadastros, anoLancamento);
            episodiosAno.forEach(System.out::println);
        }
    }
}