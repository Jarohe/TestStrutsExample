package common.action;

import common.db.dao.DaoFactory;
import common.db.dao.UserDao;
import common.db.model.User;
import common.utils.StatusAction;
import servletunit.struts.MockStrutsTestCase;

import java.sql.Connection;
import java.sql.SQLException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by derevyanko on 27.09.2016.
 */
public class LoginActionTest extends MockStrutsTestCase {

    Connection connection = mock(Connection.class);
    UserDao userDao = mock(UserDao.class);
    DaoFactory factory = mock(DaoFactory.class);
    User user = mock(User.class);

    public void setUp() throws Exception {
        super.setUp();
        getRequest().setAttribute("connection",connection);
        when(factory.createUserDao(connection)).thenReturn(userDao);
    }

    public void testSuccessLogin() throws SQLException {
        setRequestPathInfo("/login");
        when(user.getUsername()).thenReturn("login");
        when(user.getPassword()).thenReturn("password");
        when(userDao.getUserByUsername("login")).thenReturn(user);

        getActionServlet().getServletContext().setAttribute("daoFactory",factory);

        addRequestParameter("login","login");
        addRequestParameter("pass","password");
        actionPerform();
        verifyForward(StatusAction.SUCCESS);
    }

}