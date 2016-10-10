package common.filters;

import org.apache.commons.dbcp.BasicDataSource;

import javax.servlet.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;


public class ConnectionFilter implements Filter {
    private BasicDataSource dataSource;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        if (filterConfig != null) {
            ServletContext context = filterConfig.getServletContext();
            dataSource = (BasicDataSource) context.getAttribute("db");
        }
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        try (Connection connection = dataSource.getConnection()) {
            servletRequest.setAttribute("connection", connection);
            try {
                filterChain.doFilter(servletRequest, servletResponse);
                connection.commit();
            } catch (Exception e) {
                connection.rollback();
            }
        } catch (SQLException e) {
            System.out.println("SQL error: can't get connection");
        }
    }

    @Override
    public void destroy() {

    }
}
