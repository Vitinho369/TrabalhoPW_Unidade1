package eaj.ufrn.br.trabalhopw.view;

import eaj.ufrn.br.trabalhopw.dominio.Produto;
import eaj.ufrn.br.trabalhopw.persistencia.ProdutoDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.ArrayList;

@Controller
public class ProdutosController {

    @RequestMapping(value = "/LojaOnline")
    public void doListarProdutos(HttpServletRequest request, HttpServletResponse response) throws IOException {

        ArrayList<Produto> produtosListar = ProdutoDAO.listarProdutos();

        GenerateHTML gerarPagina = new GenerateHTML(request, response);

        gerarPagina.openHTML("Listar Produtos");
        ArrayList<String> cabecalhos = new ArrayList<String>();
        cabecalhos.add("Nome");
        cabecalhos.add("Descrição");
        cabecalhos.add("Preço");
        cabecalhos.add("Estoque");

        gerarPagina.generateTable(produtosListar,cabecalhos, "Lista Produtos");
        gerarPagina.closeHTML();
    }
}
