package eaj.ufrn.br.trabalhopw.view;

import eaj.ufrn.br.trabalhopw.dominio.Produto;
import eaj.ufrn.br.trabalhopw.persistencia.ProdutoDAO;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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

        if (command.equals("add")){

            Cookie [] cookieCarrinho = request.getCookies();
            Integer id = Integer.parseInt(request.getParameter("id"));
            boolean carrinhoExiste = false;
            String arrayProdutos = "";
            Produto produtoAdd = ProdutoDAO.buscarProduto(id);
            String stringProduto = produtoAdd.getId() + "_";

            for(Cookie c: cookieCarrinho){
                if(c.getName().equals("arrayCarrinho")) {
                    arrayProdutos = c.getValue();
                    break;
                }
            }

            arrayProdutos += stringProduto;
            Cookie arrayCokiee = new Cookie("arrayCarrinho", arrayProdutos);
            arrayCokiee.setMaxAge(-1);
            response.addCookie(arrayCokiee);

            //remover do estoque
        }else if (command.equals("remove")){
            //remover do carrinho
            //adicionar ao estoque
        }

    }

}
