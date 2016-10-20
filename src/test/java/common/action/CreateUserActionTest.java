package common.action;

import common.db.dao.exceptions.DuplicateUserException;
import common.db.model.Role;
import common.db.model.User;
import common.utils.Attributes;
import common.utils.ErrorMessageKey;
import common.utils.StatusAction;

import java.sql.SQLException;

import static org.mockito.Mockito.*;


public class CreateUserActionTest extends UtilActionTest {

    private User user = new User(10, "login", "firstName", "lastName", Role.MANAGER);

    public void setUp() throws Exception {
        super.setUp();
        getSession().setAttribute(Attributes.SESSION_USER, sessionUser);
        setUserFormParameters(user);
    }

    @Override
    String getRequestPathInfo() {
        return "/system/createUser";
    }

    public void testSuccessUserCreate() throws SQLException, DuplicateUserException {
        actionPerform();
        verifyForward(StatusAction.SUCCESS);
        verify(userDao, times(1)).addUser(user, "password");
        verifyNoActionErrors();
    }

    public void testDuplicateUserCreate() throws SQLException, DuplicateUserException {
        when(userDao.addUser(user, "password")).thenThrow(new DuplicateUserException());
        actionPerform();
        verifyForward(StatusAction.CreateUser.DUPLICATE_USER);
        verifyActionErrors(new String[]{ErrorMessageKey.CreateUser.DUPLICATE_LOGIN});
    }

    public void testPasswordNull() {
        addRequestParameter("password", "");
        actionPerform();
        verifyForward(StatusAction.ERROR);
        verifyActionErrors(new String[]{ErrorMessageKey.CreateUser.CAN_NOT_BLANK});
    }
}