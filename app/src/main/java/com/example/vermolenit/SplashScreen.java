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

        doAsync();
    }

    private void doAsync() {
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

            Artikel artikel3 = new Artikel();
            artikel3.setOmschrijving("Chromecast");
            artikel3.setPrijs(39);
            artikel3.setEenheid(Eenheid.Stuk);
            artikel3.setVoorraad(0);
            artikel3.setMeldingOpVoorraad(-1);
            long artikel_id3 = artikelDAO.insert(artikel3);

            Artikel artikel2 = new Artikel();
            artikel2.setOmschrijving("USB-Stick (3.0 - 16Gb)");
            artikel2.setPrijs(7.49);
            artikel2.setEenheid(Eenheid.Stuk);
            artikel2.setVoorraad(0);
            artikel2.setMeldingOpVoorraad(-1);
            long artikel_id2 = artikelDAO.insert(artikel2);

            Artikel artikel4 = new Artikel();
            artikel4.setOmschrijving("USB-Stick (3.0 - 32Gb)");
            artikel4.setPrijs(11.49);
            artikel4.setEenheid(Eenheid.Stuk);
            artikel4.setVoorraad(0);
            artikel4.setMeldingOpVoorraad(-1);
            artikelDAO.insert(artikel4);

            Artikel artikel5 = new Artikel();
            artikel5.setOmschrijving("USB-Stick (3.0 - 64Gb)");
            artikel5.setPrijs(18.49);
            artikel5.setEenheid(Eenheid.Stuk);
            artikel5.setVoorraad(0);
            artikel5.setMeldingOpVoorraad(-1);
            artikelDAO.insert(artikel5);

            Artikel artikel6 = new Artikel();
            artikel6.setOmschrijving("UTP Kabel (0,25m)");
            artikel6.setPrijs(1.05);
            artikel6.setEenheid(Eenheid.Stuk);
            artikel6.setVoorraad(0);
            artikel6.setMeldingOpVoorraad(-1);
            artikelDAO.insert(artikel6);

            Artikel artikel7 = new Artikel();
            artikel7.setOmschrijving("UTP Kabel (0,50m)");
            artikel7.setPrijs(1.35);
            artikel7.setEenheid(Eenheid.Stuk);
            artikel7.setVoorraad(0);
            artikel7.setMeldingOpVoorraad(-1);
            artikelDAO.insert(artikel7);

            Artikel artikel8 = new Artikel();
            artikel8.setOmschrijving("UTP Kabel (10m)");
            artikel8.setPrijs(6.95);
            artikel8.setEenheid(Eenheid.Stuk);
            artikel8.setVoorraad(0);
            artikel8.setMeldingOpVoorraad(-1);
            artikelDAO.insert(artikel8);

            Artikel artikel9 = new Artikel();
            artikel9.setOmschrijving("UTP Kabel (3m)");
            artikel9.setPrijs(1.85);
            artikel9.setEenheid(Eenheid.Stuk);
            artikel9.setVoorraad(0);
            artikel9.setMeldingOpVoorraad(-1);
            artikelDAO.insert(artikel9);

            Artikel artikel10 = new Artikel();
            artikel10.setOmschrijving("UTP Kabel (5m)");
            artikel10.setPrijs(3.15);
            artikel10.setEenheid(Eenheid.Stuk);
            artikel10.setVoorraad(0);
            artikel10.setMeldingOpVoorraad(-1);
            artikelDAO.insert(artikel10);

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

            Klant klant2 = new Klant();
            klant2.setAanspreking(Aanspreking.Mevrouw);
            klant2.setNaam("Vermolen");
            klant2.setVoornaam("Febe");
            klant2.setEmail("febevermolen@hotmail.com");
            klant2.setTel_nr("0476798346");
            klant2.setWoonplaats("Merksplas");
            klant2.setPostcode(2330);
            klant2.setAdres("Consciencestraat 8");
            klantDAO.insert(klant2);

            Klant klant3 = new Klant();
            klant3.setAanspreking(Aanspreking.Meneer);
            klant3.setNaam("Vermolen");
            klant3.setVoornaam("Robbe");
            klant3.setEmail("robbe.vermolen@hotmail.com");
            klant3.setTel_nr("0495807401");
            klant3.setWoonplaats("Merksplas");
            klant3.setPostcode(2330);
            klant3.setAdres("Consciencestraat 8");
            klantDAO.insert(klant3);
        }catch (Exception e){ }
    }
}
