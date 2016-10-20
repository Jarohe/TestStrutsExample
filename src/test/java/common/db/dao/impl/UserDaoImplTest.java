package common.db.dao.impl;

import common.db.dao.UserDao;
import common.db.dao.exceptions.DuplicateUserException;
import common.db.model.Role;
import common.db.model.User;
import junit.framework.TestCase;
import org.apache.commons.dbcp.BasicDataSource;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class UserDaoImplTest {
    private static BasicDataSource db = new BasicDataSource();
    private Connection connection = null;
    private UserDao userDao = null;
    private static String dbUrl = "jdbc:mysql://localhost:3306/test_actimind";
    private static String dbUser = "testActimind";
    private static String dbPassword = "testActimind";
    private static String dbDriver = "com.mysql.jdbc.Driver";
    private static User userManager = new User(0, "managerUser", "managerName", "lastName", Role.MANAGER);
    private static User userDefault = new User(0, "defaultUser", "defaultName", "lastName", Role.DEFAULT);

    static {
        db.setDriverClassName(dbDriver);
        db.setUrl(dbUrl);
        db.setUsername(dbUser);
        db.setPassword(dbPassword);
        db.setDefaultAutoCommit(false);
    }

    @BeforeClass
    public static void initDb() throws SQLException {
        String dropUserTableSql = "DROP TABLE IF EXISTS users;";
        String dropRoleTableSql = "DROP TABLE IF EXISTS role;";
        String usersTableSql = "CREATE TABLE IF NOT EXISTS role (id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,name VARCHAR(100) NOT NULL);";
        String roleTableSql = "CREATE TABLE IF NOT EXISTS users (id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,\n" +
                "username VARCHAR(100) NOT NULL UNIQUE,\n" +
                "password VARCHAR(100) NOT NULL,\n" +
                "firstName VARCHAR(100) NOT NULL,\n" +
                "lastName VARCHAR(100) NOT NULL,\n" +
                "role INT NOT NULL,\n" +
                "FOREIGN KEY (role) REFERENCES role(id) ON DELETE CASCADE ON UPDATE CASCADE);";
        String addRoleDefault = "INSERT INTO role (name) VALUES ('default');";
        String addRoleManager = "INSERT INTO role (name) VALUES ('manager');";

        try (Connection conn = db.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(dropUserTableSql);
            stmt.execute(dropRoleTableSql);
            stmt.execute(usersTableSql);
            stmt.execute(roleTableSql);
            stmt.execute(addRoleDefault);
            stmt.execute(addRoleManager);
            UserDao dao = new UserDaoImpl(conn);
            int idManager = dao.addUser(userManager, "password");
            userManager.setId(idManager);
            int idDefault = dao.addUser(userDefault, "password");
            userDefault.setId(idDefault);
            conn.commit();
        } catch (DuplicateUserException e) {
            e.printStackTrace();
        }
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

    @Test
    public void testGetUserById() throws SQLException, DuplicateUserException {
        User defaultUser = buildDefaultUser();
        int id = userDao.addUser(defaultUser, "password");
        defaultUser.setId(id);
        User resultUser = userDao.getUserById(id);
        assertEquals(defaultUser, resultUser);
    }

    @Test
    public void testNotFoundUserById() throws SQLException {
        User user = userDao.getUserById(-1);
        assertNull(user);
    }

    @Test
    public void testGetUserByUsername() throws SQLException, DuplicateUserException {
        User defaultUser = buildDefaultUser();
        int id = userDao.addUser(defaultUser, "password");
        defaultUser.setId(id);
        User resultUser = userDao.getUserByUsername(defaultUser.getUsername());
        assertEquals(resultUser, defaultUser);
    }

    @Test
    public void testNotFoundUserByUsername() throws SQLException {
        User user = userDao.getUserByUsername("");
        assertNull(user);
    }

    @Test
    public void testGetAllUsers() throws SQLException, DuplicateUserException {
        User defaultUser = buildDefaultUser();
        List<User> userList = userDao.getAllUsers();
        int id = userDao.addUser(defaultUser, "testUser");
        defaultUser.setId(id);
        List<User> resultUserList = userDao.getAllUsers();
        assertEquals(userList.size() + 1, resultUserList.size());
        TestCase.assertEquals(resultUserList, Arrays.asList(userDefault, defaultUser, userManager));
    }

    @Test
    public void testAddUser() throws SQLException, DuplicateUserException {
        User defaultUser = buildDefaultUser();
        int id = userDao.addUser(defaultUser, "password");
        User user = userDao.getUserById(id);
        assertEquals(defaultUser.getUsername(), user.getUsername());
        assertEquals(defaultUser.getUsername(), user.getUsername());
        assertEquals(defaultUser.getFirstName(), user.getFirstName());
        assertEquals(defaultUser.getLastName(), user.getLastName());
        assertEquals(defaultUser.getRole(), user.getRole());
        assertEquals(id, user.getId());
    }

    @Test(expected = DuplicateUserException.class)
    public void testAddDuplicateUser() throws SQLException, DuplicateUserException {
        userDao.addUser(userManager, "password");
    }

    @Test
    public void testDeleteUserById() throws SQLException, DuplicateUserException {
        User defaultUser = buildDefaultUser();
        int id = userDao.addUser(defaultUser, "testUser");
        User user = userDao.getUserById(id);
        assertNotNull(user);
        boolean result = userDao.deleteUserById(id);
        User dropUser = userDao.getUserById(id);
        assertTrue(result);
        assertNull(dropUser);
    }

    @Test
    public void testDeleteNoCreateUser() throws SQLException {
        boolean result = userDao.deleteUserById(-1);
        assertFalse(result);
    }

    @Test
    public void testUpdateUserWithPassword() throws SQLException, DuplicateUserException {
        User defaultUser = buildDefaultUser();
        int id = userDao.addUser(defaultUser, "password");
        defaultUser.setId(id);
        User updateUser = new User(0, "updateUserName", "updateFirstName", "updateLastName", Role.MANAGER);
        updateUser.setId(id);
        boolean result = userDao.updateUser(updateUser, "UpdatePassword");
        updateUser = userDao.getUserById(id);

        assertTrue(result);
        assertNotEquals(defaultUser.getUsername(), updateUser.getUsername());
        assertNotNull(userDao.getUser(updateUser.getUsername(), "UpdatePassword"));
        assertEquals("updateUserName", updateUser.getUsername());
        assertEquals("updateFirstName", updateUser.getFirstName());
        assertEquals("updateLastName", updateUser.getLastName());
        assertEquals(Role.MANAGER, updateUser.getRole());
        assertEquals(id, updateUser.getId());
    }

    @Test(expected = DuplicateUserException.class)
    public void testUpdateUserDuplicateWithPassword() throws SQLException, DuplicateUserException {
        User user = new User(userManager.getId(), userDefault.getUsername(), userManager.getFirstName(), userManager.getLastName(), userManager.getRole());
        userDao.updateUser(user, "password");
    }

    @Test(expected = DuplicateUserException.class)
    public void testUpdateUserDuplicate() throws SQLException, DuplicateUserException {
        User user = new User(userManager.getId(), userDefault.getUsername(), userManager.getFirstName(), userManager.getLastName(), userManager.getRole());
        userDao.updateUser(user);
    }

    @Test
    public void testUpdateNonexistentUser() throws SQLException, DuplicateUserException {
        User user = buildDefaultUser();
        user.setId(-1);
        boolean result = userDao.updateUser(user, "password");
        assertFalse(result);
    }

    @Test
    public void testUpdateWithoutPassword() throws SQLException, DuplicateUserException {
        User defaultUser = buildDefaultUser();
        int id = userDao.addUser(defaultUser, "password");
        defaultUser.setId(id);
        User updateUser = new User(0, "updateUserName", "updateFirstName", "updateLastName", Role.MANAGER);
        updateUser.setId(id);
        boolean result = userDao.updateUser(updateUser);
        updateUser = userDao.getUserById(id);
        assertTrue(result);
        assertNotEquals(defaultUser.getUsername(), updateUser.getUsername());
        assertNotNull(userDao.getUser(updateUser.getUsername(), "password"));
        assertEquals("updateUserName", updateUser.getUsername());
        assertEquals("updateFirstName", updateUser.getFirstName());
        assertEquals("updateLastName", updateUser.getLastName());
        assertEquals(Role.MANAGER, updateUser.getRole());
        assertEquals(defaultUser.getId(), updateUser.getId());
    }

    @Test
    public void testUpdateNonexistentUserWithoutPassword() throws SQLException, DuplicateUserException {
        User user = buildDefaultUser();
        user.setId(-1);
        boolean result = userDao.updateUser(user, "password");
        assertFalse(result);
    }

    private User buildDefaultUser() {
        return new User(0, "username", "firstName", "lastName", Role.DEFAULT);
    }

}