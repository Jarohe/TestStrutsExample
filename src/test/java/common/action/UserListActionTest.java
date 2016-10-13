package common.action;

import common.db.model.User;
import common.utils.StatusAction;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;


public class UserListActionTest extends UtilActionTest {

    public void setUp() throws Exception {
        super.setUp("/system/usersList");
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