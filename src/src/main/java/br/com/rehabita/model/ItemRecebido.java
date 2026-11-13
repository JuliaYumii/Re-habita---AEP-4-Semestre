package br.com.rehabita.model;

public class ItemRecebido extends MovimentacaoItem {

    private int idDoacao;

    public ItemRecebido() {
    }

    public ItemRecebido(int id, int quantidade, ItemDoacao itemDoacao, int idDoacao) {
        super(id, quantidade, itemDoacao);
        this.idDoacao = idDoacao;
    }

    @Override
    public void aplicarNoEstoque() {
        if (getItemDoacao() != null) {
            getItemDoacao().adicionarSaldo(getQuantidade());
        }
    }

    public int getIdDoacao() {
        return idDoacao;
    }

    public void setIdDoacao(int idDoacao) {
        this.idDoacao = idDoacao;
    }
}
