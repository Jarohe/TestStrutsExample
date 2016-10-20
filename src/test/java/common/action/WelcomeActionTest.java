package common.action;

import common.db.model.User;
import common.utils.Attributes;
import common.utils.StatusAction;


public class WelcomeActionTest extends UtilActionTest {

    public void setUp() throws Exception {
        super.setUp();
    }

    @Override
    String getRequestPathIbfo() {
        return "/welcome";
    }

    public void testSuccessWelcomeAction() {
        actionPerform();
        verifyForward(StatusAction.SUCCESS);
    }

    public void testErrorWelcomeAction() {
        getSession().setAttribute(Attributes.Session.USER, new User(10, "username", "firstName", "lastName"));
        actionPerform();
        verifyForward(StatusAction.ERROR);
    }

}