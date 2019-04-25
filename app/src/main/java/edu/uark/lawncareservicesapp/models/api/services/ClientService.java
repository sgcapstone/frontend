package edu.uark.lawncareservicesapp.models.api.services;

import android.util.Log;

import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import edu.uark.lawncareservicesapp.models.api.ApiResponse;
import edu.uark.lawncareservicesapp.models.api.Client;
import edu.uark.lawncareservicesapp.models.api.enums.ApiObject;
import edu.uark.lawncareservicesapp.models.api.enums.ClientRole;
import edu.uark.lawncareservicesapp.*;


public class ClientService extends BaseRemoteService {
    public ApiResponse<Client> getClient(UUID clientId) {
        return this.readClientDetailsFromResponse(
                this.<Client>performGetRequest(
                        this.buildPath(clientId)
                )
        );
    }

    public ApiResponse<Client> provider_login(String clientId, String password) {
        return this.readClientDetailsFromResponse(
                this.<Client>performPostRequest(
                        this.buildPath("login"),
                        Client.convertLogInToJson(clientId, password, true)
                )
        );
    }

    public ApiResponse<Client> consumer_login(String clientId, String password) {
        return this.readClientDetailsFromResponse(
                this.<Client>performPostRequest(
                        this.buildPath("login"),
                        Client.convertLogInToJson(clientId, password, false)
                )
        );
    }

    public ApiResponse<Integer> check() {
        return this.readIntegerFromResponse(
                this.<Integer>performGetRequest(
                        this.buildPath("count")
                )
        );
    }

    public ApiResponse<Client> createClient(Client client) {
        return this.readClientDetailsFromResponse(
                this.<Client>performPostRequest(
                        this.buildPath(),
                        client.convertToJson()
                )
        );
    }


    public ApiResponse<List<Client>> findProvidersByServices(String services) {
        ApiResponse<List<Client>> apiResponse = this.<List<Client>>performGetRequest(
                this.buildPath("service/" + services)
        );

        JSONArray rawJsonArray = this.rawResponseToJSONArray(apiResponse.getRawResponse());
        Log.d("RAW", apiResponse.getRawResponse());
        if (rawJsonArray != null) {
            ArrayList<Client> providers = new ArrayList<>(rawJsonArray.length());
            for (int i = 0; i < rawJsonArray.length(); i++) {
                try {
                    providers.add((new Client()).loadFromJson(rawJsonArray.getJSONObject(i)));
                } catch (JSONException e) {
                    Log.d("GET PROVIDERS", e.getMessage());
                }
            }

            apiResponse.setData(providers);
        } else {
            apiResponse.setData(new ArrayList<Client>(0));
        }
        return apiResponse;
    }

    public ApiResponse<List<Client>> findProvidersByName(String name) {
        ApiResponse<List<Client>> apiResponse = this.<List<Client>>performGetRequest(
                this.buildPath("name/" + name)
        );

        JSONArray rawJsonArray = this.rawResponseToJSONArray(apiResponse.getRawResponse());
        if (rawJsonArray != null) {
            ArrayList<Client> providers = new ArrayList<>(rawJsonArray.length());
            for (int i = 0; i < rawJsonArray.length(); i++) {
                try {
                    providers.add((new Client()).loadFromJson(rawJsonArray.getJSONObject(i)));
                } catch (JSONException e) {
                    Log.d("GET PROVIDERS", e.getMessage());
                }
            }
            apiResponse.setData(providers);
        } else {
            apiResponse.setData(new ArrayList<Client>(0));
        }
        return apiResponse;
    }


    public ApiResponse<List<Client>> findProvidersByCity(String city) {
        ApiResponse<List<Client>> apiResponse = this.<List<Client>>performGetRequest(
                this.buildPath("city/" + city)
        );
        JSONArray rawJsonArray = this.rawResponseToJSONArray(apiResponse.getRawResponse());
        if (rawJsonArray != null) {
            ArrayList<Client> providers = new ArrayList<>(rawJsonArray.length());
            for (int i = 0; i < rawJsonArray.length(); i++) {
                try {
                    providers.add((new Client()).loadFromJson(rawJsonArray.getJSONObject(i)));
                } catch (JSONException e) {
                    Log.d("GET PROVIDERS", e.getMessage());
                }
            }

            apiResponse.setData(providers);
        } else {
            apiResponse.setData(new ArrayList<Client>(0));
        }

        return apiResponse;

    }


    private ApiResponse<Client> readClientDetailsFromResponse(ApiResponse<Client> apiResponse) {
        JSONObject rawJsonObject = this.rawResponseToJSONObject(
                apiResponse.getRawResponse()
        );

        if (rawJsonObject != null) {
            apiResponse.setData(
                    (new Client()).loadFromJson(rawJsonObject)
            );
        }

        return apiResponse;
    }

    private ApiResponse<Integer> readIntegerFromResponse(ApiResponse<Integer> apiResponse) {
        try {
            Integer i = Integer.parseInt(apiResponse.getRawResponse());
            apiResponse.setData(i);
            return apiResponse;
        } catch (Exception e) {
            return apiResponse;
        }
    }


    public ClientService() {
        super(ApiObject.CLIENT);
    }

    public ClientService(ClientRole role) {
        super(ApiObject.PROVIDER);
    }
}
