package eaj.ufrn.br.trabalhopw.view;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter({"/LojaOnline/CarrinhoServlet"})
public class FiltroCarrinhoServlet implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletResponse response = ((HttpServletResponse) servletResponse);
        HttpServletRequest request = ((HttpServletRequest) servletRequest);

        HttpSession sessao = request.getSession(false);

        if(sessao == null){
            response.sendRedirect("../index.html?msg=Usuario_nao_autorizado");
            return;
        }else{

            Boolean logado = (Boolean) sessao.getAttribute("logado");

            if(!logado || logado == null){
                sessao.invalidate();
                response.sendRedirect("../index.html");
                return;
            }else{
                String tipo = (String) sessao.getAttribute("tipo");

                if(!tipo.equals("cliente")) {
                    response.sendRedirect("/LojaOnline");
                    return;
                }
            }

        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
