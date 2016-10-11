package common.action;

import common.db.dao.UserDao;
import common.db.model.User;
import common.utils.Attributes;
import common.utils.ErrorForvard;
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
        ErrorForvard errorForvard;
        try {
            Integer userId = Integer.valueOf(request.getParameter("id"));
            if (user.getId() == userId) {
                errorForvard = new ErrorForvard(StatusAction.ERROR, "remove", ErrorMessageKey.DeleteUser.CAN_NOT_REMOVE);
                return actionErrorForward(request, mapping, errorForvard, "yourself");
            }
            UserDao userDao = getUserDao(request);
            if (userDao.deleteUserById(userId)) {
                return mapping.findForward(StatusAction.SUCCESS);
            }
            errorForvard = new ErrorForvard(StatusAction.ERROR, "noFoundId", ErrorMessageKey.DeleteUser.NOT_FOUND_USER_ID);
            return actionErrorForward(request, mapping, errorForvard, userId);
        } catch (NumberFormatException e) {
            errorForvard = new ErrorForvard(StatusAction.ERROR, "notNumber", ErrorMessageKey.DeleteUser.ID_NOT_NUMBER);
            return actionErrorForward(request, mapping, errorForvard);
        }
    }
}
