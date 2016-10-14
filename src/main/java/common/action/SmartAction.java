package common.action;

import common.db.dao.DaoFactory;
import common.db.dao.UserDao;
import common.utils.ErrorForward;
import org.apache.struts.action.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Connection;

abstract class SmartAction extends Action {

    protected UserDao getUserDao(HttpServletRequest request) {
        Connection connection = (Connection) request.getAttribute("connection");
        DaoFactory factory = (DaoFactory) getServlet().getServletContext().getAttribute("daoFactory");
        return factory.createUserDao(connection);
    }

    protected ActionForward actionErrorForward(HttpServletRequest request, ActionMapping mapping, ErrorForward errorForward) {
        ActionErrors errors = new ActionErrors();
        errors.add(errorForward.getProperty(), new ActionMessage(errorForward.getKey()));
        saveErrors(request, errors);
        return mapping.findForward(errorForward.getForwardName());
    }

    protected ActionForward actionErrorForward(HttpServletRequest request, ActionMapping mapping, ErrorForward errorForward, Object value) {
        ActionErrors errors = new ActionErrors();
        errors.add(errorForward.getProperty(), new ActionMessage(errorForward.getKey(), value));
        saveErrors(request, errors);
        return mapping.findForward(errorForward.getForwardName());
    }

    protected ActionForward actionErrorForward(HttpSession session, ActionMapping mapping, ErrorForward errorForward) {
        ActionErrors errors = new ActionErrors();
        errors.add(errorForward.getProperty(), new ActionMessage(errorForward.getKey()));
        saveErrors(session, errors);
        return mapping.findForward(errorForward.getForwardName());
    }


}
