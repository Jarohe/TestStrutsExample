package common.action;

import common.db.dao.UserDao;
import common.db.dao.impl.UserDaoImpl;
import common.db.model.User;
import common.utils.Attributes;
import common.utils.StatusAction;
import org.apache.struts.action.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;

public class DeleteUserAction extends SmartAction {
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ActionErrors errors = new ActionErrors();
        User user = (User) request.getSession(false).getAttribute(Attributes.Session.USER);
        if(!isManager(request)) {
            errors.add("access denied", new ActionMessage("error.access.denied"));
            saveErrors(request.getSession(),errors);
            return mapping.findForward(StatusAction.ERROR);
        }
        Integer userId = Integer.valueOf(request.getParameter("id"));
        if(user.getId() == userId) {
            errors.add("remove",new ActionMessage("error.can.not.remove","yourself"));
            saveErrors(request.getSession(),errors);
            return mapping.findForward(StatusAction.ERROR);
        }
        UserDao userDao = getUserDao(request);
        if(userDao.deleteUserById(userId)){
            return mapping.findForward(StatusAction.SUCCESS);
        }

        errors.add("noFoundId", new ActionMessage("error.not.found.user.id", userId));
        saveErrors(request.getSession(),errors);
        return mapping.findForward(StatusAction.ERROR);
    }
}
