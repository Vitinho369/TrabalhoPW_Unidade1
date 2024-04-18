package eaj.ufrn.br.trabalhopw.view;

import eaj.ufrn.br.trabalhopw.dominio.Carrinho;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter({"/Carrinho","/FinalizaCompra"})
public class FiltroCliente implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse response = ((HttpServletResponse) servletResponse);
        HttpServletRequest request = ((HttpServletRequest) servletRequest);

        HttpSession sessao = request.getSession(false);
        String tipoUsuario = (String) sessao.getAttribute("tipo");

        if(!tipoUsuario.equals("cliente")){
            response.sendRedirect("./index.html");
            return;
        }else{
            String emailUsuario = (String) sessao.getAttribute("usuario");
            if(emailUsuario.isEmpty()){
                response.sendRedirect("./index.html");
                return;
            }
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
