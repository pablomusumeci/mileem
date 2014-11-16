package ar.uba.fi.proyectos2.mileem.stats;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.PointF;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Pair;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.androidplot.LineRegion;
import com.androidplot.ui.AnchorPosition;
import com.androidplot.ui.SeriesRenderer;
import com.androidplot.ui.SizeLayoutType;
import com.androidplot.ui.SizeMetrics;
import com.androidplot.ui.XLayoutStyle;
import com.androidplot.ui.YLayoutStyle;
import com.androidplot.xy.BarFormatter;
import com.androidplot.xy.BarRenderer;
import com.androidplot.xy.BoundaryMode;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYSeries;
import com.androidplot.xy.XYStepMode;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import ar.uba.fi.proyectos2.mileem.R;
import ar.uba.fi.proyectos2.mileem.utils.JSONParser;

public class PriceStatsActivity extends Activity {

    private XYPlot plot;

    private MyBarFormatter formatter1;

    private MyBarFormatter selectionFormatter;

    private Pair<Integer, XYSeries> selection;

    private List<Pair<String, Integer>> neighborhoodStats;

    private int maxPrice = 0;

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
                    Pair<String, Integer> p = new Pair<String, Integer>(obj.getString("neighbourhood_name"), obj.getInt("average"));
                    if (maxPrice == 0) {
                        maxPrice = p.second;
                    } else if (maxPrice < p.second) {
                        maxPrice = p.second;
                    }
                    list.add(p);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return list;
        }

        @Override
        protected void onPostExecute(List<Pair<String, Integer>> result) {
            neighborhoodStats = result;
            if (neighborhoodStats.size() > 1) {
                loadAndPlot();
            } else {
                showNoStatsAlert();
            }
        }
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

    private String operation;
    private String currency;
    private String neighbourhood_name;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_price_stats);
        getActionBar().setDisplayHomeAsUpEnabled(false);

    }

    @Override
    public void onStart(){
        super.onStart();
        String url = "http://" + getString(R.string.host) + getString(R.string.stats_price_path);

        int neighbourhood_id = getIntent().getIntExtra("neighbourhood_id", 0);
        operation = getIntent().getStringExtra("operation");
        currency = getIntent().getStringExtra("currency");
        neighbourhood_name = getIntent().getStringExtra("neighbourhood_name");
        TextView tv = (TextView) findViewById(R.id.neighbourhoodName);
        tv.setText(neighbourhood_name);
        new GetHTTPTask().execute(url+"?neighbourhood_id="+ Integer.toString(neighbourhood_id) +"&operation="+operation + "&currency="+currency);
    }

    synchronized private void loadAndPlot(){

        // initialize our XYPlot reference:
        plot = (XYPlot) findViewById(R.id.plotBarras);

        formatter1 = new MyBarFormatter(Color.argb(200, 100, 150, 100), Color.LTGRAY);
        selectionFormatter = new MyBarFormatter(Color.YELLOW, Color.WHITE);

        Number[] series1Numbers = toNumbersArray(neighborhoodStats);

        // Setup our Series with the selected number of elements
        XYSeries series1 = new SimpleXYSeries(Arrays.asList(series1Numbers), SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "");

        // add a new series' to the xyplot:
        plot.addSeries(series1, formatter1);

        // reduce the number of range labels
        plot.setTicksPerRangeLabel(3);
        plot.getGraphWidget().setGridPadding(50, 50, 50, 0);

        plot.setTicksPerDomainLabel(1);

        plot.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    //onPlotClicked(new PointF(motionEvent.getX(), motionEvent.getY()));
                }
                return true;
            }
        });

        plot.setDomainStep(XYStepMode.INCREMENT_BY_VAL, 1);
        plot.setDomainValueFormat(new Format() {
            @Override
            public StringBuffer format(Object object, StringBuffer buffer, FieldPosition field) {
                int parsedInt = Math.round(Float.parseFloat(object.toString()));
                String labelString = neighborhoodStats.get(parsedInt).first;

                buffer.append(labelString);
                return buffer;
            }

            @Override
            public Object parseObject(String string, ParsePosition position) {
                return toListStrings().indexOf(string);
            }
        });
        plot.setRangeTopMax(((maxPrice + 10)/10)*10);
        plot.setRangeValueFormat(new DecimalFormat("#"));

        plot.getLayoutManager().remove(plot.getDomainLabelWidget());
        plot.getLegendWidget().setVisible(false);

        plot.getGraphWidget().getRangeLabelPaint().setTextSize(30);
        plot.getGraphWidget().getDomainLabelPaint().setTextSize(30);
        plot.getGraphWidget().setSize(new SizeMetrics(
                0.5f, SizeLayoutType.RELATIVE,
                0.5f, SizeLayoutType.RELATIVE));

        // Setup the BarRenderer with our selected options
        MyBarRenderer renderer = ((MyBarRenderer) plot.getRenderer(MyBarRenderer.class));
        if (renderer == null) {
            renderer = new MyBarRenderer(plot);
            //System.out.println("Es NULL");
        }
        renderer.setBarRenderStyle(BarRenderer.BarRenderStyle.OVERLAID);
        renderer.setBarWidthStyle(BarRenderer.BarWidthStyle.FIXED_WIDTH);
        renderer.setBarWidth(50);
        renderer.setBarGap(10);

        plot.redraw();

        TableLayout tl = (TableLayout) findViewById(R.id.neighborhoodTable);
        tl.removeAllViews();
        for (Pair<String, Integer> p : neighborhoodStats){
            TableRow tr = new TableRow(this);

            TextView col1 = new TextView(this);
            col1.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,TableRow.LayoutParams.WRAP_CONTENT, 0.5f));
            col1.setText(p.first);
            col1.setTextSize(17.0f);
            col1.setPadding(5, 0, 0, 2);
            col1.setTextColor(Color.GRAY);
            col1.setGravity(Gravity.CENTER_VERTICAL);
            tr.addView(col1);

            TextView col2 = new TextView(this);
            col2.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
            col2.setText(p.second.toString() + " " + (currency != null ? currency : ""));
            col2.setTextSize(17.0f);
            col2.setPadding(80, 2, 20, 2);
            col2.setTextColor(Color.GRAY);
            col2.setGravity(Gravity.CENTER_VERTICAL);

            tr.addView(col2);

            tl.addView(tr);
        }
    }

    private List<String> toListStrings(){
        List<String> res = new ArrayList<String>();
        for (Pair<String, Integer> p : neighborhoodStats){
            res.add(p.first);
        }
        return res;
    }

    private Number[] toNumbersArray(List<Pair<String, Integer>> data) {
        Number[] res = new Number[data.size()];
        int i = 0;
        for (Pair<String, Integer> p : data) {
            res[i++] = p.second;
        }
        return res;
    }

    private void onPlotClicked(PointF point) {

        // make sure the point lies within the graph area.  we use gridrect
        // because it accounts for margins and padding as well.
        if (plot.getGraphWidget().getGridRect().contains(point.x, point.y)) {
            Number x = plot.getXVal(point);
            Number y = plot.getYVal(point);


            selection = null;
            double xDistance = 0;
            double yDistance = 0;

            // find the closest value to the selection:
            for (XYSeries series : plot.getSeriesSet()) {
                for (int i = 0; i < series.size(); i++) {
                    Number thisX = series.getX(i);
                    Number thisY = series.getY(i);
                    if (thisX != null && thisY != null) {
                        double thisXDistance =
                                LineRegion.measure(x, thisX).doubleValue();
                        double thisYDistance =
                                LineRegion.measure(y, thisY).doubleValue();
                        if (selection == null) {
                            selection = new Pair<Integer, XYSeries>(i, series);
                            xDistance = thisXDistance;
                            yDistance = thisYDistance;
                        } else if (thisXDistance < xDistance) {
                            selection = new Pair<Integer, XYSeries>(i, series);
                            xDistance = thisXDistance;
                            yDistance = thisYDistance;
                        } else if (thisXDistance == xDistance &&
                                thisYDistance < yDistance &&
                                thisY.doubleValue() >= y.doubleValue()) {
                            selection = new Pair<Integer, XYSeries>(i, series);
                            xDistance = thisXDistance;
                            yDistance = thisYDistance;
                        }
                    }
                }
            }

        } else {
            // if the press was outside the graph area, deselect:
            selection = null;
        }
        plot.redraw();
    }


    class MyBarFormatter extends BarFormatter {
        public MyBarFormatter(int fillColor, int borderColor) {
            super(fillColor, borderColor);
        }

        @Override
        public Class<? extends SeriesRenderer> getRendererClass() {
            return MyBarRenderer.class;
        }

        @Override
        public SeriesRenderer getRendererInstance(XYPlot plot) {
            return new MyBarRenderer(plot);
        }
    }

    class MyBarRenderer extends BarRenderer<MyBarFormatter> {

        public MyBarRenderer(XYPlot plot) {
            super(plot);
        }

        /**
         * Implementing this method to allow us to inject our
         * special selection formatter.
         *
         * @param index  index of the point being rendered.
         * @param series XYSeries to which the point being rendered belongs.
         * @return a formatter
         */
        @Override
        public MyBarFormatter getFormatter(int index, XYSeries series) {
            if (selection != null &&
                    selection.second == series &&
                    selection.first == index) {
                return selectionFormatter;
            } else {
                return getFormatter(series);
            }
        }
    }
}
