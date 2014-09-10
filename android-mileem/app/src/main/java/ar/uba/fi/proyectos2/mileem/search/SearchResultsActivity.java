package ar.uba.fi.proyectos2.mileem.search;

import android.app.ListActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import ar.uba.fi.proyectos2.mileem.model.Publication;
import ar.uba.fi.proyectos2.mileem.service.JSONParser;
import ar.uba.fi.proyectos2.mileem.service.ListAdapter;
import ar.uba.fi.proyectos2.mileem.R;


public class SearchResultsActivity extends ListActivity {

    ArrayList<Publication> list = new ArrayList<Publication>();
    private String url = "http://192.168.1.100:8080/mockJSONService-0.1/publicacion/getAllPublications";

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
                    list.add(new Publication(obj.getString("name").trim(), obj.getString("direccion").trim(),
                             obj.getInt("precio")));
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
}
