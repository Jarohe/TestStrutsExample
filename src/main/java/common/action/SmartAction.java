package common.action;

import common.db.dao.DaoFactory;
import common.db.dao.UserDao;
import common.db.model.Role;
import common.db.model.User;
import common.utils.Attributes;
import common.utils.ErrorForvard;
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

    protected ActionForward actionErrorForward(HttpServletRequest request, ActionMapping mapping, ErrorForvard errorForvard) {
        ActionErrors errors = new ActionErrors();
        errors.add(errorForvard.getProperty(), new ActionMessage(errorForvard.getKey()));
        saveErrors(request, errors);
        return mapping.findForward(errorForvard.getForwardName());
    }

    protected ActionForward actionErrorForward(HttpServletRequest request, ActionMapping mapping, ErrorForvard errorForvard, Object value) {
        ActionErrors errors = new ActionErrors();
        errors.add(errorForvard.getProperty(), new ActionMessage(errorForvard.getKey(), value));
        saveErrors(request, errors);
        return mapping.findForward(errorForvard.getForwardName());
    }

    protected ActionForward actionErrorForward(HttpSession session, ActionMapping mapping, ErrorForvard errorForvard) {
        ActionErrors errors = new ActionErrors();
        errors.add(errorForvard.getProperty(), new ActionMessage(errorForvard.getKey()));
        saveErrors(session, errors);
        return mapping.findForward(errorForvard.getForwardName());
    }


}
