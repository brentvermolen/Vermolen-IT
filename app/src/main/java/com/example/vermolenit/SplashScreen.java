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
import com.example.vermolenit.Model.Eenheid;
import com.example.vermolenit.Model.Kasticket;
import com.example.vermolenit.Model.KasticketArtikel;
import com.example.vermolenit.Model.Klant;

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

    private void insertDbData() {
        klantDAO = database.klantDAO();
        kasticketArtikelDAO = database.kasticketArtikelDAO();
        artikelDAO = database.artikelDAO();
        kasticketDAO = database.kasticketDAO();

        try{
            Artikel artikel = new Artikel();
            artikel.setOmschrijving("Advies aan huis");
            artikel.setPrijs(8);
            artikel.setEenheid(Eenheid.Kwart);
            artikel.setVoorraad(-1);
            artikel.setMeldingOpVoorraad(-1);
            long artikel_id = artikelDAO.insert(artikel);

            Artikel artikel2 = new Artikel();
            artikel2.setOmschrijving("USB-Stick (8Gb)");
            artikel2.setPrijs(8);
            artikel2.setEenheid(Eenheid.Stuk);
            artikel2.setVoorraad(0);
            artikel2.setMeldingOpVoorraad(-1);
            long artikel_id2 = artikelDAO.insert(artikel2);

            Artikel artikel3 = new Artikel();
            artikel3.setOmschrijving("Chromecast");
            artikel3.setPrijs(39);
            artikel3.setEenheid(Eenheid.Stuk);
            artikel3.setVoorraad(0);
            artikel3.setMeldingOpVoorraad(-1);
            long artikel_id3 = artikelDAO.insert(artikel3);

            Klant klant = new Klant();
            klant.setAanspreking(Aanspreking.Mevrouw);
            klant.setNaam("Colson");
            klant.setVoornaam("Lieze");
            klant.setEmail("lieze_colson@hotmail.com");
            klant.setTel_nr("0476798346");
            klant.setWoonplaats("Vorselaar");
            klant.setPostcode(2390);
            klant.setAdres("Kweek 7");
            klantDAO.insert(klant);
        }catch (Exception e){ }
    }
}
