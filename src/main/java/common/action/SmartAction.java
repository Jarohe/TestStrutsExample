package common.action;

import common.db.dao.DaoFactory;
import common.db.dao.UserDao;
import common.utils.ErrorForward;
import org.apache.struts.action.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;

abstract class SmartAction extends Action {

    protected UserDao getUserDao(HttpServletRequest request) {
        Connection connection = (Connection) request.getAttribute("connection");
        DaoFactory factory = (DaoFactory) getServlet().getServletContext().getAttribute("daoFactory");
        return factory.createUserDao(connection);
    }

    protected ActionForward actionErrorForward(HttpServletRequest request, ActionMapping mapping, ErrorForward errorForward) {
        ActionErrors errors = new ActionErrors();
        if (errorForward.getValue() == null) {
            errors.add(errorForward.getProperty(), new ActionMessage(errorForward.getKey()));
        } else {
            errors.add(errorForward.getProperty(), new ActionMessage(errorForward.getKey(), errorForward.getValue()));
        }
        saveErrors(request, errors);
        return mapping.findForward(errorForward.getForwardName());
    }

}
