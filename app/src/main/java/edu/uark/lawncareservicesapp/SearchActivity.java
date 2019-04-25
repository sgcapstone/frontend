package edu.uark.lawncareservicesapp;

<<<<<<< HEAD


=======
>>>>>>> master
import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
<<<<<<< HEAD

import android.util.Log;

import android.view.View;

=======
import android.util.Log;
import android.view.View;
>>>>>>> master
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

import edu.uark.lawncareservicesapp.models.api.ApiResponse;
<<<<<<< HEAD
import edu.uark.lawncareservicesapp.models.api.services.ClientService;
import edu.uark.lawncareservicesapp.models.api.Client;


public class SearchActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

=======
import edu.uark.lawncareservicesapp.models.api.services.ProviderService;
import edu.uark.lawncareservicesapp.models.api.Provider;

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(R.string.app_bar_title);
>>>>>>> master

        name = (EditText) findViewById(R.id.edit_text_provider_name);
        city = (EditText) findViewById(R.id.edit_text_city_name);
        service = (EditText) findViewById(R.id.edit_text_services);


        Button searchButton = findViewById(R.id.search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
<<<<<<< HEAD

=======
>>>>>>> master
            @Override
            public void onClick(View view) {
                attemptSearch();
            }
        });
<<<<<<< HEAD
        
    }


=======

        (new ProviderCheckTask()).execute();

    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void setupActionBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            // Show the Up button in the action bar.
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }
    }
>>>>>>> master

    private void attemptSearch() {

        if (!this.name.getText().toString().equals("")) {
            // Perform search by name
            mAuthTask = new ProviderSearchTask();
            mAuthTask.execute("name", this.name.getText().toString());

        }
<<<<<<< HEAD

=======
>>>>>>> master
        else if (!this.city.getText().toString().equals("")) {
            // Perform search by city
            mAuthTask = new ProviderSearchTask();
            mAuthTask.execute("city", this.city.getText().toString());

<<<<<<< HEAD

        }

=======
        }
>>>>>>> master
        else if (!this.service.getText().toString().equals("")) {
            // Perform search by services offered
            mAuthTask = new ProviderSearchTask();
            mAuthTask.execute("services", this.service.getText().toString());
<<<<<<< HEAD
=======

>>>>>>> master
        }
        // Display error if there are no search results.
    }


<<<<<<< HEAD
    public class ProviderSearchTask extends AsyncTask<String, Void, ApiResponse<Client>> {

        @Override
        protected ApiResponse<Client> doInBackground(String... params) {
            String searchMethod = params[0];
            String searchValue = params[1];
            ApiResponse<Client> apiResponse = new ApiResponse<>();
            ApiResponse<List<Client>> apiResponseList = new ApiResponse<>();
            apiResponseListFlag = false;

            if (searchMethod.equals("name")) {
                apiResponseList = new ClientService(ApplicationState.getClient().getRole()).findProvidersByName(searchValue);
            }
            else if (searchMethod.equals("city")) {
                apiResponseList = new ClientService(ApplicationState.getClient().getRole()).findProvidersByCity(searchValue);
            }
            else if (searchMethod.equals("services")) {
                apiResponseList = new ClientService(ApplicationState.getClient().getRole()).findProvidersByServices(searchValue);
            }
=======
    public class ProviderCheckTask extends AsyncTask<Void, Void, ApiResponse<Integer>> {

        @Override
        protected void onPreExecute() {
            this.checkDialog.show();
        }

        @Override
        protected ApiResponse<Integer> doInBackground(Void... params) {
            ApiResponse<Integer> apiResponse = (new ProviderService()).check();
            System.out.print(apiResponse);
            return apiResponse;
        }

        @Override
        protected void onPostExecute(ApiResponse<Integer> apiResponse) {
            this.checkDialog.dismiss();

            if (apiResponse.isValidResponse()) {
                Integer count = apiResponse.getData();
            } else {
                new AlertDialog.Builder(that).
                        setMessage(R.string.provider_check_error).
                        setPositiveButton(
                                R.string.button_dismiss,
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.dismiss();
                                    }
                                }
                        ).
                        create().
                        show();

            }

        }

        private AlertDialog checkDialog;

        private ProviderCheckTask() {
            this.checkDialog = new AlertDialog.Builder(that).
                    setMessage(R.string.provider_check).
                    create();
        }
    }


    public class ProviderSearchTask extends AsyncTask<String, Void, ApiResponse<Provider>> {
        @Override
        protected ApiResponse<Provider> doInBackground(String... params) {
            String searchMethod = params[0];
            String searchValue = params[1];

            ApiResponse<Provider> apiResponse = new ApiResponse<>();
            ApiResponse<List<Provider>> apiResponseList = new ApiResponse<>();
            apiResponseListFlag = false;

            if (searchMethod.equals("name")) {
                apiResponseList = new ProviderService().findProvidersByName(searchValue);
            }
            else if (searchMethod.equals("city")) {
                apiResponseList = new ProviderService().findProvidersByCity(searchValue);
            }
            else if (searchMethod.equals("services")) {
                apiResponseList = new ProviderService().findProvidersByServices(searchValue);
            }

>>>>>>> master
            /*if (apiResponse.isValidResponse() && !apiResponseListFlag) {
                ApplicationState.setIsAuthenticated(true);
                ApplicationState.setProvider(apiResponse.getData());
                return apiResponse;
            }
            */
            if (apiResponseList.isValidResponse()) {
                try {
                    ApplicationState.setIsAuthenticated(true);
                    ApplicationState.setProviderList(apiResponseList.getData());
                    int size = ApplicationState.getProviderList().size();
                    if (size > 0) { apiResponseListFlag = true; }
                }
                catch (Exception e) { }
<<<<<<< HEAD
            }
            // Pointless return statement.
            return apiResponse;
        }

        @Override
        protected void onPostExecute(ApiResponse<Client> apiResponse) {
            this.loadingSearchResultsAlert.dismiss();
            if (!apiResponseListFlag) {
=======

            }

            // Pointless return statement.
            return apiResponse;


        }

        @Override
        protected void onPostExecute(ApiResponse<Provider> apiResponse) {
            this.loadingSearchResultsAlert.dismiss();

            if (!apiResponseListFlag) {

>>>>>>> master
                new AlertDialog.Builder(that).
                        setMessage(R.string.provider_check_error).
                        setPositiveButton(
                                R.string.button_dismiss,
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.dismiss();
                                    }
                                }
                        ).
                        create().
                        show();
            } else {
                Intent homepage = new Intent(getApplicationContext(), SearchResultsActivity.class);
                homepage.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(homepage);
                finish();
            }
<<<<<<< HEAD
            mAuthTask = null;
        }
=======

            mAuthTask = null;
        }

>>>>>>> master
        private AlertDialog loadingSearchResultsAlert;

        private ProviderSearchTask() {
            this.loadingSearchResultsAlert = new AlertDialog.Builder(that).
                    setMessage(R.string.performing_search).
                    create();
        }
    }


    private EditText name;
    private EditText city;
    private EditText service;
    private SearchActivity that = this;
    private ProviderSearchTask mAuthTask = null;
    private boolean apiResponseListFlag;
<<<<<<< HEAD

}
=======
}
>>>>>>> master
