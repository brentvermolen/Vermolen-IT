package com.example.vermolenit.Model;

public enum Eenheid {
    Stuk(1, "Per Stuk", "stuks"),
    Kwart(2, "Per 15min.", " x 15min.");

    Eenheid(int id, String volledig, String verkort){
        this.id = id;
        this.volledig = volledig;
        this.verkort = verkort;
    }

    private int id;
    private String volledig;
    private String verkort;

    @Override
    public String toString() {
        return super.toString();
    }

    public String getVolledig(){
        return volledig;
    }
    public String getVerkort() { return verkort; }
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
