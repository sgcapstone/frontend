package edu.uark.uarkregisterapp.models.api.fields;

import edu.uark.uarkregisterapp.models.api.interfaces.FieldNameInterface;

/**
 * Created by jaredramirez on 4/15/18.
 */

public enum TransactionFieldName implements FieldNameInterface {
    ID("id"),
    EMPLOYEE_ID("employeeId"),
    AMOUNT("amount"),
    TYPE("type"),
    REF_ID("refId"),
    RECIEPT_ID("receiptId"),
    PRODUCTS("products"),
    CREATED_AT("createdAt"),
    UPDATED_AT("updatedAt");

    private String fieldName;
    public String getFieldName() {
        return this.fieldName;
    }

    TransactionFieldName(String fieldName) {
        this.fieldName = fieldName;
    }
}
