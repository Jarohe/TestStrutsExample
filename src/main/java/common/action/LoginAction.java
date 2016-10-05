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

        UserDao userDao = getUserDao(request);
        User user = userDao.getUserByLoginAndPassword(loginForm.getLogin(),loginForm.getPass());
        if(user == null ){
            errors.add("error_authorization", new ActionMessage("error.login.password"));
            saveErrors(request.getSession(),errors);
            return mapping.getInputForward();
        }
        request.getSession().setAttribute("sessionUser",user);
        return mapping.findForward(StatusAction.SUCCESS);
    }
}
