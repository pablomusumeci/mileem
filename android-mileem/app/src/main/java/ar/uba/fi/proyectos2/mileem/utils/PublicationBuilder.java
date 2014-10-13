package ar.uba.fi.proyectos2.mileem.utils;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ar.uba.fi.proyectos2.mileem.R;
import ar.uba.fi.proyectos2.mileem.model.Publication;

/**
 * Created by javi on 11/10/14.
 */
public class PublicationBuilder {

    public static Publication buildFromJSON(JSONObject obj, Context context){
        Publication p = new Publication();
        try {
            p.setId(obj.getInt("id"));
            p.setEffective_date(obj.getString("effective_date"));
            p.setOperation(obj.getString("operation"));
            p.setAddress(obj.getString("address"));
            try {
                p.setFloor(obj.getInt("floor"));
            } catch (JSONException e) {
                p.setFloor(-1);
            }
            try {
                p.setApartment(obj.getString("apartment"));
            } catch (JSONException e) {
                p.setApartment("");
            }

            try {
                p.setNumber_spaces(obj.getInt("number_spaces"));
            } catch (JSONException e) {
                p.setNumber_spaces(-1);
            }
            try {
                p.setSurface(obj.getInt("surface"));
            } catch (JSONException e) {
                p.setSurface(-1);
            }
            p.setPrice(obj.getInt("price"));
            try {
                p.setExpenses(obj.getInt("expenses"));
            } catch (JSONException e) {
                p.setExpenses(-1);
            }
            try {
                p.setAntiquity(obj.getInt("antiquity"));
            } catch (JSONException e) {
                p.setAntiquity(-1);
            }
            try {
                p.setProperty_name(obj.getString("property_type"));
            } catch (JSONException e) {
                p.setProperty_name("");
            }

            try {
                p.setUserPhoneNumber(obj.getString("user_phone_number"));
            } catch (JSONException e) {
                p.setUserPhoneNumber("");
            }

            try {
                p.setUserEmail(obj.getString("user_email"));
            } catch (JSONException e) {
                p.setUserEmail("");
            }

            try {
                p.setDescription(obj.getString("description"));
            } catch (JSONException e) {
                p.setDescription("");
            }

            try {
                p.setLatitude(obj.getDouble("lat"));
            } catch (JSONException e) {
                p.setLatitude(-1);
            }

            try {
                p.setLongitude(obj.getDouble("lng"));
            } catch (JSONException e) {
                p.setLongitude(-1);
            }

            p.setNormalized_currency(obj.getString("normalized_currency"));
            p.setNormalized_price(obj.getInt("normalized_price"));
            p.setAdditional_info(obj.getString("additional_info"));
            p.setCurrency_name(obj.getString("currency_name"));
            p.setNeighbourhood_name(obj.getString("neighbourhood_name"));
            p.setCurrency_symbol(obj.getString("currency_symbol"));

            JSONArray images = obj.getJSONArray("images");
            int len = images.length();
            for (int i = 0; i < len; ++i) {
                String url = images.getString(i);
                // Esto no es una negrada, diria Rene Magritte
                p.getImagesURLs().add("http://" + context.getString(R.string.host) + url);
            }
            p.setVideo(obj.getString("video"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return p;
    }
}
