package common.filters;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
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
        HttpSession session = ((HttpServletRequest) servletRequest).getSession();
        if (session.getAttribute("sessionUser") == null) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            servletRequest.getRequestDispatcher(homePage).forward(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {

    }
}
