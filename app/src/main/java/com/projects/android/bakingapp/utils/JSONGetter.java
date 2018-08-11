package com.projects.android.bakingapp.utils;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import timber.log.Timber;

/**
 * Created by alshtray on 8/11/18.
 */

public class JSONGetter {
    public String getJson(String url) {
            URL requestUrl = buildUrl(url);
            HttpURLConnection urlConnection = null;
            try {
                //taken from sunshine exercise
                urlConnection = (HttpURLConnection) requestUrl.openConnection();
                InputStream in = urlConnection.getInputStream();

                Scanner scanner = new Scanner(in);
                scanner.useDelimiter("\\A");

                boolean hasInput = scanner.hasNext();
                if (hasInput) {
                    return scanner.next();
                } else {
                    return null;
                }
            } catch (IOException e) {
                Timber.e(e.getMessage());
                return null;
            } finally {
                if(urlConnection != null){
                    urlConnection.disconnect();
                }
            }
    }
    private static URL buildUrl(String request) {
        Uri uri = Uri.parse(request);
        Timber.d("url is: "+uri.toString());
        URL url = null;

        try {
            url = new URL(uri.toString());
        } catch (MalformedURLException e) {
            Timber.e(e.getMessage());
        }

        return url;
    }
}
