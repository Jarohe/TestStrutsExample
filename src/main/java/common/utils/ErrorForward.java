package common.utils;

public class ErrorForward {
    private String forwardName;
    private String property;
    private String key;
    private String value;

    public ErrorForward(String forwardName, String property, String key) {
        this.forwardName = forwardName;
        this.property = property;
        this.key = key;
    }

    public ErrorForward(String forwardName, String property, String key, String value) {
        this.forwardName = forwardName;
        this.property = property;
        this.key = key;
        this.value = value;
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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
