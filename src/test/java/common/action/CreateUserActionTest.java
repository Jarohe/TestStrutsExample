package common.action;

import common.db.dao.DaoFactory;
import common.db.dao.UserDao;
import common.db.dao.exceptions.DublicateUserException;
import common.db.model.Role;
import common.db.model.User;
import common.utils.Attributes;
import common.utils.ErrorMessageKey;
import common.utils.StatusAction;
import servletunit.struts.MockStrutsTestCase;

import java.sql.Connection;
import java.sql.SQLException;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class CreateUserActionTest extends UtilActionTest {
    private String pathInfo = "/system/createUser";

    public void setUp() throws Exception {
        super.setUp();
        setRequestPathInfo(pathInfo);
        init();
        getSession().setAttribute(Attributes.Session.USER, user);
        setRequestParameters();
    }

    public void testSuccessUserCreate() throws SQLException, DublicateUserException {
        actionPerform();
        verifyForward(StatusAction.SUCCESS);
        verifyNoActionErrors();
    }

    public void testDuplicateUserCreate() throws SQLException, DublicateUserException {
        when(userDao.addUser(any(User.class), eq("password"))).thenThrow(new DublicateUserException());
        actionPerform();
        verifyForward(StatusAction.CreateUser.DUBLICATE_USER);
        verifyActionErrors(new String[]{ErrorMessageKey.CreateUser.DUBLICATE_LOGIN});
    }

    public void testPasswordNull() {
        addRequestParameter("password", "");
        actionPerform();
        verifyForward(StatusAction.ERROR);
        verifyActionErrors(new String[]{ErrorMessageKey.CreateUser.CAN_NOT_BLANK});
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