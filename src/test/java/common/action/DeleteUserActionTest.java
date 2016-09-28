package common.action;

import common.db.dao.DaoFactory;
import common.db.dao.UserDao;
import common.utils.StatusAction;
import servletunit.struts.MockStrutsTestCase;

import java.sql.Connection;
import java.sql.SQLException;

import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DeleteUserActionTest extends MockStrutsTestCase {
    private static String PATH_INFO = "/system/deleteUser";

    private Connection connection = mock(Connection.class);
    private UserDao userDao = mock(UserDao.class);
    private DaoFactory factory = mock(DaoFactory.class);

    public void setUp() throws Exception {
        super.setUp();
        getRequest().setAttribute("connection", connection);
        when(factory.createUserDao(connection)).thenReturn(userDao);
    }

    public void testSuccessUserDelete() throws SQLException {
        setRequestPathInfo(PATH_INFO);
        getActionServlet().getServletContext().setAttribute("daoFactory", factory);
        when(userDao.deleteUserById(anyInt())).thenReturn(anyBoolean());
        addRequestParameter("id", "1");
        actionPerform();
        verifyForward(StatusAction.SUCCESS);
    }

}