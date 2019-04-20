package edu.uark.lawncareservicesapp;

import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.regex.Pattern;

import edu.uark.lawncareservicesapp.models.api.ApiResponse;
import edu.uark.lawncareservicesapp.models.api.Client;
import edu.uark.lawncareservicesapp.models.api.services.ClientService;

public class LoginActivity extends AppCompatActivity {

    private static final int REQUEST_READ_CONTACTS = 0;
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:hello", "bar@example.com:world"
    };
    private ClientLogInTask mAuthTask = null;

    // UI references.
    private EditText mClientId;
    private EditText mPasswordView;
    private LoginActivity that = this;
    private static boolean ProviderFlag = false;
    private static boolean ConsumerFlag = true;
    static String roletype = null;

    public static String ReturnRole(){
        if(ProviderFlag) {
            roletype = "provider";
        }
        else if(ConsumerFlag) {
            roletype = "consumer";
        }
        return roletype;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setupActionBar();
        Button ProviderFlagButton = findViewById(R.id.provider_role_switch);
        ProviderFlagButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(that).
                        setMessage(R.string.provider_box_click).
                        setPositiveButton(
                                R.string.button_dismiss,
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.dismiss();
                                    }
                                }
                        ).
                        create().show();
                ProviderFlag = true;
                ConsumerFlag = false;
            }
        });
        Button ConsumerFlagButton = findViewById(R.id.consumer_role_switch);
        ConsumerFlagButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(that).
                        setMessage(R.string.consumer_box_click).
                        setPositiveButton(
                                R.string.button_dismiss,
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.dismiss();
                                    }
                                }
                        ).
                        create().show();
                ConsumerFlag = true;
                ProviderFlag = false;
            }
        });
        mClientId = findViewById(R.id.clientId);

        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);

        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });
//UN-COMMENT LINE BELOW FOR ACTUAL USE OF APPLICATION
         (new ClientCheckTask()).execute();

    }
    @Override
    public void onBackPressed() {
    }


    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void setupActionBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            // Show the Up button in the action bar.
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }
    }

    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mClientId.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String clientId = this.mClientId.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid mClientId.
        if (TextUtils.isEmpty(clientId)) {
            this.mClientId.setError(getString(R.string.error_field_required));
            focusView = this.mClientId;
            cancel = true;
        } else if (!isClientIdValid(clientId)) {
            this.mClientId.setError(getString(R.string.error_invalid_client_id));
            focusView = this.mClientId;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            mAuthTask = new ClientLogInTask();
            mAuthTask.execute(clientId, password);

        }
    }

    private boolean isClientIdValid(String id) {
        return Pattern.matches("[a-zA-Z]+", id) == false;
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 0;
    }

    public class ClientCheckTask extends AsyncTask<Void, Void, ApiResponse<Integer>> {

        @Override
        protected void onPreExecute() {
            this.checkDialog.show();
        }

        @Override
        protected ApiResponse<Integer> doInBackground(Void... params) {
            ApiResponse<Integer> apiResponse = (new ClientService()).check();
            return apiResponse;
        }

        @Override
        protected void onPostExecute(ApiResponse<Integer> apiResponse) {
            this.checkDialog.dismiss();

            if (apiResponse.isValidResponse()) {
                Integer count = apiResponse.getData();
                if (count == 0){

                    Intent createEmp = new Intent(getApplicationContext(),CreateClientViewActivity.class);
                    startActivity(createEmp);
                }


            } else {
                new AlertDialog.Builder(that).
                        setMessage(R.string.client_check_error).
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

        private ClientCheckTask() {
            this.checkDialog = new AlertDialog.Builder(that).
                    setMessage(R.string.client_check).
                    create();
        }
    }


    public class ClientLogInTask extends AsyncTask<String, Void, ApiResponse<Client>> {
        @Override
        protected ApiResponse<Client> doInBackground(String... params) {
            String clientId = params[0];
            String password = params[1];

            ApiResponse<Client> apiResponse = (new ClientService()).login(clientId, password);
            if (apiResponse.isValidResponse()) {
                ApplicationState.setIsAuthenticated(true);
                ApplicationState.setClient(apiResponse.getData());
            }


            return apiResponse;
        }

        @Override
        protected void onPostExecute(ApiResponse<Client> apiResponse) {
            this.loadingLogInAlert.dismiss();

            if (!apiResponse.isValidResponse()) {
                new AlertDialog.Builder(that).
                        setMessage(R.string.login_error).
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
                Intent homepage = new Intent(getApplicationContext(), HomePageActivity.class);
                homepage.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(homepage);
                finish();
            }

            mAuthTask = null;
        }

        private AlertDialog loadingLogInAlert;

        private ClientLogInTask() {
            this.loadingLogInAlert = new AlertDialog.Builder(that).
                    setMessage(R.string.login_loading).
                    create();
        }
    }
}

