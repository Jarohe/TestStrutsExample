package common.action;

import common.utils.Attributes;
import common.utils.StatusAction;


public class SignOutActionTest extends UtilActionTest {

    public void testSuccessLogout() {
        setRequestPathInfo("/system/signOut");
        getSession().setAttribute(Attributes.Session.USER, user);
        assertNotNull(getSession().getAttribute(Attributes.Session.USER));
        actionPerform();
        verifyForward(StatusAction.SUCCESS);
        assertNull(getSession().getAttribute(Attributes.Session.USER));
    }

}