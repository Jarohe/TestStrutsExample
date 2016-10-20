package common.action;

import common.db.dao.exceptions.DuplicateUserException;
import common.db.model.Role;
import common.db.model.User;
import common.form.UserForm;
import common.utils.Attributes;
import common.utils.ErrorMessageKey;
import common.utils.StatusAction;

import java.sql.SQLException;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;


public class CreateUserActionTest extends UtilActionTest {

    public void setUp() throws Exception {
        super.setUp();
        getSession().setAttribute(Attributes.Session.USER, user);
        setRequestParameters();
    }

    @Override
    String getRequestPathIbfo() {
        return "/system/createUser";
    }

    public void testSuccessUserCreate() throws SQLException, DuplicateUserException {
        actionPerform();
        verifyForward(StatusAction.SUCCESS);
        UserForm form = (UserForm) getActionForm();
        verify(userDao, times(1)).addUser(form.extractUser(), form.getPassword());// TODO: Можно сделать сильнее
        verifyNoActionErrors();
    }

    public void testDuplicateUserCreate() throws SQLException, DuplicateUserException {
        User user = new User(10,"login","firstName","lastName", Role.MANAGER); // TODO: Гораздо более правильный вариант, но есть дублирование
        when(userDao.addUser(eq(user), eq("password"))).thenThrow(new DuplicateUserException());
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