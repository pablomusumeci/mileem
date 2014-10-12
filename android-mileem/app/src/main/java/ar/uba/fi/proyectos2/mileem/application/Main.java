package ar.uba.fi.proyectos2.mileem.application;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import ar.uba.fi.proyectos2.mileem.search.SearchPublicationsActivity;

public class Main extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Redirecciona a las busquedas
        Intent intent = new Intent(this, SearchPublicationsActivity.class);
        startActivity(intent);
    }


}
