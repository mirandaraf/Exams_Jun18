package com.example.labuser.exams_jun18;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    PlaceAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        adapter = new PlaceAdapter(this,
                0);

        ListView listView = (ListView) findViewById(R.id.place_list);
        listView.setAdapter(adapter);
        GetPlacesTask task = new GetPlacesTask();
        task.execute();
    }

    private class GetPlacesTask extends AsyncTask<Void, Void, Place[]> {

        protected Place[] doInBackground(Void... voids) {
            String articleJsonStr = null;

            // Get JSON from REST API

            HttpURLConnection urlConnection = null;

            try {
                final String baseUrl = "https://maps.googleapis.com/maps/api/place/textsearch/json?query=pizza+%CE%B1%CE%B8%CE%B7%CE%BD%CE%B1&key=AIzaSyB-rraeRhMpfGK6gcxezqeKsW_gpH4NspE";

                // Get input stream

                URL url = new URL(baseUrl);

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }

                // Extract JSON string from input stream


                StringBuilder out = new StringBuilder();
                InputStreamReader in = new InputStreamReader(inputStream, "UTF-8");

                char[] buffer = new char[1024];
                int rsz;
                while ((rsz = in.read(buffer, 0, buffer.length)) >= 0) {
                    out.append(buffer, 0, rsz);
                }

                articleJsonStr = out.toString();
            } catch (IOException e) {
                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }

            // Turn JSON to Array of Strings

            try {
                JSONObject placesJson = new JSONObject(placeJsonStr);
                JSONArray placeJsonArray = placesJson.getJSONArray("places");

                Place[] placesArray = new Place[placeJsonArray.length()];

                for (int i = 0; i < placeJsonArray.length(); i++) {
                    JSONObject placeJson = (JSONObject) placeJsonArray.get(i);

                    Place place = new Place();
                    place.setFormatted_address(placeJson.getString("formatted_address"));
                    place.setId(placeJson.getString("id"));
                    place.setName(placeJson.getString("name"));
                    place.setOpening_hours(placeJson.getString("opening_hours"));
                    place.setRating(placeJson.getString("rating"));
                    placesArray[i] = place;
                }

                return placesArray;
            } catch (JSONException e) {
                return null;
            }
        }

        protected void onPostExecute(Place[] places) {
            adapter.clear();

            adapter.addAll(places);

        }
    }


}
