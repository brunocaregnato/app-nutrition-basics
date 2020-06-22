package com.example.nutritionbasics.parser;

import android.util.Log;

import androidx.annotation.NonNull;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class JSONParser {

    private static final String MAIN_URL = "https://script.google.com/macros/s/AKfycbxtjj4p84EUWPPUejLLkjvm2zhFjnQZlNu6X7ZfvvckNBTlbps/exec?id=1W16u9J5jUG5yUeFge2qVgDnMZlI2qN6bfIRCatnUMl0&sheet=Sheet1";

    public static final String TAG = "TAG";

    private static Response response;

    public static JSONObject getDataFromWeb() {
        try {
            Log.i("vENI3", "PORRAAAAAAAA" );
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(MAIN_URL)
                    .build();
            Log.i("vENI9", "PORRAAAAAAAA" );
            response = client.newCall(request).execute();
            Log.i("vENI8", "PORRAAAAAAAA" );
            return new JSONObject(response.body().string());
        } catch (@NonNull IOException | JSONException e) {
            Log.e(TAG, "VISHHH" + e.getLocalizedMessage());
        }
        return null;
    }

}
