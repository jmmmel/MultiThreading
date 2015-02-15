package edu.mel06002byui.multithreading;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import static android.R.layout.simple_list_item_1;

/**
 * This is the Main Screen for this activity
 */
public class MainActivity extends ActionBarActivity {

    private static final String TAG = "MainActivity";
    private final String FILENAME = "numbers.txt";
    private final int NUMITEMS = 10;

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

    /**
     * This will clear the progress bar then load from the numbers.txt file
     *  The LoadFilesTask is an Asynctask that loads on a different thread
     * @param view default param
     */
    public void loadFromFile(View view) {
        ProgressBar bar = (ProgressBar) findViewById(R.id.progressBar);
        bar.setProgress(0);
        new LoadFilesTask().execute();

    }

    /**
     * Sets the Progress Bar to 0
     * Clears the listView on the main screen
     * @param view default param
     */
    public void clearList(View view) {
        ProgressBar bar = (ProgressBar) findViewById(R.id.progressBar);
        bar.setProgress(0);
        ListView mainListView = (ListView) findViewById(R.id.listView);
        ArrayAdapter<Integer> adapter = (ArrayAdapter<Integer>) mainListView.getAdapter();
        if (adapter != null)
            adapter.clear();
    }

    /**
     * Creates a CreateFileTask that is an Asynctask
     *     that will make a file with a sequence of numbers
     * @param view default param
     */
    public void createFile(View view) {
        ProgressBar bar = (ProgressBar) findViewById(R.id.progressBar);
        bar.setProgress(0);
        new CreateFileTask().execute();

    }

    /**
     * Asynctask that will create a file and add numbers per the NUMITEMS constant
     */
    private class CreateFileTask extends
            AsyncTask<Void, Integer, Void> {

        /**
         * Creates and writes a file
         * updates on each number
         * @param params no input used
         * @return dose not return anything
         */
        @Override
        protected Void doInBackground(Void... params) {
            OutputStreamWriter outputStreamNumbers;
            try {
                outputStreamNumbers =
                        new OutputStreamWriter(openFileOutput(FILENAME, Context.MODE_PRIVATE));

                for (Integer numInput = 1; numInput <= NUMITEMS; numInput++) {
                    outputStreamNumbers.write(numInput.toString() + "\n");
                    Thread.sleep(250);
                    publishProgress((int) (((double) numInput / NUMITEMS) * 100));
                    if (isCancelled())
                        break;
                }

                outputStreamNumbers.close();
            } catch (FileNotFoundException e) {
                Log.v(TAG, "FILE CREATION ISSUE!!!");
            } catch (IOException err) {
                Log.v(TAG, "CAN'T WRITE");
            } catch (InterruptedException e) {
                Log.v(TAG, "INTERRUPTED!!!");
            }
            return null;
        }

        /**
         * calls to update the progress bar
         * @param progress Integer from 0 - 100
         */
        protected void onProgressUpdate(Integer... progress) {
            setProgressPercent(progress[0]);
        }

    }

    /**
     * Loads the items from FILENAME constant
     */
    private class LoadFilesTask extends
            AsyncTask<Void, Integer, ArrayList<Integer>> {

        @Override
        protected ArrayList<Integer> doInBackground(Void... params) {
            ArrayList<Integer> numberList = new ArrayList<>();
            try {
                InputStream readFiles = openFileInput(FILENAME);

                if (readFiles != null) {
                    InputStreamReader inReader = new InputStreamReader(readFiles);
                    BufferedReader brInput = new BufferedReader(inReader);
                    String currLine;
                    double progressCounter = 0;
                    while ((currLine = brInput.readLine()) != null) {
                        numberList.add(Integer.valueOf(currLine));
                        Thread.sleep(250);
                        progressCounter++;
                        publishProgress((int) ((progressCounter / NUMITEMS) * 100));
                        if (isCancelled())
                            break;
                    }
                    readFiles.close();

                }
            } catch (FileNotFoundException err) {
                Log.d(TAG, "FILE MISSING!!!!");
            } catch (IOException err) {
                Log.d(TAG, "CAN'T WRITE!!!");
            } catch (InterruptedException e) {
                Log.v(TAG, "INTERRUPTED!!!");
            }
            return numberList;
        }

        /**
         * Updates after each item read in
         * @param progress Integer from 0 - 100
         */
        protected void onProgressUpdate(Integer... progress) {
            setProgressPercent(progress[0]);
        }

        /**
         * This will set the read in numbers to the listView
         * @param result List of Integers read in from the file
         */
        protected void onPostExecute(ArrayList<Integer> result) {
            setResults(result);
        }
    }

    /**
     * Takes a list of Integer objects and writes them to a listView in the main activity
     * @param result numbers to be added to the list view
     */
    private void setResults(ArrayList<Integer> result) {
        ListView mainListView = (ListView) findViewById(R.id.listView);
        mainListView.setAdapter(new ArrayAdapter<>(this,
                simple_list_item_1, result));
    }


    /**
     * Updates the progress bar in the main view
     * @param progress Integer from 0 - 100 representing percent complete
     */
    private void setProgressPercent(Integer progress) {
        ProgressBar myBar = (ProgressBar) findViewById(R.id.progressBar);
        myBar.setProgress(progress);

    }

}

