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

        if(!arrayProdutos.isEmpty()) {
            for (String i : arrayProdutos.split("_")) {
                boolean encontraProduto = false;
                if (!i.isEmpty()) {
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
        }
        return produtos;
    }

    @RequestMapping(value = "/LojaOnline/CarrinhoServlet", method = RequestMethod.GET)
    public void carrinhoServlet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession sessao = request.getSession(false);

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
            arrayCokiee.setMaxAge(48 * 3600);
            response.addCookie(arrayCokiee);

            sessao.setAttribute("carrinho", carrinho);
            var encaminhar = request.getRequestDispatcher("/LojaOnline");
            encaminhar.forward(request, response);

        } else if (command.equals("remove")) {
            carrinho.setProdutos(getProdutosCarrinho(arrayProdutos));

            carrinho.getProduto(id).diminuiEstoque();

            if (carrinho.getProduto(id).getEstoque() == 0) {
                carrinho.removeProduto(id);
            }

            String arrayRemove = arrayProdutos;
            arrayProdutos = "";
            int indiceRemove = arrayRemove.indexOf(String.valueOf(id));

            for (int i = 0; i < indiceRemove; i++) {
                arrayProdutos += String.valueOf(arrayRemove.charAt(i));
            }

            for (int i = indiceRemove + (String.valueOf(id).length()+1); i < arrayRemove.length(); i++) {
                arrayProdutos += String.valueOf(arrayRemove.charAt(i));
            }

            sessao.setAttribute("carrinho", carrinho);
            Cookie arrayCokiee = new Cookie(email, arrayProdutos);
            arrayCokiee.setMaxAge(48 * 3600);
            response.addCookie(arrayCokiee);

            var encaminhar = request.getRequestDispatcher("/Carrinho");
            encaminhar.forward(request, response);
        }

    }

    @RequestMapping(value = "/Carrinho", method = RequestMethod.GET)
    public void verCarrinho(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession sessao = request.getSession(false);
        Carrinho carrinho = (Carrinho) sessao.getAttribute("carrinho");

        GerarHTML gerarHTML = new GerarHTML(request, response);

        gerarHTML.abrirHTML("Carrinho");

        String cabecalhos[] = {"Nome", "Descrição", "Preço", "Estoque","Remover"};
        String caption = "Lista Carrinho";

        gerarHTML.gerarTabelaCarrinho(carrinho, cabecalhos, caption);

        gerarHTML.fecharHTML();
    }

    @RequestMapping(value = "/FinalizaCompra", method = RequestMethod.GET)
    public void finalizaCompra(HttpServletRequest request, HttpServletResponse response) throws IOException{
        HttpSession sessao = request.getSession(false);

        String parametro = sessao.getAttribute("usuario").toString();
        String usuario[] = parametro.split("@");
        String email = usuario[0] + "_" + usuario[1];
        String arrayProdutos = "";

        Carrinho carrinho = (Carrinho) sessao.getAttribute("carrinho");

        for(Produto p: carrinho.getProdutos()){
            Produto produtoAtt = ProdutoDAO.buscarProduto(p.getId());
            produtoAtt.diminuiEstoque(p.getEstoque());

            if(produtoAtt.getEstoque() >= 0)
                ProdutoDAO.atualizarEstoque(produtoAtt.getId(), produtoAtt.getEstoque());
        }

        sessao.removeAttribute("carrinho");
        Cookie arrayCokiee = new Cookie(email, arrayProdutos);
        arrayCokiee.setPath("/LojaOnline");
        arrayCokiee.setMaxAge(0);
        response.addCookie(arrayCokiee);

        response.sendRedirect("./LojaOnline");
    }
}
