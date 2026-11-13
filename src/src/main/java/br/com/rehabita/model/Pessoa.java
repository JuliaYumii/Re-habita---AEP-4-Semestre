package br.com.rehabita.model;

import java.time.LocalDate;
import java.time.Period;

public abstract class Pessoa {

    private String nome;
    private String cpf;
    private LocalDate dataNascimento;
    private String telefone;

    public Pessoa() {
    }

    public Pessoa(String nome, String cpf, LocalDate dataNascimento, String telefone) {
        this.nome = nome;
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
        this.telefone = telefone;
    }

    public int calcularIdade() {
        if (dataNascimento == null) {
            return 0;
        }
        return Period.between(dataNascimento, LocalDate.now()).getYears();
    }

    public boolean validarCpf() {
        if (cpf == null || cpf.isBlank()) {
            return !isCpfObrigatorio();
        }
        return cpf.matches("\\d{11}");
    }

    public abstract String getDescricao();

    public abstract boolean isCpfObrigatorio();

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
}
