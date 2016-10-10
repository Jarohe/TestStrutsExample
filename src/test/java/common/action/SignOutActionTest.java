package common.action;

import common.db.model.User;
import common.utils.Attributes;
import common.utils.StatusAction;
import servletunit.struts.MockStrutsTestCase;


public class SignOutActionTest extends MockStrutsTestCase {
    private static String PATH_INFO = "/system/signOut";

    public void testSuccessLogout() {
        setRequestPathInfo(PATH_INFO);
        User user = new User.Builder(1, "login", "password").build();
        getSession().setAttribute(Attributes.Session.USER, user);
        assertNotNull(getSession().getAttribute(Attributes.Session.USER));
        actionPerform();
        verifyForward(StatusAction.SUCCESS);
        assertNull(getSession().getAttribute(Attributes.Session.USER));
    }

}