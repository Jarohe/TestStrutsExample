package common.action;

import common.db.dao.DaoFactory;
import common.db.dao.UserDao;
import common.db.dao.impl.UserDaoImpl;
import common.db.model.User;
import common.form.LoginForm;
import common.utils.StatusAction;
import org.apache.struts.action.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;

public class LoginAction extends SmartAction {

    public ActionForward execute(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        LoginForm loginForm = (LoginForm) form;
        ActionErrors errors = new ActionErrors();
        if(loginForm.getLogin() != null && loginForm.getPass() != null) {

            UserDao userDao = getUserDao(request);
            User user = userDao.getUserByUsername(loginForm.getLogin());
            if(user != null && user.getPassword().equals(loginForm.getPass())){
                request.getSession().setAttribute("sessionUser",user);
                loginForm.setError(null);
                return mapping.findForward(StatusAction.SUCCESS);
            } else {
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.login.password"));
                saveErrors(request.getSession(),errors);
                loginForm.setError("Invalid login or password. Try again please.");
                return mapping.findForward(StatusAction.LoginUser.ACCESS_DENIED);
            }
        }
        loginForm.setError("Fill out the fields.");
        return mapping.findForward(StatusAction.FAILURE);
    }
}
