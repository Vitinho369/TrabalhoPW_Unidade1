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

@Controller
public class AutenticacaoController {

    @RequestMapping(value = "/logar", method = RequestMethod.POST)
    public void doLogin(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String email = request.getParameter("email");
        String senha = request.getParameter("senha");

        Cliente cliente = Cliente.clienteLogin(email,senha);
        Lojista lojista = Lojista.lojistaLogin(email, senha);
        HttpSession sessao = request.getSession(false);

        if(cliente != null || lojista != null){

            if(sessao != null) sessao.invalidate();

            sessao = request.getSession();
            sessao.setAttribute("logado", true);

           if(cliente != null)
               sessao.setAttribute("tipo", "cliente");
           else
               sessao.setAttribute("tipo", "lojista");


            sessao.setAttribute("usuario", email);
            response.sendRedirect("/LojaOnline");
        }else{

            if(sessao != null)
                sessao.invalidate();

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

        if(!cliente.getNome().trim().isEmpty() && !cliente.getSenha().trim().isEmpty()) {

            ClienteDAO clienteDAO = new ClienteDAO();

            boolean cadastrado = clienteDAO.cadastrar(cliente);

            if (cadastrado) {
                response.sendRedirect("index.html");
            } else {
                GerarHTML gerarHTML = new GerarHTML(request, response);
                gerarHTML.abrirHTML("Falha");
                gerarHTML.escrever("Não foi possível realizar o cadastro, já existe um usuário com este email no banco", "h2");
                gerarHTML.gerarLink("/cadastro.html", "Voltar");
                gerarHTML.fecharHTML();
            }

        }else{
            response.sendRedirect("./cadastro.html");
        }
    }

    @RequestMapping(value = "/deslogar", method = RequestMethod.GET)
    public void deslogar(HttpServletRequest request, HttpServletResponse response) throws IOException{
        HttpSession sessao = request.getSession(false);
        sessao.invalidate();
        response.sendRedirect("index.html");
    }
}
