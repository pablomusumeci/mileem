package ar.uba.fi.proyectos2.mileem.search;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import ar.uba.fi.proyectos2.mileem.R;
import ar.uba.fi.proyectos2.mileem.model.Publication;

public class PublicationDetailActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Publication p = (Publication) getIntent().getParcelableExtra(Publication.KEY);
        setContentView(R.layout.activity_publication_detail);
        TextView tv;
        tv = (TextView) findViewById(R.id.address);
        tv.setText(p.getAddress());
        tv = (TextView) findViewById(R.id.operation);
        tv.setText(p.getOperation());
        tv = (TextView) findViewById(R.id.price);
        tv.setText(Integer.toString(p.getPrice()));
        tv = (TextView) findViewById(R.id.description);
        tv.setText(p.getDescription());
        tv = (TextView) findViewById(R.id.effective_date);
        tv.setText(p.getEffective_date());
        tv = (TextView) findViewById(R.id.floor);
        tv.setText(p.getFloor() == -1 ? "No informado" : Integer.toString(p.getFloor()));
        tv = (TextView) findViewById(R.id.apartment);
        tv.setText(p.getApartment());
        tv = (TextView) findViewById(R.id.number_spaces);
        tv.setText(p.getNumber_spaces() == -1 ? "No informado" : Integer.toString(p.getNumber_spaces()));
        tv = (TextView) findViewById(R.id.surface);
        tv.setText(p.getSurface() == -1 ? "No informado" : Integer.toString(p.getSurface()));
        tv = (TextView) findViewById(R.id.expenses);
        tv.setText(p.getExpenses() == -1 ? "No informado" : Integer.toString(p.getExpenses()));
        tv = (TextView) findViewById(R.id.antiquity);
        tv.setText(p.getAntiquity() == -1 ? "No informado" : Integer.toString(p.getAntiquity()));
        tv = (TextView) findViewById(R.id.additional_info);
        tv.setText(p.getAdditional_info());
        tv = (TextView) findViewById(R.id.neighbourhood_name);
        tv.setText(p.getNeighbourhood_name());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.publication_detail, menu);
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
}