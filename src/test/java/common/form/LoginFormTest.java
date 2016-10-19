package common.form;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.junit.Test;
import org.mockito.Mock;

import javax.servlet.http.HttpServletRequest;

import static org.junit.Assert.*;


public class LoginFormTest extends UtilFormTest {
    @Mock
    private ActionMapping mapping;
    @Mock
    private HttpServletRequest request;
    private LoginForm form = new LoginForm();

    @Test
    public void successValidate() {
        form.setLogin("login");
        form.setPassword("password");
        ActionErrors errors = form.validate(mapping, request);
        assertNull(errors);
    }

    @Test
    public void errorValidate() {
        ActionErrors errors = form.validate(mapping, request);
        assertNotNull(errors);
        assertNotNull(errors.get("login"));
        assertNotNull(errors.get("password"));
        assertEquals(errors.size(), 2);
    }

    @Test
    public void errorNotPassword() {
        form.setLogin("login");
        testAbsentOneField(form, "password", "errors.required", "Password");
    }

    @Test
    public void errorNotLogin() {
        form.setPassword("password");
        testAbsentOneField(form, "login", "errors.required", "Login");
    }


}