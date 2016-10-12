package common.action;

import common.db.dao.DaoFactory;
import common.db.dao.UserDao;
import common.db.dao.exceptions.DublicateUserException;
import common.db.model.Role;
import common.db.model.User;
import common.form.UserForm;
import common.utils.Attributes;
import common.utils.ErrorMessageKey;
import common.utils.StatusAction;
import servletunit.struts.MockStrutsTestCase;

import java.sql.Connection;
import java.sql.SQLException;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


public class UpdateUserActionTest extends MockStrutsTestCase {

    private String pathInfo = "/system/updateUser";

    private Connection connection = mock(Connection.class);
    private UserDao userDao = mock(UserDao.class);
    private DaoFactory factory = mock(DaoFactory.class);

    public void setUp() throws Exception {
        super.setUp();
        getRequest().setAttribute("connection", connection);
        when(factory.createUserDao(connection)).thenReturn(userDao);
        getActionServlet().getServletContext().setAttribute("daoFactory", factory);
        setRequestPathInfo(pathInfo);
        setRequestParameters();
    }

    public void testSuccessUpdateUserWithPassword() throws SQLException, DublicateUserException {
        when(userDao.updateUser(any(User.class),eq("password"))).thenReturn(true);
        actionPerform();
        verifyForward(StatusAction.SUCCESS);
    }

    public void testSuccessUpdateUserNotPassword() throws SQLException, DublicateUserException {
        addRequestParameter("password", "");
        when(userDao.updateUser(any(User.class))).thenReturn(true);
        actionPerform();
        verifyForward(StatusAction.SUCCESS);
    }

    public void testErrorDublicateUsernameUpdate() throws SQLException, DublicateUserException {
        when(userDao.updateUser(any(User.class),eq("password"))).thenThrow(new DublicateUserException());
        actionPerform();
        verifyForward(StatusAction.ERROR);
    }

    public void testErrorDbUserUpdate() throws SQLException, DublicateUserException {
        when(userDao.updateUser(any(User.class))).thenReturn(false);
        actionPerform();
        verifyForward(StatusAction.ERROR);
        verifyActionErrors(new String[] {ErrorMessageKey.UpdateUser.USER_NOT_UPDATE});
    }

    public void testErrorNotSendId() {
        addRequestParameter("id", "0");
        actionPerform();
        verifyForward(StatusAction.ERROR);
        verifyActionErrors(new String[] {ErrorMessageKey.UpdateUser.NOT_SEND_ID});
    }


    private void setRequestParameters() {
        addRequestParameter("id", "10");
        addRequestParameter("login", "login");
        addRequestParameter("password", "password");
        addRequestParameter("firstName", "firstName");
        addRequestParameter("lastName", "lastName");
        addRequestParameter("manager", "on");
    }

}