package common.action;

import common.db.model.User;
import common.utils.Attributes;
import common.utils.StatusAction;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class WelcomeAction extends SmartAction {
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        User user = (User) request.getSession().getAttribute(Attributes.Session.USER);
        if (user == null) {
            return mapping.findForward(StatusAction.SUCCESS);
        }
        return mapping.findForward(StatusAction.ERROR);
    }
}
