package common.action;

import common.db.dao.DaoFactory;
import common.db.dao.UserDao;
import org.apache.struts.action.Action;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;

class SmartAction extends Action {

    protected UserDao getUserDao(HttpServletRequest request) {
        Connection connection = (Connection) request.getAttribute("connection");
        DaoFactory factory = (DaoFactory) getServlet().getServletContext().getAttribute("daoFactory");
        return factory.createUserDao(connection);
    }
}
