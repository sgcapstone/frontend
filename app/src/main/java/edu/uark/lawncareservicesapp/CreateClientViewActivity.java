package edu.uark.lawncareservicesapp;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.apache.commons.lang3.StringUtils;

import edu.uark.lawncareservicesapp.models.api.services.ClientService;
import edu.uark.lawncareservicesapp.models.transition.ClientTransition;
import edu.uark.lawncareservicesapp.R;
import edu.uark.lawncareservicesapp.models.api.ApiResponse;
import edu.uark.lawncareservicesapp.models.api.Client;

public class CreateClientViewActivity extends AppCompatActivity {
    private ClientTransition clientTransition;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_client_view);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

        saveButton = findViewById(R.id.createClientSaveButton);

        this.clientTransition = this.getIntent().getParcelableExtra("intent_extra_client");
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (this.clientTransition != null) {
            this.getClientFirstNameText().setText(this.clientTransition.getFirstName());
            this.getClientLastNameText().setText(this.clientTransition.getLastName());
        }
    }

    private EditText getClientFirstNameText(){
        return (EditText) this.findViewById(R.id.edit_text_client_first_name);
    }

    private EditText getClientLastNameText(){
        return (EditText) this.findViewById(R.id.edit_text_client_last_name);
    }

    private EditText getClientPasswordText(){
        return (EditText) this.findViewById(R.id.edit_text_client_password);
    }

    public void saveButtonOnClick(View view) {
        String firstName = this.getClientFirstNameText().getText().toString();
        String lastName = this.getClientLastNameText().getText().toString();
        String password = this.getClientPasswordText().getText().toString();
        if (!this.validateInput(firstName, lastName, password)) {
            return;
        }
        Client client = new Client();
        client.setFirstName(firstName);
        client.setLastName(lastName);
        client.setPassword(password);
        (new CreateClientViewActivity.CreateClientTask()).execute(client);
    }

    private class CreateClientTask extends AsyncTask<Client, Void, ApiResponse<Client>> {
        @Override
        protected void onPreExecute() {
            this.loadingCreateClient.show();
        }

        @Override
        protected ApiResponse<Client> doInBackground(Client... params) {
            Client client = params[0];
            ApiResponse<Client> apiResponse = (new ClientService()).createClient(client);

            if (apiResponse.isValidResponse()) {
               // TODO: what do do if valid
            }

            return apiResponse;
        }

        @Override
        protected void onPostExecute(ApiResponse<Client> apiResponse) {
            this.loadingCreateClient.dismiss();

            if (!apiResponse.isValidResponse()) {
                new AlertDialog.Builder(CreateClientViewActivity.this).
                        setMessage(R.string.alert_dialog_client_load_failure).
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
                new AlertDialog.Builder(CreateClientViewActivity.this).
                        setMessage("Client created!\n\nID: " + apiResponse.getData().getClientId()).
                        setPositiveButton(
                                R.string.button_dismiss,
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.dismiss();
                                        CreateClientViewActivity.this.finish();
                                    }
                                }
                        ).
                        create().
                        show();

            }
        }

        private AlertDialog loadingCreateClient;

        private CreateClientTask() {
            this.loadingCreateClient = new AlertDialog.Builder(CreateClientViewActivity.this).
                    setMessage(R.string.alert_dialog_client_loading).
                    create();
        }
    }


    private boolean validateInput(String firstName, String lastName, String password) {
        boolean inputIsValid = true;
        String validationMessage = StringUtils.EMPTY;

        if (inputIsValid && StringUtils.isBlank(firstName)) {
            validationMessage = this.getString(R.string.validation_product_lookup_code);
            inputIsValid = false;
        }

        if (inputIsValid && StringUtils.isBlank(lastName)) {
            validationMessage = this.getString(R.string.validation_product_count);
            inputIsValid = false;
        }

        if (inputIsValid && StringUtils.isBlank(password)) {
            validationMessage = this.getString(R.string.validation_product_count);
            inputIsValid = false;
        }

        if (!inputIsValid) {
            new AlertDialog.Builder(this).
                    setMessage(validationMessage).
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

        return inputIsValid;
    }

    private Button saveButton;
}
