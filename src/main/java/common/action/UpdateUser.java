package common.action;

import common.db.dao.UserDao;
import common.db.dao.exceptions.DublicateUserException;
import common.form.UserForm;
import common.utils.StatusAction;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UpdateUser extends SmartAction {

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        UserForm userForm = (UserForm) form;
        UserDao userDao = getUserDao(request);

        if (userForm.getId() == 0) {
            return actionErrorForward(request, mapping, StatusAction.ERROR, "notId", "errors.not.id");
        }

        try {
            if (userForm.getPassword().length() > 0) {
                if (!userDao.updateUser(userForm.extractUser(), userForm.getPassword())) {
                    return actionErrorForward(request, mapping, StatusAction.ERROR, "userNotUpdate", "error.user.not.update");
                }
            } else {
                if (!userDao.updateUser(userForm.extractUser())) {
                    return actionErrorForward(request, mapping, StatusAction.ERROR, "userNotUpdate", "error.user.not.update");
                }
            }

            return mapping.findForward(StatusAction.SUCCESS);

        } catch (DublicateUserException e) {
            return actionErrorForward(request, mapping, StatusAction.ERROR, "dublicateUser", "error.dublicate.user.login");
        }

    }

}
