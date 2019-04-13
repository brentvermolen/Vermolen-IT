package com.example.vermolenit.Model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;

@Entity(primaryKeys = { "artikel_id", "kasticket_id" })
public class KasticketArtikel {
    private int artikel_id;
    private int kasticket_id;

    private int aantal;
    private double huidige_prijs;

    @Ignore
    private Artikel artikel;
    @Ignore
    private Kasticket kasticket;

    public KasticketArtikel() { }

    public int getArtikel_id() {
        return artikel_id;
    }

    public void setArtikel_id(int artikel_id) {
        this.artikel_id = artikel_id;
    }

    public int getKasticket_id() {
        return kasticket_id;
    }

    public void setKasticket_id(int kasticket_id) {
        this.kasticket_id = kasticket_id;
    }

    public int getAantal() {
        return aantal;
    }

    public void setAantal(int aantal) {
        this.aantal = aantal;
    }

    public double getHuidige_prijs() {
        return huidige_prijs;
    }

    public void setHuidige_prijs(double huidige_prijs) {
        this.huidige_prijs = huidige_prijs;
    }

    public Artikel getArtikel() {
        return (artikel == null) ? new Artikel() : artikel;
    }

    public void setArtikel(Artikel artikel) {
        this.artikel = artikel;
    }

    public Kasticket getKasticket() {
        return (kasticket == null) ? new Kasticket() : kasticket;
    }

    public void setKasticket(Kasticket kasticket) {
        this.kasticket = kasticket;
    }

    @Override
    public boolean equals(Object obj) {
        try{
            KasticketArtikel kasticketArtikel = (KasticketArtikel) obj;

            if (kasticketArtikel.getArtikel_id() == getArtikel_id() && kasticketArtikel.getKasticket_id() == getKasticket_id()){
                return true;
            }
        }catch (Exception e) { }

        return false;
    }
}
