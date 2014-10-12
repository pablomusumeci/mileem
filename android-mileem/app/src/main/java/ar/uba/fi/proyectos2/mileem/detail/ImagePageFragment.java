package ar.uba.fi.proyectos2.mileem.detail;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import ar.uba.fi.proyectos2.mileem.R;
import ar.uba.fi.proyectos2.mileem.utils.DownloadImageTask;


public class ImagePageFragment extends Fragment {

    public static final String URL_KEY = "url";

    private String url;

    /**
     * Factory method for this fragment class. Constructs a new fragment for the given page number.
     */
    public static ImagePageFragment create(String url) {
        ImagePageFragment fragment = new ImagePageFragment();
        Bundle args = new Bundle();
        args.putString(URL_KEY, url);
        fragment.setArguments(args);
        return fragment;
    }

    public ImagePageFragment() {
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
        View rootView = (View) inflater
                .inflate(R.layout.fragment_image_page, container, false);
        ImageView iv = (ImageView) rootView.findViewById(R.id.imageViewGallery);

        new DownloadImageTask(iv).execute(url);

        return rootView;
    }




}
