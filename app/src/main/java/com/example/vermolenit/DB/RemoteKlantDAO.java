package com.example.vermolenit.DB;

import android.util.Log;

import com.example.vermolenit.Model.Aanspreking;
import com.example.vermolenit.Model.Artikel;
import com.example.vermolenit.Model.Eenheid;
import com.example.vermolenit.Model.Klant;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class RemoteKlantDAO {
    private static DbVermolenItRemote dbVermolenItRemote = new DbVermolenItRemote();

    public static List<Klant> getAll(){
        Connection con = null;
        List<Klant> data = new ArrayList<>();
        String z = "";

        try {
            con = dbVermolenItRemote.CONN();

            if (con == null) {
                z = "Error in connection with SQL server";
            } else {
                String query = "Select * From vit_klanten";
                Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                ResultSet rs = stmt.executeQuery(query);

                while (rs.next()){
                    Klant klant = new Klant();
                    klant.setId(rs.getInt(1));
                    klant.setAanspreking(Aanspreking.fromId(rs.getInt(2)));
                    klant.setVoornaam(rs.getString(3));
                    klant.setNaam(rs.getString(4));
                    klant.setEmail(rs.getString(5));
                    klant.setTel_nr(rs.getString(6));
                    klant.setAdres(rs.getString(7));
                    klant.setPostcode(rs.getInt(8));
                    klant.setWoonplaats(rs.getString(9));
                    klant.setBtw_nummer(rs.getString(10));

                    data.add(klant);
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

    public static long insert(Klant klant){
        Connection con = null;
        String z = "";

        try {
            con = dbVermolenItRemote.CONN();

            if (con == null) {
                z = "Error in connection with SQL server";
            } else {
                String query = "Select Max(id) From vit_klanten";
                Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                ResultSet rs = stmt.executeQuery(query);
                int id = 0;

                while (rs.next()){
                    id = rs.getInt(1) + 1;
                }

                klant.setId(id);

                query = "Insert Into vit_klanten values(" +
                        klant.getId() + ", " +
                        klant.getAanspreking().getId() + ", '" +
                        klant.getVoornaam()+ "', '" +
                        klant.getNaam() + "', '" +
                        klant.getEmail() + "', '" +
                        klant.getTel_nr() + "', '" +
                        klant.getAdres() + "', " +
                        klant.getPostcode() + ", '" +
                        klant.getWoonplaats() + "', '" +
                        klant.getBtw_nummer() + "'" +
                        ")";
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
        return klant.getId();
    }

    public static void update(Klant klant){
        Connection con = null;
        String z = "";

        try {
            con = dbVermolenItRemote.CONN();

            if (con == null) {
                z = "Error in connection with SQL server";
            } else {
                String query = "Update vit_klanten Set Aanspreking=" +
                                    klant.getAanspreking().getId() + ", Voornaam='" +
                                    klant.getVoornaam() + "', Naam='" +
                                    klant.getNaam() + "', Email='" +
                                    klant.getEmail() + "', Tel_Nr='" +
                                    klant.getTel_nr() + "', Adres='" +
                                    klant.getAdres() + "', Postcode=" +
                                    klant.getPostcode() + ", Woonplaats='" +
                                    klant.getWoonplaats() + "', Btw_Nummer='" +
                                    klant.getBtw_nummer() + "'" +
                                    " Where ID=" + klant.getId();
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
                String query = "Delete From vit_klanten Where id=" + id;
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
