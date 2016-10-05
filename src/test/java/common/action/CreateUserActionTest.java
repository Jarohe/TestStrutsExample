package common.action;

import common.db.dao.DaoFactory;
import common.db.dao.UserDao;
import common.db.model.Role;
import common.db.model.User;
import common.form.UserForm;
import common.utils.StatusAction;
import servletunit.struts.MockStrutsTestCase;

import java.sql.Connection;
import java.sql.SQLException;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class CreateUserActionTest extends MockStrutsTestCase {
    private static String PATH_INFO = "/system/createUser";

    private Connection connection = mock(Connection.class);
    private UserDao userDao = mock(UserDao.class);
    private DaoFactory factory = mock(DaoFactory.class);
    private User user = createSessionUser();

/*    public void setUp() throws Exception {
        super.setUp();
        getRequest().setAttribute("connection", connection);
        when(factory.createUserDao(connection)).thenReturn(userDao);
        getSession().setAttribute("sessionUser",user);
    }

    public void testSuccessUserCreate() throws SQLException {
        setRequestPathInfo(PATH_INFO);
        UserForm form = createUserForm();
        setActionForm(form);
        when(userDao.getUserByUsername(any(String.class))).thenReturn(null);
        when(userDao.addUser(form)).thenReturn(true);

        getActionServlet().getServletContext().setAttribute("daoFactory", factory);

        actionPerform();
        verifyForward(StatusAction.SUCCESS);
    }

    public void testDuplicateUserCreate() throws SQLException {
        setRequestPathInfo(PATH_INFO);
        UserForm form = createUserForm();
        setActionForm(form);
        when(userDao.getUserByUsername(any(String.class))).thenReturn(user);

        getActionServlet().getServletContext().setAttribute("daoFactory", factory);

        actionPerform();
        verifyForward(StatusAction.CreateUser.DUBLICATE_USER);
    }

    public void testErrorUserCteare() {
        setRequestPathInfo(PATH_INFO);
        UserForm form = createUserForm();
        form.setLogin(null);

        actionPerform();
        verifyForward(StatusAction.ERROR);
    }*/

    private UserForm createUserForm() {
        UserForm form = new UserForm();
        form.setId(0);
        form.setLogin("");
        form.setPass("");
        form.setFirstName("");
        form.setLastName("");
        form.setManager(true);
        return form;
    }

    private User createSessionUser() {
        return new User.Builder(1,"","").role(Role.MANAGER).build();
    }
}