package common.action;

import common.db.dao.UserDao;
import common.db.model.User;
import common.form.LoginForm;
import common.utils.Attributes;
import common.utils.ErrorMessageKey;
import common.utils.StatusAction;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginAction extends SmartAction {

    public ActionForward execute(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        LoginForm loginForm = (LoginForm) form;

        UserDao userDao = getUserDao(request);
        User user = userDao.getUserByLoginAndPassword(loginForm.getLogin(), loginForm.getPassword());
        if (user == null) {
            return actionErrorForward(request, mapping, StatusAction.ERROR, "error_authorization", ErrorMessageKey.Login.INVALIDE_LOGIN_PASSWORD);
        }
        request.getSession().setAttribute(Attributes.Session.USER, user);
        return mapping.findForward(StatusAction.SUCCESS);
    }
}
