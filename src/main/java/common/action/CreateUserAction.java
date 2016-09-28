package common.action;

import common.db.dao.DaoFactory;
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
import java.sql.SQLException;

public class CreateUserAction extends SmartAction {
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws SQLException {
        UserForm userForm = (UserForm) form;
        if(userForm.getLogin() != null && userForm.getPass() != null && userForm.getFirstName() !=null && userForm.getLastName() != null) {

            UserDao userDao = getUserDao(request);

            User user = userDao.getUserByUsername(userForm.getLogin());
            if(user != null) {
                userForm.setError("Duplicate User Login");
                return mapping.findForward(StatusAction.CreateUser.DUBLICATE_USER);
            } else {
                if(userForm.getRole() == null) {
                    userForm.setRole(false);
                }
                userDao.addUser(userForm);
                return mapping.findForward(StatusAction.SUCCESS);
            }
        } else {
            userForm.setError("Error. User can't be create!");
            return mapping.findForward(StatusAction.ERROR);
        }
    }
}
