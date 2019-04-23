package edu.uark.lawncareservicesapp.models.api.fields;

import edu.uark.lawncareservicesapp.models.api.interfaces.FieldNameInterface;

public enum ProviderFieldName implements FieldNameInterface {
    ID("id"),
    PROVIDER_NAME("providerName"),
    ADDRESS("address"),
    CITY("city"),
    STATE("state"),
    ZIP("zip"),
    Provider_ID("providerId"),
    ACTIVE("active"),
    PHONE("phone"),
    EMAIL("email"),
    PASSWORD("password"),
    API_REQUEST_STATUS("apiRequestStatus"),
    API_REQUEST_MESSAGE("apiRequestMessage"),
    CREATED_AT("created_at"),
    UPDATED_AT("updated_at");

    private String fieldName;
    public String getFieldName() {
        return this.fieldName;
    }

    ProviderFieldName(String fieldName) {
        this.fieldName = fieldName;
    }
}
