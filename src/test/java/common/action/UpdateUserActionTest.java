package common.action;

import common.db.dao.exceptions.DuplicateUserException;
import common.db.model.Role;
import common.db.model.User;
import common.utils.Attributes;
import common.utils.ErrorMessageKey;
import common.utils.StatusAction;

import java.sql.SQLException;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;


public class UpdateUserActionTest extends UtilActionTest {

    private User user = new User(10, "login", "firstName", "lastName", Role.MANAGER);

    public void setUp() throws Exception {
        super.setUp();
        getSession().setAttribute(Attributes.SESSION_USER, sessionUser);
        setUserFormParameters(user);
    }

    @Override
    String getRequestPathInfo() {
        return "/system/updateUser";
    }

    public void testSuccessUpdateUserWithPassword() throws SQLException, DuplicateUserException {
        when(userDao.updateUser(user, "password")).thenReturn(true);
        actionPerform();
        verifyForward(StatusAction.SUCCESS);
    }

    public void testSuccessUpdateUserNotPassword() throws SQLException, DuplicateUserException {
        addRequestParameter("password", "");
        when(userDao.updateUser(user)).thenReturn(true);
        actionPerform();
        verifyForward(StatusAction.SUCCESS);
    }

    public void testErrorDuplicateUsernameUpdate() throws SQLException, DuplicateUserException {
        when(userDao.updateUser(user, "password")).thenThrow(new DuplicateUserException());
        actionPerform();
        verifyForward(StatusAction.ERROR);
    }

    public void testErrorDbUserUpdate() throws SQLException, DuplicateUserException {
        when(userDao.updateUser(user)).thenReturn(false);
        actionPerform();
        verifyForward(StatusAction.ERROR);
        verifyActionErrors(new String[]{ErrorMessageKey.UpdateUser.USER_NOT_UPDATE});
    }

    public void testErrorNotSendId() {
        addRequestParameter("id", "");
        actionPerform();
        verifyForward(StatusAction.ERROR);
        verifyActionErrors(new String[]{ErrorMessageKey.UpdateUser.USER_NOT_UPDATE});
    }

    public void testErrorEditYourselfRole(){
        addRequestParameter("id", String.valueOf(sessionUser.getId()));
        addRequestParameter("manager","");
        actionPerform();
        verifyForward(StatusAction.ERROR);
        verifyActionErrors(new String[]{ErrorMessageKey.UpdateUser.CAN_NOT_EDIT});
    }

}