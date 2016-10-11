package common.action;

import common.db.dao.UserDao;
import common.db.dao.exceptions.DublicateUserException;
import common.form.UserForm;
import common.utils.ErrorMessageKey;
import common.utils.StatusAction;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;

public class CreateUserAction extends SmartAction {
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws SQLException, DublicateUserException {
        UserForm userForm = (UserForm) form;

        if (userForm.getPassword() == null || userForm.getPassword().length() < 1) {
            return actionErrorForward(request, mapping, StatusAction.ERROR, "password", ErrorMessageKey.CreateUser.CAN_NOT_BLANK, "Password");
        }

        UserDao userDao = getUserDao(request);
        try {
            userDao.addUser(userForm.extractUser(), userForm.getPassword());
        } catch (DublicateUserException e) {
            return actionErrorForward(request, mapping, StatusAction.CreateUser.DUBLICATE_USER, "dublicateUser", ErrorMessageKey.CreateUser.DUBLICATE_LOGIN);
        }
        return mapping.findForward(StatusAction.SUCCESS);
    }
}
