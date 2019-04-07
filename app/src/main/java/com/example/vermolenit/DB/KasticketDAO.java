package com.example.vermolenit.DB;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.vermolenit.Model.Kasticket;
import com.example.vermolenit.Model.Klant;

import java.util.List;

@Dao
public interface KasticketDAO {
    @Query("Delete From Kasticket")
    void deleteAll();

    @Insert
    long insert(Kasticket kasticket);

    @Query("Select * From Kasticket Where klant_id=(:klant_id)")
    List<Kasticket> getByKlant(int klant_id);

    @Query("Select * From Kasticket")
    List<Kasticket> getAll();
}
