package ar.uba.fi.proyectos2.mileem.detail;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.FacebookException;
import com.facebook.Session;
import com.facebook.UiLifecycleHelper;
import com.facebook.widget.FacebookDialog;
import com.facebook.widget.WebDialog;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import ar.uba.fi.proyectos2.mileem.R;
import ar.uba.fi.proyectos2.mileem.model.Publication;
import ar.uba.fi.proyectos2.mileem.stats.PriceStatsActivity;
import ar.uba.fi.proyectos2.mileem.utils.DownloadImageTask;

public class PublicationDetailActivity extends Activity {

    public Intent callIntent;

    private UiLifecycleHelper uiHelper;

    private Publication p;

    private View generateViewForGallery(String url){

        ImageView imageView = new ImageView(this);
        imageView.setLayoutParams(new LinearLayout.LayoutParams(400, 400));
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

        new DownloadImageTask(imageView).execute(url);

        return imageView;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        p = (Publication) getIntent().getParcelableExtra(Publication.KEY);

        setContentView(R.layout.activity_publication_detail);
        getActionBar().setDisplayHomeAsUpEnabled(false);

        // para el compartir en Facebook
        uiHelper = new UiLifecycleHelper(this, null);

        uiHelper.onCreate(savedInstanceState);

        LinearLayout imageGallery = (LinearLayout) findViewById(R.id.linearLayoutImages);
        int position = 0;
        for (String imageUrl : p.getImagesURLs()){
            imageGallery.addView(generateViewForGallery(imageUrl));
        }

        if (p.getVideo() != null && p.getVideo().length()>0){
            ImageView imageView = new ImageView(this);
            imageView.setLayoutParams(new LinearLayout.LayoutParams(400, 400));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setImageResource(R.drawable.ic_action_video);
            imageView.setBackgroundColor(Color.BLACK);
            imageGallery.addView(imageView);
        }

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

            final DownloadImageTask downloadImageTask = new DownloadImageTask(iv);
            downloadImageTask.execute(URL);
            final GestureDetector gestureDetector = new GestureDetector(this, new SingleTapConfirm());

            iv.setOnTouchListener(new View.OnTouchListener() {

                @Override
                public boolean onTouch(View arg0, MotionEvent arg1) {
                    if(downloadImageTask.getMapLoaded()) {
                        setUpMap(arg0);
                    }
                    return false;
                }
            });
        }
        else{
            ImageView iv = (ImageView) findViewById(R.id.mapImage);
            iv.setVisibility(View.GONE);
            tv = (TextView) findViewById(R.id.locationTextView);
            tv.setVisibility(View.GONE);
        }
        tv = (TextView) findViewById(R.id.neighbourhood_name);
        tv.setText(p.getNeighbourhood_name());

    }

    private class SingleTapConfirm extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onSingleTapConfirmed(MotionEvent event) {
            return true;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        uiHelper.onActivityResult(requestCode, resultCode, data, new FacebookDialog.Callback() {
            @Override
            public void onError(FacebookDialog.PendingCall pendingCall, Exception error, Bundle data) {
                Log.e("Activity", String.format("Error: %s", error.toString()));
            }

            @Override
            public void onComplete(FacebookDialog.PendingCall pendingCall, Bundle data) {
                Log.i("Activity", "Success!");
            }
        });
    }


    public void call(View view) {
        try {
            callIntent = new Intent(Intent.ACTION_CALL);
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
        //Intent emailIntent = new Intent(Intent.ACTION_SEND);
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO,Uri.parse("mailto:"+to[0]));
        emailIntent.setData(Uri.parse("mailto:"+to[0]));
        //emailIntent.putExtra(Intent.EXTRA_EMAIL, to);
        //emailIntent.putExtra(Intent.EXTRA_CC, cc);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        //emailIntent.putExtra(Intent.EXTRA_TEXT, mensaje);
        //emailIntent.setType("message/rfc822");
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


    public void viewMediaGallery(View view){
        Intent intent = new Intent(this, MediaDetailsActivity.class);

        intent.putStringArrayListExtra("imagesURLs", new ArrayList<String>(p.getImagesURLs()));
        intent.putExtra("videoURL", p.getVideo());
        startActivity(intent);

    }

    public static String urlEncode(String s) {
        try {
            return URLEncoder.encode(s, "UTF-8");
        }
        catch (UnsupportedEncodingException e) {
            throw new RuntimeException("URLEncoder.encode() failed for " + s);
        }
    }

    public void onShareTwitter(View view) {

        String hostBase = getString(R.string.host);

        String tweetUrl =
                String.format("https://twitter.com/intent/tweet?text=%s&url=%s",
                        urlEncode(getString(R.string.twitter_share)), urlEncode("http://"+hostBase+"/publications/"+p.getId()+"/preview"));
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(tweetUrl));

        List<ResolveInfo> matches = getPackageManager().queryIntentActivities(intent, 0);
        for (ResolveInfo info : matches) {
            if (info.activityInfo.packageName.toLowerCase().startsWith("com.twitter")) {
                intent.setPackage(info.activityInfo.packageName);
                break;
            }
        }

        startActivity(intent);
    }


    public void onShareFacebook(View view){
        if (FacebookDialog.canPresentShareDialog(getApplicationContext(),
                FacebookDialog.ShareDialogFeature.SHARE_DIALOG)) {
            String hostBase = getString(R.string.host);
            String urlToShare =  "http://"+hostBase+"/publications/"+p.getId()+"/preview";
            FacebookDialog shareDialog = new FacebookDialog.ShareDialogBuilder(this)
                    .setLink(urlToShare)
                    .setCaption("MiLEEM")
                    .setDescription(getString(R.string.facebook_share))
                    .build();
            uiHelper.trackPendingDialogCall(shareDialog.present());
        } else {
            Context context = getApplicationContext();
            CharSequence text = getString(R.string.facebook_error);
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            //publishFeedDialog();
        }
    }

    public void showPriceStats(View view){
        Intent intent = new Intent(this, PriceStatsActivity.class);
        intent.putExtra("neighbourhood_id", p.getNeighbourhood_id());
        String neighbourhood = p.getNeighbourhood_name();
        intent.putExtra("neighbourhood_name", p.getNeighbourhood_name());
        intent.putExtra("operation", p.getOperation());
        startActivity(intent);

    }

    private void publishFeedDialog() {
        String hostBase = getString(R.string.host);
        String urlToShare =  "http://"+hostBase+"/publications/"+p.getId()+"/preview";
        Bundle params = new Bundle();
        params.putString("name", getString(R.string.app_name));
        params.putString("caption", getString(R.string.app_name));
        params.putString("description", getString(R.string.facebook_share));
        params.putString("link", urlToShare);

        WebDialog feedDialog = (
                new WebDialog.FeedDialogBuilder(this,
                        Session.getActiveSession(),
                        params))
                .setOnCompleteListener(new WebDialog.OnCompleteListener() {

                    @Override
                    public void onComplete(Bundle values,
                                           FacebookException error) {

                    }

                })
                .build();
        feedDialog.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        uiHelper.onResume();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        uiHelper.onSaveInstanceState(outState);
    }

    @Override
    public void onPause() {
        super.onPause();
        uiHelper.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        uiHelper.onDestroy();
    }

}
