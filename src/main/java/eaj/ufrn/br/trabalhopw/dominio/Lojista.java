package eaj.ufrn.br.trabalhopw.dominio;

import eaj.ufrn.br.trabalhopw.persistencia.ClienteDAO;
import eaj.ufrn.br.trabalhopw.persistencia.LojistaDAO;

public class Lojista extends Usuario {
    public Lojista(String nome, String email, String senha){
        super(nome,email,senha);
    }

    public static Lojista lojistaLogin(String email, String senha){
        return LojistaDAO.lojistaLogado(email,senha);
    }
    void cadastrarProduto(){}
    void exibirProdutos(){}
}
