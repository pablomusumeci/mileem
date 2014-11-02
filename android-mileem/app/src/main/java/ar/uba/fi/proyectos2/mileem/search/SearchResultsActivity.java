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
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import ar.uba.fi.proyectos2.mileem.R;
import ar.uba.fi.proyectos2.mileem.detail.PublicationDetailActivity;
import ar.uba.fi.proyectos2.mileem.model.Publication;
import ar.uba.fi.proyectos2.mileem.service.PublicationsResultsListAdapter;
import ar.uba.fi.proyectos2.mileem.utils.JSONParser;
import ar.uba.fi.proyectos2.mileem.utils.PublicationBuilder;


public class SearchResultsActivity extends ListActivity {

    ArrayList<Publication> list = new ArrayList<Publication>();
    String url = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        TextView emptyText = (TextView)findViewById(android.R.id.empty);
        emptyText.setVisibility(View.GONE);
        getActionBar().setDisplayHomeAsUpEnabled(false);

        url = getIntent().getStringExtra("SEARCH.URL");
        new GetHttpData().execute("&sort_by=plan_priority&order=asc");
        ListView lv = getListView();
        OnPublicationClickListener listener = new OnPublicationClickListener(this);
        lv.setOnItemClickListener(listener);
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
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_search_priority) {
            new GetHttpData().execute("&sort_by=plan_priority&order=asc");
            return true;
        } else if (id == R.id.action_search_price) {
            new GetHttpData().execute("&sort_by=price&order=asc");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private class GetHttpData extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... params) {
            list.clear();
            JSONArray jArray = JSONParser.getJSONAsArray(url+params[0]);
            if (jArray == null) {
                return null;
            }
            int len = jArray.length();
            for (int i = 0; i < len; ++i) {
                try {
                    JSONObject obj = jArray.getJSONObject(i);
                    Publication p = PublicationBuilder.buildFromJSON(obj, getBaseContext());
                    list.add(p);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return null;

        }

        @Override
        protected void onPostExecute(Void result) {
            setListAdapter(new PublicationsResultsListAdapter(SearchResultsActivity.this, android.R.layout.activity_list_item, list));
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
