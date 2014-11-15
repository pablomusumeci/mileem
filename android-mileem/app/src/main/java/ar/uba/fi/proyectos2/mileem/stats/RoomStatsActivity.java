package ar.uba.fi.proyectos2.mileem.stats;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Pair;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

import ar.uba.fi.proyectos2.mileem.R;
import ar.uba.fi.proyectos2.mileem.utils.JSONParser;

public class RoomStatsActivity extends Activity {

    private List<Pair<Integer, Integer>> roomStats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_stats);
        getActionBar().setDisplayHomeAsUpEnabled(false);

        String url = "http://" + getString(R.string.host) + getString(R.string.room_stats_path);

        new GetHTTPTask().execute(url);
    }

    private class GetHTTPTask extends AsyncTask<String, Void, List<Pair<Integer, Integer>>> {

        @Override
        protected List doInBackground(String... params) {
            List<Pair<Integer, Integer>> list = new LinkedList<Pair<Integer, Integer>>();
            String url = params[0];
            JSONArray jArray = JSONParser.getJSONAsArray(url);
            if (jArray == null) {
                return new LinkedList<Pair<String, Integer>>();
            }
            int len = jArray.length();
            for (int i = 0; i < len; ++i) {
                try {
                    JSONObject obj = jArray.getJSONObject(i);
                    Pair<Integer, Integer> p = new Pair<Integer, Integer>(obj.getInt("number_spaces"), obj.getInt("quantity"));
                    list.add(p);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return list;
        }

        @Override
        protected void onPostExecute(List<Pair<Integer, Integer>> result) {
            roomStats = result;
            if (roomStats.size() > 1) {
                loadAndPlot();
            } else {
                showNoStatsAlert();
            }
        }

    }

    private void loadAndPlot(){

    }

    private void showNoStatsAlert(){
        new AlertDialog.Builder(this)
                .setTitle(getString(R.string.no_stats_title))
                .setMessage(getString(R.string.no_stats_description))
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setOnKeyListener(new Dialog.OnKeyListener() {
                    @Override
                    public boolean onKey(DialogInterface arg0, int keyCode,
                                         KeyEvent event) {
                        // TODO Auto-generated method stub
                        if (keyCode == KeyEvent.KEYCODE_BACK) {
                            finish();
                        }
                        return true;
                    }
                })
                .show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.room_stats, menu);
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
