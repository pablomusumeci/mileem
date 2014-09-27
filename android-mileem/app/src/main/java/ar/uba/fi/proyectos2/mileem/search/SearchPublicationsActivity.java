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

import ar.uba.fi.proyectos2.mileem.R;
import ar.uba.fi.proyectos2.mileem.model.PublicationSearchRequest;

public class SearchPublicationsActivity extends ExpandableListActivity {

    private PublicationSearchRequest request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_search_publications);
        request = new PublicationSearchRequest();

        // Filtros avanzados

        ExpandableListView expandableList = getExpandableListView();

        expandableList.setDividerHeight(2);
        expandableList.setGroupIndicator(null);
        expandableList.setClickable(true);

        SearchPublicationsListAdapter adapter = new SearchPublicationsListAdapter(request);

        adapter.setInflater((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE));
        expandableList.setAdapter(adapter);
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
                .encodedAuthority("192.168.1.110:3000")
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

        // if esta desplegado
        ExpandableListView expandableList = getExpandableListView();
        if (expandableList.isGroupExpanded(1)) {
            TextView tvAmbients = (TextView) findViewById(R.id.search_ambients_input);
            request.setNumber_spaces(Integer.parseInt(tvAmbients.getText().toString()));
            builder.appendQueryParameter("number_spaces", Integer.toString(request.getNumber_spaces()));
        }

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
