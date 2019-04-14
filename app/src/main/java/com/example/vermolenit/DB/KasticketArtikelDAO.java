package com.example.vermolenit.DB;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.vermolenit.Model.Artikel;
import com.example.vermolenit.Model.Kasticket;
import com.example.vermolenit.Model.KasticketArtikel;
import com.example.vermolenit.Model.Klant;

import java.util.List;

@Dao
public interface KasticketArtikelDAO {
    @Query("Delete From KasticketArtikel")
    void deleteAll();

    @Query("Select * From Artikel Inner Join KasticketArtikel On id=artikel_id Where kasticket_id=(:kasticket_id)")
    List<Artikel> getArtikelsByKasticket(int kasticket_id);

    @Query("Select * From Kasticket Inner Join KasticketArtikel On id=kasticket_id Where artikel_id=(:artikel_id)")
    List<Kasticket> getKasticketsByArtikel(int artikel_id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(KasticketArtikel kasticketArtikel);

    @Query("Delete From KasticketArtikel Where kasticket_id=(:kasticket_id)")
    void deleteByKasticket(int kasticket_id);

    @Query("Select * From KasticketArtikel")
    List<KasticketArtikel> getAll();
}
