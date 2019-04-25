package com.example.vermolenit.DB;

import android.util.Log;

import com.example.vermolenit.Model.Artikel;
import com.example.vermolenit.Model.Eenheid;
import com.example.vermolenit.Model.Kasticket;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class RemoteKasticketsDAO {
    private static DbVermolenItRemote dbVermolenItRemote = new DbVermolenItRemote();

    public static List<Kasticket> getAll(){
        Connection con = null;
        List<Kasticket> data = new ArrayList<>();
        String z = "";

        try {
            con = dbVermolenItRemote.CONN();

            if (con == null) {
                z = "Error in connection with SQL server";
            } else {
                String query = "Select * From vit_kastickets";
                Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                ResultSet rs = stmt.executeQuery(query);

                while (rs.next()){
                    Kasticket kasticket = new Kasticket();
                    kasticket.setId(rs.getInt(1));
                    kasticket.setDatum(rs.getDate(2));
                    kasticket.setKlant_id(rs.getInt(3));
                    kasticket.setBetaald(rs.getBoolean(4));

                    data.add(kasticket);
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

    public static long insert(Kasticket kasticket){
        Connection con = null;
        List<Kasticket> data = new ArrayList<>();
        String z = "";

        try {
            con = dbVermolenItRemote.CONN();

            if (con == null) {
                z = "Error in connection with SQL server";
            } else {
                String query = "Select Max(id) From vit_kastickets";
                Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                ResultSet rs = stmt.executeQuery(query);
                int id = 0;

                while (rs.next()){
                    id = rs.getInt(1) + 1;
                }

                kasticket.setId(id);

                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd 12:00:00.000");

                query = "Insert Into vit_kastickets Values(" +
                        kasticket.getId() + ", '" +
                        format.format(kasticket.getDatum()) + "', " +
                        kasticket.getKlant_id() + ", " +
                        ((kasticket.isBetaald()) ? 1 : 0) + ")";
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
        return kasticket.getId();
    }

    public static void update(Kasticket kasticket){
        Connection con = null;
        String z = "";

        try {
            con = dbVermolenItRemote.CONN();

            if (con == null) {
                z = "Error in connection with SQL server";
            } else {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd 12:00:00.000");

                String query = "Update vit_kastickets Set Datum='" +
                        format.format(kasticket.getDatum()) + "', KlantID=" +
                        kasticket.getKlant_id() + ", IsBetaald=" +
                        ((kasticket.isBetaald()) ? 1 : 0) + " Where ID=" + kasticket.getId();
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
                String query = "Delete From vit_kastickets Where id=" + id;
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
