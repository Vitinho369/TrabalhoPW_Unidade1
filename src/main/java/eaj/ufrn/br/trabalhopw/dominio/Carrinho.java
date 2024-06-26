package eaj.ufrn.br.trabalhopw.dominio;
import java.util.ArrayList;
public class Carrinho {
    public Carrinho(ArrayList<Produto> produtos) {
        super();
        this.produtos = produtos;
    }
    ArrayList<Produto> produtos;
    public ArrayList<Produto> getProdutos() {
        return produtos;
    }
    public void setProdutos(ArrayList<Produto> produtos) {
        this.produtos = produtos;
    }
    public Produto getProduto (int id){
        Produto mp = null;
        for (Produto p : produtos){
            if (p.getId() == id){
                return p;
            }
        }
        return mp;
    }
    public void removeProduto (int id){
        Produto p = getProduto(id);
        produtos.remove(p);
    }
    public void addProduto (Produto p){
        produtos.add(p);
    }

    public float TotalCompra(){
        float resultado = 0;

        for (Produto produto : this.produtos){
            resultado += produto.getPreco() * produto.getEstoque();
        }
        return resultado;
    }
}
