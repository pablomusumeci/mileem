package ar.uba.fi.proyectos2.mileem.service;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import ar.uba.fi.proyectos2.mileem.R;
import ar.uba.fi.proyectos2.mileem.model.Publication;
import ar.uba.fi.proyectos2.mileem.utils.DownloadImageTask;

/**
 * Created by javier on 07/09/14.
 */
public class PublicationsResultsListAdapter extends ArrayAdapter<Publication> {
    private final Object mLock = new Object();
    private List<Publication> publications;

    public PublicationsResultsListAdapter(Context context, int resource, List<Publication> publications) {
        super(context, resource, publications);
        synchronized (mLock) {
            this.publications = publications;
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        TextView tt = null;

        synchronized (mLock) {
            Publication p = publications.get(position);
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            if (p.getPlan_priority() == 3) {
                 view = layoutInflater.inflate(R.layout.publication_search_display_free, null);
            } else if (p.getPlan_priority() == 2) {
                 view = layoutInflater.inflate(R.layout.publication_search_display_basic, null);
            } else {
                view = layoutInflater.inflate(R.layout.publication_search_display_premium, null);
            }
            try {
                tt = (TextView) view.findViewById(R.id.PublicationStreetID);
                tt.setText(p.getAddress());
                tt = (TextView) view.findViewById(R.id.PublicationOperationId);
                tt.setText(p.getOperation());
                tt = (TextView) view.findViewById(R.id.PublicationSpacesID);
                if (p.getNumber_spaces() == -1) {
                    tt.setText("");
                    tt = (TextView) view.findViewById(R.id.detail_message_ambients);
                    tt.setText("");
                } else {
                    tt.setText(Integer.toString(p.getNumber_spaces()));
                }
                tt = (TextView) view.findViewById(R.id.PublicationSurfaceId);
                if (p.getSurface() == -1) {
                    tt.setText("");
                    tt = (TextView) view.findViewById(R.id.detail_message_surface);
                    tt.setText("");
                } else {
                    tt.setText(p.getSurface() + "m2");
                }
                tt = (TextView) view.findViewById(R.id.PublicationPriceID);
                tt.setText(Integer.toString(p.getNormalized_price()) + " " + p.getNormalized_currency());
                
                tt = (TextView) view.findViewById(R.id.neighbourhood);
                tt.setText(p.getNeighbourhood_name());

                // Carga de la imagen
                if (! p.getImagesURLs().isEmpty()) {
                    ImageView iv = (ImageView) view.findViewById(R.id.image);
                    new DownloadImageTask(iv).execute(p.getImagesURLs().get(0));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return view;
    }
}