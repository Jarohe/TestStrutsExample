package common.db.dao;

import common.db.model.User;
import common.form.UserForm;

import java.sql.SQLException;
import java.util.List;

public interface UserDao {
    User getUserByLoginAndPassword(String login, String password) throws SQLException;
    User getUserById(int id) throws SQLException;
    User getUserByUsername(String login) throws SQLException;
    List<User> getAllUsers() throws SQLException;
    boolean deleteUser(User user) throws SQLException;
    boolean deleteUserById(int id) throws SQLException;
    boolean deleteUserByUsername(String login) throws SQLException;
    boolean addUser(UserForm form) throws SQLException;
    boolean updateUserByUserForm(UserForm user) throws SQLException;
    boolean updateWithoutPassword(UserForm user) throws SQLException;
}
