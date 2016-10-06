package common.form;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;


import javax.servlet.http.HttpServletRequest;

public class LoginForm extends ActionForm {
    private String login;
    private String pass; // TODO: password

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        login = "";
        pass = "";
        super.reset(mapping,request);
    }

    @Override
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors errors = new ActionErrors();
        if(getLogin() == null || getLogin().length() < 1) {
            errors.add("login",new ActionMessage("errors.required","Login"));
        }
        if(getPass() == null || getPass().length() < 1) {
            errors.add("pass",new ActionMessage("errors.required","Password"));
        }
        return errors;
    }
}
