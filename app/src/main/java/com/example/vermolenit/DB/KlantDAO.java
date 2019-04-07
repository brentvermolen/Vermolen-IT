package com.example.vermolenit.DB;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.vermolenit.Model.Klant;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface KlantDAO {
    @Query("Delete From Klant")
    void deleteAll();

    @Query("Select * From Klant")
    List<Klant> getAll();

    @Query("Select * From Klant Where id=(:id)")
    Klant getById(int id);

    @Insert
    long insert(Klant klant);
}
