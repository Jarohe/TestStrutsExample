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
    protected User sessionUser = createSessionUser();

    private User createSessionUser() {
        return new User(10, "userName", "firstName", "lastName", Role.MANAGER);
    }

    public void setUp() throws Exception {
        super.setUp();
        setRequestPathInfo(getRequestPathInfo());
        getRequest().setAttribute("connection", connection);
        when(factory.createUserDao(connection)).thenReturn(userDao);
        getActionServlet().getServletContext().setAttribute("daoFactory", factory);
    }

    protected void setUserFormParameters(User user) {
        addRequestParameter("id", String.valueOf(user.getId()));
        addRequestParameter("login", user.getUsername());
        addRequestParameter("password", "password");
        addRequestParameter("firstName", user.getFirstName());
        addRequestParameter("lastName", user.getLastName());
        if(Role.MANAGER.equals(user.getRole())){
            addRequestParameter("manager", "on");
        }
    }

    abstract String getRequestPathInfo();
}
