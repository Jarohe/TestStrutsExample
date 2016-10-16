package common.form;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import javax.servlet.http.HttpServletRequest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

abstract class UtilFormTest {
    private ActionMapping mapping;
    private HttpServletRequest request;
    public void testAbsentOneField(ActionForm form, String errorProperty, String messageKey, String messageValue){
        ActionErrors errors = form.validate(mapping, request);
        assertNotNull(errors);
        assertNotNull(errors.get(errorProperty));
        assertTrue(errors.size() == 1);
        ActionMessage message = (ActionMessage) errors.get(errorProperty).next();
        assertEquals(message.getKey(), messageKey);
        assertTrue(message.getValues().length == 1);
        assertEquals(message.getValues()[0], messageValue);
    }
}
