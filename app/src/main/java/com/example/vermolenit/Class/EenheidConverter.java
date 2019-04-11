package com.example.vermolenit.Class;

import android.arch.persistence.room.TypeConverter;

import com.example.vermolenit.Model.Aanspreking;
import com.example.vermolenit.Model.Eenheid;

public class EenheidConverter {

    @TypeConverter
    public static Eenheid toAanspreking(int value){
        return (value == 0) ? null : Eenheid.fromId(value);
    }

    @TypeConverter
    public static int toInt(Eenheid value){
        return value == null ? 0 : value.getId();
    }
}
