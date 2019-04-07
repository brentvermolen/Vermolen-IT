package com.example.vermolenit.Model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import android.support.annotation.NonNull;

import com.example.vermolenit.Class.Singletons;
import com.example.vermolenit.Class.TimeStampConverter;
import com.example.vermolenit.DB.DbVermolenIt;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity
public class Kasticket {
    @PrimaryKey
    private int id;
    @TypeConverters(TimeStampConverter.class)
    private Date datum;
    @NonNull
    private int klant_id;

    @Ignore
    private Klant klant;
    @Ignore
    private List<KasticketArtikel> kasticketArtikels;

    public Kasticket() { }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDatum() {
        return datum;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }

    public int getKlant_id() {
        return klant_id;
    }

    public void setKlant_id(int klant_id) {
        this.klant_id = klant_id;
    }

    public Klant getKlant() {
        return (klant == null) ? new Klant() : klant;
    }

    public void setKlant(Klant klant) {
        this.klant = klant;
    }

    public List<KasticketArtikel> getKasticketArtikels() {
        return (kasticketArtikels == null) ? new ArrayList<KasticketArtikel>() : kasticketArtikels;
    }

    public void setKasticketArtikels(List<KasticketArtikel> artikels) {
        this.kasticketArtikels = artikels;
    }

    public List<Artikel> getArtikels(){
        List<Artikel> artikels = new ArrayList<>();

        for (KasticketArtikel ka : getKasticketArtikels()){
            artikels.add(ka.getArtikel());
        }

        return artikels;
    }
}
