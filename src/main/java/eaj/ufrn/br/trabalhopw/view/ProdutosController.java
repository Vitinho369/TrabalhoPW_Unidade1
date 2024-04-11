package eaj.ufrn.br.trabalhopw.view;

import eaj.ufrn.br.trabalhopw.dominio.Produto;
import eaj.ufrn.br.trabalhopw.persistencia.ProdutoDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;
import java.util.ArrayList;

@Controller
public class ProdutosController {

    @RequestMapping(value = "/LojaOnline")
    public void doListarProdutos(HttpServletRequest request, HttpServletResponse response) throws IOException {

        ArrayList<Produto> produtosListar = ProdutoDAO.listarProdutos();

        GenerateHTML gerarPagina = new GenerateHTML(request, response);

        gerarPagina.openHTML("Listar Produtos");
        String cabecalhos[] = {"Nome", "Descrição", "Preço", "Estoque"};

        gerarPagina.generateTable(produtosListar,cabecalhos, "Lista Produtos");
        gerarPagina.closeHTML();
    }

    @RequestMapping(value = "/paginaCadProd", method = RequestMethod.GET)
    public void criarPaginaProd(HttpServletRequest request, HttpServletResponse response) throws IOException{
        GenerateHTML pagina = new GenerateHTML(request, response);

        pagina.openHTML("Cadastra Produto");

        String labels[] = {"Nome", "Descrição", "Preço", "Estoque"};
        String ids[] = {"nome", "descricao", "preco", "estoque"};

        String action = "/cadastrarProduto";
        String buttonName = "Cadastrar Produto";

        pagina.generateForm(labels, ids, buttonName, action);
        pagina.closeHTML();
    }

    @RequestMapping(value = "/cadastrarProduto", method = RequestMethod.POST)
    public void cadastrarProduto(HttpServletRequest request, HttpServletResponse response) throws IOException{
        String nome = request.getParameter("nome");
        String descricao = request.getParameter("descricao");
        Float preco = Float.parseFloat(request.getParameter("preco"));
        Integer quantidade = Integer.parseInt(request.getParameter("estoque"));

        Produto produto = new Produto(preco, nome, descricao, quantidade);

        if(!nome.trim().equals("") && !descricao.trim().equals("") && preco != null && quantidade != null) {
            ProdutoDAO.Cadastrar_Produto(produto);
            response.sendRedirect("/LojaOnline");
        }
    }
}
