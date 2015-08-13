package com.mogsev.camerademo;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.mogsev.camerademo.util.IceCreamLoader;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<HashMap<String, String>> {
    private static final String TAG = "MainActivity";
    private static final int LOADER = 1;
    private TextView format = null;
    private TextView contents = null;
    private TextView iceCreamName = null;
    public HashMap<String, String> mapIceCream = new HashMap<>();
    private String code;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        format = (TextView) findViewById(R.id.format);
        contents = (TextView) findViewById(R.id.contents);
        iceCreamName = (TextView) findViewById(R.id.iceCreamName);

        // Initialize LoadManager
        getLoaderManager().initLoader(LOADER, null, this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSaveInstanceState(Bundle state) {
        state.putString("format", format.getText().toString());
        state.putString("contents", contents.getText().toString());
        state.putString("iceCreamName", iceCreamName.getText().toString());

    }

    @Override
    public void onRestoreInstanceState(Bundle state) {
        format.setText(state.getString("format"));
        contents.setText(state.getString("contents"));
        iceCreamName.setText(state.getString("iceCreamName"));
    }

    public void doScan(View v) {
        (new IntentIntegrator(this)).initiateScan();
    }

    public void onActivityResult(int request, int result, Intent i) {
        IntentResult scan = IntentIntegrator.parseActivityResult(request,
                result,
                i);

        if (scan != null) {
            format.setText(scan.getFormatName());
            code = scan.getContents();
            contents.setText(code);
            if (mapIceCream.containsKey(code)) {
                name = mapIceCream.get(code);
                iceCreamName.setText(name);
                //Toast.makeText(this.getBaseContext(), name, Toast.LENGTH_LONG).show();
            } else {
                iceCreamName.setText(R.string.no_data);
            }
        }
    }

    @Override
    public Loader<HashMap<String, String>> onCreateLoader(int id, Bundle args) {
        Log.d(TAG, "onCreateLoader start");
        Loader loader = null;
        switch (id) {
            case LOADER:
                Log.d(TAG, "Create LOADER_CASH");
                loader = new IceCreamLoader(this);
                break;
        }
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<HashMap<String, String>> loader, HashMap<String, String> data) {
        mapIceCream = data;
    }

    @Override
    public void onLoaderReset(Loader<HashMap<String, String>> loader) {
        Log.d(TAG, "onLoaderReset start");
        //mapIceCream = null;
    }
}
