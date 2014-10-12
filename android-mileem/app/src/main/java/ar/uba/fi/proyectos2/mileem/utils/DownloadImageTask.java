package ar.uba.fi.proyectos2.mileem.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;

import ar.uba.fi.proyectos2.mileem.R;

/**
 * Created by javi on 11/10/14.
 */
public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
    private ImageView imageViewTarget;
    private boolean mapLoaded;

    public DownloadImageTask(ImageView bmImage) {
        this.imageViewTarget = bmImage;
        this.mapLoaded = true;
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
            mIcon11 = BitmapFactory.decodeResource(imageViewTarget.getResources(), R.drawable.nointernet);
            mapLoaded = false;
        }
        return mIcon11;
    }

    public boolean getMapLoaded(){
        return this.mapLoaded;
    }

    protected void onPostExecute(Bitmap result) {
        imageViewTarget.setImageBitmap(result);
    }
}