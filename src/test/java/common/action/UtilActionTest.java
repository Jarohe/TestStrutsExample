package common.action;

import common.db.dao.DaoFactory;
import common.db.dao.UserDao;
import common.db.model.Role;
import common.db.model.User;
import servletunit.struts.MockStrutsTestCase;

import java.sql.Connection;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

abstract class UtilActionTest extends MockStrutsTestCase {
    private Connection connection = mock(Connection.class);
    protected UserDao userDao = mock(UserDao.class);
    private DaoFactory factory = mock(DaoFactory.class);
    protected User user = createSessionUser();

    private User createSessionUser() {
        return new User(10, "userName", "firstName", "lastName", Role.MANAGER);
    }

    public void setUp() throws Exception {
        super.setUp();
        setRequestPathInfo(getRequestPathIbfo());
        getRequest().setAttribute("connection", connection);
        when(factory.createUserDao(connection)).thenReturn(userDao);
        getActionServlet().getServletContext().setAttribute("daoFactory", factory);
    }

    abstract String getRequestPathIbfo();
}
