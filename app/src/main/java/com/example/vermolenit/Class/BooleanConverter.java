package com.example.vermolenit.Class;

import android.arch.persistence.room.TypeConverter;

import java.util.Date;

public class BooleanConverter {
    @TypeConverter
    public static boolean toBool(int value){
        return value == 0 ? false : true;
    }

    @TypeConverter
    public static int toInt(boolean value){
        return value == false ? 0 : 1;
    }
}
