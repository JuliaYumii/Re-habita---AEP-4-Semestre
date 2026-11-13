package br.com.rehabita.model;

public class ItemDoacao {

    private int id;
    private String nome;
    private CategoriaItem categoria;
    private String unidadeMedida;
    private int saldoEstoque;
    private boolean ativo;

    public ItemDoacao() {
        this.ativo = true;
    }

    public ItemDoacao(int id, String nome, CategoriaItem categoria,
                      String unidadeMedida, int saldoEstoque) {
        this.id = id;
        this.nome = nome;
        this.categoria = categoria;
        this.unidadeMedida = unidadeMedida;
        this.saldoEstoque = saldoEstoque;
        this.ativo = true;
    }

    public boolean temSaldo() {
        return saldoEstoque > 0;
    }

    public void adicionarSaldo(int quantidade) {
        if (quantidade > 0) {
            this.saldoEstoque += quantidade;
        }
    }

    public boolean baixarSaldo(int quantidade) {
        if (quantidade <= 0 || quantidade > saldoEstoque) {
            return false;
        }
        this.saldoEstoque -= quantidade;
        return true;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public CategoriaItem getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaItem categoria) {
        this.categoria = categoria;
    }

    public String getUnidadeMedida() {
        return unidadeMedida;
    }

    public void setUnidadeMedida(String unidadeMedida) {
        this.unidadeMedida = unidadeMedida;
    }

    public int getSaldoEstoque() {
        return saldoEstoque;
    }

    public void setSaldoEstoque(int saldoEstoque) {
        this.saldoEstoque = saldoEstoque;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }
}
