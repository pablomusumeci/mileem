package ar.uba.fi.proyectos2.mileem.detail;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.VideoView;

import ar.uba.fi.proyectos2.mileem.R;

public class VideoPageFragment extends Fragment {

    private static final String URL_KEY = "URL";

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
        VideoView videoView = (VideoView) rootView.findViewById(R.id.videoView);
        videoView.setVideoPath(url);
        videoView.start();
        videoView.requestFocus();
        videoView.setMediaController(new MediaController(getActivity()));
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


}
