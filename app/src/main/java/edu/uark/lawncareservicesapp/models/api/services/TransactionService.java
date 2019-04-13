package edu.uark.lawncareservicesapp.models.api.services;

import org.json.JSONObject;

import edu.uark.lawncareservicesapp.models.api.ApiResponse;
import edu.uark.lawncareservicesapp.models.api.Transaction;
import edu.uark.lawncareservicesapp.models.api.enums.ApiObject;

/**
 * Created by jaredramirez on 4/15/18.
 */

public class TransactionService extends BaseRemoteService {
    public ApiResponse<Transaction> createTransaction(Transaction transaction) {
        return this.readTransactionFromResponse(
            this.performPostRequest(this.buildPath(), transaction.convertToJson())
        );
    }

    private ApiResponse<Transaction> readTransactionFromResponse(ApiResponse<Transaction> apiResponse) {
        JSONObject rawJsonObject = this.rawResponseToJSONObject(
                apiResponse.getRawResponse()
        );

        if (rawJsonObject != null) {
            apiResponse.setData((new Transaction()).loadFromJson(rawJsonObject));
        }

        return apiResponse;
    }

    public TransactionService() { super(ApiObject.TRANSACTION); }
}
