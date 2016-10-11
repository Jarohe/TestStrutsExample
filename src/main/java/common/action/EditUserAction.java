package common.action;

import common.db.dao.UserDao;
import common.db.model.User;
import common.form.UserForm;
import common.utils.ErrorForvard;
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
        ErrorForvard errorForvard;
        UserDao userDao = getUserDao(request);
        User user = userDao.getUserById(userForm.getId());
        if (user == null) {
            errorForvard = new ErrorForvard(StatusAction.ERROR, "noFoundId", ErrorMessageKey.EditUser.NOT_FOUND_USER_ID);
            return actionErrorForward(request, mapping, errorForvard, userForm.getId());
        }

        userForm.extractUserForm(user);
        return mapping.findForward(StatusAction.SUCCESS);

    }
}
