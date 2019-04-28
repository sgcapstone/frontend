package edu.uark.lawncareservicesapp.models.api.fields;

import edu.uark.lawncareservicesapp.models.api.interfaces.FieldNameInterface;

public enum ClientFieldName implements FieldNameInterface {
    ID("id"),
    FIRST_NAME("firstName"),
    LAST_NAME("lastName"),
    CLIENT_ID("clientId"),
    ACTIVE("active"),
    PHONE("phonenumber"),
    EMAIL("email"),
    ADDRESS("address"),
    CITY("city"),
    ZIP("zip"),
    STATE("state"),
    ROLE("role"),
    CUSTOMER_ID ("customerId"),
    PASSWORD("password"),
    API_REQUEST_STATUS("apiRequestStatus"),
    API_REQUEST_MESSAGE("apiRequestMessage"),
    CREATED_AT("createdAt"),
    UPDATED_AT("updatedAt");

    private String fieldName;
    public String getFieldName() {
        return this.fieldName;
    }

    ClientFieldName(String fieldName) {
        this.fieldName = fieldName;
    }
}
