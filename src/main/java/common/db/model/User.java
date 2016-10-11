package common.db.model;

public class User {
    private final int id;
    private final String username;
    private final String firstName;
    private final String lastName;
    private final Role role;

    private User(Builder builder) {
        id = builder.id;
        username = builder.username;
        firstName = builder.firstName;
        lastName = builder.lastName;
        role = builder.role;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Role getRole() {
        return role;
    }

    public static class Builder {
        private final int id;
        private final String username;
        private String firstName = "";
        private String lastName = "";
        private Role role = Role.DEFAULT;

        public Builder(int id, String username) {
            this.id = id;
            this.username = username;
        }

        public Builder firstName(String val) {
            firstName = val;
            return this;
        }

        public Builder lastName(String val) {
            lastName = val;
            return this;
        }

        public Builder role(Role val) {
            role = val;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (id != user.id) return false;
        if (!username.equals(user.username)) return false;
        if (!firstName.equals(user.firstName)) return false;
        if (!lastName.equals(user.lastName)) return false;
        return role == user.role;

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + username.hashCode();
        result = 31 * result + firstName.hashCode();
        result = 31 * result + lastName.hashCode();
        result = 31 * result + role.hashCode();
        return result;
    }
}
