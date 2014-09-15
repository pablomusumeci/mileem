package ar.uba.fi.proyectos2.mileem.service;

import android.content.Context;
import android.graphics.Color;
import android.os.Environment;
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
                tt = (TextView) v.findViewById(R.id.PublicationStreetID);
                tt.setText(p.getAddress());
                tt = (TextView) v.findViewById(R.id.PublicationOperationId);
                tt.setText(p.getOperation());
                tt = (TextView) v.findViewById(R.id.PublicationSpacesID);
                if (p.getNumber_spaces() == -1) {
                    tt.setText("");
                    tt = (TextView) v.findViewById(R.id.detail_message_ambients);
                    tt.setText("");
                } else {
                    tt.setText(Integer.toString(p.getNumber_spaces()));
                }
                tt = (TextView) v.findViewById(R.id.PublicationSurfaceId);
                if (p.getSurface() == -1) {
                    tt.setText("");
                    tt = (TextView) v.findViewById(R.id.detail_message_surface);
                    tt.setText("");
                } else {
                    tt.setText(p.getSurface() + "m2");
                }
                tt = (TextView) v.findViewById(R.id.PublicationPriceID);
                tt.setText( Integer.toString(p.getPrice()) + " " + p.getCurrency_symbol());
                if (position % 2 != 1)
                    v.setBackgroundColor(Color.parseColor("#D7E7FF"));
                else
                    v.setBackgroundColor(Color.parseColor("#F0F0F0"));
                tt = (TextView) v.findViewById(R.id.neighbourhood);
                tt.setText(p.getNeighbourhood_name());

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return v;
    }
}