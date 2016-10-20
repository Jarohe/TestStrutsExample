package common.filters;

import org.apache.commons.dbcp.BasicDataSource;
import servletunit.struts.MockStrutsTestCase;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import static org.mockito.Mockito.*;

public class ConnectionFilterTest extends MockStrutsTestCase {
    private ConnectionFilter filter = new ConnectionFilter();
    private FilterChain filterChain = mock(FilterChain.class);
    private BasicDataSource dataSource = mock(BasicDataSource.class);
    private Connection connection = mock(Connection.class);
    private FilterConfig config = mock(FilterConfig.class);

    private HttpServletRequest request;

    public void setUp() throws Exception {
        super.setUp();
        request = getRequest();
        HttpSession session = getSession();
        session.getServletContext().setAttribute("db", dataSource);
        when(dataSource.getConnection()).thenReturn(connection);
        when(config.getServletContext()).thenReturn(session.getServletContext());
        filter.init(config);
    }

    public void testSuccessDoFilter() throws IOException, ServletException, SQLException {
        assertNull(request.getAttribute("connection"));
        filter.doFilter(request, response, filterChain);
        verify(filterChain, times(1)).doFilter(request, response);
        assertNotNull(request.getAttribute("connection"));
        verify(connection, times(1)).commit();
        verify(connection, times(1)).close();
    }

    public void testErrorDoFilter() throws IOException, ServletException, SQLException {
        doThrow(new ServletException()).when(filterChain).doFilter(request, response);
        filter.doFilter(request, response, filterChain);
        verify(connection, times(1)).rollback();
        verify(connection, times(1)).close();
    }

}