package common.action;

import common.db.dao.UserDao;
import common.db.dao.exceptions.DublicateUserException;
import common.form.UserForm;
import common.utils.ErrorForvard;
import common.utils.ErrorMessageKey;
import common.utils.StatusAction;
import org.apache.struts.action.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;

public class CreateUserAction extends SmartAction {
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws SQLException, DublicateUserException {
        UserForm userForm = (UserForm) form;
        ErrorForvard errorForvard;

        if (userForm.getPassword() == null || userForm.getPassword().length() < 1) {
            errorForvard = new ErrorForvard(StatusAction.ERROR, "password",ErrorMessageKey.CreateUser.CAN_NOT_BLANK);
            return actionErrorForward(request, mapping, errorForvard, "Password");
        }

        UserDao userDao = getUserDao(request);
        try {
            userDao.addUser(userForm.extractUser(), userForm.getPassword());
        } catch (DublicateUserException e) {
            errorForvard = new ErrorForvard(StatusAction.CreateUser.DUBLICATE_USER, "dublicateUser", ErrorMessageKey.CreateUser.DUBLICATE_LOGIN);
            return actionErrorForward(request, mapping, errorForvard);
        }
        ActionMessages messages = new ActionMessages();
        messages.add("newUser", new ActionMessage("create.new.user",userForm.getLogin()));
        saveMessages(request.getSession(),messages);

        return mapping.findForward(StatusAction.SUCCESS);
    }
}
