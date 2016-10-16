package common.form;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.junit.Test;
import org.mockito.Mock;

import javax.servlet.http.HttpServletRequest;

import static org.junit.Assert.*;


public class UserFormTest extends UtilFormTest {
    @Mock
    private ActionMapping mapping;
    @Mock
    private HttpServletRequest request;

    private UserForm form = new UserForm();

    @Test
    public void successValidate() {
        form.setLogin("login");
        form.setFirstName("firstName");
        form.setLastName("lastName");
        ActionErrors errors = form.validate(mapping, request);
        assertNull(errors);
    }

    @Test
    public void errorValidate() {
        ActionErrors errors = form.validate(mapping, request);
        assertNotNull(errors);
        assertNotNull(errors.get("login"));
        assertNotNull(errors.get("firstName"));
        assertNotNull(errors.get("lastName"));
        assertTrue(errors.size() == 3);
    }

    @Test
    public void errorNotLogin() {
        form.setFirstName("firstName");
        form.setLastName("lastName");
        testAbsentOneField(form, "login", "errors.required", "Username");
    }

    @Test
    public void errorNotFirstName() {
        form.setLogin("login");
        form.setLastName("lastName");
        testAbsentOneField(form, "firstName", "errors.required", "FirstName");
    }

    @Test
    public void errorNotLastName() {
        form.setLogin("login");
        form.setFirstName("firstName");
        testAbsentOneField(form, "lastName", "errors.required", "LastName");
    }

}