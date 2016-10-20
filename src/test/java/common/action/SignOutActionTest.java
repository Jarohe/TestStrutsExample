package common.action;

import common.utils.Attributes;
import common.utils.StatusAction;


public class SignOutActionTest extends UtilActionTest {

    @Override
    String getRequestPathInfo() {
        return "/system/signOut";
    }

    public void testSuccessLogout() {
        getSession().setAttribute(Attributes.SESSION_USER, sessionUser);
        actionPerform();
        verifyForward(StatusAction.SUCCESS);
        assertNull(getSession().getAttribute(Attributes.SESSION_USER));
    }

    public void testLogoutUnloginUser() {
        actionPerform();
        verifyForward(StatusAction.SUCCESS);
    }

}