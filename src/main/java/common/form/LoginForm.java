package common.form;

import org.apache.struts.action.ActionForm;

public class LoginForm extends ActionForm {
    private String login;
    private String pass;
    private String error;

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

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
