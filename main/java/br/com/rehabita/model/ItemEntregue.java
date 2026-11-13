package br.com.rehabita.model;

public class ItemEntregue extends MovimentacaoItem {

    private int idAtendimento;

    public ItemEntregue() {
    }

    public ItemEntregue(int id, int quantidade, ItemDoacao itemDoacao, int idAtendimento) {
        super(id, quantidade, itemDoacao);
        this.idAtendimento = idAtendimento;
    }

    @Override
    public void aplicarNoEstoque() {
        if (getItemDoacao() != null) {
            getItemDoacao().baixarSaldo(getQuantidade());
        }
    }

    public int getIdAtendimento() {
        return idAtendimento;
    }

    public void setIdAtendimento(int idAtendimento) {
        this.idAtendimento = idAtendimento;
    }
}
