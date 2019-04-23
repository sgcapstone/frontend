package edu.uark.lawncareservicesapp.models.api.services;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import edu.uark.lawncareservicesapp.models.api.ApiResponse;
import edu.uark.lawncareservicesapp.models.api.Provider;
import edu.uark.lawncareservicesapp.models.api.enums.ApiObject;

public class ProviderService extends BaseRemoteService {
    public ApiResponse<Provider> getProvider(UUID providerId) {
        return this.readProviderDetailsFromResponse(
                this.<Provider>performGetRequest(
                        this.buildPath(providerId)
                )
        );
    }


    public ApiResponse<Integer> check(){
        System.out.println(this.buildPath());
        return this.readIntegerFromResponse(
                this.<Integer>performGetRequest(
                        this.buildPath("count")
                )
        );
    }


    public ApiResponse<List<Provider>> findProvidersByServices(String services) {
        ApiResponse<List<Provider>> apiResponse = this.<List<Provider>>performGetRequest(
                this.buildPath("service/" + services)
        );

        JSONArray rawJsonArray = this.rawResponseToJSONArray(apiResponse.getRawResponse());
        Log.d("RAW", apiResponse.getRawResponse());
        if (rawJsonArray != null) {
            ArrayList<Provider> providers = new ArrayList<>(rawJsonArray.length());
            for (int i = 0; i < rawJsonArray.length(); i++) {
                try {
                    providers.add((new Provider()).loadFromJson(rawJsonArray.getJSONObject(i)));
                } catch (JSONException e) {
                    Log.d("GET PROVIDERS", e.getMessage());
                }
            }

            apiResponse.setData(providers);
        } else {
            apiResponse.setData(new ArrayList<Provider>(0));
        }

        return apiResponse;
    }

    public ApiResponse<List<Provider>> findProvidersByName(String name) {
        ApiResponse<List<Provider>> apiResponse = this.<List<Provider>>performGetRequest(
                this.buildPath("name/" + name)
        );

        JSONArray rawJsonArray = this.rawResponseToJSONArray(apiResponse.getRawResponse());
        if (rawJsonArray != null) {
            ArrayList<Provider> providers = new ArrayList<>(rawJsonArray.length());
            for (int i = 0; i < rawJsonArray.length(); i++) {
                try {
                    providers.add((new Provider()).loadFromJson(rawJsonArray.getJSONObject(i)));
                } catch (JSONException e) {
                    Log.d("GET PROVIDERS", e.getMessage());
                }
            }

            apiResponse.setData(providers);
        } else {
            apiResponse.setData(new ArrayList<Provider>(0));
        }

        return apiResponse;
    }

    public ApiResponse<List<Provider>> findProvidersByCity(String city) {
        ApiResponse<List<Provider>> apiResponse = this.<List<Provider>>performGetRequest(
                this.buildPath("city/" + city)
        );

        JSONArray rawJsonArray = this.rawResponseToJSONArray(apiResponse.getRawResponse());
        if (rawJsonArray != null) {
            ArrayList<Provider> providers = new ArrayList<>(rawJsonArray.length());
            for (int i = 0; i < rawJsonArray.length(); i++) {
                try {
                    providers.add((new Provider()).loadFromJson(rawJsonArray.getJSONObject(i)));
                } catch (JSONException e) {
                    Log.d("GET PROVIDERS", e.getMessage());
                }
            }

            apiResponse.setData(providers);
        } else {
            apiResponse.setData(new ArrayList<Provider>(0));
        }

        return apiResponse;
    }


    private ApiResponse<Provider> readProviderDetailsFromResponse(ApiResponse<Provider> apiResponse) {
        JSONObject rawJsonObject = this.rawResponseToJSONObject(
                apiResponse.getRawResponse()
        );

        if (rawJsonObject != null) {
            apiResponse.setData(
                    (new Provider()).loadFromJson(rawJsonObject)
            );
        }

        return apiResponse;
    }

    private  ApiResponse<Integer> readIntegerFromResponse (ApiResponse<Integer> apiResponse){
        try {
            Integer i = Integer.parseInt(apiResponse.getRawResponse());
            apiResponse.setData(i);
            return apiResponse;
        } catch (Exception e) {
            return apiResponse;
        }
    }

    public ProviderService() { super(ApiObject.PROVIDER); }
}
