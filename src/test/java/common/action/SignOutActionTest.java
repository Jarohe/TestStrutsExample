package common.action;

import common.db.model.User;
import common.utils.Attributes;
import common.utils.StatusAction;
import servletunit.struts.MockStrutsTestCase;


public class SignOutActionTest extends UtilActionTest {
    private String pathInfo = "/system/signOut";

    public void testSuccessLogout() {
        setRequestPathInfo(pathInfo);
        getSession().setAttribute(Attributes.Session.USER, user);
        assertNotNull(getSession().getAttribute(Attributes.Session.USER));
        actionPerform();
        verifyForward(StatusAction.SUCCESS);
        assertNull(getSession().getAttribute(Attributes.Session.USER));
    }

}