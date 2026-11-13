package br.com.rehabita.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Atendimento {

    private int id;
    private int idFamilia;
    private int idUsuario;
    private LocalDate dataVisita;
    private NivelUrgencia nivelUrgencia;
    private String observacoes;
    private LocalDate dataRetorno;

    private List<ItemEntregue> itensEntregues;

    public Atendimento() {
        this.itensEntregues = new ArrayList<>();
        this.dataVisita = LocalDate.now();
    }

    public boolean isUrgenciaAlta() {
        return nivelUrgencia == NivelUrgencia.ALTA;
    }

    public void adicionarEntrega(ItemEntregue item) {
        if (item != null) {
            itensEntregues.add(item);
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdFamilia() {
        return idFamilia;
    }

    public void setIdFamilia(int idFamilia) {
        this.idFamilia = idFamilia;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public LocalDate getDataVisita() {
        return dataVisita;
    }

    public void setDataVisita(LocalDate dataVisita) {
        this.dataVisita = dataVisita;
    }

    public NivelUrgencia getNivelUrgencia() {
        return nivelUrgencia;
    }

    public void setNivelUrgencia(NivelUrgencia nivelUrgencia) {
        this.nivelUrgencia = nivelUrgencia;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public LocalDate getDataRetorno() {
        return dataRetorno;
    }

    public void setDataRetorno(LocalDate dataRetorno) {
        this.dataRetorno = dataRetorno;
    }

    public List<ItemEntregue> getItensEntregues() {
        return itensEntregues;
    }

    public void setItensEntregues(List<ItemEntregue> itensEntregues) {
        this.itensEntregues = itensEntregues;
    }
}
