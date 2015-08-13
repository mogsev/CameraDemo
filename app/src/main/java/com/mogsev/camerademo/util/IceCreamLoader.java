package com.mogsev.camerademo.util;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Created by zhenya on 13.08.2015.
 */
public class IceCreamLoader extends AsyncTaskLoader<HashMap<String, String>> {
    private static final String TAG = "IceCreamLoader";
    private static final String URL_ITEM = "http://mail.rud.ua/mail/item.txt";
    private HashMap<String, String> map;

    public IceCreamLoader(Context context) {
        super(context);
    }

    @Override
    public HashMap<String, String> loadInBackground() {
        Log.d(TAG, "loadInBackground start");
        initList();
        Log.d(TAG, "loadInBackground end");
        return map;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        if (map != null) {
            // Deliver any previously loaded data immediately.
            deliverResult(map);
        }

        if (takeContentChanged() || map == null) {
            // When the observer detects a change, it should call onContentChanged()
            // on the Loader, which will cause the next call to takeContentChanged()
            // to return true. If this is ever the case (or if the current data is
            // null), we force a new load.
            forceLoad();
        }
    }

    private void initList() {
        Log.d(TAG, "initList start");
        map = new HashMap<>();

        try {
            URL url = new URL(URL_ITEM);
            //BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
            Scanner scanner = new Scanner(url.openStream());
            //Log.d(TAG, in.readLine());
            while (scanner.hasNext()) {
                String string = scanner.nextLine();
                Log.d(TAG, string);
                if (!string.isEmpty()) {
                    String[] str = string.split(";");
                    Log.d(TAG, " " + str.length);
                    if (str.length == 6) {
                        map.put(str[1], str[2]);
                        Log.d(TAG, str[1] + " " + str[2]);
                        Log.d(TAG, map.get(str[1]));
                    }
                }
            }
        } catch (MalformedURLException ex) {
            Log.d(TAG, ex.getMessage());
        } catch (IOException ex) {
            Log.d(TAG, ex.getMessage());
        }
        Log.d(TAG, "initList end");
    }
}
