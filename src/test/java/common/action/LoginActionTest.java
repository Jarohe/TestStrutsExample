package common.action;

import common.db.dao.DaoFactory;
import common.db.dao.UserDao;
import common.db.model.User;
import common.form.LoginForm;
import common.utils.StatusAction;
import servletunit.struts.MockStrutsTestCase;

import java.sql.Connection;
import java.sql.SQLException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class LoginActionTest extends MockStrutsTestCase {

    private static String PATH_INFO = "/login";

    private Connection connection = mock(Connection.class);
    private UserDao userDao = mock(UserDao.class);
    private DaoFactory factory = mock(DaoFactory.class);
    private User user = mock(User.class);

    public void setUp() throws Exception {
        super.setUp();
        getRequest().setAttribute("connection",connection);
        when(factory.createUserDao(connection)).thenReturn(userDao);
    }

    public void testSuccessLogin() throws SQLException {
        setRequestPathInfo(PATH_INFO);
        when(user.getUsername()).thenReturn("login");
        when(userDao.getUserByUsername("login")).thenReturn(user);

        getActionServlet().getServletContext().setAttribute("daoFactory",factory);

        addRequestParameter("login","login");
        addRequestParameter("pass","password");
        actionPerform();
        verifyForward(StatusAction.SUCCESS);
    }

    public void testFailLogin() throws SQLException {
        setRequestPathInfo(PATH_INFO);
        when(user.getUsername()).thenReturn("login");
        when(userDao.getUserByUsername("login")).thenReturn(user);

        getActionServlet().getServletContext().setAttribute("daoFactory",factory);

        addRequestParameter("login","login");
        addRequestParameter("pass","password");
        actionPerform();
        verifyForward(StatusAction.LoginUser.ACCESS_DENIED);
    }

    public void testPassNull() {
        setRequestPathInfo(PATH_INFO);
        LoginForm loginForm = new LoginForm();
        loginForm.setLogin("login");
        setActionForm(loginForm);
        actionPerform();
        verifyForward(StatusAction.FAILURE);
    }

}