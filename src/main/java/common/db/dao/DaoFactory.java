package common.db.dao;

import java.sql.Connection;

/**
 * Created by derevyanko on 27.09.2016.
 */
public interface DaoFactory {
    UserDao createUserDao(Connection connection);
}
