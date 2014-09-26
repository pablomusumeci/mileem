package ar.uba.fi.proyectos2.mileem.search;

import android.app.ExpandableListActivity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import ar.uba.fi.proyectos2.mileem.R;
import ar.uba.fi.proyectos2.mileem.model.PublicationSearchRequest;

public class SearchPublicationsActivity extends ExpandableListActivity {

    private PublicationSearchRequest request;
    private ArrayList<String> parentItems = new ArrayList<String>();
    private ArrayList<Object> childItems = new ArrayList<Object>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_publications);
        request = new PublicationSearchRequest();

        // opciones por defecto
        RadioButton rb = (RadioButton) findViewById(R.id.radioButtonAmbas);
        rb.setChecked(true);
        RadioButton rbCurrency = (RadioButton) findViewById(R.id.radioButtonARS);
        rbCurrency.setChecked(true);

        // Filtros avanzados

        ExpandableListView expandableList = getExpandableListView();

        expandableList.setDividerHeight(2);
        expandableList.setGroupIndicator(null);
        expandableList.setClickable(true);

        setGroupParents();
        setChildData();

        AdvancedSearchListAdapter adapter = new AdvancedSearchListAdapter(parentItems, childItems);

        adapter.setInflater((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE), this);
        expandableList.setAdapter(adapter);
        expandableList.setOnChildClickListener(this);
    }

    public void setGroupParents() {
        parentItems.add("Advanced Search");
    }

    public void setChildData() {

        // Android
        ArrayList<String> child = new ArrayList<String>();
        child.add("Advandec Filters");
        childItems.add(child);
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
        Spinner neighborhood = (Spinner) findViewById(R.id.searchSpinnerNeighborhood);
        request.setNeighbourhood_name(neighborhood.getSelectedItem().toString());
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

        Uri uri = builder.build();
        Intent intent = new Intent(this, SearchResultsActivity.class);
        intent.putExtra("SEARCH.URL", uri.toString());
        startActivity(intent);
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

}
