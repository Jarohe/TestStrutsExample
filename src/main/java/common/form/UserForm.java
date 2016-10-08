package common.form;

import common.db.model.Role;
import common.db.model.User;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import javax.servlet.http.HttpServletRequest;

public class UserForm extends ActionForm {
    private int id;
    private String login;
    private String password;
    private String firstName;
    private String lastName;
    private boolean isManager;

    public UserForm() {
    }

    public UserForm setUserForm(UserForm form) {
        this.id = form.getId();
        this.login = form.getLogin();
        this.password = form.getPassword();
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
        password = null;
        firstName = null;
        lastName = null;
        isManager = false;
        super.reset(mapping, request);
    }

    @Override
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors errors = new ActionErrors();
        if (login == null || login.length() < 1) {
            errors.add("login", new ActionMessage("errors.required", "Username"));
        }
        if (firstName == null || firstName.length() < 1) {
            errors.add("firstName", new ActionMessage("errors.required", "FirstName"));
        }
        if (lastName == null || lastName.length() < 1) {
            errors.add("lastName", new ActionMessage("errors.required", "LastName"));
        }
        return errors;
    }

    public User extractUser() {
        return new User.Builder(getId(), getLogin(), getPassword())
                .firstName(getFirstName())
                .lastName(getLastName())
                .role(getRole(isManager)).build();
    }

    private Role getRole(boolean isManager) {
        if (isManager) {
            return Role.MANAGER;
        }
        return Role.DEFAULT;
    }

    public UserForm extractUserForm(User user) {
        this.id = user.getId();
        this.login = user.getUsername();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        if (Role.MANAGER.equals(user.getRole())) {
            this.isManager = true;
        }
        return this;
    }
}
