package br.com.rehabita.model;

import java.time.LocalDate;

public class Membro extends Pessoa {

    private int id;
    private String parentesco;

    public Membro() {
    }

    public Membro(int id, String nome, String cpf, LocalDate dataNascimento,
                  String telefone, String parentesco) {
        super(nome, cpf, dataNascimento, telefone);
        this.id = id;
        this.parentesco = parentesco;
    }

    @Override
    public String getDescricao() {
        return getNome() + " (" + parentesco + ", " + calcularIdade() + " anos)";
    }

    @Override
    public boolean isCpfObrigatorio() {
        return false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getParentesco() {
        return parentesco;
    }

    public void setParentesco(String parentesco) {
        this.parentesco = parentesco;
    }
}
