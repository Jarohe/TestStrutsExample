package common.action;

import common.db.dao.UserDao;
import common.db.dao.impl.UserDaoImpl;
import common.utils.StatusAction;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;

public class DeleteUserAction extends SmartAction {
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Integer userId = Integer.valueOf(request.getParameter("id"));
        UserDao userDao = getUserDao(request);
        userDao.deleteUserById(userId);
        return mapping.findForward(StatusAction.SUCCESS);
    }
}
