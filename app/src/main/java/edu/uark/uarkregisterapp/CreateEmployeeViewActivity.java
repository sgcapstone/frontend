package edu.uark.uarkregisterapp;

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

import java.text.SimpleDateFormat;
import java.util.Locale;

import edu.uark.uarkregisterapp.models.api.ApiResponse;
import edu.uark.uarkregisterapp.models.api.Employee;
import edu.uark.uarkregisterapp.models.api.services.EmployeeService;
import edu.uark.uarkregisterapp.models.transition.EmployeeTransition;

public class CreateEmployeeViewActivity extends AppCompatActivity {
    private EmployeeTransition employeeTransition;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_employee_view);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

        saveButton = findViewById(R.id.createEmployeeSaveButton);

        this.employeeTransition = this.getIntent().getParcelableExtra("intent_extra_employee");
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (this.employeeTransition != null) {
            this.getEmployeeFirstNameText().setText(this.employeeTransition.getFirstName());
            this.getEmployeeLastNameText().setText(this.employeeTransition.getLastName());
        }
    }

    private EditText getEmployeeFirstNameText(){
        return (EditText) this.findViewById(R.id.edit_text_employee_first_name);
    }

    private EditText getEmployeeLastNameText(){
        return (EditText) this.findViewById(R.id.edit_text_employee_last_name);
    }

    private EditText getEmployeePasswordText(){
        return (EditText) this.findViewById(R.id.edit_text_employee_password);
    }

    public void saveButtonOnClick(View view) {
        String firstName = this.getEmployeeFirstNameText().getText().toString();
        String lastName = this.getEmployeeLastNameText().getText().toString();
        String password = this.getEmployeePasswordText().getText().toString();
        if (!this.validateInput(firstName, lastName, password)) {
            return;
        }
        Employee employee = new Employee();
        employee.setFirstName(firstName);
        employee.setLastName(lastName);
        employee.setPassword(password);
        (new CreateEmployeeViewActivity.CreateEmployeeTask()).execute(employee);
    }

    private class CreateEmployeeTask extends AsyncTask<Employee, Void, ApiResponse<Employee>> {
        @Override
        protected void onPreExecute() {
            this.loadingCreateEmployee.show();
        }

        @Override
        protected ApiResponse<Employee> doInBackground(Employee... params) {
            Employee employee = params[0];
            ApiResponse<Employee> apiResponse = (new EmployeeService()).createEmployee(employee);

            if (apiResponse.isValidResponse()) {
               // TODO: what do do if valid
            }

            return apiResponse;
        }

        @Override
        protected void onPostExecute(ApiResponse<Employee> apiResponse) {
            this.loadingCreateEmployee.dismiss();

            if (!apiResponse.isValidResponse()) {
                new AlertDialog.Builder(CreateEmployeeViewActivity.this).
                        setMessage(R.string.alert_dialog_employee_load_failure).
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
                new AlertDialog.Builder(CreateEmployeeViewActivity.this).
                        setMessage("Employee created!\n\nID: " + apiResponse.getData().getEmployeeId()).
                        setPositiveButton(
                                R.string.button_dismiss,
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.dismiss();
                                        CreateEmployeeViewActivity.this.finish();
                                    }
                                }
                        ).
                        create().
                        show();

            }
        }

        private AlertDialog loadingCreateEmployee;

        private CreateEmployeeTask() {
            this.loadingCreateEmployee = new AlertDialog.Builder(CreateEmployeeViewActivity.this).
                    setMessage(R.string.alert_dialog_employee_loading).
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
