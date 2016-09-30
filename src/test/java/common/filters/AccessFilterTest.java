package common.filters;

import common.db.dao.DaoFactory;
import common.db.dao.UserDao;
import common.db.model.User;
import servletunit.struts.MockStrutsTestCase;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;


public class AccessFilterTest extends MockStrutsTestCase {

    private AccessFilter filter = new AccessFilter();
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
        session.setAttribute("sessionUser", userSession);
        session.getServletContext().setAttribute("daoFactory", factory);
        request.setAttribute("connection", connection);
        when(factory.createUserDao(connection)).thenReturn(userDao);
        when(userDao.getUserById(anyInt())).thenReturn(userSession);
    }

    public void testInit() throws ServletException {
        filter.init(filterConfig);
        verify(filterConfig, times(1)).getInitParameter("error_page");
        verify(filterConfig, times(1)).getInitParameter("home_page");
    }

    public void testSuccessDoFilter() throws IOException, ServletException {
        filter.doFilter(getRequest(), getResponse(), filterChain);
        verify(filterChain).doFilter(request,response);
    }


}