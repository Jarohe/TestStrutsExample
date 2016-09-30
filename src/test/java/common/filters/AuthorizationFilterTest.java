package common.filters;

import common.db.model.User;
import org.junit.Test;
import servletunit.FilterChainSimulator;
import servletunit.FilterConfigSimulator;
import servletunit.HttpServletRequestSimulator;
import servletunit.struts.MockStrutsTestCase;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.io.IOException;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;


public class AuthorizationFilterTest extends MockStrutsTestCase {
    private AuthorizationFilter filter = new AuthorizationFilter();

    private FilterChain filterChain = mock(FilterChain.class);
    private User user = mock(User.class);
    private FilterConfig filterConfig = mock(FilterConfig.class);

    private HttpServletRequest request = mock(HttpServletRequest.class);
    private RequestDispatcher dispatcher = mock(RequestDispatcher.class);
    private HttpSession session;

    public void setUp() throws Exception {
        super.setUp();
        session = getSession();
        session.setAttribute("sessionUser",null);
        when(filterConfig.getInitParameter(eq("home_page"))).thenReturn("trololo");
        when(request.getSession()).thenReturn(session);
        when(request.getRequestDispatcher(anyString())).thenReturn(dispatcher);
    }

    public void testInit() throws ServletException {
        filter.init(filterConfig);
        verify(filterConfig,times(1)).getServletContext();
        verify(filterConfig,times(1)).getInitParameter("error_page");
        verify(filterConfig,times(1)).getInitParameter("home_page");
    }

    public void testSuccessDoFilter() throws Exception {
        filter.doFilter(request,response,filterChain);
        verify(filterChain,times(1)).doFilter(request,response);
    }

    public void testErrorDoFilter() throws IOException, ServletException {
        session.setAttribute("sessionUser", user);
        filter.doFilter(request,response,filterChain);
        verify(request,times(1)).getRequestDispatcher(anyString());
    }
}