package br.com.rehabita.model;

public class Doador {

    private int id;
    private TipoDoador tipo;
    private String nome;
    private String cpfCnpj;
    private String telefone;
    private String email;

    public Doador() {
    }

    public Doador(int id, TipoDoador tipo, String nome, String cpfCnpj,
                  String telefone, String email) {
        this.id = id;
        this.tipo = tipo;
        this.nome = nome;
        this.cpfCnpj = cpfCnpj;
        this.telefone = telefone;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public TipoDoador getTipo() {
        return tipo;
    }

    public void setTipo(TipoDoador tipo) {
        this.tipo = tipo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpfCnpj() {
        return cpfCnpj;
    }

    public void setCpfCnpj(String cpfCnpj) {
        this.cpfCnpj = cpfCnpj;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
