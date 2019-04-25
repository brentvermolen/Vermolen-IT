package com.example.vermolenit.DB;

import android.util.Log;

import com.example.vermolenit.Model.Artikel;
import com.example.vermolenit.Model.Eenheid;
import com.example.vermolenit.Model.KasticketArtikel;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class RemoteKasticketArtikelDAO {
    private static DbVermolenItRemote dbVermolenItRemote = new DbVermolenItRemote();

    public static List<KasticketArtikel> getAll(){
        Connection con = null;
        List<KasticketArtikel> data = new ArrayList<>();
        String z = "";

        try {
            con = dbVermolenItRemote.CONN();

            if (con == null) {
                z = "Error in connection with SQL server";
            } else {
                String query = "Select * From vit_kasticket_artikel";
                Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                ResultSet rs = stmt.executeQuery(query);

                while (rs.next()){
                    KasticketArtikel artikel = new KasticketArtikel();
                    artikel.setArtikel_id(rs.getInt(1));
                    artikel.setKasticket_id(rs.getInt(2));
                    artikel.setAantal(rs.getInt(3));
                    artikel.setHuidige_prijs(rs.getDouble(4));

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

    public static void insert(KasticketArtikel kasticketArtikel){
        Connection con = null;
        List<Artikel> data = new ArrayList<>();
        String z = "";

        try {
            con = dbVermolenItRemote.CONN();

            if (con == null) {
                z = "Error in connection with SQL server";
            } else {
                String query = "Insert Into vit_kasticket_artikel Values(" +
                        kasticketArtikel.getArtikel_id() + ", " +
                        kasticketArtikel.getKasticket_id() + ", " +
                        kasticketArtikel.getAantal() + ", " +
                        kasticketArtikel.getHuidige_prijs() + ")";
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

    public static void update(KasticketArtikel kasticketArtikel){
        Connection con = null;
        String z = "";

        try {
            con = dbVermolenItRemote.CONN();

            if (con == null) {
                z = "Error in connection with SQL server";
            } else {
                String query = "Update vit_kasticket_artikel Set Aantal=" +
                        kasticketArtikel.getAantal() + ", Huidige_Prijs=" +
                        kasticketArtikel.getHuidige_prijs() + " Where KasticketID=" + kasticketArtikel.getKasticket_id() + " AND ArtikelID=" + kasticketArtikel.getArtikel_id();
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

    public static void deleteByKasticket(int kasticket_id){
        Connection con = null;
        String z = "";

        try {
            con = dbVermolenItRemote.CONN();

            if (con == null) {
                z = "Error in connection with SQL server";
            } else {
                String query = "Delete From vit_kasticket_artikel Where KasticketID=" + kasticket_id;
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
