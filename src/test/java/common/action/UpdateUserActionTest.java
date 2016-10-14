package common.action;

import common.db.dao.exceptions.DublicateUserException;
import common.db.model.User;
import common.utils.Attributes;
import common.utils.ErrorMessageKey;
import common.utils.StatusAction;

import java.sql.SQLException;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;


public class UpdateUserActionTest extends UtilActionTest {

    public void setUp() throws Exception {
        super.setUp("/system/updateUser");
        getSession().setAttribute(Attributes.Session.USER, user);
        setRequestParameters();
    }

    public void testSuccessUpdateUserWithPassword() throws SQLException, DublicateUserException {
        when(userDao.updateUser(any(User.class), eq("password"))).thenReturn(true);  // TODO: Так вообще ничего не тестируется
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
        when(userDao.updateUser(any(User.class), eq("password"))).thenThrow(new DublicateUserException());
        actionPerform();
        verifyForward(StatusAction.ERROR);
    }

    public void testErrorDbUserUpdate() throws SQLException, DublicateUserException {
        when(userDao.updateUser(any(User.class))).thenReturn(false);
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

    public void testErrorEditYourSalfRole(){
        addRequestParameter("id", String.valueOf(user.getId()));
        addRequestParameter("manager","");
        actionPerform();
        verifyForward(StatusAction.ERROR);
        verifyActionErrors(new String[]{ErrorMessageKey.UpdateUser.CAN_NOT_EDIT});
    }

    // TODO: Проверка обязательных полей

    private void setRequestParameters() {
        addRequestParameter("id", "10");
        addRequestParameter("login", "login");
        addRequestParameter("password", "password");
        addRequestParameter("firstName", "firstName");
        addRequestParameter("lastName", "lastName");
        addRequestParameter("manager", "on");
    }

}