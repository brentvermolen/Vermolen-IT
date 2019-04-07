package com.example.vermolenit.Class;

import android.arch.persistence.room.TypeConverter;

import com.example.vermolenit.Model.Aanspreking;

import java.util.Date;

public class AansprekingConverter {

    @TypeConverter
    public static Aanspreking toAanspreking(int value){
        return (value == 0) ? null : Aanspreking.fromId(value);
    }

    @TypeConverter
    public static int toInt(Aanspreking value){
        return value == null ? 0 : value.getId();
    }
}
