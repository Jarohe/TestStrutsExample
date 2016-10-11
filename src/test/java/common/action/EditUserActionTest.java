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

public class EditUserActionTest extends MockStrutsTestCase {
    private String pathInfo = "/system/editUser";
    private Connection connection = mock(Connection.class);
    private UserDao userDao = mock(UserDao.class);
    private DaoFactory factory = mock(DaoFactory.class);
    private User userSession = createSessionUser();

    public void setUp() throws Exception {
        super.setUp();
        setRequestPathInfo(pathInfo);
        getRequest().setAttribute("connection", connection);
        when(factory.createUserDao(connection)).thenReturn(userDao);
        getSession().setAttribute(Attributes.Session.USER, userSession);
        getActionServlet().getServletContext().setAttribute("daoFactory", factory);
    }

    public void testSuccessEditUser() throws SQLException {

        addRequestParameter("id", "1");
        User user = createUser(1);
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

    private User createSessionUser() {
        return new User.Builder(1, "").role(Role.MANAGER).build();
    }

    private User createUser(int id) {
        return new User.Builder(id, "test").firstName("test").lastName("test").role(Role.DEFAULT).build();
    }

}