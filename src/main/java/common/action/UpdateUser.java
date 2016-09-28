package common.action;

import common.db.dao.UserDao;
import common.db.dao.impl.UserDaoImpl;
import common.db.model.User;
import common.form.UserForm;
import common.utils.StatusAction;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;

public class UpdateUser extends SmartAction {
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        if(!isManager(request)) {
            return mapping.findForward(StatusAction.ERROR);
        }
        UserForm userForm = (UserForm) form;
        if (userForm.getLogin() != null && userForm.getPass() != null && userForm.getFirstName() != null && userForm.getLastName() != null) {

            UserDao userDao = getUserDao(request);
            User user = userDao.getUserById(userForm.getId());
            User findDuplicate = userDao.getUserByUsername(userForm.getLogin());
            if(user != null && findDuplicate != null && user.getId() != findDuplicate.getId()) {
                userForm.setError("Error. Dublicate userName");
                return mapping.findForward(StatusAction.ERROR);
            }
            if (userForm.getPass().length() > 0) {
                if (userDao.updateUserByUserForm(userForm)) {
                    return mapping.findForward(StatusAction.SUCCESS);
                }
            } else {
                if (userDao.updateWithoutPassword(userForm)) {
                    return mapping.findForward(StatusAction.SUCCESS);
                }
            }

            userForm.setError("Error. User don't update. May be this user is apsent in DB.");
            return mapping.findForward(StatusAction.ERROR);
        } else {
            userForm.setError("Error. Please fill out the fields marked (*)!");
            return mapping.findForward(StatusAction.ERROR);
        }
    }
}
