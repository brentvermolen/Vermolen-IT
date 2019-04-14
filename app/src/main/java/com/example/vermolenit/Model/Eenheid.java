package com.example.vermolenit.Model;

public enum Eenheid {
    Stuk(1, "Per Stuk"),
    Kwart(2, "Per 15min.");

    Eenheid(int id, String verkort){
        this.id = id;
        this.verkort = verkort;
    }

    private int id;
    private String verkort;

    @Override
    public String toString() {
        return super.toString();
    }

    public String getVerkort(){
        return verkort;
    }
    public int getId() { return id; }

    public static Eenheid fromId(int id){
        for (Eenheid aanspreking : Eenheid.values()){
            if (aanspreking.getId() == id)
            {
                return aanspreking;
            }
        }

        return null;
    }
}
