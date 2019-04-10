package edu.uark.uarkregisterapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import edu.uark.uarkregisterapp.models.api.Employee;
import edu.uark.uarkregisterapp.models.api.enums.EmployeeRole;

public class HomePageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

        startTrasactionButton = findViewById(R.id.startTransactionButton);
        createEmployeeButton = findViewById(R.id.createEmployeeButton);
        salesReportProductButton = findViewById(R.id.salesReportProductButton);
        salesReportProductCashier = findViewById(R.id.saleReportCashierButton);

        this.setupView();
    }

    private void setupView() {
        if (!ApplicationState.isAuthenticated()) {
            this.startLoginActivity();
            return;
        }

        Employee employee = ApplicationState.getEmployee();

        String text =
            getString(R.string.homepage_welcome_text_start) +
            " " +
            employee.getFirstName() +
            getString(R.string.homepage_welcome_text_end);
        welcomeText = findViewById(R.id.welcomeText);
        welcomeText.setText(text);

        EmployeeRole role = employee.getRole();

        switch (role) {
            case CASHIER:
                startTrasactionButton.setVisibility(View.VISIBLE);
                salesReportProductButton.setVisibility(View.INVISIBLE);
                salesReportProductCashier.setVisibility(View.INVISIBLE);
                createEmployeeButton.setVisibility(View.INVISIBLE);
                break;

            case SHIFT_MANAGER:
                startTrasactionButton.setVisibility(View.VISIBLE);
                salesReportProductButton.setVisibility(View.VISIBLE);
                salesReportProductCashier.setVisibility(View.VISIBLE);
                createEmployeeButton.setVisibility(View.INVISIBLE);
                break;

            case GENERAL_MANAGER:
                startTrasactionButton.setVisibility(View.VISIBLE);
                salesReportProductButton.setVisibility(View.VISIBLE);
                salesReportProductCashier.setVisibility(View.VISIBLE);
                createEmployeeButton.setVisibility(View.VISIBLE);
                break;
        }

    }

    private void startLoginActivity() {
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
    }

    public void unimplementedButtonOnClick(View view) {
        new AlertDialog.Builder(this).
                setTitle(R.string.homepage_alert_dialog_unimplemented_title).
                setMessage(R.string.homepage_alert_dialog_unimplemented_subtitle).
                setPositiveButton(
                        R.string.button_ok,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                            }
                        }
                ).
                create().
                show();
    }

    public void createEmployeeClick(View view) {
        startActivity(new Intent(getApplicationContext(), CreateEmployeeViewActivity.class));
    }

    public void logoutButtonOnClick(View view) {
        ApplicationState.setIsAuthenticated(false);
        ApplicationState.setEmployee(null);
        this.startLoginActivity();
    }

    public void startTransactionOnClick(View view){
        startActivity(new Intent(getApplicationContext(), TransactionAcitivty.class));
    }

    private TextView welcomeText;
    private Button startTrasactionButton;
    private Button createEmployeeButton;
    private Button salesReportProductButton;
    private Button salesReportProductCashier;
}
