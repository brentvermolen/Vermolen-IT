package com.example.vermolenit.Model;

public enum Aanspreking {
    Meneer(1, "Mnr."),
    Mevrouw(2, "Mvr."),
    Niets(3, "");

    Aanspreking(int id, String verkort){
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

    public static Aanspreking fromId(int id){
        for (Aanspreking aanspreking : Aanspreking.values()){
            if (aanspreking.getId() == id)
            {
                return aanspreking;
            }
        }

        return null;
    }
}
