package common.action;

import common.db.dao.DaoFactory;
import common.db.dao.UserDao;
import common.db.model.User;
import common.form.UserForm;
import common.utils.StatusAction;
import servletunit.struts.MockStrutsTestCase;

import java.sql.Connection;
import java.sql.SQLException;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class UpdateUserTest extends MockStrutsTestCase {

    private static String PATH_INFO = "/system/updateUser";

    private Connection connection = mock(Connection.class);
    private UserDao userDao = mock(UserDao.class);
    private DaoFactory factory = mock(DaoFactory.class);
    private User user = mock(User.class);
    private User user2 = mock(User.class);

    public void setUp() throws Exception {
        super.setUp();
        getRequest().setAttribute("connection", connection);
        when(factory.createUserDao(connection)).thenReturn(userDao);
    }

    public void testSuccessUpdateUserWithPassword() throws SQLException {
        setRequestPathInfo(PATH_INFO);
        UserForm form = createUserFormWithPass();
        getActionServlet().getServletContext().setAttribute("daoFactory", factory);
        when(userDao.getUserById(any(Integer.class))).thenReturn(user);
        when(userDao.getUserByUsername(any(String.class))).thenReturn(user2);
        when(user.getId()).thenReturn(1);
        when(user2.getId()).thenReturn(1);
        when(userDao.updateUserByUserForm(form)).thenReturn(true);
        setActionForm(form);
        actionPerform();
        verifyForward(StatusAction.SUCCESS);
    }

    public void testSuccessUpdateUserNotPassword() throws SQLException {
        setRequestPathInfo(PATH_INFO);
        UserForm form = createUserFormNotPass();
        getActionServlet().getServletContext().setAttribute("daoFactory", factory);
        when(userDao.getUserById(any(Integer.class))).thenReturn(user);
        when(userDao.getUserByUsername(any(String.class))).thenReturn(user2);
        when(user.getId()).thenReturn(1);
        when(user2.getId()).thenReturn(1);
        when(userDao.updateWithoutPassword(form)).thenReturn(true);
        setActionForm(form);
        actionPerform();
        verifyForward(StatusAction.SUCCESS);
    }

    public void testErrorDublicateUsernameUpdate() throws SQLException {
        setRequestPathInfo(PATH_INFO);
        UserForm form = createUserFormNotPass();
        getActionServlet().getServletContext().setAttribute("daoFactory", factory);
        when(userDao.getUserById(any(Integer.class))).thenReturn(user);
        when(userDao.getUserByUsername(any(String.class))).thenReturn(user2);
        when(user.getId()).thenReturn(1);
        when(user2.getId()).thenReturn(2);
        setActionForm(form);
        actionPerform();
        verifyForward(StatusAction.ERROR);
    }

    public void testErrorDbUserUpdate() throws SQLException {
        setRequestPathInfo(PATH_INFO);
        UserForm form = createUserFormNotPass();
        getActionServlet().getServletContext().setAttribute("daoFactory", factory);
        when(userDao.getUserById(any(Integer.class))).thenReturn(user);
        when(userDao.getUserByUsername(any(String.class))).thenReturn(user2);
        when(user.getId()).thenReturn(1);
        when(user2.getId()).thenReturn(1);
        when(userDao.updateWithoutPassword(form)).thenReturn(false);
        setActionForm(form);
        actionPerform();
        verifyForward(StatusAction.ERROR);
    }

    public void testErrorBadDataFormUpdateUser() {
        setRequestPathInfo(PATH_INFO);
        UserForm form = new UserForm();
        setActionForm(form);
        actionPerform();
        verifyForward(StatusAction.ERROR);
    }

    private UserForm createUserFormNotPass() {
        UserForm form = new UserForm();
        form.setId(1);
        form.setLogin("");
        form.setPass("");
        form.setLastName("");
        form.setFirstName("");
        form.setRole(true);
        return form;
    }

    private UserForm createUserFormWithPass() {
        UserForm form = new UserForm();
        form.setId(1);
        form.setLogin("");
        form.setPass("pass");
        form.setLastName("");
        form.setFirstName("");
        form.setRole(true);
        return form;
    }

}