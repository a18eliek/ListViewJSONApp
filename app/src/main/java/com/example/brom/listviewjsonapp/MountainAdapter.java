package com.example.brom.listviewjsonapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import org.json.JSONException;
import java.util.ArrayList;


public class MountainAdapter extends ArrayAdapter<Mountain> {

    public MountainAdapter(Context context, ArrayList<Mountain> mountainList) {
        super(context, 0, mountainList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        String mountainIMG = "";

        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        Mountain currentMountain = getItem(position);

        //Skriv ut namnet på berget
        TextView nameTextView = listItemView.findViewById(R.id.mountName);
        nameTextView.setText(currentMountain.toString());

        //Skriv ut information om berget
        TextView numberTextView = listItemView.findViewById(R.id.mountInfo);
        numberTextView.setText(currentMountain.info());

        try {
            mountainIMG = Mountain.splitAuxdata(currentMountain.getAuxdata(), "img");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //Ladda ner och visa bilen
        new MountainDetailsActivity.DownloadImageTask((ImageView) listItemView.findViewById(R.id.list_item_icon)).execute(mountainIMG);

        return listItemView;
    }

}