package eaj.ufrn.br.trabalhopw.view;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;

@Controller
public class CadastroController {

    @RequestMapping(value = "/cadastro", method = RequestMethod.GET)
    public void paginaCadastro(HttpServletRequest request, HttpServletResponse response) throws IOException {
        GerarHTML pagina = new GerarHTML(request, response);

        pagina.abrirHTML("Cadastro");
        String labels[] = {"Nome", "Email", "Senha"};
        String ids[] = {"email", "nome", "senha"};

        String action = "/cadastrar";
        String buttonName = "Cadastrar";
        pagina.gerarForm(labels, ids, buttonName, action);
        pagina.fecharHTML();
    }
}
