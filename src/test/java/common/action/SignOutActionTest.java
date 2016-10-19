package common.action;

import common.utils.Attributes;
import common.utils.StatusAction;


public class SignOutActionTest extends UtilActionTest {

    public void testSuccessLogout() {
        setRequestPathInfo("/system/signOut");
        getSession().setAttribute(Attributes.Session.USER, user);
        actionPerform();
        verifyForward(StatusAction.SUCCESS);
        assertNull(getSession().getAttribute(Attributes.Session.USER));
    }
    // TODO: Хорошо бы тест на логаут незалогиненного пользователя

}