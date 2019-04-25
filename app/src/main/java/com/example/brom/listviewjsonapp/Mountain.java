package com.example.brom.listviewjsonapp;

import org.json.JSONException;
import org.json.JSONObject;

public class Mountain {
    //Member variabels
    private String name;
    private String location;
    private int height;
    private String auxdata;

    //Constructor(s)
    public Mountain(String inName, String inLocation, int inHeight, String inAuxdata) {
        name = inName;
        location = inLocation;
        height = inHeight;
        auxdata = inAuxdata;
    }

    public Mountain(String inName) {
        name = inName;
        location = "";
        height = -1;
    }

    //Member methods
    public String toString() {
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

    public String getLocation() {
        return location;
    }

    public String getHeight() {
        return String.valueOf(height);
    }

    public String getAuxdata() {
        return auxdata;
    }

    public static String splitAuxdata(String auxdata, String value) throws JSONException {
        JSONObject json = new JSONObject(auxdata);
        return json.getString(value);
    }

}
