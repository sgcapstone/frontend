package edu.uark.uarkregisterapp.models.api.fields;

import edu.uark.uarkregisterapp.models.api.interfaces.FieldNameInterface;

public enum EmployeeFieldName implements FieldNameInterface {
    ID("id"),
    FIRST_NAME("firstName"),
    LAST_NAME("lastName"),
    EMPLOYEE_ID("employeeId"),
    ACTIVE("active"),
    ROLE("role"),
    MANGER_ID("managerId"),
    PASSWORD("password"),
    API_REQUEST_STATUS("apiRequestStatus"),
    API_REQUEST_MESSAGE("apiRequestMessage"),
    CREATED_AT("createdAt"),
    UPDATED_AT("updatedAt");

    private String fieldName;
    public String getFieldName() {
        return this.fieldName;
    }

    EmployeeFieldName(String fieldName) {
        this.fieldName = fieldName;
    }
}
