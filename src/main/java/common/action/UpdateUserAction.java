package common.action;

import common.db.dao.UserDao;
import common.db.dao.exceptions.DuplicateUserException;
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
        User user = (User) request.getSession().getAttribute(Attributes.SESSION_USER);
        boolean updated;

        if (user.getId() == updateUser.getId() && !user.getRole().equals(updateUser.getRole())) {
            errorForward = new ErrorForward(StatusAction.ERROR, "access", ErrorMessageKey.UpdateUser.CAN_NOT_EDIT, "your Role");
            return actionErrorForward(request, mapping, errorForward);
        }

        try {
            if (userForm.getPassword().isEmpty()) {
                updated = userDao.updateUser(updateUser);
            } else {
                updated = userDao.updateUser(updateUser, userForm.getPassword());
            }
        } catch (DuplicateUserException e) {
            errorForward = new ErrorForward(StatusAction.ERROR, "duplicateUser", ErrorMessageKey.UpdateUser.DUPLICATE_LOGIN);
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
