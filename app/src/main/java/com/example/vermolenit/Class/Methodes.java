package com.example.vermolenit.Class;

import com.example.vermolenit.Model.Artikel;
import com.example.vermolenit.Model.Kasticket;
import com.example.vermolenit.Model.Klant;

import java.util.ArrayList;
import java.util.List;

public class Methodes {
    public static List<Kasticket> getKasticketByKlant(List<Kasticket> kastickets, Klant klant){
        List<Kasticket> kasticketen = new ArrayList<>();

        for (Kasticket k : kastickets){
            if (k.getKlant_id() == klant.getId()){
                kasticketen.add(k);
                k.setKlant(klant);
            }
        }

        return kasticketen;
    }

    public static Artikel getArtikelById(List<Artikel> artikels, int artikel_id) {
        for (Artikel artikel : artikels){
            if (artikel.getId() == artikel_id){
                return artikel;
            }
        }

        return null;
    }

    public static Kasticket getKasticketById(List<Kasticket> kastickets, int kasticket_id) {
        for (Kasticket kasticket : kastickets) {
            if (kasticket.getId() == kasticket_id){
                return kasticket;
            }
        }

        return null;
    }
}
