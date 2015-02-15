package edu.mel06002byui.multithreading;

import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOError;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;


public class MainActivity extends ActionBarActivity {

    private static final String TAG = "MainActivity";
    private ArrayAdapter<Integer> evenAdapter;
    private ArrayAdapter<Integer> oddAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

    public void loadFromFile(View view) {
        try{
            InputStream readFiles = openFileInput("evens.txt");
            if(readFiles != null) {
                InputStreamReader inReader = new InputStreamReader(readFiles);
                BufferedReader brInput = new BufferedReader(inReader);
                String currLine = "";
                StringBuilder evenInput = new StringBuilder();
                while((currLine = brInput.readLine()) != null) {
                    evenInput.append(currLine + " ");
                }
                readFiles.close();
                TextView scriptureDisplayValue = new TextView(this);
                scriptureDisplayValue.setTextSize(50);
                scriptureDisplayValue.setText(evenInput.toString());
                setContentView(scriptureDisplayValue);
            }
        }
        catch (FileNotFoundException err){
            Log.d(TAG, "FILE MISSING!!!!");
        }
        catch (IOException err) {
            Log.d(TAG, "CAN'T WRITE!!!");
        }

    }

    public void stopProgram(View view) {

    }

    public void createFile(View view) {
        String evenFilename = "evens.txt";
        String oddFilename = "odds.txt";
        OutputStreamWriter outputStreamEven;
        OutputStreamWriter outputStreamOdd;
        try{
            outputStreamEven =
                    new OutputStreamWriter(openFileOutput(evenFilename, Context.MODE_PRIVATE));
            outputStreamOdd =
                    new OutputStreamWriter(openFileOutput(oddFilename, Context.MODE_PRIVATE));
            for (Integer numInput = 1; numInput < 21; numInput++) {
                if (numInput % 2 == 1) {
                    outputStreamOdd.write(numInput.toString() + "\n");
                } else {
                    outputStreamEven.write(numInput.toString() + "\n");
                }
            }
            outputStreamEven.close();
            outputStreamOdd.close();
        }
        catch (Exception e) {
            Log.v(TAG, "FILE CREATION ISSUE!!!");
        }
    }
}
