package br.com.rehabita.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Doacao {

    private int id;
    private Integer idDoador; // nullable - doacao pode ser anonima
    private int idUsuario;
    private LocalDate dataRecebimento;
    private String observacoes;

    private List<ItemRecebido> itensRecebidos;

    public Doacao() {
        this.itensRecebidos = new ArrayList<>();
        this.dataRecebimento = LocalDate.now();
    }

    public boolean isAnonima() {
        return idDoador == null || idDoador == 0;
    }

    public void adicionarItem(ItemRecebido item) {
        if (item != null) {
            itensRecebidos.add(item);
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getIdDoador() {
        return idDoador;
    }

    public void setIdDoador(Integer idDoador) {
        this.idDoador = idDoador;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public LocalDate getDataRecebimento() {
        return dataRecebimento;
    }

    public void setDataRecebimento(LocalDate dataRecebimento) {
        this.dataRecebimento = dataRecebimento;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public List<ItemRecebido> getItensRecebidos() {
        return itensRecebidos;
    }

    public void setItensRecebidos(List<ItemRecebido> itensRecebidos) {
        this.itensRecebidos = itensRecebidos;
    }
}
