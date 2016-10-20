package common.action;

import common.db.dao.UserDao;
import common.db.dao.exceptions.DuplicateUserException;
import common.form.UserForm;
import common.utils.ErrorForward;
import common.utils.ErrorMessageKey;
import common.utils.StatusAction;
import org.apache.struts.action.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;

public class CreateUserAction extends SmartAction {
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws SQLException, DuplicateUserException {
        UserForm userForm = (UserForm) form;
        ErrorForward errorForward;

        if (userForm.getPassword() == null || userForm.getPassword().length() < 1) {
            errorForward = new ErrorForward(StatusAction.ERROR, "password", ErrorMessageKey.CreateUser.CAN_NOT_BLANK, "Password");
            return actionErrorForward(request, mapping, errorForward);
        }

        UserDao userDao = getUserDao(request);
        try {
            userDao.addUser(userForm.extractUser(), userForm.getPassword());
        } catch (DuplicateUserException e) {
            errorForward = new ErrorForward(StatusAction.CreateUser.DUPLICATE_USER, "duplicateUser", ErrorMessageKey.CreateUser.DUPLICATE_LOGIN);
            return actionErrorForward(request, mapping, errorForward);
        }
        ActionMessages messages = new ActionMessages();
        messages.add("newUser", new ActionMessage("create.new.user", userForm.getLogin()));
        saveMessages(request.getSession(), messages);

        return mapping.findForward(StatusAction.SUCCESS);
    }
}
