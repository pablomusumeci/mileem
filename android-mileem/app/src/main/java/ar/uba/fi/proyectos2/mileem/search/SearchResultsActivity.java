package ar.uba.fi.proyectos2.mileem.search;

import android.app.ListActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import ar.uba.fi.proyectos2.mileem.R;
import ar.uba.fi.proyectos2.mileem.model.Publication;
import ar.uba.fi.proyectos2.mileem.service.JSONParser;
import ar.uba.fi.proyectos2.mileem.service.ListAdapter;


public class SearchResultsActivity extends ListActivity {

    ArrayList<Publication> list = new ArrayList<Publication>();
    private String url = "http://demo9345628.mockable.io/publications.json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search_results, menu);
        return true;
    }

    @Override
    public void onStart() {
        super.onStart();
        new GetHttpData().execute();
        ListView lv = getListView();
        OnPublicationClickListener listener = new OnPublicationClickListener(this);
        lv.setOnItemClickListener(listener);
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

    private class GetHttpData extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            list.clear();
            JSONArray jArray = JSONParser.getJSONAsArray(url);
            if (jArray == null) {
                return null;
            }
            int len = jArray.length();
            for (int i = 0; i < len; ++i) {
                try {
                    JSONObject obj = jArray.getJSONObject(i);
                    Publication p = new Publication();
                    p.setId(obj.getInt("id"));
                    p.setEffective_date(obj.getString("effective_date"));
                    p.setOperation(obj.getString("operation"));
                    p.setAddress(obj.getString("address"));
                    p.setFloor(obj.getInt("floor"));
                    p.setApartment(obj.getString("apartment"));
                    p.setNumber_spaces(obj.getInt("number_spaces"));
                    p.setSurface(obj.getInt("surface"));
                    p.setPrice(obj.getInt("price"));
                    p.setExpenses(obj.getInt("expenses"));
                    p.setAntiquity(obj.getInt("antiquity"));
                    p.setDescription(obj.getString("description"));
                    p.setAdditional_info(obj.getString("additional_info"));
                    p.setCurrency_name(obj.getString("currency_name"));
                    p.setProperty_name(obj.getString("property_type"));
                    p.setNeighbourhood_name(obj.getString("neighbourhood_name"));
                    list.add(p);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            setListAdapter(new ListAdapter(SearchResultsActivity.this, android.R.layout.activity_list_item, list));

        }

    }

    private class OnPublicationClickListener implements AdapterView.OnItemClickListener {

        private SearchResultsActivity context;

        public OnPublicationClickListener(SearchResultsActivity context) {
            this.context = context;
        }

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Intent intent = new Intent(context, PublicationDetailActivity.class);
            intent.putExtra(Publication.KEY, list.get(i));
            startActivity(intent);
        }
    }
}
