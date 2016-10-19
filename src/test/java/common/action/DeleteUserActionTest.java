package common.action;

import common.db.model.User;
import common.utils.Attributes;
import common.utils.ErrorMessageKey;
import common.utils.StatusAction;

import java.sql.SQLException;

import static org.mockito.Mockito.when;

public class DeleteUserActionTest extends UtilActionTest {

    public void setUp() throws Exception {
        super.setUp("/system/deleteUser");
        getSession().setAttribute(Attributes.Session.USER, user);
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
        User userSession = (User) getSession().getAttribute(Attributes.Session.USER);
        addRequestParameter("id", String.valueOf(userSession.getId()));
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

}