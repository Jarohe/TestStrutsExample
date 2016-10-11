package common.action;

import common.db.dao.DaoFactory;
import common.db.dao.UserDao;
import common.db.model.User;
import common.utils.StatusAction;
import servletunit.struts.MockStrutsTestCase;

import java.sql.Connection;
import java.sql.SQLException;

import static org.mockito.Matchers.anyListOf;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class UserListActionTest extends MockStrutsTestCase {
    private String pathInfo = "/system/usersList";

    private Connection connection = mock(Connection.class);
    private UserDao userDao = mock(UserDao.class);
    private DaoFactory factory = mock(DaoFactory.class);

    public void setUp() throws Exception {
        super.setUp();
        getRequest().setAttribute("connection", connection);
        when(factory.createUserDao(connection)).thenReturn(userDao);
    }

    public void testSuccessUserList() throws SQLException {
        setRequestPathInfo(pathInfo);
        getActionServlet().getServletContext().setAttribute("daoFactory", factory);
        when(userDao.getAllUsers()).thenReturn(anyListOf(User.class));
        assertNull(getRequest().getAttribute("userList"));
        actionPerform();
        verifyForward(StatusAction.SUCCESS);
        assertNotNull(getRequest().getAttribute("userList"));
    }

}