package eaj.ufrn.br.trabalhopw.view;

import eaj.ufrn.br.trabalhopw.dominio.Cliente;
import eaj.ufrn.br.trabalhopw.dominio.Lojista;
import eaj.ufrn.br.trabalhopw.persistencia.ClienteDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.ArrayList;

@Controller
public class LojaController {

    @RequestMapping(value = "/logar", method = RequestMethod.POST)
    public void doLogin(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String email = request.getParameter("email");
        String senha = request.getParameter("senha");

        Cliente cliente = Cliente.clienteLogin(email,senha);
        Lojista lojista = Lojista.lojistaLogin(email, senha);

        if(cliente != null || lojista != null) {
            HttpSession session = request.getSession();
            session.setAttribute("logado", true);

            if (cliente != null) {
                session.setAttribute("tipo", "cliente");
            } else {
                session.setAttribute("tipo", "lojista");
            }
        }

        response.sendRedirect("/LojaOnline");

    }

    @RequestMapping(value = "/cadastrar", method = RequestMethod.POST)
    public void doCadastrar(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Cliente cliente = new Cliente();
        cliente.setNome(request.getParameter("nome"));
        cliente.setSenha(request.getParameter("senha"));
        cliente.setEmail(request.getParameter("email"));

        System.out.println(cliente.getSenha());
        System.out.println(cliente.getEmail());
        System.out.println(cliente.getNome());

        ClienteDAO clienteDAO = new ClienteDAO();

        clienteDAO.cadastrar(cliente);
    }


    @RequestMapping(value = "/deslogar", method = RequestMethod.GET)
    public void deslogar(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);

        session.invalidate();

        response.sendRedirect("index.html");
    }

}
