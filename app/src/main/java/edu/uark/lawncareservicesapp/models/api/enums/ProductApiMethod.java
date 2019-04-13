package edu.uark.lawncareservicesapp.models.api.enums;

import edu.uark.lawncareservicesapp.models.api.interfaces.PathElementInterface;

public enum ProductApiMethod implements PathElementInterface {
	NONE(""),
	BY_LOOKUP_CODE("byLookupCode");

	@Override
	public String getPathValue() {
		return value;
	}

	private String value;

	ProductApiMethod(String value) {
		this.value = value;
	}
}
