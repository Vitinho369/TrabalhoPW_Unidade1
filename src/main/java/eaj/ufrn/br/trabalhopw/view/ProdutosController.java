package eaj.ufrn.br.trabalhopw.view;

import eaj.ufrn.br.trabalhopw.dominio.Carrinho;
import eaj.ufrn.br.trabalhopw.dominio.Produto;
import eaj.ufrn.br.trabalhopw.persistencia.ProdutoDAO;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;
import java.util.ArrayList;

import static eaj.ufrn.br.trabalhopw.view.CarrinhoController.getProdutosCarrinho;

@Controller
public class ProdutosController {

    private ArrayList<Produto> listaProdutos;

    @RequestMapping(value = "/LojaOnline")
    public void doListarProdutos(HttpServletRequest request, HttpServletResponse response) throws IOException {

        GerarHTML gerarPagina = new GerarHTML(request, response);
        HttpSession sessao = request.getSession(false);

        if(sessao != null) {
            Carrinho carrinho = (Carrinho) sessao.getAttribute("carrinho");

            if(carrinho ==  null){
                Object teste = sessao.getAttribute("usuario");
                if(teste == null){
                    response.sendRedirect("index.html");
                    return;
                }
                String parametro = sessao.getAttribute("usuario").toString();

                String usuario[] = parametro.split("@");

                if(2 > usuario.length){
                    response.sendRedirect("index.html");
                    return;
                }
                String email = usuario[0] + "_" + usuario[1];
                Cookie[] cookieCarrinho = request.getCookies();
                String arrayProdutos = "_";

                for (Cookie c : cookieCarrinho) {
                    if (c.getName().equals(email)) {
                        arrayProdutos = c.getValue();
                        break;
                    }
                }

                carrinho = new Carrinho(getProdutosCarrinho(arrayProdutos));

                sessao.setAttribute("carrinho", carrinho);
            }

            gerarPagina.abrirHTML("Listar Produtos");
            String cabecalhos[] = {"Nome", "Descrição", "Preço", "Estoque"};

            this.listaProdutos = ProdutoDAO.listarProdutos();
            gerarPagina.gerarTabelaProdutos(this.listaProdutos, cabecalhos, "Lista Produtos", carrinho);
            gerarPagina.fecharHTML();
        }
    }

    @RequestMapping(value = "/paginaCadProd", method = RequestMethod.GET)
    public void criarPaginaProd(HttpServletRequest request, HttpServletResponse response) throws IOException{
        GerarHTML pagina = new GerarHTML(request, response);

        HttpSession sessao = request.getSession();

        if(sessao != null) {
            String tipoUsuario = (String) sessao.getAttribute("tipo");
            if(tipoUsuario != null && tipoUsuario.equals("lojista")) {
                pagina.abrirHTML("Cadastra Produto");

                String labels[] = {"Nome", "Descrição", "Preço", "Estoque"};
                String ids[] = {"nome", "descricao", "preco", "estoque"};

                String action = "/cadastrarProduto";
                String buttonName = "Cadastrar Produto";

                pagina.gerarForm(labels, ids, buttonName, action);
                pagina.gerarLink("/LojaOnline", "Voltar");
                pagina.fecharHTML();
            }else{
                response.sendRedirect("./index.html");
            }
        }
    }

    @RequestMapping(value = "/cadastrarProduto", method = RequestMethod.POST)
    public void cadastrarProduto(HttpServletRequest request, HttpServletResponse response) throws IOException{
        String nome = request.getParameter("nome");
        String descricao = request.getParameter("descricao");
        Float preco = Float.parseFloat(request.getParameter("preco"));
        Integer quantidade = Integer.parseInt(request.getParameter("estoque"));

        Produto produto = new Produto(preco, nome, descricao, quantidade);

        if(!nome.trim().isEmpty() && !descricao.trim().isEmpty() && preco > 0 && quantidade > 0) {
            ProdutoDAO.Cadastrar_Produto(produto);
            response.sendRedirect("/LojaOnline");
        }else{
            response.sendRedirect("/paginaCadProd?msg=Nao_eh_possivel_cadastrar");
        }
    }
}
