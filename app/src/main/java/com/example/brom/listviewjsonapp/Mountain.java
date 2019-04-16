package com.example.brom.listviewjsonapp;

public class Mountain {
    //Member variabels
    private  String name;
    private String location;
    private int height;

    //Constructor(s)
    public Mountain(String inName, String inLocation, int inHeight) {
        name = inName;
        location = inLocation;
        height = inHeight;
    }

    public Mountain(String inName) {
        name = inName;
        location = "";
        height = -1;
    }

    //Member methods
    public String toString () {
        return name;
    }

    public String info() {
        String str = name;
        str += " is located in ";
        str += location;
        str += " and has an height of ";
        str += Integer.toString(height);
        str += "m. ";
        return str;
    }

    public void setHeight(int newHeight) {
        height = newHeight;
    }
}
