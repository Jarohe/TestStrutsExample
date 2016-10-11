package common.db.dao;

import common.db.dao.exceptions.DublicateUserException;
import common.db.model.User;

import java.sql.SQLException;
import java.util.List;

public interface UserDao {
    User getUser(String login, String password) throws SQLException;

    User getUserById(int id) throws SQLException;

    User getUserByUsername(String login) throws SQLException;

    List<User> getAllUsers() throws SQLException;

    boolean deleteUser(User user) throws SQLException;

    boolean deleteUserById(int id) throws SQLException;

    int addUser(User user, String password) throws SQLException, DublicateUserException;

    boolean updateUser(User user, String password) throws SQLException, DublicateUserException;

    boolean updateUser(User user) throws SQLException, DublicateUserException;
}
