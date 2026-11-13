package br.com.rehabita.model;

import java.time.LocalDate;

public class Usuario extends Pessoa {

    private int id;
    private String email;
    private String senhaHash;
    private PerfilUsuario perfil;
    private LocalDate dataCadastro;
    private boolean ativo;

    public Usuario() {
    }

    public Usuario(int id, String nome, String cpf, LocalDate dataNascimento,
                   String telefone, String email, String senhaHash,
                   PerfilUsuario perfil, boolean ativo) {
        super(nome, cpf, dataNascimento, telefone);
        this.id = id;
        this.email = email;
        this.senhaHash = senhaHash;
        this.perfil = perfil;
        this.dataCadastro = LocalDate.now();
        this.ativo = ativo;
    }

    @Override
    public String getDescricao() {
        String descPerfil = (perfil == PerfilUsuario.COORDENADOR) ? "coordenador" : "assistente social";
        return getNome() + " (" + descPerfil + ")";
    }

    @Override
    public boolean isCpfObrigatorio() {
        return true;
    }

    public boolean autenticar(String senha) {
        if (senha == null || senhaHash == null) {
            return false;
        }
        return senhaHash.equals(senha);
    }

    public boolean podeGerenciarCadastros() {
        return perfil == PerfilUsuario.COORDENADOR;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenhaHash() {
        return senhaHash;
    }

    public void setSenhaHash(String senhaHash) {
        this.senhaHash = senhaHash;
    }

    public PerfilUsuario getPerfil() {
        return perfil;
    }

    public void setPerfil(PerfilUsuario perfil) {
        this.perfil = perfil;
    }

    public LocalDate getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(LocalDate dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }
}
