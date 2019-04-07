package com.example.vermolenit.Model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.content.Context;

import com.example.vermolenit.Class.Singletons;
import com.example.vermolenit.DB.DbVermolenIt;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Artikel {
    @PrimaryKey
    private int id;
    private String omschrijving;
    private double bedrag;

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

    public double getBedrag() {
        return bedrag;
    }

    public void setBedrag(double bedrag) {
        this.bedrag = bedrag;
    }

    public List<KasticketArtikel> getKastickets() {
        return (kastickets == null) ? new ArrayList<KasticketArtikel>() : kastickets;
    }

    public void setKastickets(List<KasticketArtikel> kastickets){
        this.kastickets = kastickets;
    }

    @Override
    public String toString() {
        return omschrijving;
    }
}
