package edu.uark.lawncareservicesapp;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import edu.uark.lawncareservicesapp.R;
import edu.uark.lawncareservicesapp.models.api.Client;
import edu.uark.lawncareservicesapp.models.api.enums.ClientRole;

public class ProfilePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Client client = ApplicationState.getClient();
        FNBN.findViewById(R.id.FNBN_received);
        FNBN.setText(client.getFirstName());
        LNON.findViewById(R.id.LNON_received);
        LNON.setText(client.getLastName());
        PN.findViewById(R.id.PN_received);
        PN.setText(client.getPhoneNumber());
        EA.findViewById(R.id.E_receieved);
        EA.setText(client.getEmailAddress());
        ADDRESS.findViewById(R.id.A_received);
        ADDRESS.setText(client.getAddress());
        CITY.findViewById(R.id.C_received);
        CITY.setText(client.getCity());
        STATE.findViewById(R.id.S_received);
        STATE.setText(client.getState());
        ZIP.findViewById(R.id.Z_received);
        ZIP.setText(client.getZip());
        this.setupView();
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }

        });
    }
    private void setupView(){
        Client client = ApplicationState.getClient();
        ClientRole role = client.getRole();

        switch(role){
            case CONSUMER:
                FNBN.setVisibility(View.VISIBLE);

            case PROVIDER:
                FNBN.setVisibility(View.VISIBLE);
        }
    }
    private TextView FNBN;
    private TextView LNON;
    private TextView PN;
    private TextView EA;
    private TextView ADDRESS;
    private TextView CITY;
    private TextView STATE;
    private TextView ZIP;

}
