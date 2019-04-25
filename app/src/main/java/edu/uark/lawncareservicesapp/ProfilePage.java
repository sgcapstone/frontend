package edu.uark.lawncareservicesapp;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewStructure;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import edu.uark.lawncareservicesapp.R;
import edu.uark.lawncareservicesapp.LoginActivity;
import edu.uark.lawncareservicesapp.models.api.Client;
import edu.uark.lawncareservicesapp.models.api.enums.ClientRole;

public class ProfilePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_profile_page);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        that = this;
        //Make a client object, then use ApplicationState to get the current client information

        Client client;
        client = ApplicationState.getClient();
        ClientRole role = client.getRole();

        //Set up and connect views used in the xml file
        ScrollView Info_List = findViewById(R.id.scrollView);
        LinearLayout List = new LinearLayout(getApplicationContext());

        //Setup all text fields to populate with client information
        TextView FNBN = new TextView(getApplicationContext());
        TextView LNON = new TextView(getApplicationContext());
        TextView PN = new TextView(getApplicationContext());
        TextView EA = new TextView(getApplicationContext());
        TextView ADDRESS = new TextView(getApplicationContext());
        TextView CITY = new TextView(getApplicationContext());
        TextView STATE = new TextView(getApplicationContext());
        TextView ZIP = new TextView(getApplicationContext());
        TextView PHONEPLATE = new TextView((getApplicationContext()));
        TextView EMAILPLATE = new TextView(getApplicationContext());
        TextView LNPLATE = new TextView(getApplicationContext());
        switch(role){
            //If the role of the client is set as consumer, the fields will be populated as such
            case CONSUMER:
                List.findViewById(R.id.List_Layout);
                setContentView(Info_List);
                LayoutInflater.from(List.getContext());
                LayoutInflater.from(Info_List.getContext());
                FNBN = (TextView) findViewById(R.id.FNBN_received);
                FNBN.setText(String.valueOf(client.getFirstName()));
                LNON = (TextView) findViewById(R.id.LNON_received);
                LNON.setText(client.getLastName());
                if(ClientRole.CONSUMER.equals(client.getRole())){
                PN = (TextView) findViewById(R.id.PN_received);
                PN.setText("N/A");
                PN.setVisibility(View.GONE);
                PHONEPLATE = (TextView) findViewById(R.id.phone_number);
                PHONEPLATE.setVisibility(View.GONE);
                EMAILPLATE = (TextView) findViewById(R.id.email_address);
                EMAILPLATE.setVisibility(View.GONE);}
                EA = (TextView) findViewById(R.id.E_receieved);
                EA.setText("N/A");
                EA.setVisibility(View.GONE);
                ADDRESS = (TextView) findViewById(R.id.A_received);
                ADDRESS.setText(client.getAddress());
                CITY = (TextView) findViewById(R.id.C_received);
                CITY.setText(client.getCity());
                STATE = (TextView) findViewById(R.id.S_received);
                STATE.setText(client.getState());
                ZIP = (TextView) findViewById(R.id.Z_received);
                ZIP.setText(String.valueOf(client.getZip()));

            //If the role of the client is set as consumer, the fields will be populated as such
            case PROVIDER:
                List.findViewById(R.id.List_Layout);
                setContentView(Info_List);
                LayoutInflater.from(List.getContext());
                LayoutInflater.from(Info_List.getContext());
                FNBN = (TextView) findViewById(R.id.FNBN_received);
                FNBN.setText(String.valueOf(client.getFirstName()));
                LNPLATE = (TextView) findViewById(R.id.last_name);
                if(ClientRole.PROVIDER.equals(client.getRole())){
                LNPLATE.setVisibility(View.GONE);
                LNON = (TextView) findViewById(R.id.LNON_received);
                LNON.setText(client.getLastName());
                LNON.setVisibility(View.GONE);}
                PN = (TextView) findViewById(R.id.PN_received);
                PN.setText(client.getPhoneNumber());
                EA = (TextView) findViewById(R.id.E_receieved);
                EA.setText(client.getEmailAddress());
                ADDRESS = (TextView) findViewById(R.id.A_received);
                ADDRESS.setText(client.getAddress());
                CITY = (TextView) findViewById(R.id.C_received);
                CITY.setText(client.getCity());
                STATE = (TextView) findViewById(R.id.S_received);
                STATE.setText(client.getState());
                ZIP = (TextView) findViewById(R.id.Z_received);
                ZIP.setText(String.valueOf(client.getZip()));
        }
    }

    private ProfilePage that;
}
