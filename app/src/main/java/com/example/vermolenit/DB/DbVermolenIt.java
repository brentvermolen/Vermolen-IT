package com.example.vermolenit.DB;


import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;

import com.example.vermolenit.Model.Aanspreking;
import com.example.vermolenit.Model.Artikel;
import com.example.vermolenit.Model.Kasticket;
import com.example.vermolenit.Model.KasticketArtikel;
import com.example.vermolenit.Model.Klant;

import java.util.Calendar;
import java.util.concurrent.Executors;

@Database(entities =  {Klant.class, Artikel.class, Kasticket.class, KasticketArtikel.class}, version = 1)
public abstract class DbVermolenIt extends RoomDatabase {
    private static DbVermolenIt INSTANCE;
    private static final String DB_NAME = "Vermolen_IT.db";

    public abstract KlantDAO klantDAO();
    public abstract KasticketArtikelDAO kasticketArtikelDAO();
    public abstract ArtikelDAO artikelDAO();
    public abstract KasticketDAO kasticketDAO();

    public static DbVermolenIt getDatabase (final Context context){
        if (INSTANCE == null) {
            synchronized (DbVermolenIt.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            DbVermolenIt.class, DB_NAME)
                            .allowMainThreadQueries() // SHOULD NOT BE USED IN PRODUCTION !!!
                            .addCallback(new Callback() {
                                @Override
                                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                                    super.onCreate(db);
                                    Executors.newSingleThreadScheduledExecutor().execute(new Runnable() {
                                        @Override
                                        public void run() {
                                            new EmptyAndFillDbAsync(INSTANCE).insertData();
                                        }
                                    });

                                }
                            })
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }

        return INSTANCE;
    }

    /*public void clearDb(){
        if (INSTANCE != null){
            new EmptyAndFillDbAsync(INSTANCE).execute();
        }
    }*/

    private static class EmptyAndFillDbAsync {
        private final KlantDAO klantDAO;
        private final KasticketArtikelDAO kasticketArtikelDAO;
        private final ArtikelDAO artikelDAO;
        private final KasticketDAO kasticketDAO;

        public EmptyAndFillDbAsync(DbVermolenIt instance) {
            klantDAO = instance.klantDAO();
            kasticketArtikelDAO = instance.kasticketArtikelDAO();
            artikelDAO = instance.artikelDAO();
            kasticketDAO = instance.kasticketDAO();
        }

        protected Void insertData() {
            klantDAO.deleteAll();
            kasticketArtikelDAO.deleteAll();
            artikelDAO.deleteAll();
            kasticketDAO.deleteAll();

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

            return null;
        }
    }
}