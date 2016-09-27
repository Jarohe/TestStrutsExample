package common.db.dao;

import common.db.dao.impl.UserDaoImpl;

import java.sql.Connection;

/**
 * Created by derevyanko on 27.09.2016.
 */
public class UserDaoFactoryImpl implements DaoFactory {
    @Override
    public UserDao createUserDao(Connection connection) {
        return new UserDaoImpl(connection);
    }
}
