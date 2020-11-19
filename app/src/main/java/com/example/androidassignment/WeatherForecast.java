package com.example.androidassignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class WeatherForecast extends AppCompatActivity
{
    protected static final String ACTIVITY_NAME = "WeatherForecast";
    ProgressBar bar;
    TextView crrtemp;
    TextView tempmin;
    TextView tempmax;
    ImageView imgbtn;
    List<String> citylist;
    TextView city;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_forecast);
        Log.i(ACTIVITY_NAME,"In onCreate()");

        city = findViewById(R.id.citydropdown);
        crrtemp = findViewById(R.id.currtemp);
        tempmin = findViewById(R.id.mintemp);
        tempmax = findViewById(R.id.maxtemp);
        imgbtn = findViewById(R.id.weatherimage);

        bar = findViewById(R.id.progressbar);
        bar.setVisibility(View.VISIBLE);

        chooseCity();
    }

    public void chooseCity() {

        citylist = Arrays.asList(getResources().getStringArray(R.array.cities));
        final Spinner citySpinner = findViewById(R.id.cityList);
        ArrayAdapter<CharSequence> adapter =
                ArrayAdapter.createFromResource(
                        this, R.array.cities, android.R.layout.simple_spinner_dropdown_item);

        citySpinner.setAdapter(adapter);
        citySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView <?> adapterView, View view, int i, long l) {
                new ForecastQuery(citylist.get(i)).execute("this will go to background");
                city.setText(citylist.get(i) + " Weather");
            }

            @Override
            public void onNothingSelected(AdapterView <?> adapterView) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(ACTIVITY_NAME, "In onResume()");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(ACTIVITY_NAME, "In onStart()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(ACTIVITY_NAME, "In onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(ACTIVITY_NAME, "In onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(ACTIVITY_NAME, "In onDestroy()");
    }

    private class ForecastQuery extends AsyncTask<String, Integer, String>{

        String cityarr;
        String minTemp;
        String maxTemp;
        String currTemp;
        Bitmap image;

        ForecastQuery(String cityarr){
            this.cityarr = cityarr;
        }

        @Override
        protected String doInBackground(String... args)
        {
            Log.i("incoming param", args[0] + "---------------------------") ;
            try {

                URL url = new URL("https://api.openweathermap.org/data/2.5/weather?" + "q="
                        + this.cityarr + ",ca&"
                        + "APPID=eafe20ca80f5b3f54aa044d0df26006d&" + "mode=xml&" + "units=metric");
                HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
                conn.setReadTimeout(10000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);

                conn.connect();
                InputStream in = conn.getInputStream();
                try {
                    XmlPullParser parse = Xml.newPullParser();
                    parse.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES,false);
                    parse.setInput(in,null);
                    int type;

                    while((type = parse.getEventType()) != XmlPullParser.END_DOCUMENT){

                        if (parse.getEventType() == XmlPullParser.START_TAG){

                            if (parse.getName().equals("temperature")){
                                Log.i(ACTIVITY_NAME,"Getting temperature");
                                currTemp = parse.getAttributeValue(null,"value");
                                publishProgress(25);
                                minTemp = parse.getAttributeValue(null,"min");
                                publishProgress(50);
                                maxTemp = parse.getAttributeValue(null,"max");
                                publishProgress(75);
                            }

                            if (parse.getName().equals("weather")){
                                String iconName = parse.getAttributeValue(null,"icon");
                                String fileName = iconName + ".png";

                                Log.i(ACTIVITY_NAME,"Looking for file: " + fileName);
                                if(fileExistence(fileName)){
                                    FileInputStream fis = null;
                                    try {
                                        fis = openFileInput(fileName);
                                    }
                                    catch (FileNotFoundException e){
                                        e.printStackTrace();
                                    }
                                    Log.i(ACTIVITY_NAME,"Found the file locally");
                                    image = BitmapFactory.decodeStream(fis);
                                    }
                                else {
                                    String iconUrl = "https://openweathermap.org/img/w/" + fileName;
                                    image = getImage(new URL(iconUrl));

                                    FileOutputStream fos = openFileOutput(fileName, Context.MODE_PRIVATE);
                                    image.compress(Bitmap.CompressFormat.PNG, 80, fos);
                                    Log.i(ACTIVITY_NAME,"Downloaded file from internet");
                                    fos.flush();
                                    fos.close();
                                }
                                publishProgress(100);
                            }
                        }
                        parse.next();
                    }

                }
                finally {
                    conn.disconnect();
                    in.close();
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }
            return "do background ended";
        }

        public boolean fileExistence(String fname){
            File f = getBaseContext().getFileStreamPath(fname);
            return f.exists();
        }

        public Bitmap getImage(URL url){
            HttpsURLConnection con = null;
            try {
                con = (HttpsURLConnection) url.openConnection();
                con.connect();
                int respcode = con.getResponseCode();
                if(respcode == HttpsURLConnection.HTTP_OK)
                    return BitmapFactory.decodeStream(con.getInputStream());
                else
                    return null;
            }
            catch (Exception e1){
                return null;
            }
            finally {
                if (con != null)
                    con.disconnect();
            }
        }

        @Override
        protected void onProgressUpdate(Integer... vals){
            Log.i(ACTIVITY_NAME,"In onProgressUpdate()"+vals[0]+"----------------");
            bar.setProgress(vals[0]);
        }

        @Override
        protected void onPostExecute(String a){

            bar.setVisibility(View.INVISIBLE);
            imgbtn.setImageBitmap(image);
            crrtemp.setText(currTemp + "C\u00b0");
            tempmin.setText(minTemp + "C\u00b0");
            tempmax.setText(maxTemp + "C\u00b0");
            Log.i(ACTIVITY_NAME,"In onPostExecute()"+ a.toString());
        }
    }
}