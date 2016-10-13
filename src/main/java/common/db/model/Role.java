package common.db.model;

public enum Role {
    DEFAULT(1, "default"), MANAGER(2, "manager");

    private Integer id;
    private String name;

    Role(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static Role getRole(int id) {
        for(Role role : Role.values()) {
            if(role.getId() == id) {
                return role;
            }
        }
        return null;
    }
}
