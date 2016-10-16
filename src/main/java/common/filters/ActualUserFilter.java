package common.filters;

import common.db.dao.DaoFactory;
import common.db.dao.UserDao;
import common.db.model.User;
import common.utils.Attributes;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class ActualUserFilter implements Filter {
    private String homePage;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        if (filterConfig != null) {
            homePage = filterConfig.getInitParameter("home_page");
        }
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpSession session = ((HttpServletRequest) servletRequest).getSession(false);
        if (session == null) {
            HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
            httpResponse.sendRedirect(homePage);
            return;
        }
        User user = (User) session.getAttribute(Attributes.Session.USER);
        if (user == null) {
            HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
            httpResponse.sendRedirect(homePage);
            return;
        }
        try {
            Connection connection = (Connection) servletRequest.getAttribute("connection");
            DaoFactory factory = (DaoFactory) session.getServletContext().getAttribute("daoFactory");
            UserDao userDao = factory.createUserDao(connection);
            User userFromDb = userDao.getUserById(user.getId());

            if (userFromDb == null) {
                session.invalidate();
                HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
                httpResponse.sendRedirect(homePage);
                return;
            }

            session.setAttribute(Attributes.Session.USER, userFromDb);

        } catch (SQLException e) {
            throw new ServletException(e);
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
