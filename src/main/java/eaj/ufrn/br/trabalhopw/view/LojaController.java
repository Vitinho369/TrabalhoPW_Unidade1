package eaj.ufrn.br.trabalhopw.view;

import eaj.ufrn.br.trabalhopw.dominio.Cliente;
import eaj.ufrn.br.trabalhopw.persistencia.ClienteDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;
import java.net.http.HttpResponse;

@Controller
public class LojaController {

    @RequestMapping(value = "/logar", method = RequestMethod.POST)
    public void doLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String email = request.getParameter("email");
        String senha = request.getParameter("senha");

        Cliente cliente = Cliente.clienteLogin(email,senha);
        if(cliente != null){
            System.out.println("Logado");
            return;
        }

        System.out.println("Cliente não cadastrado");
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
}
