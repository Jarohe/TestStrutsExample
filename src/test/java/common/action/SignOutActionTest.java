package common.action;

import common.utils.Attributes;
import common.utils.StatusAction;


public class SignOutActionTest extends UtilActionTest {

    public void testSuccessLogout() {
        getSession().setAttribute(Attributes.Session.USER, user);
        actionPerform();
        verifyForward(StatusAction.SUCCESS);
        assertNull(getSession().getAttribute(Attributes.Session.USER));
    }

    @Override
    String getRequestPathIbfo() {
        return "/system/signOut";
    }
    // TODO: Хорошо бы тест на логаут незалогиненного пользователя

}