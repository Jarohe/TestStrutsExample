package common.action;

import common.db.dao.DaoFactory;
import common.db.dao.UserDao;
import common.db.model.User;
import common.utils.Attributes;
import common.utils.ErrorMessageKey;
import common.utils.StatusAction;
import servletunit.struts.MockStrutsTestCase;

import java.sql.Connection;
import java.sql.SQLException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class LoginActionTest extends MockStrutsTestCase {

    private String pathInfo = "/login";

    private Connection connection = mock(Connection.class);
    private UserDao userDao = mock(UserDao.class);
    private DaoFactory factory = mock(DaoFactory.class);
    private User user = mock(User.class);

    public void setUp() throws Exception {
        super.setUp();
        getRequest().setAttribute("connection", connection);
        when(factory.createUserDao(connection)).thenReturn(userDao);
        setRequestPathInfo(pathInfo);
        addRequestParameter("login", "login");
        addRequestParameter("password", "password");
        when(user.getUsername()).thenReturn("login");
        getActionServlet().getServletContext().setAttribute("daoFactory", factory);

    }

    public void testSuccessLogin() throws SQLException {
        when(userDao.getUserByLoginAndPassword("login", "password")).thenReturn(user);
        actionPerform();
        verifyForward(StatusAction.SUCCESS);
        User userFromSession = (User) getSession().getAttribute(Attributes.Session.USER);
        assertTrue(user.equals(userFromSession));
    }

    public void testFailLogin() throws SQLException {
        when(userDao.getUserByLoginAndPassword("login", "password")).thenReturn(null);
        actionPerform();
        verifyForward(StatusAction.ERROR);
        assertNull(getSession().getAttribute(Attributes.Session.USER));
        verifyActionErrors(new String[]{ErrorMessageKey.Login.INVALIDE_LOGIN_PASSWORD});
    }

}