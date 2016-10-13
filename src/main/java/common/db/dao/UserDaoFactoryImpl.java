package common.db.dao;

import common.db.dao.impl.UserDaoImpl;

import java.sql.Connection;

public class UserDaoFactoryImpl implements DaoFactory {
    @Override
    public UserDao createUserDao(Connection connection) {
        return new UserDaoImpl(connection);
    }
}
