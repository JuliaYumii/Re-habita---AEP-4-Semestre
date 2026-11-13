package br.com.rehabita.model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class Familia {

    private int id;
    private Responsavel responsavel;
    private Bairro bairro;
    private String cep;
    private String rua;
    private String numero;
    private String complemento;
    private String tipoMoradia;
    private String faixaRenda;
    private StatusFamilia status;
    private LocalDate dataCadastro;

    private List<Membro> membros;
    private List<Atendimento> atendimentos;
    private List<HistoricoStatus> historicoStatus;

    public Familia() {
        this.membros = new ArrayList<>();
        this.atendimentos = new ArrayList<>();
        this.historicoStatus = new ArrayList<>();
        this.status = StatusFamilia.NOVO;
        this.dataCadastro = LocalDate.now();
    }

    public void adicionarMembro(Membro membro) {
        if (membro != null) {
            membros.add(membro);
        }
    }

    public int getQuantidadePessoas() {
        return 1 + membros.size();
    }

    public void registrarAtendimento(Atendimento atendimento) {
        if (atendimento != null) {
            atendimentos.add(atendimento);
        }
    }

      public void alterarStatus(StatusFamilia novoStatus, String motivo) {
        StatusFamilia anterior = this.status;
        this.status = novoStatus;

        HistoricoStatus historico = new HistoricoStatus();
        historico.setStatusAnterior(anterior);
        historico.setStatusNovo(novoStatus);
        historico.setMotivo(motivo);
        historico.setDataAlteracao(LocalDate.now());
        historicoStatus.add(historico);
    }

    public NivelUrgencia getUltimaUrgencia() {
        if (atendimentos == null || atendimentos.isEmpty()) {
            return null;
        }
        return atendimentos.get(atendimentos.size() - 1).getNivelUrgencia();
    }

    public long diasSemEntrega() {
        LocalDate referencia = dataCadastro;
        if (atendimentos != null && !atendimentos.isEmpty()) {
            Atendimento ultimo = atendimentos.get(atendimentos.size() - 1);
            if (ultimo.getDataVisita() != null) {
                referencia = ultimo.getDataVisita();
            }
        }
        return ChronoUnit.DAYS.between(referencia, LocalDate.now());
    }

    public boolean podeSerExcluida() {
        return status == StatusFamilia.INATIVO
                && (atendimentos == null || atendimentos.isEmpty());
    }

     public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Responsavel getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(Responsavel responsavel) {
        this.responsavel = responsavel;
    }

    public Bairro getBairro() {
        return bairro;
    }

    public void setBairro(Bairro bairro) {
        this.bairro = bairro;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getTipoMoradia() {
        return tipoMoradia;
    }

    public void setTipoMoradia(String tipoMoradia) {
        this.tipoMoradia = tipoMoradia;
    }

    public String getFaixaRenda() {
        return faixaRenda;
    }

    public void setFaixaRenda(String faixaRenda) {
        this.faixaRenda = faixaRenda;
    }

    public StatusFamilia getStatus() {
        return status;
    }

    public void setStatus(StatusFamilia status) {
        this.status = status;
    }

    public LocalDate getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(LocalDate dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public List<Membro> getMembros() {
        return membros;
    }

    public void setMembros(List<Membro> membros) {
        this.membros = membros;
    }

    public List<Atendimento> getAtendimentos() {
        return atendimentos;
    }

    public void setAtendimentos(List<Atendimento> atendimentos) {
        this.atendimentos = atendimentos;
    }

    public List<HistoricoStatus> getHistoricoStatus() {
        return historicoStatus;
    }

    public void setHistoricoStatus(List<HistoricoStatus> historicoStatus) {
        this.historicoStatus = historicoStatus;
    }
}
