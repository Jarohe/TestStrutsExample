package common.filters;

import common.db.model.Role;
import common.db.model.User;
import common.utils.Attributes;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class ManagerFilter implements Filter {
    private String homePage;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        if (filterConfig != null) {
            homePage = filterConfig.getInitParameter("home_page");
        }
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpSession session = ((HttpServletRequest) servletRequest).getSession();
        User user = (User) session.getAttribute(Attributes.Session.USER);
        if (Role.MANAGER.equals(user.getRole())) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
            ActionErrors errors = new ActionErrors();
            errors.add("access denied", new ActionMessage("error.access.denied"));
            session.setAttribute("org.apache.struts.action.ERROR", errors);
            httpResponse.sendRedirect(homePage);
        }
    }

    @Override
    public void destroy() {

    }
}
