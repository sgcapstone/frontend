package edu.uark.uarkregisterapp.models.api.enums;

import edu.uark.uarkregisterapp.models.api.interfaces.PathElementInterface;

/**
 * Created by jaredramirez on 3/2/18.
 */

public enum EmployeeRole {
    CASHIER("cashier"),
    SHIFT_MANAGER("shift manager"),
    GENERAL_MANAGER("general manager");

    public static EmployeeRole fromString(String text) {
        for (EmployeeRole b : EmployeeRole.values()) {
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

    EmployeeRole(String value) {
        this.value = value;
    }

}
