package com.example.androidassignments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class WeatherForecast extends AppCompatActivity {

    private ImageView weatherIconImageView;
    private TextView minTempTextView;
    private TextView maxTempTextView;
    private TextView currentTempTextView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_forecast);

        weatherIconImageView = findViewById(R.id.weatherImageView);
        minTempTextView = findViewById(R.id.minTempTextView);
        maxTempTextView = findViewById(R.id.maxTempTextView);
        currentTempTextView = findViewById(R.id.currentTempTextView);
        progressBar = findViewById(R.id.progressBar);


        String selectedCity = "ottawa";
        String weatherUrl = "http://api.openweathermap.org/data/2.5/weather?q=" +
                selectedCity + ",ca&APPID=79cecf493cb6e52d25bb7b7050ff723c&mode=xml&units=metric";

        new ForecastQuery(this).execute(weatherUrl);
    }

    public void setTemperatures(String minTemp, String maxTemp, String currentTemp) {
        minTempTextView.setText("Min Temp: " + minTemp + " °C");
        maxTempTextView.setText("Max Temp: " + maxTemp + " °C");
        currentTempTextView.setText("Current Temp: " + currentTemp + " °C");
    }

    public void updateProgressBar(int progress) {
        // Update progress bar visibility and progress
        progressBar.setVisibility(View.VISIBLE);
        progressBar.setProgress(progress);
    }

    public void saveImageToLocalStorage(String iconName, Bitmap image) {

        if (!fileExists(iconName + ".png")) {

            try {
                FileOutputStream outputStream = openFileOutput(iconName + ".png", MODE_PRIVATE);
                image.compress(Bitmap.CompressFormat.PNG, 80, outputStream);
                outputStream.flush();
                outputStream.close();

                weatherIconImageView.setImageBitmap(image);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Log.i("ImageDownload", "Image saved locally: " + iconName);
        } else {

            loadImageFromLocalStorage(iconName);
            Log.i("ImageDownload", "Image already exists locally: " + iconName);
        }
    }

    private boolean fileExists(String fileName) {

        File file = new File(getFilesDir(), fileName);
        return file.exists();
    }

    private void loadImageFromLocalStorage(String iconName) {

        try {
            FileInputStream fis = openFileInput(iconName + ".png");
            Bitmap bm = BitmapFactory.decodeStream(fis);
            weatherIconImageView.setImageBitmap(bm);
            fis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
