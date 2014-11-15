package ar.uba.fi.proyectos2.mileem.stats;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.EmbossMaskFilter;
import android.graphics.PointF;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Pair;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import com.androidplot.pie.PieChart;
import com.androidplot.pie.PieRenderer;
import com.androidplot.pie.Segment;
import com.androidplot.pie.SegmentFormatter;
import com.androidplot.xy.XYPlot;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.List;

import ar.uba.fi.proyectos2.mileem.R;
import ar.uba.fi.proyectos2.mileem.utils.JSONParser;

public class RoomStatsActivity extends Activity {

    private List<Pair<String, Integer>> roomStats;

    private PieChart pie;

    private Segment s1;
    private Segment s2;
    private Segment s3;
    private Segment s4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_stats);
        getActionBar().setDisplayHomeAsUpEnabled(false);

        String url = "http://" + getString(R.string.host) + getString(R.string.room_stats_path);

        new GetHTTPTask().execute(url);
    }

    private class GetHTTPTask extends AsyncTask<String, Void, List<Pair<String, Integer>>> {

        @Override
        protected List doInBackground(String... params) {
            List<Pair<String, Integer>> list = new LinkedList<Pair<String, Integer>>();
            String url = params[0];
            JSONArray jArray = JSONParser.getJSONAsArray(url);
            if (jArray == null) {
                return new LinkedList<Pair<String, Integer>>();
            }
            int len = jArray.length();
            for (int i = 0; i < len; ++i) {
                try {
                    JSONObject obj = jArray.getJSONObject(i);
                    Pair<String, Integer> p = new Pair<String, Integer>(obj.getString("number_spaces"), obj.getInt("quantity"));
                    list.add(p);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return list;
        }

        @Override
        protected void onPostExecute(List<Pair<String, Integer>> result) {
            roomStats = result;
            if (roomStats.size() > 1) {
                loadAndPlot();
            } else {
                showNoStatsAlert();
            }
        }

    }

    private void loadAndPlot(){

        pie = (PieChart) findViewById(R.id.pieChartRooms);


        // detect segment clicks:
        pie.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                PointF click = new PointF(motionEvent.getX(), motionEvent.getY());
                if(pie.getPieWidget().containsPoint(click)) {
                    Segment segment = pie.getRenderer(PieRenderer.class).getContainingSegment(click);
                    if(segment != null) {
                        // handle the segment click...for now, just print
                        // the clicked segment's title to the console:
                        System.out.println("Clicked Segment: " + segment.getTitle());
                    }
                }
                return false;
            }
        });




        EmbossMaskFilter emf = new EmbossMaskFilter(
                new float[]{1, 1, 1}, 0.4f, 10, 8.2f);

        SegmentFormatter sf1 = new SegmentFormatter();
        sf1.configure(getApplicationContext(), R.xml.pie_segment_formatter1);

        sf1.getFillPaint().setMaskFilter(emf);

        SegmentFormatter sf2 = new SegmentFormatter();
        sf2.configure(getApplicationContext(), R.xml.pie_segment_formatter2);

        sf2.getFillPaint().setMaskFilter(emf);

        SegmentFormatter sf3 = new SegmentFormatter();
        sf3.configure(getApplicationContext(), R.xml.pie_segment_formatter3);

        sf3.getFillPaint().setMaskFilter(emf);

        SegmentFormatter sf4 = new SegmentFormatter();
        sf4.configure(getApplicationContext(), R.xml.pie_segment_formatter4);

        sf4.getFillPaint().setMaskFilter(emf);

        SegmentFormatter formatters[] = new SegmentFormatter[]{sf1, sf2, sf3, sf4};
        int posFormatter = 0;
        double sum = 0;
        for (Pair<String, Integer> p : roomStats) {
            sum+=p.second;
        }
        DecimalFormat df = new DecimalFormat("#");
        for (Pair<String, Integer> p : roomStats) {
            pie.addSeries(new Segment(p.first + " ("+df.format(p.second/sum*100.0)+"%)", p.second), formatters[(posFormatter++)%(formatters.length)]);
        }

        pie.getBorderPaint().setColor(Color.TRANSPARENT);
        pie.getBackgroundPaint().setColor(Color.TRANSPARENT);
        pie.redraw();
        pie.getRenderer(PieRenderer.class).setDonutSize(0.0f, PieRenderer.DonutMode.PERCENT);
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
