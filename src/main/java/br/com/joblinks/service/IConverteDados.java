package br.com.joblinks.service;

public interface IConverteDados {
    <T> T  obterDados(String json, Class<T> classe);
}
