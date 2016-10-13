package common.action;

import common.db.dao.DaoFactory;
import common.db.dao.UserDao;
import common.db.model.Role;
import common.db.model.User;
import common.form.UserForm;
import common.utils.Attributes;
import common.utils.ErrorMessageKey;
import common.utils.StatusAction;
import servletunit.struts.MockStrutsTestCase;

import java.sql.Connection;
import java.sql.SQLException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class EditUserActionTest extends UtilActionTest {
    private String pathInfo = "/system/editUser";

    public void setUp() throws Exception {
        super.setUp();
        setRequestPathInfo(pathInfo);
        init();
        getSession().setAttribute(Attributes.Session.USER, user); // TODO: А зачем?
    }

    public void testSuccessEditUser() throws SQLException {

        addRequestParameter("id", "1");
        User user = new User(1, "userName", "firstName", "lastName", Role.DEFAULT);
        when(userDao.getUserById(1)).thenReturn(user);

        actionPerform();
        UserForm form = (UserForm) getActionForm();
        assertNotNull(form);
        assertTrue(user.getId() == form.getId());
        assertTrue(user.getUsername().equals(form.getLogin()));
        assertTrue(user.getFirstName().equals(form.getFirstName()));
        assertTrue(user.getLastName().equals(form.getLastName()));
        assertTrue(!form.isManager());
        verifyForward(StatusAction.SUCCESS);
    }

    public void testErrorNoFoundUserId() throws SQLException {
        addRequestParameter("id", "1");
        when(userDao.getUserById(1)).thenReturn(null);
        actionPerform();
        verifyForward(StatusAction.ERROR);
        verifyActionErrors(new String[]{ErrorMessageKey.EditUser.NOT_FOUND_USER_ID});
    }

}