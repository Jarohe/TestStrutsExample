package common.action;

import common.db.dao.UserDao;
import common.db.dao.exceptions.DublicateUserException;
import common.db.model.User;
import common.form.UserForm;
import common.utils.Attributes;
import common.utils.ErrorForward;
import common.utils.ErrorMessageKey;
import common.utils.StatusAction;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UpdateUserAction extends SmartAction {

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        UserForm userForm = (UserForm) form;
        User updateUser = ((UserForm) form).extractUser();
        ErrorForward errorForward;
        UserDao userDao = getUserDao(request);
        User user = (User) request.getSession().getAttribute(Attributes.Session.USER);
        boolean updated;

        if (user.getId() == updateUser.getId() && !user.getRole().equals(updateUser.getRole())) {
            errorForward = new ErrorForward(StatusAction.ERROR, "access", ErrorMessageKey.UpdateUser.CAN_NOT_EDIT);
            return actionErrorForward(request, mapping, errorForward, "your Role");
        }

        try {
            if (userForm.getPassword().isEmpty()) {
                updated = userDao.updateUser(updateUser);
            } else {
                updated = userDao.updateUser(updateUser, userForm.getPassword());
            }
        } catch (DublicateUserException e) {
            errorForward = new ErrorForward(StatusAction.ERROR, "duplicateUser", ErrorMessageKey.UpdateUser.DUBLICATE_LOGIN);
            return actionErrorForward(request, mapping, errorForward);
        }

        if (updated) {
            return mapping.findForward(StatusAction.SUCCESS);
        } else {
            errorForward = new ErrorForward(StatusAction.ERROR, "userNotUpdate", ErrorMessageKey.UpdateUser.USER_NOT_UPDATE);
            return actionErrorForward(request, mapping, errorForward);
        }

    }

}
