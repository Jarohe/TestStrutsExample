package common.action;

import common.db.dao.UserDao;
import common.db.model.Role;
import common.db.model.User;
import common.utils.Attributes;
import common.utils.StatusAction;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


public class UserListAction extends SmartAction {

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        UserDao userDao = getUserDao(request);
        List<User> users = userDao.getAllUsers();
        request.setAttribute("userList", users);

        User user = (User) request.getSession().getAttribute(Attributes.Session.USER);
        if(Role.MANAGER.equals(user.getRole())) {
            return mapping.findForward(StatusAction.UserList.MANAGER);
        }
        return mapping.findForward(StatusAction.UserList.DEFAULT);
    }
}
