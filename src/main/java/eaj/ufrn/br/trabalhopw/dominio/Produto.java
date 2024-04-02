package eaj.ufrn.br.trabalhopw.dominio;

public class Produto {
    int id;

    int id_lojista;
    float preco;
    String nome;
    String Descricao;
    int estoque;

    public Produto(int id, int id_lojista, float preco, String nome, String descricao, int estoque) {
        super();
        this.id = id;
        this.id_lojista = id_lojista;
        this.preco = preco;
        this.nome = nome;
        this.Descricao = descricao;
        this.estoque = estoque;
    }

    public int getId_lojista() {
        return id_lojista;
    }

    public void setId_lojista(int id_lojista) {
        this.id_lojista = id_lojista;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public float getPreco() {
        return preco;
    }
    public void setPreco(int preco) {
        this.preco = preco;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getDescricao() {
        return Descricao;
    }
    public void setDescricao(String descricao) {
        Descricao = descricao;
    }
    public int getEstoque() {
        return estoque;
    }
    public void incrementaEstoque() {
        this.estoque++;
    }
    public void diminuiEstoque() {
        this.estoque--;
    }
}
