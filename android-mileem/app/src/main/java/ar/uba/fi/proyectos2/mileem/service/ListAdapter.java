package ar.uba.fi.proyectos2.mileem.service;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import ar.uba.fi.proyectos2.mileem.model.Publication;
import ar.uba.fi.proyectos2.mileem.R;

/**
 * Created by javier on 07/09/14.
 */
public class ListAdapter extends ArrayAdapter<Publication> {
    private List<Publication> publications;
    public ListAdapter(Context context, int resource, List<Publication> publications) {
        super(context, resource, publications);
        this.publications = publications;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        TextView tt = null;
        Publication p = publications.get(position);

        try {
            if (v == null) {
                LayoutInflater vi = LayoutInflater.from(getContext());
                v = vi.inflate(R.layout.publication_search_display, null);
                tt = (TextView) v.findViewById(R.id.PublicationNameID);
                tt.setText(p.getDescription());
                tt = (TextView) v.findViewById(R.id.PublicationStreetID);
                tt.setText(p.getAddress());
                tt = (TextView) v.findViewById(R.id.PublicationPriceID);
                tt.setText(Integer.toString(p.getPrice()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return v;
    }
}