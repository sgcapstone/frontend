package edu.uark.lawncareservicesapp.models.api.enums;

import edu.uark.lawncareservicesapp.models.api.interfaces.PathElementInterface;

public enum ApiObject implements PathElementInterface {
    NONE(""),
    PRODUCT("product/"),
    CLIENT("client/"),
    PROVIDER("provider/"),
    CONSUMER("consumer/"),
    TRANSACTION("transaction/");

    @Override
    public String getPathValue() {
        return value;
    }

    private String value;

    ApiObject(String value) {
        this.value = value;
    }
}
