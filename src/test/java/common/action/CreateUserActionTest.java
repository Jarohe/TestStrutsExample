package common.action;

import common.db.dao.exceptions.DublicateUserException;
import common.db.model.User;
import common.form.UserForm;
import common.utils.Attributes;
import common.utils.ErrorMessageKey;
import common.utils.StatusAction;

import java.sql.SQLException;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;


public class CreateUserActionTest extends UtilActionTest {

    public void setUp() throws Exception {
        super.setUp("/system/createUser");
        getSession().setAttribute(Attributes.Session.USER, user);
        setRequestParameters();
    }

    public void testSuccessUserCreate() throws SQLException, DublicateUserException {
        actionPerform();
        verifyForward(StatusAction.SUCCESS);
        UserForm form = (UserForm) getActionForm();
        verify(userDao, times(1)).addUser(form.extractUser(), form.getPassword());
        verifyNoActionErrors();
    }

    public void testDuplicateUserCreate() throws SQLException, DublicateUserException {
        when(userDao.addUser(any(User.class), eq("password"))).thenThrow(new DublicateUserException());// TODO: Тут стоило проверку посильнее сделать
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

    // TODO: А проверки на обязательные поля теперь где?

    private void setRequestParameters() {
        addRequestParameter("id", "10");
        addRequestParameter("login", "login");
        addRequestParameter("password", "password");
        addRequestParameter("firstName", "firstName");
        addRequestParameter("lastName", "lastName");
        addRequestParameter("manager", "on");
    }
}