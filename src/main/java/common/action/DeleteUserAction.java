package common.action;

import common.db.dao.UserDao;
import common.db.model.User;
import common.utils.Attributes;
import common.utils.StatusAction;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteUserAction extends SmartAction {
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        User user = (User) request.getSession(false).getAttribute(Attributes.Session.USER);

        Integer userId = Integer.valueOf(request.getParameter("id"));
        if (user.getId() == userId) {
            return actionErrorForward(request, mapping, StatusAction.ERROR, "remove", "error.can.not.remove", "yourself");
        }
        UserDao userDao = getUserDao(request);
        if (userDao.deleteUserById(userId)) {
            return mapping.findForward(StatusAction.SUCCESS);
        }
        return actionErrorForward(request, mapping, StatusAction.ERROR, "noFoundId", "error.not.found.user.id", userId);
    }
}
