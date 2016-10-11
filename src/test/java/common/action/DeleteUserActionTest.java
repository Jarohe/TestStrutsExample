package common.action;

import common.db.dao.DaoFactory;
import common.db.dao.UserDao;
import common.db.model.Role;
import common.db.model.User;
import common.utils.Attributes;
import common.utils.ErrorMessageKey;
import common.utils.StatusAction;
import servletunit.struts.MockStrutsTestCase;

import java.sql.Connection;
import java.sql.SQLException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DeleteUserActionTest extends MockStrutsTestCase {
    private String pathInfo = "/system/deleteUser";

    private Connection connection = mock(Connection.class);
    private UserDao userDao = mock(UserDao.class);
    private DaoFactory factory = mock(DaoFactory.class);
    private User userSession = createSessionUserManager();

    public void setUp() throws Exception {
        super.setUp();
        getRequest().setAttribute("connection", connection);
        when(factory.createUserDao(connection)).thenReturn(userDao);
        getSession().setAttribute(Attributes.Session.USER, userSession);
        getActionServlet().getServletContext().setAttribute("daoFactory", factory);

        setRequestPathInfo(pathInfo);
    }

    public void testSuccessUserDelete() throws SQLException {
        when(userDao.deleteUserById(1)).thenReturn(true);
        addRequestParameter("id", "1");
        actionPerform();
        verifyForward(StatusAction.SUCCESS);
    }

    public void testErrorIdIsNotNumber() {
        addRequestParameter("id", "");
        actionPerform();
        verifyForward(StatusAction.ERROR);
        verifyActionErrors(new String[]{ErrorMessageKey.DeleteUser.ID_NOT_NUMBER});
    }

    public void testErrorDropYourself() {
        addRequestParameter("id", "10");
        actionPerform();
        verifyForward(StatusAction.ERROR);
        verifyActionErrors(new String[]{ErrorMessageKey.DeleteUser.CAN_NOT_REMOVE});
    }

    public void testErrorNotFoundUser() throws SQLException {
        when(userDao.deleteUserById(1)).thenReturn(false);
        addRequestParameter("id", "1");
        actionPerform();
        verifyForward(StatusAction.ERROR);
        verifyActionErrors(new String[]{ErrorMessageKey.DeleteUser.NOT_FOUND_USER_ID});
    }

    private User createSessionUserManager() {
        return new User.Builder(10, "test").role(Role.MANAGER).build();
    }

}