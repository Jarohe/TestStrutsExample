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


public class LoginActionTest extends UtilActionTest {

    private String pathInfo = "/login";

    private User user = mock(User.class);

    public void setUp() throws Exception {
        super.setUp();
        init();
        setRequestPathInfo(pathInfo);
        addRequestParameter("login", "login");
        addRequestParameter("password", "password");
        when(user.getUsername()).thenReturn("login");
    }

    public void testSuccessLogin() throws SQLException {
        when(userDao.getUser("login", "password")).thenReturn(user);
        actionPerform();
        verifyForward(StatusAction.SUCCESS);
        User userFromSession = (User) getSession().getAttribute(Attributes.Session.USER);
        assertTrue(user.equals(userFromSession));
    }

    public void testFailLogin() throws SQLException {
        when(userDao.getUser("login", "password")).thenReturn(null);
        actionPerform();
        verifyForward(StatusAction.ERROR);
        assertNull(getSession().getAttribute(Attributes.Session.USER));
        verifyActionErrors(new String[]{ErrorMessageKey.Login.INVALIDE_LOGIN_PASSWORD});
    }
}