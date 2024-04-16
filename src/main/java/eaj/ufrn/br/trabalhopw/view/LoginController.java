package eaj.ufrn.br.trabalhopw.view;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;

//@Controller
public class LoginController {

    @RequestMapping(value = {"/", "index.html"}, method = RequestMethod.GET)
    public void paginaInicial(HttpServletRequest request, HttpServletResponse response) throws IOException {
        GerarHTML pagina = new GerarHTML(request, response);
        pagina.abrirHTML("Loja");

        String labels[] = {"Email", "Senha"};
        String ids[] = {"email", "senha"};

        String action = "/logar";
        String buttonName = "Entrar";
        pagina.gerarForm(labels, ids, buttonName, action);

        pagina.gerarLink("/cadastro", "Crie uma conta");
        pagina.fecharHTML();
    }
}
