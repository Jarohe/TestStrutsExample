package common.db.dao.impl;


import com.mysql.jdbc.Statement;
import common.db.dao.UserDao;
import common.db.dao.exceptions.DublicateUserException;
import common.db.model.Role;
import common.db.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao {
    private Connection connection;

    public UserDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public User getUserByLoginAndPassword(String login, String password) throws SQLException {
        String sql = "SELECT id, username, firstName,lastName,password,role FROM users WHERE username = ? AND password = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, login);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            return getUser(resultSet);
        }
    }

    @Override
    public User getUserById(int id) throws SQLException {
        String sql = "SELECT id, username, firstName,lastName,password,role FROM users WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            return getUser(resultSet);
        }
    }

    @Override
    public User getUserByUsername(String login) throws SQLException {
        String sql = "SELECT id, username, firstName,lastName,password,role FROM users WHERE username = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();
            return getUser(resultSet);
        }
    }

    private User getUser(ResultSet resultSet) throws SQLException {
        if (resultSet.next()) {
            Role role = null;
            for (Role i : Role.values()) {
                if (i.getId() == resultSet.getInt("role")) {
                    role = i;
                }
            }
            return new User.Builder(resultSet.getInt("id"), resultSet.getString("username"))
                    .firstName(resultSet.getString("firstName"))
                    .lastName(resultSet.getString("lastName"))
                    .role(role).build();
        }

        return null;
    }

    @Override
    public List<User> getAllUsers() throws SQLException {
        List<User> users = new ArrayList<>();
        String sql = "SELECT id, username, firstName,lastName,password,role FROM users ORDER BY firstName, lastName";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                users.add(new User.Builder(resultSet.getInt("id"), resultSet.getString("username"))
                        .firstName(resultSet.getString("firstName")).lastName(resultSet.getString("lastName")).build());
            }
            return users;
        }
    }

    @Override
    public boolean deleteUser(User user) throws SQLException {
        return deleteUserById(user.getId());
    }

    @Override
    public boolean deleteUserById(int id) throws SQLException {
        String sql = "DELETE FROM users WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            return statement.executeUpdate() == 1;
        }
    }

    @Override
    public int addUser(User user, String password) throws SQLException, DublicateUserException {
        String sql = "INSERT INTO users (username, firstName,lastName, password,role) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getFirstName());
            statement.setString(3, user.getLastName());
            statement.setString(4, password);
            statement.setInt(5, user.getRole().getId());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
            return 0;
        } catch (SQLException e) {
            if (getUserByUsername(user.getUsername()) != null) {
                throw new DublicateUserException();
            }
            throw e;
        }
    }

    @Override
    public boolean updateUser(User user, String password) throws SQLException, DublicateUserException {
        String sql = "UPDATE users SET username=?, password=?,firstName=?,lastName=?,role=? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, user.getUsername());
            statement.setString(2, password);
            statement.setString(3, user.getFirstName());
            statement.setString(4, user.getLastName());
            statement.setInt(5, user.getRole().getId());
            statement.setInt(6, user.getId());
            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            if (getUserByUsername(user.getUsername()) != null) {
                throw new DublicateUserException();
            }
            throw e;
        }

    }

    @Override
    public boolean updateUser(User user) throws SQLException, DublicateUserException {
        String sql = "UPDATE users SET username=?, firstName=?,lastName=?,role=? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getFirstName());
            statement.setString(3, user.getLastName());
            statement.setInt(4, user.getRole().getId());
            statement.setInt(5, user.getId());
            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            if (getUserByUsername(user.getUsername()) != null) {
                throw new DublicateUserException();
            }
            throw e;
        }
    }
}
