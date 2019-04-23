package edu.uark.lawncareservicesapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import edu.uark.lawncareservicesapp.models.api.Provider;

public class SearchResultsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        listView = (ListView) findViewById(R.id.list_view_search_results);
        ArrayList<Provider> providers = ApplicationState.getProviderList();
        ArrayList<String> providerInfo = new ArrayList<String>();
        int size = providers.size();
        Log.d("Size", String.valueOf(size));
        for (int i = 0; i < providers.size(); i++) {
            providerInfo.add(providers.get(i).getProviderName());
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                providerInfo );
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ApplicationState.setProviderList(providers);
                Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                intent.putExtra("index", position);
                startActivity(intent);
            }
        });

    }
    private ListView listView;
}
