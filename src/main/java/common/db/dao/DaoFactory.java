package common.db.dao;

import java.sql.Connection;

public interface DaoFactory {
    UserDao createUserDao(Connection connection);
}
