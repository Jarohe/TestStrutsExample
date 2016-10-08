package common.action;

import common.db.dao.DaoFactory;
import common.db.dao.UserDao;
import common.db.model.Role;
import common.db.model.User;
import common.utils.Attributes;
import org.apache.struts.action.Action;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;

class SmartAction extends Action {

    protected UserDao getUserDao(HttpServletRequest request) {
        Connection connection = (Connection) request.getAttribute("connection");
        DaoFactory factory = (DaoFactory) getServlet().getServletContext().getAttribute("daoFactory");
        return factory.createUserDao(connection);
    }

    protected boolean isManager(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute(Attributes.Session.USER);
        return Role.MANAGER.equals(user.getRole());
    }
}
