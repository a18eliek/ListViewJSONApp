package com.example.brom.listviewjsonapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import org.json.JSONException;
import java.io.InputStream;

public class MountainDetailsActivity extends AppCompatActivity {
    ImageView image;
    public static final String MOUNTAIN_URL = "MOUNTAIN_URL";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mountaindetails);

        //Ta emot allt som blev skickat ifrån MainActivity
        Intent intent = getIntent();
        String mountainName = intent.getStringExtra(MainActivity.MOUNTAIN_NAME);
        String mountainLocation = intent.getStringExtra(MainActivity.MOUNTAIN_LOCATION);
        String mountainHeight = intent.getStringExtra(MainActivity.MOUNTAIN_HEIGHT);
        String mountainAuxdata = intent.getStringExtra(MainActivity.MOUNTAIN_AUXDATA);

        String mountainURL = "", mountainIMG = "";

        try {
            mountainURL = Mountain.splitAuxdata(mountainAuxdata, "url");
        } catch (JSONException e) {
            Log.e("brom","mountainURL Exception:"+e.getMessage());
        }

        try {
            mountainIMG = Mountain.splitAuxdata(mountainAuxdata, "img");
        } catch (JSONException e) {
            Log.e("brom","mountainIMG Exception:"+e.getMessage());
        }

        Log.e("brom", mountainURL);

        //Ändra TextView för bergets namn
        TextView mountainNameTextView = findViewById(R.id.MOUNTAIN_NAME);
        mountainNameTextView.setText(mountainName);
        setTitle(mountainName); //Ändra lable text till bergnamnet

        //Ändra TextView för bergets plats
        TextView mountainLocationTextView = findViewById(R.id.MOUNTAIN_LOCATION);
        mountainLocationTextView.setText("Plats: " + mountainLocation);

        //Ändra TextView för bergets höjd
        TextView mountainHeightTextView = findViewById(R.id.MOUNTAIN_HEIGHT);
        mountainHeightTextView.setText("Höjd: " + mountainHeight + "m");

        TextView mountainLinkTextView = findViewById(R.id.MOUNTAIN_LINK);
        mountainLinkTextView.setContentDescription(mountainURL); //Vi sparar URL som en description

        //Visa bilen på berget
        new DownloadImageTask((ImageView) findViewById(R.id.MountainImageView)).execute(mountainIMG);

    }

    public void openMountainLink(View v) {
        TextView tv = findViewById(R.id.MOUNTAIN_LINK);
        String url = (String) tv.getContentDescription();

        Intent myIntent = new Intent(v.getContext(), WebViewActivity.class);
        myIntent.putExtra(MOUNTAIN_URL, url);
        startActivity(myIntent);
    }


    //Ladda bilder ifrån en URL
    //Tagen ifrån https://stackoverflow.com/a/9288544/3822307
    static class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}