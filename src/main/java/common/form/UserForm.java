package common.form;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import javax.servlet.http.HttpServletRequest;

public class UserForm extends ActionForm {
    private int id;
    private String login;
    private String pass;
    private String firstName;
    private String lastName;
    private boolean isManager;

    public UserForm() {
    }

    public UserForm setUserForm(UserForm form) {
        this.id = form.getId();
        this.login = form.getLogin();
        this.pass = form.getPass();
        this.firstName = form.getFirstName();
        this.lastName = form.getLastName();
        this.isManager = form.isManager();
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isManager() {
        return isManager;
    }

    public void setManager(boolean manager) {
        isManager = manager;
    }

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        id = 0;
        login = null;
        pass = null;
        firstName = null;
        lastName = null;
        isManager = false;
        super.reset(mapping, request);
    }

    @Override
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors errors = new ActionErrors();
        if (login == null || login.length() < 1) {
            errors.add("login",new ActionMessage("errors.required", "Username"));
        }
        if(firstName == null || firstName.length()<1){
            errors.add("firstName",new ActionMessage("errors.required","FirstName"));
        }
        if(lastName == null || lastName.length()<1){
            errors.add("lastName",new ActionMessage("errors.required","LastName"));
        }
        return errors;
    }
}
