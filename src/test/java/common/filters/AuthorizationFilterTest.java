package common.filters;

import common.db.model.User;
import common.utils.Attributes;
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
import javax.servlet.http.HttpServletResponse;
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

    private HttpSession session;
    private HttpServletRequest request;


    public void setUp() throws Exception {
        super.setUp();
        session = getSession();
        request = getRequest();
        session.setAttribute(Attributes.Session.USER,null);
        when(filterConfig.getInitParameter(eq("home_page"))).thenReturn("trololo");
        filter.init(filterConfig);
    }

    public void testSuccessDoFilter() throws Exception {
        filter.doFilter(getRequest(),response,filterChain);
        verify(filterChain,times(1)).doFilter(request,response);
    }

    public void testErrorDoFilter() throws IOException, ServletException {
        session.setAttribute(Attributes.Session.USER, user);
        filter.doFilter(request,response,filterChain);
        verify(filterChain,times(0)).doFilter(request,response);
    }
}