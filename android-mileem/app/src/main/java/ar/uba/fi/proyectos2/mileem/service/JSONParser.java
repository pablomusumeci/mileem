
package ar.uba.fi.proyectos2.mileem.service;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

/**
 * Created by javier on 07/09/14.
 */
public class JSONParser {

    public static JSONArray getJSONAsArray(String url) {

        InputStream is = null;
        JSONArray jObj = null;
        String json = "";
        // Making HTTP request
/*        try {
            // defaultHttpClient
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);

            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            is = httpEntity.getContent();

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    is, "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            is.close();
            json = sb.toString();
        } catch (Exception e) {
            Log.e("Buffer Error", "Error converting result " + e.toString());
        }*/
        json = "[{\"id\":1,\"effective_date\":\"2014-09-25\",\"operation\":\"Alquiler\",\"address\":\"Avenida Cordoba, Buenos Aires, Argentina\",\"floor\":1,\"apartment\":\"1\",\"number_spaces\":1,\"surface\":1,\"price\":1.0,\"expenses\":1.0,\"antiquity\":1,\"description\":\"\",\"additional_info\":\"\",\"url\":\"http://localhost:3000/publications/1.json\"},{\"id\":2,\"effective_date\":\"2014-09-13\",\"operation\":\"Alquiler\",\"address\":\"Adolfo Alsina, Villa Martelli, Buenos Aires Province, Argentina\",\"floor\":1,\"apartment\":\"\",\"number_spaces\":1,\"surface\":1,\"price\":1.0,\"expenses\":1.0,\"antiquity\":1,\"description\":\"\",\"additional_info\":\"\",\"url\":\"http://localhost:3000/publications/2.json\"},{\"id\":3,\"effective_date\":\"2014-09-25\",\"operation\":\"Alquiler\",\"address\":\"Adolfo Alsina, Buenos Aires, Argentina\",\"floor\":2,\"apartment\":\"\",\"number_spaces\":3,\"surface\":45,\"price\":1000.0,\"expenses\":350,\"antiquity\":1,\"description\":\"\",\"additional_info\":\"\",\"url\":\"http://localhost:3000/publications/3.json\"}]";

        // try parse the string to a JSON object
        try {
            JSONArray obj = new JSONArray(json);
            // Extraigo la lista de publicaciones, bajo la clave 'propiedades'
            return obj;
        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }

        // return JSON String
        return jObj;

    }
}
