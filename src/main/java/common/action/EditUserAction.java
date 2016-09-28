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


public class EditUserAction extends SmartAction {

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        if(!isManager(request)) {
            return mapping.findForward(StatusAction.ERROR);
        }
        Integer userId = Integer.valueOf(request.getParameter("id"));
        UserForm userForm = (UserForm) form;
        UserDao userDao = getUserDao(request);
        User user = userDao.getUserById(userId);
        userForm.setUserForm(user.convertToForm());
        return mapping.findForward(StatusAction.SUCCESS);

    }
}
