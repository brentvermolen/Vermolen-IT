package com.example.vermolenit.DB;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import android.util.Log;

import com.example.vermolenit.Model.Artikel;
import com.example.vermolenit.Model.Eenheid;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class RemoteArtikelDAO {
    private static DbVermolenItRemote dbVermolenItRemote = new DbVermolenItRemote();

    public static List<Artikel> getAll(){
        Connection con = null;
        List<Artikel> data = new ArrayList<>();
        String z = "";

        try {
            con = dbVermolenItRemote.CONN();

            if (con == null) {
                z = "Error in connection with SQL server";
            } else {
                String query = "Select * From vit_artikels";
                Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                ResultSet rs = stmt.executeQuery(query);

                while (rs.next()){
                    Artikel artikel = new Artikel();
                    artikel.setId(rs.getInt(1));
                    artikel.setOmschrijving(rs.getString(2));
                    artikel.setPrijs(rs.getDouble(3));
                    artikel.setEenheid(Eenheid.fromId(rs.getInt(4)));
                    artikel.setVoorraad(rs.getInt(5));
                    artikel.setMeldingOpVoorraad(rs.getInt(6));

                    data.add(artikel);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            z = "Exception: " + ex.toString();
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
                z = "Error in closing: " + e.toString();
            }
        }

        Log.e("DB Conn", z);
        return data;
    }

    public static long insert(Artikel artikel){
        Connection con = null;
        List<Artikel> data = new ArrayList<>();
        String z = "";

        try {
            con = dbVermolenItRemote.CONN();

            if (con == null) {
                z = "Error in connection with SQL server";
            } else {
                String query = "Select Max(id) From vit_artikels";
                Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                ResultSet rs = stmt.executeQuery(query);
                int id = 0;

                while (rs.next()){
                    id = rs.getInt(1) + 1;
                }

                artikel.setId(id);

                query = "Insert Into vit_artikels values(" +
                        artikel.getId() + ", '" +
                        artikel.getOmschrijving() + "', " +
                        artikel.getPrijs() + ", " +
                        artikel.getEenheid().getId() + ", " +
                        artikel.getVoorraad() + ", " +
                        artikel.getMeldingOpVoorraad() + ")";
                stmt.execute(query);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            z = "Exception: " + ex.toString();
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
                z = "Error in closing: " + e.toString();
            }
        }

        Log.e("DB Conn", z);
        return artikel.getId();
    }

    public static void update(Artikel artikel){
        Connection con = null;
        String z = "";

        try {
            con = dbVermolenItRemote.CONN();

            if (con == null) {
                z = "Error in connection with SQL server";
            } else {
                String query = "Update vit_artikels set " +
                        "Omschrijving='" + artikel.getOmschrijving() + "', " +
                        "Prijs=" + artikel.getPrijs() + ", " +
                        "Eenheid=" + artikel.getEenheid().getId() + ", " +
                        "Voorraad=" + artikel.getVoorraad() + ", " +
                        "MeldingOpVoorraad=" + artikel.getMeldingOpVoorraad() + " Where id=" + artikel.getId();
                Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                stmt.executeUpdate(query);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            z = "Exception: " + ex.toString();
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
                z = "Error in closing: " + e.toString();
            }
        }

        Log.e("DB Conn", z);
    }

    public static void delete(int id){
        Connection con = null;
        String z = "";

        try {
            con = dbVermolenItRemote.CONN();

            if (con == null) {
                z = "Error in connection with SQL server";
            } else {
                String query = "Delete From vit_artikels Where id=" + id;
                Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                stmt.execute(query);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            z = "Exception: " + ex.toString();
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
                z = "Error in closing: " + e.toString();
            }
        }

        Log.e("DB Conn", z);
    }
}
