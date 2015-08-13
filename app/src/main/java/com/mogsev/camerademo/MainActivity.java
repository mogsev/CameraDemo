package com.mogsev.camerademo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class MainActivity extends AppCompatActivity {
    TextView format=null;
    TextView contents=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        format=(TextView)findViewById(R.id.format);
        contents=(TextView)findViewById(R.id.contents);
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
    }

    @Override
    public void onRestoreInstanceState(Bundle state) {
        format.setText(state.getString("format"));
        contents.setText(state.getString("contents"));
    }

    public void doScan(View v) {
        (new IntentIntegrator(this)).initiateScan();
    }

    public void onActivityResult(int request, int result, Intent i) {
        IntentResult scan=IntentIntegrator.parseActivityResult(request,
                result,
                i);

        if (scan!=null) {
            format.setText(scan.getFormatName());
            contents.setText(scan.getContents());
        }
    }
}
