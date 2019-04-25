package com.example.vermolenit;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.vermolenit.Class.DAC;
import com.example.vermolenit.Class.Methodes;
import com.example.vermolenit.DB.RemoteArtikelDAO;
import com.example.vermolenit.DB.RemoteKasticketArtikelDAO;
import com.example.vermolenit.DB.RemoteKasticketsDAO;
import com.example.vermolenit.DB.RemoteKlantDAO;
import com.example.vermolenit.Model.Aanspreking;
import com.example.vermolenit.Model.Artikel;
import com.example.vermolenit.Model.Eenheid;
import com.example.vermolenit.Model.Kasticket;
import com.example.vermolenit.Model.KasticketArtikel;
import com.example.vermolenit.Model.Klant;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        doAsync();
    }

    private void doAsync() {
        new AsyncTask<Void, Void, Void>(){
            @Override
            protected Void doInBackground(Void... voids) {
                DAC.Artikels = RemoteArtikelDAO.getAll();
                DAC.KasticketArtikels = RemoteKasticketArtikelDAO.getAll();
                DAC.Kastickets = RemoteKasticketsDAO.getAll();
                DAC.Klanten = RemoteKlantDAO.getAll();

                for (Klant klant : DAC.Klanten){
                    klant.setKasticketen(Methodes.getKasticketByKlant(DAC.Kastickets, klant));
                }

                for (Kasticket kasticket : DAC.Kastickets){
                    kasticket.setKlant(Methodes.getKlantById(DAC.Klanten, kasticket.getKlant_id()));
                }

                List<KasticketArtikel> lstAf;
                for (KasticketArtikel ka : DAC.KasticketArtikels){
                    Kasticket kasticket = Methodes.getKasticketById(DAC.Kastickets, ka.getKasticket_id());
                    Artikel artikel = Methodes.getArtikelById(DAC.Artikels, ka.getArtikel_id());

                    try {
                        ka.setKasticket(kasticket);
                        ka.setArtikel(artikel);

                        lstAf = kasticket.getKasticketArtikels();
                        lstAf.add(ka);
                        kasticket.setKasticketArtikels(lstAf);
                        lstAf = artikel.getKastickets();
                        lstAf.add(ka);
                        artikel.setKastickets(lstAf);
                    }catch (Exception e){ e.printStackTrace(); }
                }


                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                Intent intent = new Intent(SplashScreen.this, HomeActivity.class);
                startActivity(intent);
            }
        }.execute();
    }
}
