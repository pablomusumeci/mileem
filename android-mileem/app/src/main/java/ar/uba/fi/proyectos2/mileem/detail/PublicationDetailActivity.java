package ar.uba.fi.proyectos2.mileem.detail;

import android.app.Activity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import ar.uba.fi.proyectos2.mileem.R;
import ar.uba.fi.proyectos2.mileem.model.Publication;

public class PublicationDetailActivity extends Activity {

    public TextView makeCall;
    public Intent callIntent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Publication p = (Publication) getIntent().getParcelableExtra(Publication.KEY);
        setContentView(R.layout.activity_publication_detail);
        getActionBar().setDisplayHomeAsUpEnabled(false);


        TabHost tabHost = (TabHost)findViewById(R.id.tabHostPictures);
        tabHost.setup();
        TabHost.TabSpec tab1 = tabHost.newTabSpec("Fotos");
        tab1.setContent(R.id.imageView2);
        tab1.setIndicator("Fotos");
        tabHost.addTab(tab1);
        TabHost.TabSpec tab2 = tabHost.newTabSpec("Videos");
        tab2.setContent(R.id.imageView2);
        tab2.setIndicator("Videos");
        tabHost.addTab(tab2);
        tabHost.setCurrentTab(1);


        TextView tv;
        tv = (TextView) findViewById(R.id.address);
        tv.setText(p.getAddress());
        tv = (TextView) findViewById(R.id.operation);
        tv.setText(p.getOperation());
        tv = (TextView) findViewById(R.id.price);
        tv.setText(Integer.toString(p.getNormalized_price()) + " " + p.getNormalized_currency());

        if(p.getFloor() == -1){
            LinearLayout layout =(LinearLayout)findViewById(R.id.floorLayout);
            layout.setVisibility(View.GONE);
        }
        else{
            tv = (TextView) findViewById(R.id.floor);
            tv.setText(Integer.toString(p.getFloor()));
        }

        if(p.getApartment().equals("")){
            LinearLayout layout =(LinearLayout)findViewById(R.id.apartmentLayout);
            layout.setVisibility(View.GONE);
        }
        else{
            tv = (TextView) findViewById(R.id.apartment);
            tv.setText(p.getApartment());
        }

        if(p.getNumber_spaces() == -1){
            LinearLayout layout =(LinearLayout)findViewById(R.id.spacesLayout);
            layout.setVisibility(View.GONE);
        }
        else{
            tv = (TextView) findViewById(R.id.number_spaces);
            tv.setText(Integer.toString(p.getNumber_spaces()));
        }

        if(p.getSurface() == -1){
            LinearLayout layout =(LinearLayout)findViewById(R.id.surfaceLayout);
            layout.setVisibility(View.GONE);
        }
        else{
            tv = (TextView) findViewById(R.id.surface);
            tv.setText(Integer.toString(p.getSurface()) + "m2");
        }

        if(p.getExpenses() == -1){
            LinearLayout layout =(LinearLayout)findViewById(R.id.expensesLayout);
            layout.setVisibility(View.GONE);
        }
        else{
            tv = (TextView) findViewById(R.id.expenses);
            tv.setText(Integer.toString(p.getExpenses())  + " " + p.getCurrency_symbol());
        }

        if(p.getAntiquity() == -1){
            LinearLayout layout =(LinearLayout)findViewById(R.id.antiquityLayout);
            layout.setVisibility(View.GONE);
        }
        else{
            tv = (TextView) findViewById(R.id.antiquity);
            tv.setText(Integer.toString(p.getAntiquity()));
        }

        if(p.getProperty_name().equals("")){
            LinearLayout layout =(LinearLayout)findViewById(R.id.propertyTypeLayout);
            layout.setVisibility(View.GONE);
        }
        else{
            tv = (TextView) findViewById(R.id.propertyType);
            tv.setText(p.getProperty_name());
        }

        if(p.getDescription().equals("")){
            LinearLayout layout =(LinearLayout)findViewById(R.id.descriptionLayout);
            layout.setVisibility(View.GONE);
        }
        else{
            tv = (TextView) findViewById(R.id.description);
            tv.setText(p.getDescription());
        }

        if(p.getUserPhoneNumber().equals("")){
            LinearLayout layout =(LinearLayout)findViewById(R.id.phoneLayout);
            layout.setVisibility(View.GONE);
        }
        else{
            tv = (TextView) findViewById(R.id.phone);
            SpannableString content = new SpannableString(p.getUserPhoneNumber());
            content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
            tv.setText(content);


            makeCall = (TextView) findViewById(R.id.phone);

            makeCall.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    call();
                }
            });
        }

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


    private void call() {
        try {
            callIntent = new Intent(Intent.ACTION_CALL); //ACTION_INSERT_OR_EDIT);//ACTION_CALL);
            //Log.e("valor",);
            callIntent.setData(Uri.parse("tel:" + ((TextView) findViewById(R.id.phone)).getText()));

            startActivity(callIntent);
        } catch (ActivityNotFoundException activityException) {
            Log.e("dialing-example", "Call failed", activityException);
        }
    }

}
