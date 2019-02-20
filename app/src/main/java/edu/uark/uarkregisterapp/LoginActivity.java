package edu.uark.uarkregisterapp;

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

import edu.uark.uarkregisterapp.models.api.ApiResponse;
import edu.uark.uarkregisterapp.models.api.Employee;
import edu.uark.uarkregisterapp.models.api.services.EmployeeService;

public class LoginActivity extends AppCompatActivity {

    private static final int REQUEST_READ_CONTACTS = 0;
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:hello", "bar@example.com:world"
    };
    private EmployeeLogInTask mAuthTask = null;

    // UI references.
    private EditText mEmployeeId;
    private EditText mPasswordView;
    private LoginActivity that = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setupActionBar();

        mEmployeeId = findViewById(R.id.employeeId);

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

        (new EmployeeCheckTask()).execute();

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
        mEmployeeId.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String employeeId = this.mEmployeeId.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid mEmployeeId.
        if (TextUtils.isEmpty(employeeId)) {
            this.mEmployeeId.setError(getString(R.string.error_field_required));
            focusView = this.mEmployeeId;
            cancel = true;
        } else if (!isEmployeeIdValid(employeeId)) {
            this.mEmployeeId.setError(getString(R.string.error_invalid_employee_id));
            focusView = this.mEmployeeId;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            mAuthTask = new EmployeeLogInTask();
            mAuthTask.execute(employeeId, password);

        }
    }

    private boolean isEmployeeIdValid(String id) {
        return Pattern.matches("[a-zA-Z]+", id) == false;
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 0;
    }

    public class EmployeeCheckTask extends AsyncTask<Void, Void, ApiResponse<Integer>> {

        @Override
        protected ApiResponse<Integer> doInBackground(Void... params) {
            ApiResponse<Integer> apiResponse = (new EmployeeService()).check();
            return apiResponse;
        }

        @Override
        protected void onPostExecute(ApiResponse<Integer> apiResponse) {
            if (apiResponse.isValidResponse()) {
                Integer count = apiResponse.getData();
                if (count == 0){

                    Intent createEmp = new Intent(getApplicationContext(),CreateEmployeeViewActivity.class);
                    startActivity(createEmp);
                }


            } else {
                new AlertDialog.Builder(that).
                        setMessage(R.string.employee_check_error).
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

        private EmployeeCheckTask() {}
    }


    public class EmployeeLogInTask extends AsyncTask<String, Void, ApiResponse<Employee>> {
        @Override
        protected ApiResponse<Employee> doInBackground(String... params) {
            String employeeId = params[0];
            String password = params[1];

            ApiResponse<Employee> apiResponse = (new EmployeeService()).login(employeeId, password);

            if (apiResponse.isValidResponse()) {
                ApplicationState.setIsAuthenticated(true);
                ApplicationState.setEmployee(apiResponse.getData());
            }


            return apiResponse;
        }

        @Override
        protected void onPostExecute(ApiResponse<Employee> apiResponse) {
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

        private EmployeeLogInTask() {
            this.loadingLogInAlert = new AlertDialog.Builder(that).
                    setMessage(R.string.login_loading).
                    create();
        }
    }
}

