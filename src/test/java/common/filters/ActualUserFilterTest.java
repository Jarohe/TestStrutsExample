package common.filters;

import common.db.dao.DaoFactory;
import common.db.dao.UserDao;
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

import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;


public class ActualUserFilterTest extends MockStrutsTestCase {

    private ActualUserFilter filter = new ActualUserFilter();
    private HttpServletRequest request;
    private HttpSession session;

    private FilterChain filterChain = mock(FilterChain.class);
    private User userSession = mock(User.class);
    private UserDao userDao = mock(UserDao.class);
    private Connection connection = mock(Connection.class);
    private DaoFactory factory = mock(DaoFactory.class);
    private FilterConfig filterConfig = mock(FilterConfig.class);

    public void setUp() throws Exception {
        super.setUp();
        request = getRequest();
        session = getSession();
        session.setAttribute(Attributes.Session.USER, userSession);
        session.getServletContext().setAttribute("daoFactory", factory);
        request.setAttribute("connection", connection);
        when(factory.createUserDao(connection)).thenReturn(userDao);
        when(userDao.getUserById(anyInt())).thenReturn(userSession);
        when(userSession.getUsername()).thenReturn("user");
        filter.init(filterConfig);
    }

    public void testSuccessDoFilter() throws IOException, ServletException { // TODO: Он разве проходит?
        filter.doFilter(getRequest(), getResponse(), filterChain);
        verify(filterChain).doFilter(request,response);
    }

    public void testErrorUserAbcentInSession() throws IOException, ServletException {
        session.setAttribute(Attributes.Session.USER, null);
        filter.doFilter(request, response, filterChain);
        verify(filterChain,times(0)).doFilter(request,response);
    }

    public void testUpdateUserInSession() throws SQLException, IOException, ServletException {
        User DbUser = mock(User.class);
        when(DbUser.getUsername()).thenReturn("updateUser");
        when(userDao.getUserById(anyInt())).thenReturn(DbUser); //  TODO: как это ???
        assertTrue(session.getAttribute(Attributes.Session.USER).equals(userSession)); //TODO: ???
        filter.doFilter(request, response, filterChain);
        assertTrue(session.getAttribute(Attributes.Session.USER).equals(DbUser));
        verify(filterChain).doFilter(request,response);
    }


}