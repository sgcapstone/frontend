package edu.uark.lawncareservicesapp.models.api.services;

import org.json.JSONObject;

import java.util.UUID;

import edu.uark.lawncareservicesapp.models.api.ApiResponse;
import edu.uark.lawncareservicesapp.models.api.Client;
import edu.uark.lawncareservicesapp.models.api.enums.ApiObject;

public class ClientService extends BaseRemoteService {
    public ApiResponse<Client> getClient(UUID clientId) {
        return this.readClientDetailsFromResponse(
                this.<Client>performGetRequest(
                        this.buildPath(clientId)
                )
        );
    }

    public ApiResponse<Client> login(String clientId, String password){
        return this.readClientDetailsFromResponse(
                this.<Client>performPostRequest(
                        this.buildPath("login"),
                        Client.convertLogInToJson(clientId,password)
                )
        );
    }

    public ApiResponse<Integer> check(){
        return this.readIntegerFromResponse(
                this.<Integer>performGetRequest(
                        this.buildPath("count")
                )
        );
    }

    public ApiResponse<Client> createClient(Client client){
        return this.readClientDetailsFromResponse(
                this.<Client>performPostRequest(
                        this.buildPath(),
                        client.convertToJson()
                )
        );
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

    private  ApiResponse<Integer> readIntegerFromResponse (ApiResponse<Integer> apiResponse){
        try {
            Integer i = Integer.parseInt(apiResponse.getRawResponse());
            apiResponse.setData(i);
            return apiResponse;
        } catch (Exception e) {
            return apiResponse;
        }
    }


    

    public ClientService() { super(ApiObject.CLIENT); }
}
