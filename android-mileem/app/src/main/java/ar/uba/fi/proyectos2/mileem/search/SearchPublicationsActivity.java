package ar.uba.fi.proyectos2.mileem.search;

import android.app.Activity;
import android.app.ExpandableListActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import ar.uba.fi.proyectos2.mileem.R;
import ar.uba.fi.proyectos2.mileem.model.MultiSpinner;
import ar.uba.fi.proyectos2.mileem.model.PublicationSearchRequest;

public class SearchPublicationsActivity extends Activity implements MultiSpinner.MultiSpinnerListener {

    private PublicationSearchRequest request;
    private boolean expanded;
    private Logger logger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        expanded = false;
        setContentView(R.layout.activity_search_publications);
        MultiSpinner multiSpinner = (MultiSpinner) findViewById(R.id.searchSpinnerNeighborhood);
        List<String> neighbourhoods = Arrays.asList(getResources().getStringArray(R.array.neighborhood_array));
        multiSpinner.setItems(neighbourhoods,"",this);

        request = new PublicationSearchRequest();

        // opciones por defecto
        RadioButton rb = (RadioButton) findViewById(R.id.radioButtonAmbas);
        rb.setChecked(true);
        request.setOperation(null);
        RadioButton rbCurrency = (RadioButton) findViewById(R.id.radioButtonARS);
        rbCurrency.setChecked(true);
        request.setCurrency("$");

        TextView btn=(TextView) findViewById(R.id.textViewAdvancedOptions);
        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                toggleAdvancedOptions();
            }
        });

        LinearLayout l = (LinearLayout) findViewById(R.id.advancedOptionsGroup);
        l.setVisibility(View.GONE);
        expanded = false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search_publications, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void doSearch(View view) {

        // seleccion del tipo de barrio
        MultiSpinner neighborhood = (MultiSpinner ) findViewById(R.id.searchSpinnerNeighborhood);
        request.setNeighbourhood_name(neighborhood.getSelected());
        Spinner property_name = (Spinner) findViewById(R.id.searchSpinnerType);
        request.setProperty_name(property_name.getSelectedItem().toString());
        Uri.Builder builder = new Uri.Builder()
                .scheme("http")
                .encodedAuthority("192.168.1.100:3000")
                .appendEncodedPath("publications/search.json");
        if (request.getOperation() != null) {
            builder.appendQueryParameter("operation", request.getOperation());
        }
        builder.appendQueryParameter("neighbourhood_name", request.getNeighbourhood_name());
        builder.appendQueryParameter("property_name", request.getProperty_name());

        // Filtros de precios
        TextView tvMinPrice = (TextView) findViewById(R.id.searchMinPrice);
        if (tvMinPrice.getText().length() > 0) {
            request.setMin_price(Integer.parseInt(tvMinPrice.getText().toString()));
            builder.appendQueryParameter("min_price", Integer.toString(request.getMin_price()));
        }

        TextView tvMaxPrice = (TextView) findViewById(R.id.searchMaxPrice);
        if (tvMaxPrice.getText().length() > 0) {
            request.setMax_price(Integer.parseInt(tvMaxPrice.getText().toString()));
            builder.appendQueryParameter("max_price", Integer.toString(request.getMax_price()));
        }

        builder.appendQueryParameter("currency", request.getCurrency());

        if (expanded){
            // numero de ambientes
            TextView tvAmbients = (TextView) findViewById(R.id.search_ambients_input);
            String ambients = tvAmbients.getText().toString();
            if (ambients.length() > 0) {
                request.setNumber_spaces(Integer.parseInt(ambients));
                builder.appendQueryParameter("number_spaces", Integer.toString(request.getNumber_spaces()));
            }
            // valor de expensas
            TextView tvExpenses = (TextView) findViewById(R.id.search_expenses_input);
            String expenses = tvExpenses.getText().toString();
            if (expenses.length() > 0) {
                request.setMax_expenses(Integer.parseInt(expenses));
                builder.appendQueryParameter("max_expenses", Integer.toString(request.getMax_expenses()));
            }
            // antiguedad
            Spinner tvAntiquity = (Spinner) findViewById(R.id.spinner_antiquity);
            int antiquity = tvAntiquity.getSelectedItemPosition();
            switch (antiquity){
                case 0:
                    break;
                case 1: // menor a 10 anyos
                    request.setMax_antiquity(10);
                    builder.appendQueryParameter("max_antiquity", "10");
                    break;
                case 2: // entre 10 y 20
                    request.setMin_antiquity(10);
                    request.setMax_antiquity(20);
                    builder.appendQueryParameter("min_antiquity", "10");
                    builder.appendQueryParameter("max_antiquity", "20");
                    break;
                case 3: // mas de 20 anyos
                    request.setMin_antiquity(20);
                    builder.appendQueryParameter("min_antiquity", "20");
                    break;
            }

            Spinner surfaceSelector = (Spinner) findViewById(R.id.search_surface_selector);
            int surface = surfaceSelector.getSelectedItemPosition();
            switch (surface){
                case 0:
                    break;
                case 1:  // hasta 60 m2
                    request.setMax_surface(60);
                    builder.appendQueryParameter("max_surface", "10");
                    break;
                case 2: // entre 60 y 100
                    request.setMin_surface(60);
                    request.setMax_surface(100);
                    builder.appendQueryParameter("min_surface", "60");
                    builder.appendQueryParameter("max_surface", "100");
                    break;
                case 3:// entre 100 y 300
                    request.setMin_surface(100);
                    request.setMax_surface(300);
                    builder.appendQueryParameter("min_surface", "100");
                    builder.appendQueryParameter("max_surface", "300");
                    break;
                case 4:// mas de 300
                    request.setMin_surface(300);
                    builder.appendQueryParameter("min_surface", "300");
                    break;
            }

        }


        Uri uri = builder.build();
        Intent intent = new Intent(this, SearchResultsActivity.class);
        intent.putExtra("SEARCH.URL", uri.toString());
        startActivity(intent);
    }

    public void toggleAdvancedOptions(){
        LinearLayout l = (LinearLayout) findViewById(R.id.advancedOptionsGroup);
        if (expanded == true) {
            l.setVisibility(View.GONE);
            expanded = false;
        } else {
            l.setVisibility(View.VISIBLE);
            expanded = true;
        }
    }

    public void onRadioButtonClicked(View view) {

        boolean checked = ((RadioButton) view).isChecked();

        switch (view.getId()) {
            case R.id.radioButtonAlquiler:
                if (checked)
                    request.setOperation("Alquiler");
                break;
            case R.id.radioButtonAmbas:
                if (checked)
                    request.setOperation(null);
                break;
            case R.id.radioButtonVenta:
                if (checked)
                    request.setOperation("Venta");
                break;
        }
    }

    public void onRadioButtonCurrencyClicked(View view) {

        boolean checked = ((RadioButton) view).isChecked();

        switch (view.getId()) {
            case R.id.radioButtonARS:
                if (checked)
                    request.setCurrency("$");
                break;
            case R.id.radioButtonUSD:
                if (checked)
                    request.setCurrency("USD");
                break;
        }
    }

    @Override
    public void onItemsSelected(boolean[] selected) {

    }
}
