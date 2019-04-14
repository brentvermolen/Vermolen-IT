package com.example.vermolenit.DB;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.vermolenit.Model.Kasticket;

import java.util.List;

@Dao
public interface KasticketDAO {
    @Query("Delete From Kasticket")
    void deleteAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Kasticket kasticket);

    @Query("Select * From Kasticket Where klant_id=(:klant_id)")
    List<Kasticket> getByKlant(int klant_id);

    @Query("Select * From Kasticket")
    List<Kasticket> getAll();

    @Query("Delete From Kasticket Where id=(:id)")
    void delete(int id);
}
