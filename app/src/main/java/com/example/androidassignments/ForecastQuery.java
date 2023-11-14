package com.example.androidassignments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ForecastQuery extends AsyncTask<String, Integer, String> {

    private WeatherForecast weatherForecast; // Reference to the WeatherForecast activity

    public ForecastQuery(WeatherForecast weatherForecast) {
        this.weatherForecast = weatherForecast;
    }

    @Override
    protected String doInBackground(String... args) {
        try {
            URL url = new URL(args[0]);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);

            conn.connect();

            InputStream stream = conn.getInputStream();

            XmlPullParserFactory xmlFactoryObject = XmlPullParserFactory.newInstance();
            XmlPullParser parser = xmlFactoryObject.newPullParser();
            parser.setInput(stream, null);

            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG && parser.getName().equals("temperature")) {
                    String minTemp = parser.getAttributeValue(null, "min");
                    publishProgress(25);
                    String maxTemp = parser.getAttributeValue(null, "max");
                    publishProgress(50);
                    String currentTemp = parser.getAttributeValue(null, "value");

                    publishProgress(75);
                    weatherForecast.setTemperatures(minTemp, maxTemp, currentTemp);
                } else if (eventType == XmlPullParser.START_TAG && parser.getName().equals("weather")) {
                    String iconName = parser.getAttributeValue(null, "icon");

                    downloadAndHandleIcon(iconName);

                    publishProgress(100);
                }

                eventType = parser.next();
            }

            stream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private void downloadAndHandleIcon(String iconName) {
        try {
            String imageUrl = "http://openweathermap.org/img/w/" + iconName + ".png";
            Bitmap image = getImageFromUrl(imageUrl);

            weatherForecast.saveImageToLocalStorage(iconName, image);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Bitmap getImageFromUrl(String imageUrl) {
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true);
            conn.connect();

            InputStream input = conn.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(input);
            input.close();

            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        weatherForecast.updateProgressBar(values[0]);
    }
}

