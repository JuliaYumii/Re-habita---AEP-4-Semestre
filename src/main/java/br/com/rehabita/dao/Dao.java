package br.com.rehabita.dao;

import java.util.List;

public interface Dao<T> {

    void inserir(T entidade);

    T buscarPorId(int id);

    List<T> listarTodos();

    void atualizar(T entidade);

    void excluir(int id);
}
