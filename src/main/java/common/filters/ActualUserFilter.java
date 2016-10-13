package common.filters;

import common.db.dao.DaoFactory;
import common.db.dao.UserDao;
import common.db.model.User;
import common.utils.Attributes;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class ActualUserFilter implements Filter {
    private String homePage;
    private String errorPage;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        if (filterConfig != null) {
            errorPage = filterConfig.getInitParameter("error_page");
            homePage = filterConfig.getInitParameter("home_page");
        }
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpSession session = ((HttpServletRequest) servletRequest).getSession(false);
        User user = (User) session.getAttribute(Attributes.Session.USER);// TODO: NullPointerException
        if (user == null) {
            servletRequest.getRequestDispatcher(homePage).forward(servletRequest, servletResponse);// TODO: Так не очень
            return;
        }
        try {
            Connection connection = (Connection) servletRequest.getAttribute("connection");
            DaoFactory factory = (DaoFactory) session.getServletContext().getAttribute("daoFactory");
            UserDao userDao = factory.createUserDao(connection);
            User userFromDb = userDao.getUserById(user.getId());

            if (userFromDb == null) {
                session.invalidate();
                servletRequest.getRequestDispatcher(homePage).forward(servletRequest, servletResponse);
                return;
            }

            if (!user.equals(userFromDb)) { // TODO: А зачем эта проверка?
                session.setAttribute(Attributes.Session.USER, userFromDb);
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
