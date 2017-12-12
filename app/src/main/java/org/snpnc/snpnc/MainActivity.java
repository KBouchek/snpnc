package org.snpnc.snpnc;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public Dialog dialog;
    public AlertDialog.Builder builder;
    public AlertDialog alert;
    int width,height;
    public ArrayList<Rotation> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("SN PNC");
        setSupportActionBar(toolbar);

        list = new ArrayList<Rotation>();

        Rotation rotation = new Rotation();
        rotation.setCont("AMERIQUE NORD");
        rotation.setEsc("ATL");
        rotation.setOn("3");
        rotation.setAnumvol("682");
        rotation.setAtpsvol("09H55");
        rotation.setNbrp("2");
        rotation.setIrmenu("51,01");
        rotation.setTotalir("102,02");
        rotation.setTep("26H50");
        rotation.setRnumvol("681");
        rotation.setRtpsvol("08H15");
        rotation.setRpc("3 RNN");
        rotation.setPdj("");
        rotation.setChcdg("Si retard > à 2H45mn");
        list.add((rotation));

        Rotation rotationx = new Rotation();
        rotationx.setCont("AMERIQUE NORD");
        rotationx.setEsc("ATL");
        rotationx.setOn("3");
        rotationx.setAnumvol("688");
        rotationx.setAtpsvol("09H50");
        rotationx.setNbrp("2");
        rotationx.setIrmenu("51,01");
        rotationx.setTotalir("102,02");
        rotationx.setTep("26h30");
        rotationx.setRnumvol("689");
        rotationx.setRtpsvol("08H20");
        rotationx.setRpc("3 RNN");
        rotationx.setPdj("");
        rotationx.setChcdg("Si retard > à 2H40mn");
        list.add((rotationx));

        Rotation rotationy = new Rotation();
        rotationy.setCont("AMERIQUE NORD");
        rotationy.setEsc("BOS");
        rotationy.setOn("3");
        rotationy.setAnumvol("334");
        rotationy.setAtpsvol("08H05");
        rotationy.setNbrp("2");
        rotationy.setIrmenu("51,01");
        rotationy.setTotalir("102,02");
        rotationy.setTep("26H00");
        rotationy.setRnumvol("333");
        rotationy.setRtpsvol("06H35");
        rotationy.setRpc("2 RNN");
        rotationy.setPdj("");
        rotationy.setChcdg("Si retard > à 3H25mn");
        list.add((rotationy));

        Button angryButton = (Button) findViewById(R.id.btnRegion);
        angryButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                tutu();
            }
        });

        //toolbar.setLogo(R.drawable.logo);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                //showDialog();
                tutu();
            }
        });

         width = (int)(getResources().getDisplayMetrics().widthPixels*0.80);
         height = (int)(getResources().getDisplayMetrics().heightPixels*0.70);



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

    public void showDialog() {
        dialog = new Dialog(this);
        builder = new AlertDialog.Builder( this );



        ListView listView = new ListView(this);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //dialog.cancel();
                alert.cancel();
                //imageList.get(position); // here you will get the clicked item from
                //your imagelist and you can check by getting a title  by using this

                //String title= imageList.get(position).getTitle();
                //if(title.equals("you title to match")){
                //do your action or you can get a particular position and click there
                //}
            }
        });
        listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, new String[] {"item 1", "item 2", "item 3", "item 3", "item 3", "item 3", "item 3", "item 3", "item 3", "item 3", "item 3", "item 3", "item 3", "item 3"}));

        builder.setTitle("Pick A Colour");
        dialog.setTitle("Pick A Colour");
        //dialog.setContentView(listView);
        builder.setView(listView);
        //dialog.getWindow().setLayout(width, height);

        //dialog.show();
        alert = builder.create();
        alert.getWindow().setLayout(width, height);
        alert.show();
    }
    public void tutu() {


        ArrayList<Rotation> secondList = new ArrayList<Rotation>();
  /**/
         for (int counter = 0; counter < list.size(); counter++) {
             Rotation r = list.get(counter);
             if (r.getEsc() == "ATL") {
                 Log.i("KK", "== " + r.getAnumvol());
                 secondList.add(r);
             }
        }
        Log.i("KK", "== " + secondList.size());
        CharSequence colors[] = new CharSequence[secondList.size()];
        for (int counter = 0; counter < secondList.size(); counter++) {
            Rotation r = secondList.get(counter);
            colors[counter]= r.getAnumvol();
        }
      //  for(Rotation article : list)
        //{
// or equalsIgnoreCase or whatever your conditon is
         //   if (article.getEsc().equalsIgnoreCase("ATL")) {
// do something
                //secondList.add(article);
          //  }
      //  }



      //  CharSequence colors[] = new CharSequence[] {"red", "green", "blue", "black"};


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Pick a color");
        builder.setItems(colors, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // the user clicked on colors[which]
                Log.i("KK","which = "+which);
            }
        });
        builder.show();
    }
}
