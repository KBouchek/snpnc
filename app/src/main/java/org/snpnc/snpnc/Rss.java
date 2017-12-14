package org.snpnc.snpnc;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.List;

public class Rss extends AppCompatActivity {
    private Rss local;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rss);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        local = this;


        // task.execute("view-source:https://snpnc.org/rss");
        //view-source:https://snpnc.org/rss
        // Debug the thread name
        //Log.d("ITCRssReader", Thread.currentThread().getName())
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(haveNetworkConnection()) ReFreshList(); else lauchAlert();
                //Snackbar.make(view, "Rafraichissement", Snackbar.LENGTH_LONG)
                 //       .setAction("Action", null).show();
            }
        });
        if(haveNetworkConnection()) ReFreshList(); else lauchAlert();
    }

    public void ReFreshList() {
        GetRSSDataTask task = new GetRSSDataTask();
        // Start download RSS task
        task.execute("https://snpnc.org/rss");
        ProgressBar  progressBar = (ProgressBar)findViewById((R.id.progressBar));
        progressBar.setVisibility(View.VISIBLE);
        ListView itcItems = (ListView) findViewById(R.id.listMainView);
        itcItems.setVisibility(View.GONE);
    }

    private class GetRSSDataTask extends AsyncTask<String, Void, List<RssItem>> {
        @Override
        protected List<RssItem> doInBackground(String... urls) {

            // Debug the task thread name
            Log.d("ITCRssReader", Thread.currentThread().getName());

            try {
                // Create RSS reader
                RssReader rssReader = new RssReader(urls[0]);

                // Parse RSS, get items
                return rssReader.getItems();

            } catch (Exception e) {
                Log.e("ITCRssReader", e.getMessage());
            }

            return null;
        }

        @Override
        protected void onPostExecute(List<RssItem> result) {

            // Get a ListView from main view
            ListView itcItems = (ListView) findViewById(R.id.listMainView);
            itcItems.setVisibility(View.VISIBLE);
            ProgressBar  progressBar = (ProgressBar)findViewById((R.id.progressBar));
            progressBar.setVisibility(View.GONE);
            // Create a list adapter
            ArrayAdapter<RssItem> adapter = new ArrayAdapter<RssItem>(local,android.R.layout.simple_list_item_1, result);
            // Set list adapter for the ListView
            itcItems.setAdapter(adapter);

            // Set list view item click listener
            itcItems.setOnItemClickListener(new ListListener(result, local));
        }
    }


    private boolean haveNetworkConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }

    public void lauchAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Pas de connexion")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setCancelable(true)
                .setMessage("Vérifier votre connectivité")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();

                    }
                });

        AlertDialog alert = builder.create();
        alert.show();
    }

}
