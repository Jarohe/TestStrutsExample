package common.action;

import common.db.dao.DaoFactory;
import common.db.dao.UserDao;
import common.db.model.Role;
import common.db.model.User;
import common.form.UserForm;
import servletunit.struts.MockStrutsTestCase;

import java.sql.Connection;
import java.sql.SQLException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class EditUserActionTest extends MockStrutsTestCase {
    private static String PATH_INFO = "/system/editUser";
    private Connection connection = mock(Connection.class);
    private UserDao userDao = mock(UserDao.class);
    private DaoFactory factory = mock(DaoFactory.class);
    private User userSession = createSessionUser();

    public void setUp() throws Exception {
        super.setUp();
        getRequest().setAttribute("connection", connection);
        when(factory.createUserDao(connection)).thenReturn(userDao);
        getSession().setAttribute("sessionUser", userSession);
    }

    public void testSuccessEditUser() throws SQLException {
        setRequestPathInfo(PATH_INFO);
        addRequestParameter("id", "1");
        User user = createUser(1);
        getActionServlet().getServletContext().setAttribute("daoFactory", factory);
        when(userDao.getUserById(1)).thenReturn(user);
        actionPerform();
        UserForm form = (UserForm) getActionForm();
        assertNotNull(form);
        assertTrue(user.getId() == form.getId());
        assertTrue(user.getUsername().equals(form.getLogin()));
        assertTrue(user.getFirstName().equals(form.getFirstName()));
        assertTrue(user.getLastName().equals(form.getLastName()));
        assertTrue(!form.isManager());
    }

    private User createSessionUser() {
        return new User.Builder(1, "", "").role(Role.MANAGER).build();
    }

    private User createUser(int id) {
        return new User.Builder(id, "test", "test").firstName("test").lastName("test").role(Role.DEFAULT).build();
    }

}