package br.com.rehabita.model;

import java.time.LocalDate;

/**
 * Registro de alteracao de status de uma familia.
 * No banco fica na tabela historico_status.
 */
public class HistoricoStatus {

    private int id;
    private int idFamilia;
    private int idUsuario;
    private StatusFamilia statusAnterior;
    private StatusFamilia statusNovo;
    private String motivo;
    private LocalDate dataAlteracao;

    public HistoricoStatus() {
        this.dataAlteracao = LocalDate.now();
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

    public StatusFamilia getStatusAnterior() {
        return statusAnterior;
    }

    public void setStatusAnterior(StatusFamilia statusAnterior) {
        this.statusAnterior = statusAnterior;
    }

    public StatusFamilia getStatusNovo() {
        return statusNovo;
    }

    public void setStatusNovo(StatusFamilia statusNovo) {
        this.statusNovo = statusNovo;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public LocalDate getDataAlteracao() {
        return dataAlteracao;
    }

    public void setDataAlteracao(LocalDate dataAlteracao) {
        this.dataAlteracao = dataAlteracao;
    }
}
