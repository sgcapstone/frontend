package edu.uark.uarkregisterapp.models.api.services;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.AsyncTask;

import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.UUID;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import edu.uark.uarkregisterapp.R;
import edu.uark.uarkregisterapp.models.api.ApiResponse;
import edu.uark.uarkregisterapp.models.api.Employee;
import edu.uark.uarkregisterapp.models.api.enums.ApiObject;

public class EmployeeService extends BaseRemoteService {
    public ApiResponse<Employee> getEmployee(UUID employeeId) {
        return this.readEmployeeDetailsFromResponse(
                this.<Employee>performGetRequest(
                        this.buildPath(employeeId)
                )
        );
    }

    public ApiResponse<Employee> login(String employeeId, String password){
        return this.readEmployeeDetailsFromResponse(
                this.<Employee>performPostRequest(
                        this.buildPath("login"),
                        Employee.convertLogInToJson(employeeId,password)
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

    public ApiResponse<Employee> createEmployee(Employee employee){
        return this.readEmployeeDetailsFromResponse(
                this.<Employee>performPostRequest(
                        this.buildPath(),
                        employee.convertToJson()
                )
        );
    }

    private ApiResponse<Employee> readEmployeeDetailsFromResponse(ApiResponse<Employee> apiResponse) {
        JSONObject rawJsonObject = this.rawResponseToJSONObject(
                apiResponse.getRawResponse()
        );

        if (rawJsonObject != null) {
            apiResponse.setData(
                    (new Employee()).loadFromJson(rawJsonObject)
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


    

    public EmployeeService() { super(ApiObject.EMPLOYEE); }
}
