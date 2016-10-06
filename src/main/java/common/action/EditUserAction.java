package common.action;

import common.db.dao.UserDao;
import common.db.dao.impl.UserDaoImpl;
import common.db.model.User;
import common.form.UserForm;
import common.utils.StatusAction;
import org.apache.struts.action.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.SQLException;


public class EditUserAction extends SmartAction {

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ActionErrors errors = new ActionErrors();
        if(!isManager(request)) {
            return mapping.findForward(StatusAction.ERROR);
        }
        UserForm userForm = (UserForm) form;
        UserDao userDao = getUserDao(request);
        User user = userDao.getUserById(userForm.getId());// TODO: Пользователь если не найден?
        if(user == null){
            errors.add("noFoundId", new ActionMessage("error.not.found.user.id", userForm.getId()));
            saveErrors(request.getSession(),errors);
            return mapping.findForward(StatusAction.ERROR);
        }

        userForm.extractUserForm(user);
        return mapping.findForward(StatusAction.SUCCESS);

    }
}
