package common.action;

import common.db.dao.UserDao;
import common.db.model.User;
import common.form.UserForm;
import common.utils.StatusAction;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UpdateUser extends SmartAction {

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        ActionErrors errors = new ActionErrors();

        if (!isManager(request)) { // TODO: Дублируется
            errors.add("access denied", new ActionMessage("error.access.denied"));
            saveErrors(request, errors);
            return mapping.findForward(StatusAction.ERROR);
        }

        UserForm userForm = (UserForm) form;
        UserDao userDao = getUserDao(request);

        if (userForm.getId() == 0) {
            errors.add("dublicateUser", new ActionMessage("errors.not.id"));
            saveErrors(request, errors);
            return mapping.getInputForward();
        }

        User findDuplicate = userDao.getUserByUsername(userForm.getLogin());
        User user = userDao.getUserById(userForm.getId());

        if (user != null && findDuplicate != null && user.getId() != findDuplicate.getId()) {
            errors.add("dublicateUser", new ActionMessage("error.dublicate.user.login"));
            saveErrors(request, errors);
            return mapping.getInputForward();
        }
        if (userForm.getPassword().length() > 0) {
            if (userDao.updateUser(userForm.extractUser(), userForm.getPassword())) {
                return mapping.findForward(StatusAction.SUCCESS);
            }
        } else {
            if (userDao.updateUser(userForm.extractUser())) {
                return mapping.findForward(StatusAction.SUCCESS);
            }
        }
        errors.add("userNotUpdate", new ActionMessage("error.user.not.update")); // TODO: А что, точно не определить?
        return mapping.getInputForward();
    }

}
