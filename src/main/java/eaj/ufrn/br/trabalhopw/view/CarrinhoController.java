package eaj.ufrn.br.trabalhopw.view;

import eaj.ufrn.br.trabalhopw.dominio.Carrinho;
import eaj.ufrn.br.trabalhopw.dominio.Produto;
import eaj.ufrn.br.trabalhopw.persistencia.ProdutoDAO;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.core.ConfigurableObjectInputStream;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;
@Controller
public class CarrinhoController {

    @RequestMapping(value = "/CarrinhoServlet", method = RequestMethod.GET)
    public void carrinhoServlet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String command = request.getParameter("comando");
        Carrinho carrinho;

        HttpSession sessao = request.getSession(false);

        if(sessao != null) {
            String parametro = sessao.getAttribute("usuario").toString();
            String usuario[] = parametro.split("@");
            String email = usuario[0] + "_" + usuario[1];

            if (command.equals("add")) {

                Cookie[] cookieCarrinho = request.getCookies();
                Integer id = Integer.parseInt(request.getParameter("id"));
                boolean carrinhoExiste = false;
                String arrayProdutos = "";
//            Produto produtoAdd = ProdutoDAO.buscarProduto(id);
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

                //remover do estoque
            } else if (command.equals("remove")) {

                //remover do carrinho
                //adicionar ao estoque
            }
        }else{
            response.sendRedirect("index.html");
        }
    }

    @RequestMapping(value = "/CarrinhoServlet", method = RequestMethod.GET)
    public void carrinho(HttpServletRequest request, HttpServletResponse response) throws IOException {

    }

}
