package edu.uark.uarkregisterapp.models.api.fields;

import edu.uark.uarkregisterapp.models.api.interfaces.FieldNameInterface;

/**
 * Created by jaredramirez on 4/15/18.
 */

public enum ProductCountFieldName implements FieldNameInterface {
    PRODUCT_ID("productId"),
    QUANTITY("quantity");

    private String fieldName;

    public String getFieldName() {
        return this.fieldName;
    }

    ProductCountFieldName(String fieldName) {
        this.fieldName = fieldName;
    }
}

