package edu.mel06002byui.multithreading;

import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.FileInputStream;
import java.io.FileOutputStream;


public class MainActivity extends ActionBarActivity {

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
        if(evenAdapter == null) {
            
        }
        evenAdapter.clear();
        oddAdapter.clear();

        String evenFilename = "evens.txt";
        String oddFilename = "odds.txt";
        FileInputStream inputEven;
        FileInputStream inputOdd;


    }

    public void stopProgram(View view) {

    }

    public void createFile(View view) {
        String evenFilename = "evens.txt";
        String oddFilename = "odds.txt";
        FileOutputStream outputStreamEven;
        FileOutputStream outputStreamOdd;
        try{
            outputStreamEven = openFileOutput(evenFilename, Context.MODE_PRIVATE);
            outputStreamOdd = openFileOutput(oddFilename, Context.MODE_PRIVATE);
            for (Integer numInput = 1; numInput < 21; numInput++) {
                if (numInput % 2 == 1) {
                    outputStreamOdd.write(numInput.toString().getBytes());
                } else {
                    outputStreamEven.write(numInput.toString().getBytes());
                }
            }
        }
        catch (Exception e) {
            System.out.println("Error Creating Files");
        }
    }
}
