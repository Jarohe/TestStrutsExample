package common.db.dao.impl;

import common.db.dao.UserDao;
import common.db.dao.exceptions.DublicateUserException;
import common.db.model.Role;
import common.db.model.User;
import common.form.UserForm;
import junit.framework.TestCase;
import org.apache.commons.dbcp.BasicDataSource;
import org.junit.After;
import org.junit.Before;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;


public class UserDaoImplTest extends TestCase {
    //TODO create test DB
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

    public void testGetUserById() throws SQLException, DublicateUserException {
        User defoultUser = buildDefoultUser();
        int id = userDao.addUser(defoultUser, "password");
        defoultUser.setId(id);
        User resultUser = userDao.getUserById(id);
        assertTrue(defoultUser.equals(resultUser));
    }

    public void testGetUserByUsername() throws SQLException, DublicateUserException {
        User defoultUser = buildDefoultUser();
        userDao.addUser(defoultUser, "testUser");
        User user = userDao.getUserByUsername(defoultUser.getUsername());
        assertTrue(defoultUser.getUsername().equals(user.getUsername()));
        assertTrue(defoultUser.getFirstName().equals(user.getFirstName()));
        assertTrue(defoultUser.getLastName().equals(user.getLastName()));
        assertTrue(Role.DEFAULT.equals(user.getRole()));
    }

    public void testGetAllUsers() throws SQLException, DublicateUserException {
        User defoultUser = buildDefoultUser();
        List<User> resultUsers = userDao.getAllUsers();
        userDao.addUser(defoultUser, "testUser");
        List<User> userList = userDao.getAllUsers();
        assertTrue(resultUsers.size() + 1 == userList.size());
    }

    public void testAddUser() throws SQLException, DublicateUserException {
        User defoultUser = buildDefoultUser();
        userDao.addUser(defoultUser, "testUser");
        User user = userDao.getUserByUsername(defoultUser.getUsername());
        assertTrue(defoultUser.getUsername().equals(user.getUsername()));
        assertTrue(defoultUser.getFirstName().equals(user.getFirstName()));
        assertTrue(defoultUser.getLastName().equals(user.getLastName()));
        assertTrue(Role.DEFAULT.equals(user.getRole()));
    }

    public void testDeleteUserById() throws SQLException, DublicateUserException {
        User defoultUser = buildDefoultUser();
        userDao.addUser(defoultUser, "testUser");
        User test1 = userDao.getUserByUsername(defoultUser.getUsername());
        assertTrue(test1 != null);
        userDao.deleteUserById(test1.getId());
        User test2 = userDao.getUserByUsername(defoultUser.getUsername());
        assertTrue(test2 == null);
    }

    public void testUpdateUserWithPassword() throws SQLException, DublicateUserException {
        User defoultUser = buildDefoultUser();
        int id = userDao.addUser(defoultUser, "testUser");
        User user1 = userDao.getUserByUsername(defoultUser.getUsername());
        defoultUser.setId(id);
        defoultUser.setRole(Role.MANAGER);
        userDao.updateUser(defoultUser, "password");
        User user2 = userDao.getUserById(user1.getId());
        assertTrue(!user1.getRole().equals(user2.getRole()));
        assertTrue(user1.getUsername().equals(user2.getUsername()));
        assertTrue(user1.getFirstName().equals(user2.getFirstName()));
        assertTrue(user1.getLastName().equals(user2.getLastName()));
        assertTrue(user1.getId() == user2.getId());
    }

    public void testUpdateWithoutPassword() throws SQLException, DublicateUserException {
        User defoultUser = buildDefoultUser();
        int id = userDao.addUser(defoultUser, "testUser");
        User user1 = userDao.getUserByUsername(defoultUser.getUsername());
        defoultUser.setId(id);
        userDao.updateUser(defoultUser);
        User user2 = userDao.getUserById(user1.getId());
        assertTrue(user1.equals(user2));
    }

    private User buildDefoultUser() {
        return new User(0,"username","firstName","lastName",Role.DEFAULT);
    }

}