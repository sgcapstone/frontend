package edu.uark.lawncareservicesapp.models.api.fields;

import edu.uark.lawncareservicesapp.models.api.interfaces.FieldNameInterface;

public enum ClientFieldName implements FieldNameInterface {
    ID("id"),
    FIRST_NAME("firstName"),
    PROVIDER_NAME("providerName"),
    LAST_NAME("lastName"),
    CLIENT_ID("customerId"),
    ACTIVE("active"),
    PHONE("phonenumber"),
    PROVIDER_NUMBER("phone"),
    EMAIL("email"),
    ADDRESS("address"),
    CITY("city"),
    ZIP("zip"),
    STATE("state"),
    PROVIDER_ADDRESS("address"),
    PROVIDER_CITY("city"),
    PROVIDER_ZIP("zip"),
    PROVIDER_STATE("state"),
    ROLE("role"),
    PROVIDER_ID("providerId"),
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
