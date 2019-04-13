package edu.uark.lawncareservicesapp.models.api.fields;

import edu.uark.lawncareservicesapp.models.api.interfaces.FieldNameInterface;

public enum ProductListingFieldName implements FieldNameInterface {
	PRODUCTS("products");

	private String fieldName;
	public String getFieldName() {
		return this.fieldName;
	}

	ProductListingFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
}
