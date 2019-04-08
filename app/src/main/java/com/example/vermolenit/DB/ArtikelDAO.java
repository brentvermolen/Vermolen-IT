package com.example.vermolenit.DB;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.vermolenit.Model.Artikel;

import java.util.List;

@Dao
public interface ArtikelDAO {
    @Query("Delete From Artikel")
    void deleteAll();

    @Query("Select * From Artikel")
    List<Artikel> getAll();

    @Query("Select * From Artikel Where id=(:id)")
    Artikel getById(int id);

    @Insert
    long insert(Artikel artikel);

    @Query("Select * From Artikel Where voorraad<=meldingOpVoorraad AND voorraad!=-1 AND meldingOpVoorraad!=-1")
    List<Artikel> getWhereVoorraadIsLow();
}
