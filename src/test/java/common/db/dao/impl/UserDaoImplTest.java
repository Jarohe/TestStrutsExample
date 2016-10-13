package common.db.dao.impl;

import common.db.dao.UserDao;
import common.db.dao.exceptions.DublicateUserException;
import common.db.model.Role;
import common.db.model.User;
import junit.framework.TestCase;
import org.apache.commons.dbcp.BasicDataSource;
import org.junit.After;
import org.junit.Before;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class UserDaoImplTest extends TestCase {
    private static String dbUrl = "jdbc:mysql://localhost:3306/actimind";
    private static String dbUser = "actimind";
    private static String dbPassword = "actimindAdmin";
    private static String dbDriver = "com.mysql.jdbc.Driver";
    private static BasicDataSource db = new BasicDataSource();
    private Connection connection = null;
    private UserDao userDao = null;

    static {
        db.setDriverClassName(dbDriver);
        db.setUrl(dbUrl);
        db.setUsername(dbUser);
        db.setPassword(dbPassword);
        db.setDefaultAutoCommit(false);
    }

    @Before
    public void setUp() throws Exception {
        connection = db.getConnection();
        userDao = new UserDaoImpl(connection);
    }

    @After
    public void tearDown() throws Exception {
        connection.rollback();
        connection.close();
    }

    // TODO: Нет теста, в котором getUserById() не находит пользователя
    // TODO: Нет теста, в котором getUserById() находит нужного среди нескольких
    public void testGetUserById() throws SQLException, DublicateUserException {
        User defoultUser = buildDefoultUser();
        int id = userDao.addUser(defoultUser, "password");
        defoultUser.setId(id);
        User resultUser = userDao.getUserById(id);
        assertEquals(defoultUser, resultUser);
    }

    // TODO: То же самое
    public void testGetUserByUsername() throws SQLException, DublicateUserException {
        User defoultUser = buildDefoultUser();
        int id = userDao.addUser(defoultUser, "password");
        defoultUser.setId(id);
        User resultUser = userDao.getUserByUsername(defoultUser.getUsername());
        assertEquals(resultUser, defoultUser);
    }

    // TODO: Сортировка?
    // TODO: Параметры самих пользователей?
    public void testGetAllUsers() throws SQLException, DublicateUserException {
        User defoultUser = buildDefoultUser();
        List<User> userList = userDao.getAllUsers();
        userDao.addUser(defoultUser, "testUser");
        List<User> resultUserList = userDao.getAllUsers();
        assertTrue(userList.size() + 1 == resultUserList.size());
    }

    public void testAddUser() throws SQLException, DublicateUserException {
        User defoultUser = buildDefoultUser();
        int id = userDao.addUser(defoultUser, "password");
        User user = userDao.getUserById(id);
        assertEquals(defoultUser.getUsername(), user.getUsername());
        assertEquals(defoultUser.getUsername(), user.getUsername());
        assertEquals(defoultUser.getFirstName(), user.getFirstName());
        assertEquals(defoultUser.getLastName(), user.getLastName());
        assertEquals(defoultUser.getRole(), user.getRole());
        assertTrue(id == user.getId());
    }

    public void testDeleteUserById() throws SQLException, DublicateUserException {
        User defoultUser = buildDefoultUser();
        int id = userDao.addUser(defoultUser, "testUser");
        User user = userDao.getUserById(id);
        assertTrue(user != null);
        userDao.deleteUserById(id);
        User dropUser = userDao.getUserById(id);
        assertTrue(dropUser == null);
    }

    public void testUpdateUserWithPassword() throws SQLException, DublicateUserException {
        User defoultUser = buildDefoultUser();
        int id = userDao.addUser(defoultUser, "password");
        defoultUser.setId(id);
        User updateUser = new User(0, "updateUserName", "updateFirstName", "updateLastName", Role.MANAGER);
        updateUser.setId(id);
        userDao.updateUser(updateUser, "UpdatePassword");
        updateUser = userDao.getUserById(id);
        assertFalse(defoultUser.getUsername().equals(updateUser.getUsername()));
        assertNotNull(userDao.getUser(updateUser.getUsername(), "UpdatePassword"));
        assertFalse(defoultUser.getFirstName().equals(updateUser.getFirstName()));
        assertFalse(defoultUser.getLastName().equals(updateUser.getLastName()));
        assertEquals(Role.MANAGER, updateUser.getRole());
        assertTrue(defoultUser.getId() == updateUser.getId());
    }

    public void testUpdateWithoutPassword() throws SQLException, DublicateUserException {
        User defoultUser = buildDefoultUser();
        int id = userDao.addUser(defoultUser, "password");
        defoultUser.setId(id);
        User updateUser = new User(0, "updateUserName", "updateFirstName", "updateLastName", Role.MANAGER);
        updateUser.setId(id);
        userDao.updateUser(updateUser);
        updateUser = userDao.getUserById(id);
        assertFalse(defoultUser.getUsername().equals(updateUser.getUsername()));
        assertNotNull(userDao.getUser(updateUser.getUsername(), "password"));
        assertFalse(defoultUser.getFirstName().equals(updateUser.getFirstName()));
        assertFalse(defoultUser.getLastName().equals(updateUser.getLastName()));
        assertEquals(Role.MANAGER, updateUser.getRole());
        assertTrue(defoultUser.getId() == updateUser.getId());
    }

    private User buildDefoultUser() {
        return new User(0, "username", "firstName", "lastName", Role.DEFAULT);
    }

}