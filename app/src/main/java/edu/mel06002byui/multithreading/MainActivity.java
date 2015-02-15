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
import android.widget.ListAdapter;
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
import java.util.ArrayList;

import static android.R.layout.simple_list_item_1;


public class MainActivity extends ActionBarActivity {

    private static final String TAG = "MainActivity";
    private final String FILENAME = "numbers.txt";

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
        try {
            InputStream readFiles = openFileInput(FILENAME);
            ArrayList<Integer> numberList = new ArrayList<>();
            if (readFiles != null) {
                InputStreamReader inReader = new InputStreamReader(readFiles);
                BufferedReader brInput = new BufferedReader(inReader);
                String currLine = "";
                while ((currLine = brInput.readLine()) != null) {
                    numberList.add(Integer.valueOf(currLine));
                }
                readFiles.close();
                ListView mainListView = (ListView) findViewById(R.id.listView);
                ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(this,
                        simple_list_item_1, numberList);
                mainListView.setAdapter(adapter);
            }
        } catch (FileNotFoundException err) {
            Log.d(TAG, "FILE MISSING!!!!");
        } catch (IOException err) {
            Log.d(TAG, "CAN'T WRITE!!!");
        }

    }

    public void clearList(View view) {
        ListView mainListView = (ListView) findViewById(R.id.listView);
        ArrayAdapter<Integer> adapter = (ArrayAdapter<Integer>)mainListView.getAdapter();
        adapter.clear();
    }

    public void createFile(View view) {

        OutputStreamWriter outputStreamNumbers;
        try {
            outputStreamNumbers =
                    new OutputStreamWriter(openFileOutput(FILENAME, Context.MODE_PRIVATE));

            for (Integer numInput = 1; numInput <= 10; numInput++) {
                outputStreamNumbers.write(numInput.toString() + "\n");
                Thread.sleep(250);
            }

            outputStreamNumbers.close();
        } catch (FileNotFoundException e) {
            Log.v(TAG, "FILE CREATION ISSUE!!!");
        } catch (IOException err) {
            Log.v(TAG, "CAN'T WRITE");
        } catch (InterruptedException e) {
            Log.v(TAG, "INTERRUPTED!!!");
        }
    }
}
