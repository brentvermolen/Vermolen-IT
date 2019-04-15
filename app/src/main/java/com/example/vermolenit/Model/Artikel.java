package com.example.vermolenit.Model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import com.example.vermolenit.Class.EenheidConverter;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Artikel {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String omschrijving;
    private double prijs;
    @TypeConverters(EenheidConverter.class)
    private Eenheid eenheid;
    private int voorraad; //-1 is ongelimiteerd
    private int meldingOpVoorraad; //-1 is geen melding

    @Ignore
    private List<KasticketArtikel> kastickets;

    public Artikel(){ }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOmschrijving() {
        return omschrijving;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    public double getPrijs() {
        return prijs;
    }

    public void setPrijs(double prijs) {
        this.prijs = prijs;
    }

    public Eenheid getEenheid() {
        return eenheid;
    }

    public void setEenheid(Eenheid eenheid) {
        this.eenheid = eenheid;
    }

    public int getVoorraad() {
        return voorraad;
    }

    public void setVoorraad(int voorraad) {
        this.voorraad = voorraad;
    }

    public int getMeldingOpVoorraad() {
        return meldingOpVoorraad;
    }

    public void setMeldingOpVoorraad(int meldingOpVoorraad) {
        this.meldingOpVoorraad = meldingOpVoorraad;
    }

    public List<KasticketArtikel> getKastickets() {
        return (kastickets == null) ? new ArrayList<KasticketArtikel>() : kastickets;
    }

    public void setKastickets(List<KasticketArtikel> kastickets){
        this.kastickets = kastickets;
    }

    @Override
    public String toString() {
        return omschrijving + " (" + eenheid.getVolledig() + ")";
    }
}
