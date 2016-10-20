package common.filters;

import common.db.dao.DaoFactory;
import common.db.dao.UserDao;
import common.db.model.Role;
import common.db.model.User;
import common.utils.Attributes;
import servletunit.struts.MockStrutsTestCase;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;

public class ManagerFilterTest extends MockStrutsTestCase {

    private ManagerFilter filter = new ManagerFilter();
    private HttpServletRequest request;
    private HttpServletResponse response;

    private FilterChain filterChain = mock(FilterChain.class);
    private User userSession = mock(User.class);
    private UserDao userDao = mock(UserDao.class);
    private Connection connection = mock(Connection.class);
    private DaoFactory factory = mock(DaoFactory.class);

    public void setUp() throws Exception {
        super.setUp();
        request = getRequest();
        HttpSession session = getSession();
        response = mock(HttpServletResponse.class);
        session.setAttribute(Attributes.SESSION_USER, userSession);
        session.getServletContext().setAttribute("daoFactory", factory);
        request.setAttribute("connection", connection);
        when(factory.createUserDao(connection)).thenReturn(userDao);
        when(userDao.getUserById(anyInt())).thenReturn(userSession);

    }


    public void testSuccessDoFilter() throws Exception {
        when(userSession.getRole()).thenReturn(Role.MANAGER);
        filter.doFilter(request, response, filterChain);
        verify(filterChain,times(1)).doFilter(request,response);
    }

    public void testFailDoFilter() throws IOException, ServletException {
        when(userSession.getRole()).thenReturn(Role.DEFAULT);
        filter.doFilter(request, response, filterChain);
        verify(response,times(1)).sendRedirect(null);
        verify(filterChain,times(0)).doFilter(request,response);
    }

}