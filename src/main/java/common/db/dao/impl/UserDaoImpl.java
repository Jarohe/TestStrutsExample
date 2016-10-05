package common.db.dao.impl;


import common.db.dao.UserDao;
import common.db.model.Role;
import common.db.model.User;
import common.form.UserForm;

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
        try (PreparedStatement statement = connection.prepareStatement(
                "SELECT id, username, firstName,lastName,password,role FROM users WHERE username = ? AND password = ?")) {
            statement.setString(1, login);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            return getUser(resultSet);
        }
    }

    @Override
    public User getUserById(int id) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(
                "SELECT id, username, firstName,lastName,password,role FROM users WHERE id = ?")) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            return getUser(resultSet);
        }
    }

    @Override
    public User getUserByUsername(String login) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(
                "SELECT id, username, firstName,lastName,password,role FROM users WHERE username = ?")) {
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();
            return getUser(resultSet);
        }
    }

    private User getUser(ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            Role role = null;
            if(resultSet.getInt("role") == 1) {
                role = Role.DEFAULT;
            } else if (resultSet.getInt("role") == 2) {
                role = Role.MANAGER;
            }
            return new User.Builder(resultSet.getInt("id"), resultSet.getString("username"), resultSet.getString("password"))
                    .firstName(resultSet.getString("firstName"))
                    .lastName(resultSet.getString("lastName"))
                    .role(role).build();
        }
        return null;
    }

    @Override
    public List<User> getAllUsers() throws SQLException {
        List<User> users = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(
                "SELECT id, username, firstName,lastName,password,role FROM users")) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                users.add(new User.Builder(resultSet.getInt("id"), resultSet.getString("username"), resultSet.getString("password"))
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
        try (PreparedStatement statement = connection.prepareStatement(
                "DELETE FROM users WHERE id = ?")) {
            statement.setInt(1, id);
            return statement.execute();
        }
    }

    @Override
    public boolean deleteUserByUsername(String login) throws SQLException {
        boolean execute;
        try (PreparedStatement statement = connection.prepareStatement(
                "DELETE FROM users WHERE username = ?")) {
            statement.setString(1, login);
            execute = statement.execute();

        }
        return execute;
    }

    @Override
    public boolean addUser(UserForm form) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement("INSERT INTO users (username, firstName,lastName, password,role) VALUES (?, ?, ?, ?, ?)")) {
            statement.setString(1, form.getLogin());
            statement.setString(2, form.getFirstName());
            statement.setString(3, form.getLastName());
            statement.setString(4, form.getPass());
            if (form.isManager()) {
                statement.setInt(5, 2);
            } else {
                statement.setInt(5, 1);
            }
            return statement.execute();
        }
    }

    @Override
    public boolean updateUserByUserForm(UserForm user) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement("UPDATE users SET username=?, password=?,firstName=?,lastName=?,role=? WHERE id = ?")) {
            statement.setString(1, user.getLogin());
            statement.setString(2, user.getPass());
            statement.setString(3, user.getFirstName());
            statement.setString(4, user.getLastName());
            if (user.isManager()) {
                statement.setInt(5, 2);
            } else {
                statement.setInt(5, 1);
            }
            statement.setInt(6, user.getId());
            return statement.executeUpdate() == 1;
        }
    }

    @Override
    public boolean updateWithoutPassword(UserForm user) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement("UPDATE users SET username=?, firstName=?,lastName=?,role=? WHERE id = ?")) {
            statement.setString(1, user.getLogin());
            statement.setString(2, user.getFirstName());
            statement.setString(3, user.getLastName());
            if (user.isManager()) {
                statement.setInt(4, 2);
            } else {
                statement.setInt(4, 1);
            }
            statement.setInt(5, user.getId());
            return statement.executeUpdate() == 1;
        }
    }
}
