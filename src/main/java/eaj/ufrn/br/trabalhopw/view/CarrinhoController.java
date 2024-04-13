package eaj.ufrn.br.trabalhopw.view;

import eaj.ufrn.br.trabalhopw.dominio.Carrinho;
import eaj.ufrn.br.trabalhopw.dominio.Produto;
import eaj.ufrn.br.trabalhopw.persistencia.ProdutoDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;
import java.util.ArrayList;

@Controller
public class CarrinhoController {

    public static ArrayList<Produto> getProdutosCarrinho(String arrayProdutos){
        ArrayList<Produto> produtos = new ArrayList<Produto>();

        for(String i : arrayProdutos.split("_")){
            boolean encontraProduto = false;
            Produto prodCookie = ProdutoDAO.buscarProduto(Integer.parseInt(i));
            prodCookie.zerarEstoque();
            prodCookie.incrementaEstoque();

            for(Produto p : produtos){
                if(p.getId() == prodCookie.getId()){
                    encontraProduto = true;
                    p.incrementaEstoque();
                }
            }

            if(!encontraProduto)
                produtos.add(prodCookie);
        }

        return produtos;
    }

    @RequestMapping(value = "/LojaOnline/CarrinhoServlet", method = RequestMethod.GET)
    public void carrinhoServlet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String command = request.getParameter("comando");
        Carrinho carrinho = new Carrinho(new ArrayList<Produto>());

        HttpSession sessao = request.getSession(false);

        if(sessao != null) {
            String parametro = sessao.getAttribute("usuario").toString();
            String usuario[] = parametro.split("@");
            String email = usuario[0] + "_" + usuario[1];

            if (command.equals("add")) {

                Cookie[] cookieCarrinho = request.getCookies();
                Integer id = Integer.parseInt(request.getParameter("id"));
                String arrayProdutos = "";
                Produto produtoAdd = ProdutoDAO.buscarProduto(id);
                produtoAdd.zerarEstoque();
                String stringProduto = id + "_";

                for (Cookie c : cookieCarrinho) {
                    if (c.getName().equals(email)) {
                        arrayProdutos = c.getValue();
                        break;
                    }
                }

                arrayProdutos += stringProduto;
                Cookie arrayCokiee = new Cookie(email, arrayProdutos);
                arrayCokiee.setMaxAge(60 * 24 * 3600);
                response.addCookie(arrayCokiee);

                carrinho.setProdutos(getProdutosCarrinho(arrayProdutos));
                System.out.println(carrinho);
                sessao.setAttribute("carrinho", carrinho);

                //remover do estoque
            } else if (command.equals("remove")) {

                //remover do carrinho
                //adicionar ao estoque
            }


            var encaminhar = request.getRequestDispatcher("/LojaOnline");
            encaminhar.forward(request, response);
//            response.sendRedirect("/LojaOnline");
        }else{
            response.sendRedirect("./index.html");
        }
    }

    @RequestMapping(value = "/Carrinho", method = RequestMethod.GET)
    public void VerCarrinhoController(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession sessao = request.getSession(false);

        if(sessao != null) {
            Carrinho carrinho = (Carrinho) sessao.getAttribute("carrinho");
            ArrayList<Produto> Lista = carrinho.getProdutos();

            GerarHTML gerarHTML = new GerarHTML(request, response);

            gerarHTML.abrirHTML("Carrinho");

            String cabecalhos[] = {"Nome", "Descrição", "Preço", "Estoque","Remover"};
            String caption = "Lista Carrinho";
            gerarHTML.gerarTabelaCarrinho(carrinho, cabecalhos, caption);

            gerarHTML.fecharHTML();
        }else{
            response.sendRedirect("index.html?msg=Usuario_nao_autorizado");
        }
    }

}
