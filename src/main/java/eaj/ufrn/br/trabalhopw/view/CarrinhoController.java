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

        if(!arrayProdutos.equals("")) {
            for (String i : arrayProdutos.split("_")) {
                boolean encontraProduto = false;
                Produto prodCookie = ProdutoDAO.buscarProduto(Integer.parseInt(i));
                if (prodCookie != null) {
                    prodCookie.zerarEstoque();
                    prodCookie.incrementaEstoque();

                    for (Produto p : produtos) {
                        if (p.getId() == prodCookie.getId()) {
                            encontraProduto = true;
                            p.incrementaEstoque();
                        }
                    }

                    if (!encontraProduto)
                        produtos.add(prodCookie);
                }
            }
        }
        return produtos;
    }

    @RequestMapping(value = "/LojaOnline/CarrinhoServlet", method = RequestMethod.GET)
    public void carrinhoServlet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession sessao = request.getSession(false);

        if(sessao != null) {
            String command = request.getParameter("comando");
            Carrinho carrinho = new Carrinho(new ArrayList<Produto>());

            String parametro = sessao.getAttribute("usuario").toString();
            String usuario[] = parametro.split("@");
            String email = usuario[0] + "_" + usuario[1];

            Cookie[] cookieCarrinho = request.getCookies();
            Integer id = Integer.parseInt(request.getParameter("id"));
            String arrayProdutos = "";

            for (Cookie c : cookieCarrinho) {
                if (c.getName().equals(email)) {
                    arrayProdutos = c.getValue();
                    break;
                }
            }

            if (command.equals("add")) {
                arrayProdutos += id + "_";

                carrinho.setProdutos(getProdutosCarrinho(arrayProdutos));

                Cookie arrayCokiee = new Cookie(email, arrayProdutos);
                arrayCokiee.setPath("../");
                arrayCokiee.setMaxAge(48 * 3600);
                response.addCookie(arrayCokiee);

                sessao.setAttribute("carrinho", carrinho);
                var encaminhar = request.getRequestDispatcher("/LojaOnline");
                encaminhar.forward(request, response);

            } else if (command.equals("remove")) {
                carrinho.setProdutos(getProdutosCarrinho(arrayProdutos));

                carrinho.getProduto(id).diminuiEstoque();

                if(carrinho.getProduto(id).getEstoque() == 0){
                    carrinho.removeProduto(id);
                }

                String arrayRemove = arrayProdutos;
                arrayProdutos = "";
                int indiceRemove = arrayRemove.indexOf(String.valueOf(id));

                for(int i=0; i < indiceRemove;i++){
                    arrayProdutos += String.valueOf(arrayRemove.charAt(i));
                }

                for(int i=indiceRemove+2; i < arrayRemove.length();i++){
                    arrayProdutos += String.valueOf(arrayRemove.charAt(i));
                }

                sessao.setAttribute("carrinho", carrinho);
                Cookie arrayCokiee = new Cookie(email, arrayProdutos);
                arrayCokiee.setPath("../");
                arrayCokiee.setMaxAge(48 * 3600);
                response.addCookie(arrayCokiee);

                var encaminhar = request.getRequestDispatcher("/Carrinho");
                encaminhar.forward(request, response);
            }

        }else{
            var writer = response.getWriter();
            response.sendRedirect("../index.html");
        }
    }

    @RequestMapping(value = "/Carrinho", method = RequestMethod.GET)
    public void verCarrinho(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession sessao = request.getSession(false);
        Carrinho carrinho = (Carrinho) sessao.getAttribute("carrinho");

        if(sessao != null && carrinho != null) {
            ArrayList<Produto> Lista = carrinho.getProdutos();

            GerarHTML gerarHTML = new GerarHTML(request, response);

            gerarHTML.abrirHTML("Carrinho");

            String cabecalhos[] = {"Nome", "Descrição", "Preço", "Estoque","Remover"};
            String caption = "Lista Carrinho";
            gerarHTML.gerarTabelaCarrinho(carrinho, cabecalhos, caption);

            gerarHTML.fecharHTML();
        }else{
            response.sendRedirect("./index.html?msg=Usuario_nao_autorizado");
        }
    }

    @RequestMapping(value = "/FinalizaCompra", method = RequestMethod.GET)
    public void finalizaCompra(HttpServletRequest request, HttpServletResponse response) throws IOException{

        HttpSession sessao = request.getSession(false);

        if(sessao != null) {
            String parametro = sessao.getAttribute("usuario").toString();
            String usuario[] = parametro.split("@");
            String email = usuario[0] + "_" + usuario[1];
            System.out.println(email);
            Cookie[] cookieCarrinho = request.getCookies();
            String arrayProdutos = "";

            for (Cookie c : cookieCarrinho) {
                System.out.println(c.getName());
                if (c.getName().equals(email)) {
                    arrayProdutos = c.getValue();
                    break;
                }
            }

            Carrinho carrinho = new Carrinho(getProdutosCarrinho(arrayProdutos));
            System.out.println(arrayProdutos);
            for(Produto p: carrinho.getProdutos()){
                Produto produtoAtt = ProdutoDAO.buscarProduto(p.getId());
                System.out.println(p.getNome());
                produtoAtt.diminuiEstoque(p.getEstoque());
                ProdutoDAO.atualizarEstoque(produtoAtt.getId(), produtoAtt.getEstoque());
            }

            Cookie arrayCokiee = new Cookie(email, arrayProdutos);
            arrayCokiee.setMaxAge(-1);
            response.sendRedirect("./LojaOnline");
        }else{
            response.sendRedirect("../index.html");
        }
    }
}
