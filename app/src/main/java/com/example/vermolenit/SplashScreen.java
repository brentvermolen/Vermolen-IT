package com.example.vermolenit;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.vermolenit.Class.DAC;
import com.example.vermolenit.Class.Methodes;
import com.example.vermolenit.DB.ArtikelDAO;
import com.example.vermolenit.DB.DbVermolenIt;
import com.example.vermolenit.DB.KasticketArtikelDAO;
import com.example.vermolenit.DB.KasticketDAO;
import com.example.vermolenit.DB.KlantDAO;
import com.example.vermolenit.Model.Aanspreking;
import com.example.vermolenit.Model.Artikel;
import com.example.vermolenit.Model.Kasticket;
import com.example.vermolenit.Model.KasticketArtikel;
import com.example.vermolenit.Model.Klant;

import java.security.spec.ECField;
import java.util.Calendar;
import java.util.List;

public class SplashScreen extends AppCompatActivity {

    private DbVermolenIt database;

    private KlantDAO klantDAO;
    private KasticketArtikelDAO kasticketArtikelDAO;
    private ArtikelDAO artikelDAO;
    private KasticketDAO kasticketDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        database = DbVermolenIt.getDatabase(this);

        //insertDbData();

        new AsyncTask<Void, Void, Void>(){
            @Override
            protected Void doInBackground(Void... voids) {
                DAC.Klanten = database.klantDAO().getAll();
                DAC.Artikels = database.artikelDAO().getAll();
                DAC.KasticketArtikels = database.kasticketArtikelDAO().getAll();
                DAC.Kastickets = database.kasticketDAO().getAll();

                for (Klant klant : DAC.Klanten){
                    klant.setKasticketen(Methodes.getKasticketByKlant(DAC.Kastickets, klant));
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

    private void insertDbData() {
        klantDAO = database.klantDAO();
        kasticketArtikelDAO = database.kasticketArtikelDAO();
        artikelDAO = database.artikelDAO();
        kasticketDAO = database.kasticketDAO();

        try{
            Klant klant = new Klant();
            klant.setVoornaam("Brent");
            klant.setNaam("Vermolen");
            klant.setAanspreking(Aanspreking.Meneer);
            long klant_id = klantDAO.insert(klant);

            Artikel artikel = new Artikel();
            artikel.setOmschrijving("Advies aan huis");
            artikel.setBedrag(15);
            long artikel_id = artikelDAO.insert(artikel);

            Kasticket kasticket = new Kasticket();
            kasticket.setKlant_id((int)klant_id);
            kasticket.setDatum(Calendar.getInstance().getTime());
            long kasticket_id = kasticketDAO.insert(kasticket);

            KasticketArtikel kasticketArtikel = new KasticketArtikel();
            kasticketArtikel.setArtikel_id((int)artikel_id);
            kasticketArtikel.setKasticket_id((int)kasticket_id);
            kasticketArtikel.setHuidige_prijs(15);
            kasticketArtikel.setAantal(4);
            kasticketArtikelDAO.insert(kasticketArtikel);
        }catch (Exception e){ }
    }
}
