package common.action;

import common.db.dao.DaoFactory;
import common.db.dao.UserDao;
import common.db.model.Role;
import common.db.model.User;
import servletunit.struts.MockStrutsTestCase;

import java.sql.Connection;

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
        getSession().setAttribute("sessionUser",userSession);
    }
    //TODO
    public void testSuccessEditUser() {
        setRequestPathInfo(PATH_INFO);
        getActionServlet().getServletContext().setAttribute("daoFactory", factory);
    }

    private User createSessionUser() {
        return new User.Builder(1,"","").role(Role.MANAGER).build();
    }

}