package common.action;

import common.db.dao.UserDao;
import common.db.model.User;
import common.form.UserForm;
import common.utils.ErrorForward;
import common.utils.ErrorMessageKey;
import common.utils.StatusAction;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class EditUserAction extends SmartAction {

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        UserForm userForm = (UserForm) form;
        ErrorForward errorForward;
        UserDao userDao = getUserDao(request);
        User user = userDao.getUserById(userForm.getId());
        if (user == null) {
            errorForward = new ErrorForward(StatusAction.ERROR, "noFoundId", ErrorMessageKey.EditUser.NOT_FOUND_USER_ID);
            return actionErrorForward(request, mapping, errorForward, userForm.getId());// TODO: Не ясно почему просто параметрами не передать
        }

        userForm.extractUserForm(user);
        return mapping.findForward(StatusAction.SUCCESS);

    }
}
