package common.action;

import common.db.dao.DaoFactory;
import common.db.dao.UserDao;
import common.db.model.User;
import common.utils.StatusAction;
import servletunit.struts.MockStrutsTestCase;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.anyList;
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class UserListActionTest extends UtilActionTest {
    private String pathInfo = "/system/usersList";

    public void setUp() throws Exception {
        super.setUp(pathInfo);
    }

    public void testSuccessUserList() throws SQLException {
        List<User> userList = new ArrayList<>();
        userList.add(user);
        when(userDao.getAllUsers()).thenReturn(userList);
        assertNull(getRequest().getAttribute("userList"));
        actionPerform();
        verifyForward(StatusAction.SUCCESS);
        assertNotNull(getRequest().getAttribute("userList"));
        List<User> resultList = (List<User>) getRequest().getAttribute("userList");
        assertTrue(userList.equals(resultList));
    }

}