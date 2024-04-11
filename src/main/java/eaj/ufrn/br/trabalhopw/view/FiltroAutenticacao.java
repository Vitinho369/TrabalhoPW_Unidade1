package eaj.ufrn.br.trabalhopw.view;

import eaj.ufrn.br.trabalhopw.dominio.Cliente;
import eaj.ufrn.br.trabalhopw.dominio.Lojista;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter("/LojaOnline")
public class FiltroAutenticacao implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletResponse response = ((HttpServletResponse) servletResponse);
        HttpServletRequest request = ((HttpServletRequest) servletRequest);

        HttpSession sessao = request.getSession(false);

        if(sessao == null){
            response.sendRedirect("./index.html?msg=Usuario_nao_autorizado");
        }else{

            Boolean logado = (Boolean) sessao.getAttribute("logado");

            System.out.println(logado);
            if(!logado || logado == null){
                response.sendRedirect("index.html");
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
