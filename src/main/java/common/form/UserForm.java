package common.form;

import org.apache.struts.action.ActionForm;

public class UserForm extends ActionForm {
    private int id;
    private String login;
    private String pass;
    private String firstName;
    private String lastName;
    private String error;
    private Boolean role;

    public UserForm() {
    }

    public UserForm setUserForm(UserForm form) {
        this.id = form.getId();
        this.login = form.getLogin();
        this.pass = form.getPass();
        this.firstName = form.getFirstName();
        this.lastName = form.getLastName();
        this.role = form.getRole();
        return this;
    }

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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Boolean getRole() {
        return role;
    }

    public void setRole(Boolean role) {
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
