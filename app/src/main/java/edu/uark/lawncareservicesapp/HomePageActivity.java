package edu.uark.lawncareservicesapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import edu.uark.lawncareservicesapp.models.api.Client;
import edu.uark.lawncareservicesapp.models.api.enums.ClientRole;

public class HomePageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

        SearchforServices = findViewById(R.id.SearchforServices);
        ListofServices = findViewById(R.id.ListofServices);
        ProfilePage = findViewById(R.id.ProfilePage);

        this.setupView();
    }

    private void setupView() {
        if (!ApplicationState.isAuthenticated()) {
            this.startLoginActivity();
            return;
        }

        Client client = ApplicationState.getClient();

        String text =
                getString(R.string.homepage_welcome_text_start) +
                        " " +
                        client.getFirstName() +
                        getString(R.string.homepage_welcome_text_end);
        welcomeText = findViewById(R.id.welcomeText);
        welcomeText.setText(text);

        ClientRole role = client.getRole();

        switch (role) {
            case CONSUMER:
                SearchforServices.setVisibility(View.VISIBLE);
                ListofServices.setVisibility(View.GONE);
                ProfilePage.setVisibility(View.VISIBLE);
                break;

            case PROVIDER:
                ListofServices.setVisibility(View.VISIBLE);
                SearchforServices.setVisibility(View.GONE);
                ProfilePage.setVisibility(View.VISIBLE);
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

    public void ProfilePageOnClick(View view) {
       startActivity(new Intent(getApplicationContext(), ProfilePage.class));
    }

    public void logoutButtonOnClick(View view) {
        ApplicationState.setIsAuthenticated(false);
        ApplicationState.setClient(null);
        this.startLoginActivity();
    }

    public void startListOnClick(View view) {
        // startActivity(new Intent(getApplicationContext(), TransactionActivity.class));
    }
    public void startSearchOnClick(View view){
        startActivity(new Intent(getApplicationContext(), TransactionAcitivty.class));
    }

    private TextView welcomeText;
    private Button SearchforServices;
    private Button ListofServices;
    private Button ProfilePage;
}
