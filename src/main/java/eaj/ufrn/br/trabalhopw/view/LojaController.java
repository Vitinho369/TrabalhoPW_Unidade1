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

import java.io.IOException;
import java.net.http.HttpResponse;

@Controller
public class LojaController {

    @RequestMapping(value = "/logar", method = RequestMethod.POST)
    public void doLogin(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String email = request.getParameter("email");
        String senha = request.getParameter("senha");

        Cliente cliente = Cliente.clienteLogin(email,senha);
        Lojista lojista = Lojista.lojistaLogin(email, senha);
        HttpSession session = request.getSession(false);

        if(cliente != null || lojista != null){

            if(session != null){
                session.invalidate();
            }
            session = request.getSession();
            session.setAttribute("logado", true);

           if(cliente != null) {
               session.setAttribute("tipo", "cliente");
           }else{
               session.setAttribute("tipo", "lojista");
           }

            session.setAttribute("usuario", email);
            response.sendRedirect("/LojaOnline");
        }else{
            if(session != null){
                session.invalidate();
                System.out.println("Cliente não cadastrado");
            }

            response.sendRedirect("./index.html?msg=Usuario_nao_autorizado");
        }

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

        boolean cadastrado = clienteDAO.cadastrar(cliente);

        if(cadastrado) {
            response.sendRedirect("index.html");
        }else{
            GerarHTML gerarHTML = new GerarHTML(request, response);
            gerarHTML.abrirHTML("Falha");
            gerarHTML.escrever("Não foi possível realizar o cadastro, já existe um usuário com este email no banco", "h2");
            gerarHTML.gerarLink("/cadastro.html","Voltar");
            gerarHTML.fecharHTML();
        }

    }

    @RequestMapping(value = "/deslogar", method = RequestMethod.GET)
    public void deslogar(HttpServletRequest request, HttpServletResponse response) throws IOException{
        HttpSession sessao = request.getSession(false);

        if(sessao != null) {
            sessao.invalidate();
        }
        response.sendRedirect("index.html");
    }
}
