package com.example.dz.androidlab;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.os.AsyncTask;

import org.xmlpull.v1.XmlPullParser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherForecast extends Activity {

    protected static final String ACTIVITY_NAME = "WeatherForecast";

    ImageView weatherImage;
    TextView currentTemp;
    TextView minTemp;
    TextView maxTemp;
    TextView windSpeed;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_forecast);
        Log.i(ACTIVITY_NAME, "In OnCreate()");

        weatherImage = (ImageView) findViewById(R.id.weatherImg);

        currentTemp = (TextView) findViewById(R.id.currentTmp);

        minTemp = (TextView) findViewById(R.id.minTmp);

        maxTemp = (TextView) findViewById(R.id.maxTmp);

        windSpeed = (TextView) findViewById(R.id.windSpd);

        progressBar = findViewById(R.id.progress);
        progressBar.setVisibility(View.VISIBLE);

        ForecastQuery fQuery = new ForecastQuery();  // creating object for inner class
        fQuery.execute("http://api.openweathermap.org/data/2.5/weather?q=ottawa,ca&APPID=d99666875e0e51521f0040a3d97d0f6a&mode=xml&units=metric");
    }

    protected static Bitmap getImage(URL url) {

        HttpURLConnection iconConn = null;
        try {
            iconConn = (HttpURLConnection) url.openConnection();
            iconConn.connect();
            int response = iconConn.getResponseCode();
            if (response == 200) {
                return BitmapFactory.decodeStream(iconConn.getInputStream());
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (iconConn != null) {
                iconConn.disconnect();
            }
        }
    }

    public boolean fileExistance(String fname) {
        File file = getBaseContext().getFileStreamPath(fname);
        return file.exists();
    }

    public class ForecastQuery extends AsyncTask<String, Integer, String>{

        String wind_speed;
        String min_temp;
        String max_temp;
        String current_temp;
        Bitmap current_weather;
        String icon_Name;

        @Override
        protected String doInBackground(String... string) {
            Log.i(ACTIVITY_NAME, "doInBackground is started");
            try {
                URL url = new URL(string[0]);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection(); // establishing connection
                InputStream stream = conn.getInputStream();
                XmlPullParser parser = Xml.newPullParser(); // creating parser object to parse xml
                parser.setInput(stream, null);

                while (parser.next() != XmlPullParser.END_DOCUMENT) {
                    if (parser.getEventType() != XmlPullParser.START_TAG) {
                        continue;
                    }
                    else if (parser.getName().equals("temperature"))
                    {
                        current_temp = parser.getAttributeValue(null, "value");
                        publishProgress(25);
                        min_temp = parser.getAttributeValue(null, "min");
                        publishProgress(50);
                        max_temp = parser.getAttributeValue(null, "max");
                        publishProgress(75);
                    }
                    else if(parser.getName().equals("speed")) {
                        wind_speed = parser.getAttributeValue(null, "value");
                    }
                    else  if (parser.getName().equals("weather")) {
                        icon_Name = parser.getAttributeValue(null, "icon");
                        String iconFile = icon_Name+".png";
                        if (fileExistance(iconFile)) {
                            FileInputStream fis = null;
                            try {
                                fis = openFileInput(iconFile);
                                fis = new FileInputStream(getBaseContext().getFileStreamPath(iconFile));

                            }
                            catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                            current_weather = BitmapFactory.decodeStream(fis);


                            Log.i(ACTIVITY_NAME, "Image already exists");
                        } else {

                            URL imageURL = new URL("http://openweathermap.org/img/w/" + icon_Name + ".png");
                            current_weather = getImage(imageURL);
                            FileOutputStream outputStream = openFileOutput(icon_Name + ".png", Context.MODE_PRIVATE);
                            current_weather.compress(Bitmap.CompressFormat.PNG, 80, outputStream);
                            outputStream.flush();
                            outputStream.close();
                            Log.i(ACTIVITY_NAME, "downloading new image");
                        }
                        Log.i(ACTIVITY_NAME, "file name="+iconFile);
                        publishProgress(100);  // publishing progress for progress bar
                        // Log.i(ACTIVITY_NAME, "publishprogress"+progressBar);
                    }
                }
            } catch(Exception e) {
                e.printStackTrace();
            }
            return "done";
        }

        public void onProgressUpdate(Integer... data) {
            progressBar.setVisibility(View.VISIBLE);        // seting visibilty of progress bar
            progressBar.setProgress(data[0]);  // progress for progressbar


        }

        public void onPostExecute(String result) {

            currentTemp.setText("Current Temperature:  " + current_temp + " °C");
            minTemp.setText("Min Temperature:  " + min_temp + " °C");
            maxTemp.setText("Max Temperature:  " + max_temp + " °C");
            windSpeed.setText("Wind Speed:  " + wind_speed);
            weatherImage.setImageBitmap(current_weather);
            progressBar.setVisibility(View.INVISIBLE);
        }

    }
}

