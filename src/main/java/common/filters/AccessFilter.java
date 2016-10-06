package common.filters;

import common.db.dao.DaoFactory;
import common.db.dao.UserDao;
import common.db.dao.impl.UserDaoImpl;
import common.db.model.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class AccessFilter implements Filter { // TODO: Название совершенно не отражает назначение
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
        HttpSession session = ((HttpServletRequest) servletRequest).getSession(false);// TODO: Фильтр сам не должен стартовать сессию
        User user = (User) session.getAttribute("sessionUser");
        if (user == null) {
            servletRequest.getRequestDispatcher(homePage).forward(servletRequest, servletResponse);
        } else{
            try {
                Connection connection = (Connection) servletRequest.getAttribute("connection");
                DaoFactory factory = (DaoFactory)session.getServletContext().getAttribute("daoFactory");
                UserDao userDao = factory.createUserDao(connection);
                User userFromDb = userDao.getUserById(user.getId());

                if(!user.equals(userFromDb)) {
                    session.setAttribute("sessionUser", null);
                    servletRequest.getRequestDispatcher(homePage).forward(servletRequest, servletResponse); // TODO: Не стоит логаутить
                    return;
                }
            } catch (SQLException e) {
                throw new IOException("SQL:"+this.getClass()); // TODO: Почему IOException???
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
