package edu.uark.lawncareservicesapp.models.api.enums;

/**
 * Created by jaredramirez on 3/2/18.
 */

public enum ClientRole {
    CONSUMER("consumer"),
    PROVIDER("provider");

    public static ClientRole fromString(String text) {
        for (ClientRole b : ClientRole.values()) {
            if (b.value.equalsIgnoreCase(text)) {
                return b;
            }
        }
        return null;
    }

    private String value;

    public String getValue() {
        return value;
    }

    ClientRole(String value) {
        this.value = value;
    }

}
