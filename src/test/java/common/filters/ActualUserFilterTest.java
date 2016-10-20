package common.filters;

import common.db.dao.DaoFactory;
import common.db.dao.UserDao;
import common.db.model.Role;
import common.db.model.User;
import common.utils.Attributes;
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
import static org.mockito.internal.verification.VerificationModeFactory.times;


public class ActualUserFilterTest extends MockStrutsTestCase {

    private ActualUserFilter filter = new ActualUserFilter();
    private HttpServletRequest request;
    private HttpSession session;

    private FilterChain filterChain = mock(FilterChain.class);
    private User userSession = new User(10, "username", "firstName", "lastName", Role.MANAGER);
    private User userDb = new User(10, "usernameDb", "firstNameDb", "lastNameDb", Role.DEFAULT);
    private UserDao userDao = mock(UserDao.class);
    private Connection connection = mock(Connection.class);
    private DaoFactory factory = mock(DaoFactory.class);
    private FilterConfig filterConfig = mock(FilterConfig.class);

    public void setUp() throws Exception {
        super.setUp();
        request = getRequest();
        session = getSession();
        session.setAttribute(Attributes.SESSION_USER, userSession);
        session.getServletContext().setAttribute("daoFactory", factory);
        request.setAttribute("connection", connection);
        when(factory.createUserDao(connection)).thenReturn(userDao);
        filter.init(filterConfig);
    }

    public void testSuccessDoFilter() throws IOException, ServletException, SQLException {
        when(userDao.getUserById(10)).thenReturn(userDb);
        filter.doFilter(getRequest(), getResponse(), filterChain);
        verify(filterChain).doFilter(request, response);
        verify(userDao, times(1)).getUserById(10);
        assertEquals(session.getAttribute(Attributes.SESSION_USER), userDb);
    }

    public void testErrorUserAbsentInSession() throws IOException, ServletException {
        session.setAttribute(Attributes.SESSION_USER, null);
        filter.doFilter(request, response, filterChain);
        verify(filterChain, times(0)).doFilter(request, response);
    }

    public void testErrorNotUserInDb() throws SQLException, IOException, ServletException {
        when(userDao.getUserById(10)).thenReturn(null);
        filter.doFilter(getRequest(), getResponse(), filterChain);
        verify(filterChain, times(0)).doFilter(request, response);
    }

}