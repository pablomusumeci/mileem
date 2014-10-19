package ar.uba.fi.proyectos2.mileem.detail;

import android.app.Activity;
import android.app.ProgressDialog;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.VideoView;

import ar.uba.fi.proyectos2.mileem.R;

public class VideoPageFragment extends Fragment {

    private static final String URL_KEY = "URL";
    private VideoView videoView;
    private ProgressDialog pDialog;
    private String url;

    public static VideoPageFragment create(String url) {
        VideoPageFragment fragment = new VideoPageFragment();
        Bundle args = new Bundle();
        args.putString(URL_KEY, url);
        fragment.setArguments(args);
        return fragment;
    }
    public VideoPageFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            url = getArguments().getString(URL_KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = (View) inflater.inflate(R.layout.fragment_video_page, container, false);
        videoView = (VideoView) rootView.findViewById(R.id.videoView);

        /*videoView.setVideoPath(url);
        MediaController mc = new MediaController(getActivity());
        mc.setMediaPlayer(videoView);
        videoView.setMediaController(mc);
        videoView.start();
        videoView.requestFocus();
        */
        new StreamVideo().execute();
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }



    // StreamVideo AsyncTask
    private class StreamVideo extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressbar
            pDialog = new ProgressDialog(getActivity());
            // Set progressbar title
            pDialog.setTitle("Video");
            // Set progressbar message
            pDialog.setMessage("Cargando...");
            pDialog.setIndeterminate(false);
            // Show progressbar
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... params) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        protected void onPostExecute(Void args) {

            try {
                // Start the MediaController
                MediaController mediacontroller = new MediaController(getActivity());
                mediacontroller.setAnchorView(videoView);
                // Get the URL from String VideoURL
                Uri video = Uri.parse(url);
                videoView.setMediaController(mediacontroller);
                videoView.setVideoURI(video);

                videoView.requestFocus();
                videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    // Close the progress bar and play the video
                    public void onPrepared(MediaPlayer mp) {
                        pDialog.dismiss();
                        videoView.start();
                    }
                });
            } catch (Exception e) {
                pDialog.dismiss();

                // Error Here

                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }

        }

    }


}
