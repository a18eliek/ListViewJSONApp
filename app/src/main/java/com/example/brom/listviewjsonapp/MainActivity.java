package com.example.brom.listviewjsonapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public ArrayList<Mountain> list = new ArrayList<Mountain>();
    public static final String MOUNTAIN_NAME = "MOUNTAIN_NAME", MOUNTAIN_LOCATION = "MOUNTAIN_LOCATION", MOUNTAIN_HEIGHT = "", MOUNTAIN_AUXDATA = "MOUNTAIN_AUXDATA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new FetchData().execute(); //Starta utplock av json-data
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_refresh) {
            Log.e("brom","Refreshing data...");
            list.clear(); //Ta bort all gamal data
            new FetchData().execute(); //Hämta ny data
            Toast.makeText(getApplicationContext(), "Ny data hämtad!", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private class FetchData extends AsyncTask<Void,Void,String>{
        @Override
        protected String doInBackground(Void... params) {
            // These two variables need to be declared outside the try/catch
            // so that they can be closed in the finally block.
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            // Will contain the raw JSON response as a Java string.
            String jsonStr = null;

            try {
                // Construct the URL for the Internet service
                URL url = new URL("http://wwwlab.iit.his.se/brom/kurser/mobilprog/dbservice/admin/getdataasjson.php?type=brom");

                // Create the request to the PHP-service, and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return null;
                }
                jsonStr = buffer.toString();
                return jsonStr;
            } catch (IOException e) {
                Log.e("PlaceholderFragment", "Error ", e);
                // If the code didn't successfully get the weather data, there's no point in
                // attempting to parse it.
                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e("Network error", "Error closing stream", e);
                    }
                }
            }
        }

        @Override
        protected void onPostExecute(String o) {
            super.onPostExecute(o);
            Log.e("brom","onPostExecute:"+o);


            // This code executes after we have received our data. The String object o holds
            // the un-parsed JSON string or is null if we had an IOException during the fetch.

            // Implement a parsing code that loops through the entire JSON and creates objects
            // of our newly created Mountain class.

            try {
                if (o != null) {
                    JSONArray jsonArray = new JSONArray(o);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = (JSONObject) jsonArray.get(i);

                        Mountain m =  new Mountain(object.getString("name"),
                                object.getString("location"),
                                object.getInt("size"),
                                object.getString("auxdata")
                        );

                        list.add(m);
                    }
                }
            } catch (JSONException e) {
                Log.e("brom","E:"+e.getMessage());
            }

            //Skicka bergen till vår MountainAdapter
            MountainAdapter mountainAdapter = new MountainAdapter(getApplicationContext(), list);
            ListView listView = findViewById(R.id.my_listview);
            listView.setAdapter(mountainAdapter);


            //Skicka all information vid klick till vår intent
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent myIntent = new Intent(view.getContext(), MountainDetailsActivity.class);
                    myIntent.putExtra(MOUNTAIN_NAME, list.get(position).toString());
                    myIntent.putExtra(MOUNTAIN_LOCATION, list.get(position).getLocation());
                    myIntent.putExtra(MOUNTAIN_HEIGHT, list.get(position).getHeight());
                    myIntent.putExtra(MOUNTAIN_AUXDATA, list.get(position).getAuxdata());
                    startActivity(myIntent);
                }
            });
        }
    }
}

