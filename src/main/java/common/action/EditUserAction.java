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


public class EditUserAction extends Action {

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Integer userId = Integer.valueOf(request.getParameter("id"));
        UserForm userForm = (UserForm) form;

        Connection connection = (Connection) request.getAttribute("connection");
        UserDao userDao = new UserDaoImpl(connection);

        User user = userDao.getUserById(userId);
        userForm.setUserForm(user.convertToForm());
        return mapping.findForward(StatusAction.SUCCESS);

    }
}
