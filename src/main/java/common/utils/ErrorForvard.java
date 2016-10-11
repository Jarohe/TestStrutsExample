package common.utils;

public class ErrorForvard {
    private String forwardName;
    private String property;
    private String key;

    public ErrorForvard(String forwardName, String property, String key) {
        this.forwardName = forwardName;
        this.property = property;
        this.key = key;
    }

    public String getForwardName() {
        return forwardName;
    }

    public void setForwardName(String forwardName) {
        this.forwardName = forwardName;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
