package common.action;

import common.db.dao.UserDao;
import common.db.model.User;
import common.utils.Attributes;
import common.utils.ErrorMessageKey;
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
        try {
            Integer userId = Integer.valueOf(request.getParameter("id"));
            if (user.getId() == userId) {
                return actionErrorForward(request, mapping, StatusAction.ERROR, "remove", ErrorMessageKey.DeleteUser.CAN_NOT_REMOVE, "yourself");
            }
            UserDao userDao = getUserDao(request);
            if (userDao.deleteUserById(userId)) {
                return mapping.findForward(StatusAction.SUCCESS);
            }
            return actionErrorForward(request, mapping, StatusAction.ERROR, "noFoundId", ErrorMessageKey.DeleteUser.NOT_FOUND_USER_ID, userId);
        } catch (NumberFormatException e) {
            return actionErrorForward(request, mapping, StatusAction.ERROR, "notNumber", ErrorMessageKey.DeleteUser.ID_NOT_NUMBER);
        }
    }
}
