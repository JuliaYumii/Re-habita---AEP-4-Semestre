package br.com.rehabita.model;

public abstract class MovimentacaoItem {

    private int id;
    private int quantidade;
    private ItemDoacao itemDoacao;

    public MovimentacaoItem() {
    }

    public MovimentacaoItem(int id, int quantidade, ItemDoacao itemDoacao) {
        this.id = id;
        this.quantidade = quantidade;
        this.itemDoacao = itemDoacao;
    }

    public abstract void aplicarNoEstoque();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public ItemDoacao getItemDoacao() {
        return itemDoacao;
    }

    public void setItemDoacao(ItemDoacao itemDoacao) {
        this.itemDoacao = itemDoacao;
    }
}
