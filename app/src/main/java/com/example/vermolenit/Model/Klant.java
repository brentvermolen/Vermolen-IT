package com.example.vermolenit.Model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import com.example.vermolenit.Class.AansprekingConverter;
import com.example.vermolenit.Class.Singletons;
import com.example.vermolenit.DB.DbVermolenIt;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Klant {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @TypeConverters(AansprekingConverter.class)
    private Aanspreking aanspreking;
    private String voornaam;
    private String naam;
    private String email;
    private String tel_nr;
    private String adres;
    private int postcode;
    private String woonplaats;
    private String btw_nummer;

    public Klant() { }

    @Ignore
    private List<Kasticket> kasticketen;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Aanspreking getAanspreking() {
        return aanspreking;
    }

    public void setAanspreking(Aanspreking aanspreking) {
        this.aanspreking = aanspreking;
    }

    public String getVoornaam() {
        return voornaam;
    }

    public void setVoornaam(String voornaam) {
        this.voornaam = voornaam;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTel_nr() {
        return tel_nr;
    }

    public void setTel_nr(String tel_nr) {
        this.tel_nr = tel_nr;
    }

    public String getAdres() {
        return adres;
    }

    public void setAdres(String adres) {
        this.adres = adres;
    }

    public int getPostcode() {
        return postcode;
    }

    public void setPostcode(int postcode) {
        this.postcode = postcode;
    }

    public String getWoonplaats() {
        return woonplaats;
    }

    public void setWoonplaats(String woonplaats) {
        this.woonplaats = woonplaats;
    }

    public String getBtw_nummer() {
        return btw_nummer;
    }

    public void setBtw_nummer(String btw_nummer) {
        this.btw_nummer = btw_nummer;
    }

    public List<Kasticket> getKasticketen() {
        return (kasticketen == null) ? new ArrayList<Kasticket>() : kasticketen;
    }

    public void setKasticketen(List<Kasticket> kasticketen) {
        this.kasticketen = kasticketen;
    }

    @Override
    public String toString() {
        return naam + " " + voornaam;
    }
}
