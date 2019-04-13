package edu.uark.lawncareservicesapp.models.api.interfaces;

import org.json.JSONObject;

public interface LoadFromJsonInterface<T> {
	T loadFromJson(JSONObject rawJsonObject);
}
