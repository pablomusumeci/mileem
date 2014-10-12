package ar.uba.fi.proyectos2.mileem.detail;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.ImageButton;
import android.widget.ImageView;
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

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import ar.uba.fi.proyectos2.mileem.R;
import ar.uba.fi.proyectos2.mileem.model.Publication;

public class PublicationDetailActivity extends Activity {

    public TextView makeCall;
    public TextView makeEmail;
    public ImageButton makeButtonCall;
    public ImageButton makeButtonEmail;
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
        }
        if(p.getUserEmail().equals("")){
            LinearLayout layout =(LinearLayout)findViewById(R.id.emailLayout);
            layout.setVisibility(View.GONE);
        }
        else{
            tv = (TextView) findViewById(R.id.email);
            SpannableString content = new SpannableString(p.getUserEmail());
            content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
            tv.setText(content);
        }

        boolean showMap = true;

        if(p.getLatitude() == -1){
            tv = (TextView) findViewById(R.id.latitude);
            tv.setText("");
            showMap = false;
        }
        else{
            tv = (TextView) findViewById(R.id.latitude);
            tv.setText(Double.toString(p.getLatitude()));
        }

        if(p.getLongitude() == -1){
            tv = (TextView) findViewById(R.id.longitude);
            tv.setText("");
            showMap = false;
        }
        else{
            tv = (TextView) findViewById(R.id.longitude);
            tv.setText(Double.toString(p.getLongitude()));
        }

        if(showMap)
        {
            double lat = p.getLatitude();
            double lon = p.getLongitude();
            ImageView iv = (ImageView) findViewById(R.id.mapImage);
            String URL = "http://maps.google.com/maps/api/staticmap?center=" +lat + "," + lon + "&zoom=14"
                    + "&markers=color:red%7Ccolor:red%7Clabel:A%7C" + lat +"," + lon + "&size=400x180&sensor=false";

            new DownloadImageTask(iv).execute(URL);

        }
        else{
            ImageView iv = (ImageView) findViewById(R.id.mapImage);
            iv.setVisibility(View.GONE);
            tv = (TextView) findViewById(R.id.locationTextView);
            tv.setVisibility(View.GONE);
        }



        tv = (TextView) findViewById(R.id.neighbourhood_name);
        tv.setText(p.getNeighbourhood_name());


        final GestureDetector gestureDetector = new GestureDetector(this, new SingleTapConfirm());
        ImageView iv = (ImageView) findViewById(R.id.mapImage);

        iv.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View arg0, MotionEvent arg1) {
                setUpMap(arg0);
                return false;
            }
        });

    }

    private class SingleTapConfirm extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onSingleTapConfirmed(MotionEvent event) {
            return true;
        }
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


    public void call(View view) {
        try {
            callIntent = new Intent(Intent.ACTION_CALL); //ACTION_INSERT_OR_EDIT);//ACTION_CALL);
            //Log.e("valor",);
            callIntent.setData(Uri.parse("tel:" + ((TextView) findViewById(R.id.phone)).getText()));

            startActivity(callIntent);
        } catch (ActivityNotFoundException activityException) {
            Log.e("dialing-example", "Call failed", activityException);
        }
    }

    public void email(View view) {

        // Obtengo el mail del anunciante
        String[] to =  {((TextView) findViewById(R.id.email)).getText().toString()};
        String subject = "Consulta Propiedad " + ((TextView) findViewById(R.id.address)).getText();
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.putExtra(Intent.EXTRA_EMAIL, to);
        //emailIntent.putExtra(Intent.EXTRA_CC, cc);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        //emailIntent.putExtra(Intent.EXTRA_TEXT, mensaje);
        emailIntent.setType("message/rfc822");
        startActivity(Intent.createChooser(emailIntent, "Email"));
    }

    public void setUpMap(View view) {
        if (!((TextView) findViewById(R.id.latitude)).getText().toString().isEmpty() && !((TextView) findViewById(R.id.longitude)).getText().toString().isEmpty()) {

            TextView tv = (TextView) findViewById(R.id.latitude);
            double lat = Double.parseDouble(tv.getText().toString());
            tv = (TextView) findViewById(R.id.longitude);
            double lon = Double.parseDouble(tv.getText().toString());

            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:" + lat + "," + lon + "?q="+ lat + "," + lon));
            view.getContext().startActivity(intent);
        }
    }


    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }

    public void viewMediaGallery(View view){
        Intent intent = new Intent(this, MediaDetailsActivity.class);
        intent.putExtra("imagesURLs", "http://lorempixel.com/500/800/city/,http://lorempixel.com/500/800/city/");
        startActivity(intent);

    }

}
