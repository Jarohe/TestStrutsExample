package common.filters;

import common.utils.Attributes;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;


public class AuthorizationFilter implements Filter {
    private ServletContext context;
    private String errorPage;
    private String homePage;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        if (filterConfig != null) {
            context = filterConfig.getServletContext();
            errorPage = filterConfig.getInitParameter("error_page");
            homePage = filterConfig.getInitParameter("home_page");
        }
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpSession session = ((HttpServletRequest) servletRequest).getSession(); // TODO: Не надо тут начинать сессию
        if (session.getAttribute(Attributes.Session.USER) == null) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            servletRequest.getRequestDispatcher(homePage).forward(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {

    }
}
