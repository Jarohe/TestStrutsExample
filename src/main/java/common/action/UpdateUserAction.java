package common.action;

import common.db.dao.UserDao;
import common.db.dao.exceptions.DublicateUserException;
import common.form.UserForm;
import common.utils.ErrorForvard;
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
        ErrorForvard errorForvard;
        UserDao userDao = getUserDao(request);
        boolean updated;

        if (userForm.getId() == 0) {
            errorForvard = new ErrorForvard(StatusAction.ERROR, "notId", ErrorMessageKey.UpdateUser.NOT_SEND_ID);
            return actionErrorForward(request, mapping, errorForvard);
        }

        try {
            if (userForm.getPassword().isEmpty()) {
                updated = userDao.updateUser(userForm.extractUser());
            } else {
                updated = userDao.updateUser(userForm.extractUser(), userForm.getPassword());
            }
        } catch (DublicateUserException e) {
            errorForvard = new ErrorForvard(StatusAction.ERROR, "dublicateUser", ErrorMessageKey.UpdateUser.DUBLICATE_LOGIN);
            return actionErrorForward(request.getSession(), mapping, errorForvard);
        }

        if (updated) {
            return mapping.findForward(StatusAction.SUCCESS);
        } else {
            errorForvard = new ErrorForvard(StatusAction.ERROR, "userNotUpdate", ErrorMessageKey.UpdateUser.USER_NOT_UPDATE);
            return actionErrorForward(request, mapping, errorForvard);
        }

    }

}
