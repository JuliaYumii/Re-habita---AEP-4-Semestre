package br.com.rehabita.model;

import java.time.LocalDate;

public class Responsavel extends Pessoa {

    private String escolaridade;
    private String situacaoTrabalho;

    public Responsavel() {
    }

    public Responsavel(String nome, String cpf, LocalDate dataNascimento,
                       String telefone, String escolaridade, String situacaoTrabalho) {
        super(nome, cpf, dataNascimento, telefone);
        this.escolaridade = escolaridade;
        this.situacaoTrabalho = situacaoTrabalho;
    }

    @Override
    public String getDescricao() {
        return getNome() + " (responsavel, " + calcularIdade() + " anos)";
    }

    @Override
    public boolean isCpfObrigatorio() {
        return true;
    }

    public String getEscolaridade() {
        return escolaridade;
    }

    public void setEscolaridade(String escolaridade) {
        this.escolaridade = escolaridade;
    }

    public String getSituacaoTrabalho() {
        return situacaoTrabalho;
    }

    public void setSituacaoTrabalho(String situacaoTrabalho) {
        this.situacaoTrabalho = situacaoTrabalho;
    }
}
