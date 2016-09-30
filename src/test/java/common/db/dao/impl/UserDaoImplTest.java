package common.db.dao.impl;

import common.db.dao.UserDao;
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

import static org.junit.Assert.assertTrue;


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

    public void testGetUserById() throws SQLException {
        UserForm form  = buildDefoultUserForm();
        userDao.addUser(form);
        User user1 = userDao.getUserByUsername(form.getLogin());
        User user2 = userDao.getUserById(user1.getId());
        assertTrue(user1.equals(user2));
    }

    public void testGetUserByUsername() throws SQLException {
        UserForm form  = buildDefoultUserForm();
        userDao.addUser(form);
        User user = userDao.getUserByUsername(form.getLogin());
        assertTrue(form.getLogin().equals(user.getUsername()));
        assertTrue(form.getPass().equals(user.getPassword()));
        assertTrue(form.getFirstName().equals(user.getFirstName()));
        assertTrue(form.getLastName().equals(user.getLastName()));
        assertTrue(Role.DEFAULT.equals(user.getRole()));
    }

    public void testGetAllUsers() throws SQLException {
        List<User> resultUsers = userDao.getAllUsers();
        assertTrue(resultUsers.size() == 3);
    }

    public void testAddUser() throws SQLException {
        UserForm form = buildDefoultUserForm();
        userDao.addUser(form);
        User user = userDao.getUserByUsername(form.getLogin());
        assertTrue(form.getLogin().equals(user.getUsername()));
        assertTrue(form.getPass().equals(user.getPassword()));
        assertTrue(form.getFirstName().equals(user.getFirstName()));
        assertTrue(form.getLastName().equals(user.getLastName()));
        assertTrue(Role.DEFAULT.equals(user.getRole()));
    }

    public void testDeleteUserById() throws SQLException {
        UserForm form = buildDefoultUserForm();
        userDao.addUser(form);
        User test1 = userDao.getUserByUsername(form.getLogin());
        assertTrue(test1 != null);
        userDao.deleteUserById(test1.getId());
        User test2 = userDao.getUserByUsername(form.getLogin());
        assertTrue(test2 == null);
    }

    public void testUpdateUserByUserForm() throws SQLException {
        UserForm form = buildDefoultUserForm();
        userDao.addUser(form);
        User user1 = userDao.getUserByUsername(form.getLogin());
        form.setId(user1.getId());
        form.setRole(true);
        userDao.updateUserByUserForm(form);
        User user2 = userDao.getUserById(user1.getId());
        assertTrue(!user1.getRole().equals(user2.getRole()));
        assertTrue(user1.getUsername().equals(user2.getUsername()));
        assertTrue(user1.getPassword().equals(user2.getPassword()));
        assertTrue(user1.getFirstName().equals(user2.getFirstName()));
        assertTrue(user1.getLastName().equals(user2.getLastName()));
        assertTrue(user1.getId() == user2.getId());
    }

    public void testUpdateWithoutPassword() throws SQLException {
        UserForm form = buildDefoultUserForm();
        userDao.addUser(form);
        User user1 = userDao.getUserByUsername(form.getLogin());
        form.setId(user1.getId());
        form.setPass("trololo");
        userDao.updateWithoutPassword(form);
        User user2 = userDao.getUserById(user1.getId());
        assertTrue(user1.equals(user2));
    }

    public void testDeleteUserByUsername() throws SQLException {
        UserForm form = buildDefoultUserForm();
        userDao.addUser(form);
        User user = userDao.getUserByUsername(form.getLogin());
        assertTrue(user != null);
        userDao.deleteUserByUsername(user.getUsername());
        user = userDao.getUserById(user.getId());
        assertTrue(user == null);
    }

    private UserForm buildDefoultUserForm() {
        UserForm form = new UserForm();
        form.setLogin("testUser");
        form.setPass("testUser");
        form.setFirstName("testUser");
        form.setLastName("testUser");
        form.setRole(false);
        return form;
    }

}