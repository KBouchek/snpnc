package org.snpnc.snpnc;

import android.app.Dialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FlyIR extends AppCompatActivity {
    public Dialog dialog;
    public AlertDialog.Builder builder;
    public AlertDialog alert;
    public TextView tv_region,tv_esc,tv_on,tv_vols;
    public Button btnRegion,btnEsc,btnOn,btnVols;

    int width,height;
    public ArrayList<Rotation> list;
    public String[] name_regions,name_regions_short;
    public String[] name_escales;
    public String[] name_on;
    public String[] name_vols,vol_string_array;
    public TableLayout table_layout_result;
    public TextView result_atpsvol,result_rtpsvol,result_somme_tpsvol,
            result_repos_escale,
            result_rpc,
            result_ir_menufrais,result_ir_total,
            result_ptd_ircdg,
            result_cha_cdg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fly_ir);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setVisibility(View.GONE);
        /*fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        final Animation animAlpha = AnimationUtils.loadAnimation(this, R.anim.anim_alpha);
        list = new ArrayList<Rotation>();

        btnRegion = (Button) findViewById(R.id.btnRegion);
        btnEsc = (Button)findViewById(R.id.btnEsc);
        btnOn = (Button)findViewById(R.id.btnOn);
        btnVols = (Button)findViewById(R.id.btnVols);

        tv_region = (TextView)findViewById(R.id.regx) ;
        tv_esc = (TextView)findViewById(R.id.esc) ;
        tv_on = (TextView)findViewById(R.id.on) ;
        tv_vols = (TextView)findViewById(R.id.vols) ;

        table_layout_result = (TableLayout)findViewById(R.id.table_layout_result);

        result_atpsvol = (TextView) findViewById(R.id.result_atpsvol);
        result_rtpsvol = (TextView) findViewById(R.id.result_rtpsvol);
        result_somme_tpsvol = (TextView) findViewById(R.id.result_somme_tpsvol);
        result_repos_escale = (TextView) findViewById(R.id.result_repos_escale);
        result_rpc = (TextView) findViewById(R.id.result_rpc);
        result_ir_menufrais = (TextView) findViewById(R.id.result_ir_menufrais);
        result_ir_total = (TextView) findViewById(R.id.result_ir_total);
        result_ptd_ircdg = (TextView) findViewById(R.id.result_ptd_ircdg);
        result_cha_cdg = (TextView) findViewById(R.id.result_cha_cdg);


        width = (int)(getResources().getDisplayMetrics().widthPixels*0.80);
        height = (int)(getResources().getDisplayMetrics().heightPixels*0.70);

        initEscalesx();
        initVariables();

        btnRegion.setText("Région");
        tv_region.setText("");

        btnEsc.setText("Escale");
        btnEsc.setVisibility(View.GONE);
        tv_esc.setText("");

        btnOn.setText("On");
        btnOn.setVisibility(View.GONE);
        tv_on.setText("");

        btnVols.setText("Vols");
        btnVols.setVisibility(View.GONE);
        tv_vols.setText("");

        btnRegion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnRegion.startAnimation(animAlpha);
                clickRegion();
            }
        });
        btnEsc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnEsc.startAnimation(animAlpha);
                clickEscale();
            }
        });
        btnOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnOn.startAnimation(animAlpha);
                clickOn();
            }
        });
        btnVols.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnVols.startAnimation(animAlpha);
                clickVols();
            }
        });

        btnRegion.startAnimation(animAlpha);
    }

    public void clickRegion() {

        tv_region.setText("");
        btnRegion.setText("Région");

        btnEsc.setText("Escale");
        btnEsc.setVisibility(View.GONE);
        tv_esc.setText("");

        btnOn.setText("On");
        btnOn.setVisibility(View.GONE);
        tv_on.setText("");

        btnVols.setText("Vols");
        btnVols.setVisibility(View.GONE);
        tv_vols.setText("");

        table_layout_result.setVisibility(View.GONE);

        dialog = new Dialog(this);
        builder = new AlertDialog.Builder( this );

        ListView listView = new ListView(this);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String regionid = name_regions[position];
                String regionid_short = name_regions_short[position];
                Log.i("KK", "== position " + position+ " regionid = "+ regionid+ " name_regions_short "+regionid_short);
                tv_region.setText(regionid_short);
                btnRegion.setText(regionid);
                btnEsc.setVisibility(View.VISIBLE);

                alert.cancel();
            }
        });

        listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,name_regions));
        builder.setView(listView);
        alert = builder.create();
        alert.getWindow().setLayout(width, height);
        alert.show();
    }

    public void clickEscale() {

        tv_esc.setText("");

        btnOn.setText("On");
        btnOn.setVisibility(View.GONE);
        tv_on.setText("");

        btnVols.setText("Vols");
        btnVols.setVisibility(View.GONE);
        tv_vols.setText("");

        table_layout_result.setVisibility(View.GONE);

        ArrayList<Rotation> local_rotations = new ArrayList<>();
        String region_string;
        if(tv_region.getText() != null && tv_region.getText() != ""){
            region_string = tv_region.getText().toString();
        } else return;

        for(int i=0; i<list.size(); i++) {
            if(list.get(i).getCont().equals(region_string)) {
                local_rotations.add(list.get(i));
            }
        }
        int nb_resultats_escales = local_rotations.size();
        name_escales = new String[nb_resultats_escales];
        for (int i = 0; i < nb_resultats_escales;i++) {
            name_escales[i] = local_rotations.get(i).getEsc();
        }

        List<String> listlocal = Arrays.asList(name_escales);
        Set<String> set = new HashSet<String>(listlocal);
        name_escales = new String[set.size()];
        set.toArray(name_escales);

        Arrays.sort(name_escales);

        dialog = new Dialog(this);
        builder = new AlertDialog.Builder( this );

        ListView listView = new ListView(this);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String escale = name_escales[position];
                tv_esc.setText(escale);
                btnEsc.setText(escale);
                int[] num_rep = getnumRotation_reg_esc(tv_region.getText().toString(),escale);
                Log.i("KK", "num reponses  = "+num_rep[0]+  " / = "+num_rep[1]);
                if(num_rep[0] <= 0) {alert.cancel(); return;}
                if(num_rep[0] == 1) {display_rest(num_rep[1],false);alert.cancel(); return;}
                if(num_rep[0] > 1) {

                    int[] numons = getnumOn_reg_esc(tv_region.getText().toString(),escale);
                    if(numons[0] == 1) {
                        btnOn.setEnabled(false);
                        btnOn.setVisibility(View.VISIBLE);
                        btnOn.setText(numons[1]+" ON");
                        tv_on.setText(""+numons[1]);

                        btnVols.setVisibility(View.VISIBLE);
                        btnVols.setEnabled(true);
                        tv_vols.setText("");
                        //display_rest(numons[1]);
                        alert.cancel();
                        return;
                    }
                    btnOn.setEnabled(true);
                    btnOn.setVisibility(View.VISIBLE);
                    //btnOn.setEnabled(false);
                    //btnVols.setEnabled(false);
                    alert.cancel();
                    return;
                }


            }
        });

        listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,name_escales));
        builder.setView(listView);
        alert = builder.create();
        alert.getWindow().setLayout(width, height);
        alert.show();
    }

    public void clickOn() {
        Log.i("KK", " clickOn Okkkkkkkkkkk");
        tv_on.setText("");

        btnVols.setText("Vols");
        btnVols.setVisibility(View.GONE);
        tv_vols.setText("");

        table_layout_result.setVisibility(View.GONE);

        ArrayList<Rotation> local_rotations = new ArrayList<>();
        String region_string, escale_string;

        if(tv_region.getText() != null && tv_region.getText() != "" && tv_esc.getText() != null && tv_esc.getText() != ""){
            region_string = tv_region.getText().toString();
            escale_string = tv_esc.getText().toString();
        } else return;
        Log.i("KK", "******* 1111");

        Rotation r = new Rotation();
        for(int i=0; i<list.size(); i++) {
            if(list.get(i).getCont().equals(region_string) && list.get(i).getEsc().equals(escale_string)) {
                local_rotations.add(list.get(i));
                r = list.get(i);
            }
        }

        if(local_rotations.size() == 1) {
            display_IR_for_rotation(r);
            return;
        }

        Log.i("KK", "******* "+local_rotations);
        int nb_resultats_on = local_rotations.size();
        name_on = new String[nb_resultats_on];
        for (int i = 0; i < nb_resultats_on;i++) {
            name_on[i] = local_rotations.get(i).getOn();
        }

        List<String> listlocal = Arrays.asList(name_on);
        Set<String> set = new HashSet<String>(listlocal);
        name_on = new String[set.size()];
        set.toArray(name_on);

        Arrays.sort(name_on);

        dialog = new Dialog(this);
        builder = new AlertDialog.Builder( this );

        ListView listView = new ListView(this);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String on = name_on[position];
                tv_on.setText(on);
                btnOn.setText(on);

                ArrayList<Rotation> local_rotations = new ArrayList<>();
                int found_rot_id = 0;
                for(int i=0; i<list.size(); i++) {
                    if(list.get(i).getCont().equals(tv_region.getText().toString())
                            && list.get(i).getEsc().equals(tv_esc.getText().toString())
                            && list.get(i).getOn().equals(on)) {
                        local_rotations.add(list.get(i));
                        found_rot_id = i;
                    }
                }
                int nb_resultats_vols = local_rotations.size();
                if(nb_resultats_vols == 1) {
                    display_rest(found_rot_id,true);alert.cancel();return;
                }
                //if(name_on.length == 1) {Log.i("KK", "1 ON");}
                /*int[] num_rep = getnumRotation_reg_esc(tv_region.getText().toString(),escale);
                Log.i("KK", "num reponses  = "+num_rep[0]+  " / = "+num_rep[1]);
                if(num_rep[0] <= 0) return;
                if(num_rep[0] == 1) {display_rest(num_rep[1]); return;}
                if(num_rep[0] > 1) {
                    display_rest(num_rep[1]); return;
                }

                btnOn.setVisibility(View.VISIBLE);
                btnOn.setEnabled(false);*/
                btnVols.setVisibility(View.VISIBLE);
                btnVols.setEnabled(true);
                alert.cancel();
            }
        });

        listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,name_on));
        builder.setView(listView);
        alert = builder.create();
        alert.getWindow().setLayout(width, height);
        alert.show();
    }

    public void clickVols() {
        tv_vols.setText("");
        table_layout_result.setVisibility(View.GONE);

        ArrayList<Rotation> local_rotations = new ArrayList<>();
        String region_string, escale_string,on_string;
        if(tv_region.getText() != null && tv_region.getText() != "" && tv_esc.getText() != null && tv_esc.getText() != ""  && tv_on.getText() != null && tv_on.getText() != ""){
            region_string = tv_region.getText().toString();
            escale_string = tv_esc.getText().toString();
            on_string= tv_on.getText().toString();
        } else return;

        for(int i=0; i<list.size(); i++) {
            if(list.get(i).getCont().equals(region_string) && list.get(i).getEsc().equals(escale_string) && list.get(i).getOn().equals(on_string)) {
                local_rotations.add(list.get(i));
            }
        }
        int nb_resultats_vols = local_rotations.size();

        name_vols = new String[nb_resultats_vols];
        vol_string_array = new String[nb_resultats_vols];
        Rotation last_visited_id = new Rotation();
        for (int i = 0; i < nb_resultats_vols;i++) {
            name_vols[i] = "AF"+local_rotations.get(i).getAnumvol()+" - AF"+local_rotations.get(i).getRnumvol();
            vol_string_array[i] = local_rotations.get(i).getAnumvol()+"-"+local_rotations.get(i).getRnumvol();
            //last_visited_id = local_rotations.get(i);
        }
        if(nb_resultats_vols <= 0) return;
        if(nb_resultats_vols == 1) {display_IR_for_rotation(last_visited_id);return;}
        Arrays.sort(name_vols);

        dialog = new Dialog(this);
        builder = new AlertDialog.Builder( this );

        final ListView listView = new ListView(this);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String vol = name_vols[position];
                String vol_tv =  vol_string_array[position];
                btnVols.setText(vol);
                String replaceString= vol.replace(" ","");
                String replaceString1= replaceString.replace("AF","");
                //String copy = vol.replace('AF', '');
                //tv_vols.setText(vol_tv);
                tv_vols.setText(replaceString1);

                Rotation r = new Rotation();
                for(int i=0; i<list.size(); i++) {
                    String vol_search = list.get(i).getAnumvol()+"-"+list.get(i).getRnumvol();
                    if(list.get(i).getCont().equals(tv_region.getText().toString())
                            && list.get(i).getEsc().equals(tv_esc.getText().toString())
                            && vol_search.equals(vol_tv)) {
                        r = list.get(i);
                        display_IR_for_rotation(r);
                        alert.cancel();
                        return;
                    }
                }
                //tv_on.setText(on);
                //btnOn.setText(on);
                //if(name_on.length == 1) {Log.i("KK", "1 ON");}
                /*int[] num_rep = getnumRotation_reg_esc(tv_region.getText().toString(),escale);
                Log.i("KK", "num reponses  = "+num_rep[0]+  " / = "+num_rep[1]);
                if(num_rep[0] <= 0) return;
                if(num_rep[0] == 1) {display_rest(num_rep[1]); return;}
                if(num_rep[0] > 1) {
                    display_rest(num_rep[1]); return;
                }

                btnOn.setVisibility(View.VISIBLE);
                btnOn.setEnabled(false);*/
                //btnVols.setVisibility(View.VISIBLE);
                //btnVols.setEnabled(true);
                alert.cancel();
            }
        });

        listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,name_vols));
        builder.setView(listView);
        alert = builder.create();
        alert.getWindow().setLayout(width, height);
        alert.show();
    }
    /*****************************/
    /***** fcts graphiques *******/
    /*****************************/
    public void display_rest(int id,boolean onx){
        Rotation r = list.get(id);


        btnOn.setVisibility(View.VISIBLE);
        btnOn.setText(r.getOn()+" ON");
        btnOn.setEnabled(onx);

        tv_on.setText(r.getOn());
        btnVols.setText("AF"+r.getAnumvol()+" - AF"+r.getRnumvol());
        btnVols.setVisibility(View.VISIBLE);
        btnVols.setEnabled(false);

        tv_vols.setText(r.getAnumvol()+"-"+r.getRnumvol());

        display_IR_for_rotation(r);
    }

    public void display_IR_for_rotation(Rotation r){

        table_layout_result.setVisibility(View.VISIBLE);
        result_atpsvol.setText(r.getAtpsvol());
        result_rtpsvol.setText(r.getRtpsvol());
        result_somme_tpsvol.setText(r.getTpsvol());
        result_repos_escale.setText(r.getTep());
        result_rpc.setText(r.getRpc());
        result_ir_menufrais.setText(r.getIrmenu());
        result_ir_total.setText(r.getTotalir());
        result_ptd_ircdg.setText(r.getPdj());
        result_cha_cdg.setText(r.getChcdg());
    }


    /*****************************/
    /***** fcts de selections ****/
    public int[] getnumRotation_reg_esc(String reg,String esc) {
        int[] retour0 = {0,0};
        int[] retour1 = {1,0};
        int[] retourelse = {0,0};
        int ii = 0;
        int found_id = 0;
        for(int i=0; i<list.size(); i++) {
            if(list.get(i).getCont().equals(reg) && list.get(i).getEsc().equals(esc)) {
                found_id = i;
                ii++;
            }
        }
        if(ii <=0) return retour0;
        if(ii == 1) {
            retour1[1] = found_id;
            return retour1;
        }
        retourelse[0] = ii;
        return retourelse;
    }

    public int[] getnumOn_reg_esc(String reg,String esc) {
        int[] retour0 = {0,0};
        int found_id = 0;
        int ii = 0;
        ArrayList<Rotation> arx = new ArrayList<>();
        for(int i=0; i<list.size(); i++) {
            if(list.get(i).getCont().equals(reg) && list.get(i).getEsc().equals(esc)) {
                found_id = Integer.valueOf(list.get(i).getOn());
                arx.add(list.get(i));
                ii++;
            }
        }
        int num_ons = arx.size();
        String[] ons = new String[num_ons];
        for (int i = 0; i < num_ons;i++) {
            ons[i] = arx.get(i).getOn();
        }

        List<String> listlocal = Arrays.asList(ons);
        Set<String> set = new HashSet<String>(listlocal);
        ons = new String[set.size()];
        set.toArray(ons);

        Arrays.sort(ons);
        if(ons.length == 0) return retour0;
        if(ons.length == 1) {
            int[] retour1 = {1,found_id};
            return retour1;
        }
        int[] retour2 = {ons.length,0};

        return retour2;
    }

    /*****************************/



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


    public void initEscalesx() {
        Rotation rotation1 = new Rotation();
        rotation1.setCont("ame");
        rotation1.setId("1");
        rotation1.setEsc("ATL");
        rotation1.setOn("3");
        rotation1.setAnumvol("682");
        rotation1.setAtpsvol("09H55");
        rotation1.setNbrp("2");
        rotation1.setIrmenu("51,01");
        rotation1.setTotalir("102,02");
        rotation1.setTep("26H50");
        rotation1.setRnumvol("681");
        rotation1.setRtpsvol("08H15");
        rotation1.setRpc("3 RNN");
        rotation1.setPdj("non");
        rotation1.setChcdg("Si retard > 2H45mn");
        list.add((rotation1));

        Rotation rotation2 = new Rotation();
        rotation2.setCont("ame");
        rotation2.setId("2");
        rotation2.setEsc("ATL");
        rotation2.setOn("3");
        rotation2.setAnumvol("688");
        rotation2.setAtpsvol("09H50");
        rotation2.setNbrp("2");
        rotation2.setIrmenu("51,01");
        rotation2.setTotalir("102,02");
        rotation2.setTep("26h30");
        rotation2.setRnumvol("689");
        rotation2.setRtpsvol("08H20");
        rotation2.setRpc("3 RNN");
        rotation2.setPdj("non");
        rotation2.setChcdg("Si retard > 2H40mn");
        list.add((rotation2));

        Rotation rotation3 = new Rotation();
        rotation3.setCont("ame");
        rotation3.setId("3");
        rotation3.setEsc("BOS");
        rotation3.setOn("3");
        rotation3.setAnumvol("334");
        rotation3.setAtpsvol("08H05");
        rotation3.setNbrp("2");
        rotation3.setIrmenu("51,01");
        rotation3.setTotalir("102,02");
        rotation3.setTep("26H00");
        rotation3.setRnumvol("333");
        rotation3.setRtpsvol("06H35");
        rotation3.setRpc("2 RNN");
        rotation3.setPdj("non");
        rotation3.setChcdg("Si retard > 3H25mn");
        list.add((rotation3));

        Rotation rotation4 = new Rotation();
        rotation4.setCont("ame");
        rotation4.setId("4");
        rotation4.setEsc("BOS");
        rotation4.setOn("4");
        rotation4.setAnumvol("334");
        rotation4.setAtpsvol("08H05");
        rotation4.setNbrp("4");
        rotation4.setIrmenu("51,01");
        rotation4.setTotalir("204,04");
        rotation4.setTep("26H00");
        rotation4.setRnumvol("333");
        rotation4.setRtpsvol("06H35");
        rotation4.setRpc("2 RNN");
        rotation4.setPdj("non");
        rotation4.setChcdg("Si retard > 3H25mn");
        list.add((rotation4));

        Rotation rotation5 = new Rotation();
        rotation5.setCont("ame");
        rotation5.setId("5");
        rotation5.setEsc("CUN");
        rotation5.setOn("4");
        rotation5.setAnumvol("650");
        rotation5.setAtpsvol("10H55");
        rotation5.setNbrp("4");
        rotation5.setIrmenu("23,58");
        rotation5.setTotalir("94,32");
        rotation5.setTep("50H15");
        rotation5.setRnumvol("651");
        rotation5.setRtpsvol("09H30");
        rotation5.setRpc("3 RNN");
        rotation5.setPdj("non");
        rotation5.setChcdg("Si retard > 1h30mn");
        list.add((rotation5));

        Rotation rotation6 = new Rotation();
        rotation6.setCont("ame");
        rotation6.setId("6");
        rotation6.setEsc("CUN");
        rotation6.setOn("5");
        rotation6.setAnumvol("650");
        rotation6.setAtpsvol("10H55");
        rotation6.setNbrp("6");
        rotation6.setIrmenu("23,58");
        rotation6.setTotalir("141,48");
        rotation6.setTep("74H15");
        rotation6.setRnumvol("651");
        rotation6.setRtpsvol("09H30");
        rotation6.setRpc("3 RNN");
        rotation6.setPdj("non");
        rotation6.setChcdg("Si retard > 1h30mn");
        list.add((rotation6));

        Rotation rotation7 = new Rotation();
        rotation7.setCont("ame");
        rotation7.setId("7");
        rotation7.setEsc("CUN");
        rotation7.setOn("6");
        rotation7.setAnumvol("650");
        rotation7.setAtpsvol("10H55");
        rotation7.setNbrp("8");
        rotation7.setIrmenu("23,58");
        rotation7.setTotalir("188,64");
        rotation7.setTep("98H15");
        rotation7.setRnumvol("651");
        rotation7.setRtpsvol("09H30");
        rotation7.setRpc("3 RNN");
        rotation7.setPdj("non");
        rotation7.setChcdg("Si retard > 1h30mn");
        list.add((rotation7));

        Rotation rotation8 = new Rotation();
        rotation8.setCont("ame");
        rotation8.setId("8");
        rotation8.setEsc("CUN");
        rotation8.setOn("7");
        rotation8.setAnumvol("650");
        rotation8.setAtpsvol("10H55");
        rotation8.setNbrp("10");
        rotation8.setIrmenu("23,58");
        rotation8.setTotalir("235,80");
        rotation8.setTep("122H15");
        rotation8.setRnumvol("651");
        rotation8.setRtpsvol("09H30");
        rotation8.setRpc("3 RNN");
        rotation8.setPdj("non");
        rotation8.setChcdg("Si retard > 1h30mn");
        list.add((rotation8));

        Rotation rotation9 = new Rotation();
        rotation9.setCont("ame");
        rotation9.setId("9");
        rotation9.setEsc("DTW");
        rotation9.setOn("3");
        rotation9.setAnumvol("378");
        rotation9.setAtpsvol("09H00");
        rotation9.setNbrp("2");
        rotation9.setIrmenu("51,01");
        rotation9.setTotalir("102,02");
        rotation9.setTep("26H55");
        rotation9.setRnumvol("377");
        rotation9.setRtpsvol("07H50");
        rotation9.setRpc("2 RNN");
        rotation9.setPdj("non");
        rotation9.setChcdg("Si retard > 3h10mn");
        list.add((rotation9));

        Rotation rotation10 = new Rotation();
        rotation10.setCont("ame");
        rotation10.setId("10");
        rotation10.setEsc("DTW");
        rotation10.setOn("4");
        rotation10.setAnumvol("378");
        rotation10.setAtpsvol("09H00");
        rotation10.setNbrp("4");
        rotation10.setIrmenu("51,01");
        rotation10.setTotalir("204,04");
        rotation10.setTep("50H55");
        rotation10.setRnumvol("377");
        rotation10.setRtpsvol("07H50");
        rotation10.setRpc("2 RNN");
        rotation10.setPdj("non");
        rotation10.setChcdg("Si retard > 3h10mn");
        list.add((rotation10));

        Rotation rotation11 = new Rotation();
        rotation11.setCont("ame");
        rotation11.setId("11");
        rotation11.setEsc("DTW");
        rotation11.setOn("5");
        rotation11.setAnumvol("378");
        rotation11.setAtpsvol("09H00");
        rotation11.setNbrp("6");
        rotation11.setIrmenu("51,01");
        rotation11.setTotalir("306,06");
        rotation11.setTep("74H55");
        rotation11.setRnumvol("377");
        rotation11.setRtpsvol("07H50");
        rotation11.setRpc("2 RNN");
        rotation11.setPdj("non");
        rotation11.setChcdg("Si retard > 3h10mn");
        list.add((rotation11));

        Rotation rotation12 = new Rotation();
        rotation12.setCont("ame");
        rotation12.setId("12");
        rotation12.setEsc("IAD");
        rotation12.setOn("3");
        rotation12.setAnumvol("054");
        rotation12.setAtpsvol("08H50");
        rotation12.setNbrp("2");
        rotation12.setIrmenu("51,01");
        rotation12.setTotalir("102,02");
        rotation12.setTep("26H05");
        rotation12.setRnumvol("055");
        rotation12.setRtpsvol("07H25");
        rotation12.setRpc("2 RNN");
        rotation12.setPdj("non");
        rotation12.setChcdg("Si retard > 3h35mn");
        list.add((rotation12));

        Rotation rotation13 = new Rotation();
        rotation13.setCont("ame");
        rotation13.setId("13");
        rotation13.setEsc("IAH");
        rotation13.setOn("3");
        rotation13.setAnumvol("636");
        rotation13.setAtpsvol("11h10");
        rotation13.setNbrp("2");
        rotation13.setIrmenu("51,01");
        rotation13.setTotalir("102,02");
        rotation13.setTep("25H45");
        rotation13.setRnumvol("639");
        rotation13.setRtpsvol("09H30");
        rotation13.setRpc("3 RNN");
        rotation13.setPdj("non");
        rotation13.setChcdg("Si retard > 1h30mn");
        list.add((rotation13));

        Rotation rotation14 = new Rotation();
        rotation14.setCont("ame");
        rotation14.setId("14");
        rotation14.setEsc("JFK");
        rotation14.setOn("3");
        rotation14.setAnumvol("006");
        rotation14.setAtpsvol("08H35");
        rotation14.setNbrp("2");
        rotation14.setIrmenu("51,01");
        rotation14.setTotalir("102,02");
        rotation14.setTep("26H45");
        rotation14.setRnumvol("007");
        rotation14.setRtpsvol("07H20");
        rotation14.setRpc("2 RNN");
        rotation14.setPdj("non");
        rotation14.setChcdg("Si retard > 3h40mn");
        list.add((rotation14));

        Rotation rotation15 = new Rotation();
        rotation15.setCont("ame");
        rotation15.setId("15");
        rotation15.setEsc("JFK");
        rotation15.setOn("3");
        rotation15.setAnumvol("008");
        rotation15.setAtpsvol("08H40");
        rotation15.setNbrp("1+1CDG");
        rotation15.setIrmenu("51,01");
        rotation15.setTotalir("69,41");
        rotation15.setTep("26H00");
        rotation15.setRnumvol("009");
        rotation15.setRtpsvol("07H05");
        rotation15.setRpc("2 RNN");
        rotation15.setPdj("non");
        rotation15.setChcdg("Si retard > 3h55mn");
        list.add((rotation15));

        Rotation rotation16 = new Rotation();
        rotation16.setCont("ame");
        rotation16.setId("16");
        rotation16.setEsc("JFK");
        rotation16.setOn("3");
        rotation16.setAnumvol("010");
        rotation16.setAtpsvol("08H45");
        rotation16.setNbrp("2");
        rotation16.setIrmenu("51,01");
        rotation16.setTotalir("102,02");
        rotation16.setTep("27H10");
        rotation16.setRnumvol("011");
        rotation16.setRtpsvol("07H20");
        rotation16.setRpc("2 RNN");
        rotation16.setPdj("non");
        rotation16.setChcdg("Si retard > 3h40mn");
        list.add((rotation16));

        Rotation rotation17 = new Rotation();
        rotation17.setCont("ame");
        rotation17.setId("17");
        rotation17.setEsc("JFK");
        rotation17.setOn("3");
        rotation17.setAnumvol("022");
        rotation17.setAtpsvol("08H50");
        rotation17.setNbrp("2");
        rotation17.setIrmenu("51,01");
        rotation17.setTotalir("102,02");
        rotation17.setTep("28H25");
        rotation17.setRnumvol("023");
        rotation17.setRtpsvol("07H15");
        rotation17.setRpc("2 RNN");
        rotation17.setPdj("IR 13 ");
        rotation17.setChcdg("Si retard > 3h45mn");
        list.add((rotation17));

        Rotation rotation18 = new Rotation();
        rotation18.setCont("ame");
        rotation18.setId("18");
        rotation18.setEsc("JFK-ORY");
        rotation18.setOn("3");
        rotation18.setAnumvol("032");
        rotation18.setAtpsvol("08H40");
        rotation18.setNbrp("2");
        rotation18.setIrmenu("51,01");
        rotation18.setTotalir("102,02");
        rotation18.setTep("26H50");
        rotation18.setRnumvol("019");
        rotation18.setRtpsvol("07H20");
        rotation18.setRpc("2 RNN");
        rotation18.setPdj("non");
        rotation18.setChcdg("Si retard > 3h40mn");
        list.add((rotation18));

        Rotation rotation19 = new Rotation();
        rotation19.setCont("ame");
        rotation19.setId("19");
        rotation19.setEsc("JFK");
        rotation19.setOn("4");
        rotation19.setAnumvol("008");
        rotation19.setAtpsvol("08H40");
        rotation19.setNbrp("3+1CDG");
        rotation19.setIrmenu("51,01");
        rotation19.setTotalir("171,43");
        rotation19.setTep("50H00");
        rotation19.setRnumvol("009");
        rotation19.setRtpsvol("07H05");
        rotation19.setRpc("2 RNN");
        rotation19.setPdj("non");
        rotation19.setChcdg("Si retard > 3h55mn");
        list.add((rotation19));

        Rotation rotation20 = new Rotation();
        rotation20.setCont("ame");
        rotation20.setId("20");
        rotation20.setEsc("JFK");
        rotation20.setOn("4");
        rotation20.setAnumvol("010");
        rotation20.setAtpsvol("08H45");
        rotation20.setNbrp("4");
        rotation20.setIrmenu("51,01");
        rotation20.setTotalir("204,04");
        rotation20.setTep("51H10");
        rotation20.setRnumvol("011");
        rotation20.setRtpsvol("07H20");
        rotation20.setRpc("2 RNN");
        rotation20.setPdj("non");
        rotation20.setChcdg("Si retard > 3h40mn");
        list.add((rotation20));

        Rotation rotation21 = new Rotation();
        rotation21.setCont("ame");
        rotation21.setId("21");
        rotation21.setEsc("JFK");
        rotation21.setOn("4");
        rotation21.setAnumvol("022");
        rotation21.setAtpsvol("08H50");
        rotation21.setNbrp("4");
        rotation21.setIrmenu("51,01");
        rotation21.setTotalir("204,04");
        rotation21.setTep("52H25");
        rotation21.setRnumvol("023");
        rotation21.setRtpsvol("07H15");
        rotation21.setRpc("2 RNN");
        rotation21.setPdj("IR 13 ");
        rotation21.setChcdg("Si retard > 3h45mn");
        list.add((rotation21));

        Rotation rotation22 = new Rotation();
        rotation22.setCont("ame");
        rotation22.setId("22");
        rotation22.setEsc("JFK");
        rotation22.setOn("5");
        rotation22.setAnumvol("010");
        rotation22.setAtpsvol("08H45");
        rotation22.setNbrp("6");
        rotation22.setIrmenu("51,01");
        rotation22.setTotalir("306,06");
        rotation22.setTep("75H10");
        rotation22.setRnumvol("011");
        rotation22.setRtpsvol("07H20");
        rotation22.setRpc("2 RNN");
        rotation22.setPdj("non");
        rotation22.setChcdg("Si retard > 3h40mn");
        list.add((rotation22));

        Rotation rotation23 = new Rotation();
        rotation23.setCont("ame");
        rotation23.setId("23");
        rotation23.setEsc("LAX");
        rotation23.setOn("4");
        rotation23.setAnumvol("066");
        rotation23.setAtpsvol("11H45");
        rotation23.setNbrp("4");
        rotation23.setIrmenu("51,01");
        rotation23.setTotalir("204,04");
        rotation23.setTep("50H25");
        rotation23.setRnumvol("065");
        rotation23.setRtpsvol("10H45");
        rotation23.setRpc("3 RNN");
        rotation23.setPdj("non");
        rotation23.setChcdg("Si retard > 0h15mn");
        list.add((rotation23));

        Rotation rotation24 = new Rotation();
        rotation24.setCont("ame");
        rotation24.setId("24");
        rotation24.setEsc("LAX");
        rotation24.setOn("4");
        rotation24.setAnumvol("066");
        rotation24.setAtpsvol("11H45");
        rotation24.setNbrp("4+1CDG");
        rotation24.setIrmenu("51,01");
        rotation24.setTotalir("222,44");
        rotation24.setTep("56H00");
        rotation24.setRnumvol("077");
        rotation24.setRtpsvol("10H45");
        rotation24.setRpc("3 RNN");
        rotation24.setPdj("non");
        rotation24.setChcdg("Si retard > 0h15mn");
        list.add((rotation24));

        Rotation rotation25 = new Rotation();
        rotation25.setCont("ame");
        rotation25.setId("25");
        rotation25.setEsc("LAX");
        rotation25.setOn("4");
        rotation25.setAnumvol("066");
        rotation25.setAtpsvol("11H45");
        rotation25.setNbrp("4+1CDG");
        rotation25.setIrmenu("51,01");
        rotation25.setTotalir("222,44");
        rotation25.setTep("53H00");
        rotation25.setRnumvol("069");
        rotation25.setRtpsvol("10H45");
        rotation25.setRpc("3 RNN");
        rotation25.setPdj("non");
        rotation25.setChcdg("Si retard > 0h15mn");
        list.add((rotation25));

        Rotation rotation26 = new Rotation();
        rotation26.setCont("ame");
        rotation26.setId("26");
        rotation26.setEsc("LAX");
        rotation26.setOn("4");
        rotation26.setAnumvol("072");
        rotation26.setAtpsvol("11H45");
        rotation26.setNbrp("4+1CDG");
        rotation26.setIrmenu("51,01");
        rotation26.setTotalir("222,44");
        rotation26.setTep("50H10");
        rotation26.setRnumvol("069");
        rotation26.setRtpsvol("10H45");
        rotation26.setRpc("3 RNN");
        rotation26.setPdj("non");
        rotation26.setChcdg("Si retard > 0h15mn");
        list.add((rotation26));

        Rotation rotation27 = new Rotation();
        rotation27.setCont("ame");
        rotation27.setId("27");
        rotation27.setEsc("LAX");
        rotation27.setOn("5");
        rotation27.setAnumvol("072");
        rotation27.setAtpsvol("11H45");
        rotation27.setNbrp("6+1CDG");
        rotation27.setIrmenu("51,01");
        rotation27.setTotalir("324,46");
        rotation27.setTep("74H10");
        rotation27.setRnumvol("069");
        rotation27.setRtpsvol("10H45");
        rotation27.setRpc("3 RNN");
        rotation27.setPdj("non");
        rotation27.setChcdg("Si retard > 0h15mn");
        list.add((rotation27));

        Rotation rotation28 = new Rotation();
        rotation28.setCont("ame");
        rotation28.setId("28");
        rotation28.setEsc("LAX");
        rotation28.setOn("5");
        rotation28.setAnumvol("076");
        rotation28.setAtpsvol("11H50");
        rotation28.setNbrp("5+1CDG");
        rotation28.setIrmenu("51,01");
        rotation28.setTotalir("273,45");
        rotation28.setTep("71H30");
        rotation28.setRnumvol("077");
        rotation28.setRtpsvol("10H45");
        rotation28.setRpc("4 RNN");
        rotation28.setPdj("non");
        rotation28.setChcdg("Si retard > 0h15mn");
        list.add((rotation28));

        Rotation rotation29 = new Rotation();
        rotation29.setCont("ame");
        rotation29.setId("29");
        rotation29.setEsc("LAX");
        rotation29.setOn("5");
        rotation29.setAnumvol("076");
        rotation29.setAtpsvol("11H50");
        rotation29.setNbrp("6");
        rotation29.setIrmenu("51,01");
        rotation29.setTotalir("306,06");
        rotation29.setTep("65H55");
        rotation29.setRnumvol("065");
        rotation29.setRtpsvol("10H45");
        rotation29.setRpc("3 RNN");
        rotation29.setPdj("non");
        rotation29.setChcdg("Si retard > 0h15mn");
        list.add((rotation29));

        Rotation rotation30 = new Rotation();
        rotation30.setCont("ame");
        rotation30.setId("30");
        rotation30.setEsc("LAX");
        rotation30.setOn("5");
        rotation30.setAnumvol("072");
        rotation30.setAtpsvol("11H45");
        rotation30.setNbrp("6");
        rotation30.setIrmenu("51,01");
        rotation30.setTotalir("306,06");
        rotation30.setTep("71H35");
        rotation30.setRnumvol("065");
        rotation30.setRtpsvol("10H45");
        rotation30.setRpc("3 RNN");
        rotation30.setPdj("non");
        rotation30.setChcdg("Si retard > 0h15mn");
        list.add((rotation30));

        Rotation rotation31 = new Rotation();
        rotation31.setCont("ame");
        rotation31.setId("31");
        rotation31.setEsc("LAX");
        rotation31.setOn("5");
        rotation31.setAnumvol("066");
        rotation31.setAtpsvol("11H45");
        rotation31.setNbrp("6");
        rotation31.setIrmenu("51,01");
        rotation31.setTotalir("306,06");
        rotation31.setTep("74H25");
        rotation31.setRnumvol("065");
        rotation31.setRtpsvol("10H45");
        rotation31.setRpc("4 RNN");
        rotation31.setPdj("non");
        rotation31.setChcdg("Si retard > 0h15mn");
        list.add((rotation31));

        Rotation rotation32 = new Rotation();
        rotation32.setCont("ame");
        rotation32.setId("32");
        rotation32.setEsc("LAX");
        rotation32.setOn("6");
        rotation32.setAnumvol("076");
        rotation32.setAtpsvol("11H50");
        rotation32.setNbrp("7+1CDG");
        rotation32.setIrmenu("51,01");
        rotation32.setTotalir("375,47");
        rotation32.setTep("95H30");
        rotation32.setRnumvol("077");
        rotation32.setRtpsvol("10H45");
        rotation32.setRpc("4 RNN");
        rotation32.setPdj("non");
        rotation32.setChcdg("Si retard > 0h15mn");
        list.add((rotation32));

        Rotation rotation33 = new Rotation();
        rotation33.setCont("ame");
        rotation33.setId("33");
        rotation33.setEsc("MEX");
        rotation33.setOn("4");
        rotation33.setAnumvol("178");
        rotation33.setAtpsvol("12H20");
        rotation33.setNbrp("4+1CDG");
        rotation33.setIrmenu("23,58");
        rotation33.setTotalir("112,72");
        rotation33.setTep("51H25");
        rotation33.setRnumvol("179");
        rotation33.setRtpsvol("10H45");
        rotation33.setRpc("3 RNN");
        rotation33.setPdj("non");
        rotation33.setChcdg("Si retard > 0h15mn");
        list.add((rotation33));

        Rotation rotation34 = new Rotation();
        rotation34.setCont("ame");
        rotation34.setId("34");
        rotation34.setEsc("MEX");
        rotation34.setOn("5");
        rotation34.setAnumvol("178");
        rotation34.setAtpsvol("12H20");
        rotation34.setNbrp("6+1CDG");
        rotation34.setIrmenu("23,58");
        rotation34.setTotalir("159,88");
        rotation34.setTep("75H25");
        rotation34.setRnumvol("179");
        rotation34.setRtpsvol("10H45");
        rotation34.setRpc("3 RNN");
        rotation34.setPdj("non");
        rotation34.setChcdg("Si retard > 0h15mn");
        list.add((rotation34));

        Rotation rotation35 = new Rotation();
        rotation35.setCont("ame");
        rotation35.setId("35");
        rotation35.setEsc("MIA");
        rotation35.setOn("3");
        rotation35.setAnumvol("090");
        rotation35.setAtpsvol("10H05");
        rotation35.setNbrp("2");
        rotation35.setIrmenu("51,01");
        rotation35.setTotalir("102,02");
        rotation35.setTep("26H40");
        rotation35.setRnumvol("099");
        rotation35.setRtpsvol("08H35");
        rotation35.setRpc("3 RNN");
        rotation35.setPdj("non");
        rotation35.setChcdg("Si retard > 2h25mn");
        list.add((rotation35));

        Rotation rotation36 = new Rotation();
        rotation36.setCont("ame");
        rotation36.setId("36");
        rotation36.setEsc("MIA");
        rotation36.setOn("4");
        rotation36.setAnumvol("090");
        rotation36.setAtpsvol("10H05");
        rotation36.setNbrp("4");
        rotation36.setIrmenu("51,01");
        rotation36.setTotalir("204,04");
        rotation36.setTep("26H40");
        rotation36.setRnumvol("099");
        rotation36.setRtpsvol("08H35");
        rotation36.setRpc("3 RNN");
        rotation36.setPdj("non");
        rotation36.setChcdg("Si retard > 2h25mn");
        list.add((rotation36));

        Rotation rotation37 = new Rotation();
        rotation37.setCont("ame");
        rotation37.setId("37");
        rotation37.setEsc("ORD");
        rotation37.setOn("3");
        rotation37.setAnumvol("136");
        rotation37.setAtpsvol("09H20");
        rotation37.setNbrp("2");
        rotation37.setIrmenu("51,01");
        rotation37.setTotalir("102,02");
        rotation37.setTep("25H40");
        rotation37.setRnumvol("137");
        rotation37.setRtpsvol("08H20");
        rotation37.setRpc("3 RNN");
        rotation37.setPdj("non");
        rotation37.setChcdg("Si retard > 2h40mn");
        list.add((rotation37));

        Rotation rotation38 = new Rotation();
        rotation38.setCont("ame");
        rotation38.setId("38");
        rotation38.setEsc("ORD");
        rotation38.setOn("4");
        rotation38.setAnumvol("136");
        rotation38.setAtpsvol("09H20");
        rotation38.setNbrp("4");
        rotation38.setIrmenu("51,01");
        rotation38.setTotalir("204,04");
        rotation38.setTep("49H40");
        rotation38.setRnumvol("137");
        rotation38.setRtpsvol("08H20");
        rotation38.setRpc("3 RNN");
        rotation38.setPdj("non");
        rotation38.setChcdg("Si retard > 2h40mn");
        list.add((rotation38));

        Rotation rotation39 = new Rotation();
        rotation39.setCont("ame");
        rotation39.setId("39");
        rotation39.setEsc("SFO");
        rotation39.setOn("4");
        rotation39.setAnumvol("084");
        rotation39.setAtpsvol("11H40");
        rotation39.setNbrp("5");
        rotation39.setIrmenu("51,01");
        rotation39.setTotalir("255,05");
        rotation39.setTep("50H05");
        rotation39.setRnumvol("083");
        rotation39.setRtpsvol("10H45");
        rotation39.setRpc("3 RNN");
        rotation39.setPdj("non");
        rotation39.setChcdg("Si retard > 0h15mn");
        list.add((rotation39));

        Rotation rotation40 = new Rotation();
        rotation40.setCont("ame");
        rotation40.setId("40");
        rotation40.setEsc("YUL");
        rotation40.setOn("3");
        rotation40.setAnumvol("342");
        rotation40.setAtpsvol("07H50");
        rotation40.setNbrp("1");
        rotation40.setIrmenu("43,96");
        rotation40.setTotalir("43,96");
        rotation40.setTep("26H10");
        rotation40.setRnumvol("345");
        rotation40.setRtpsvol("07H00");
        rotation40.setRpc("2 RNN");
        rotation40.setPdj("non");
        rotation40.setChcdg("Si retard > 4h00mn");
        list.add((rotation40));

        Rotation rotation41 = new Rotation();
        rotation41.setCont("ame");
        rotation41.setId("41");
        rotation41.setEsc("YUL");
        rotation41.setOn("3");
        rotation41.setAnumvol("344");
        rotation41.setAtpsvol("08H05");
        rotation41.setNbrp("2");
        rotation41.setIrmenu("43,96");
        rotation41.setTotalir("87,92");
        rotation41.setTep("30H20");
        rotation41.setRnumvol("345");
        rotation41.setRtpsvol("07H00");
        rotation41.setRpc("2 RNN");
        rotation41.setPdj("non");
        rotation41.setChcdg("Si retard > 4h00mn");
        list.add((rotation41));

        Rotation rotation42 = new Rotation();
        rotation42.setCont("ame");
        rotation42.setId("42");
        rotation42.setEsc("YUL");
        rotation42.setOn("3");
        rotation42.setAnumvol("344");
        rotation42.setAtpsvol("08H05");
        rotation42.setNbrp("2");
        rotation42.setIrmenu("43,96");
        rotation42.setTotalir("87,92");
        rotation42.setTep("26H20");
        rotation42.setRnumvol("347");
        rotation42.setRtpsvol("06H55");
        rotation42.setRpc("2 RNN");
        rotation42.setPdj("non");
        rotation42.setChcdg("Si retard > 4h05mn");
        list.add((rotation42));

        Rotation rotation43 = new Rotation();
        rotation43.setCont("ame");
        rotation43.setId("43");
        rotation43.setEsc("YUL");
        rotation43.setOn("4");
        rotation43.setAnumvol("342");
        rotation43.setAtpsvol("07H50");
        rotation43.setNbrp("3");
        rotation43.setIrmenu("43,96");
        rotation43.setTotalir("131,88");
        rotation43.setTep("46H10");
        rotation43.setRnumvol("347");
        rotation43.setRtpsvol("06H55");
        rotation43.setRpc("2 RNN");
        rotation43.setPdj("non");
        rotation43.setChcdg("Si retard > 4h05mn");
        list.add((rotation43));

        Rotation rotation44 = new Rotation();
        rotation44.setCont("ame");
        rotation44.setId("44");
        rotation44.setEsc("YUL");
        rotation44.setOn("4");
        rotation44.setAnumvol("342");
        rotation44.setAtpsvol("07H50");
        rotation44.setNbrp("3");
        rotation44.setIrmenu("43,96");
        rotation44.setTotalir("131,88");
        rotation44.setTep("50H10");
        rotation44.setRnumvol("345");
        rotation44.setRtpsvol("07H00");
        rotation44.setRpc("2 RNN");
        rotation44.setPdj("non");
        rotation44.setChcdg("Si retard > 4h00mn");
        list.add((rotation44));

        Rotation rotation45 = new Rotation();
        rotation45.setCont("ame");
        rotation45.setId("45");
        rotation45.setEsc("YYZ");
        rotation45.setOn("3");
        rotation45.setAnumvol("356");
        rotation45.setAtpsvol("08H25");
        rotation45.setNbrp("2");
        rotation45.setIrmenu("43,96");
        rotation45.setTotalir("87,92");
        rotation45.setTep("25H55");
        rotation45.setRnumvol("351");
        rotation45.setRtpsvol("07H25");
        rotation45.setRpc("2 RNN");
        rotation45.setPdj("non");
        rotation45.setChcdg("Si retard > 3h35mn");
        list.add((rotation45));

        Rotation rotation46 = new Rotation();
        rotation46.setCont("ame");
        rotation46.setId("46");
        rotation46.setEsc("YYZ");
        rotation46.setOn("4");
        rotation46.setAnumvol("356");
        rotation46.setAtpsvol("08H25");
        rotation46.setNbrp("4");
        rotation46.setIrmenu("43,96");
        rotation46.setTotalir("175,84");
        rotation46.setTep("49h55");
        rotation46.setRnumvol("351");
        rotation46.setRtpsvol("07H25");
        rotation46.setRpc("2 RNN");
        rotation46.setPdj("non");
        rotation46.setChcdg("Si retard > 3h35mn");
        list.add((rotation46));

        Rotation rotation47 = new Rotation();
        rotation47.setCont("ame");
        rotation47.setId("47");
        rotation47.setEsc("YVR");
        rotation47.setOn("4");
        rotation47.setAnumvol("374");
        rotation47.setAtpsvol("10H15");
        rotation47.setNbrp("4+1CDG");
        rotation47.setIrmenu("43,96");
        rotation47.setTotalir("194,24");
        rotation47.setTep("50H00");
        rotation47.setRnumvol("379");
        rotation47.setRtpsvol("09H45");
        rotation47.setRpc("3 RNN");
        rotation47.setPdj("oui");
        rotation47.setChcdg("Si retard > 1h15mn");
        list.add((rotation47));

        Rotation rotation48 = new Rotation();
        rotation48.setCont("ame");
        rotation48.setId("48");
        rotation48.setEsc("YVR");
        rotation48.setOn("5");
        rotation48.setAnumvol("374");
        rotation48.setAtpsvol("10H15");
        rotation48.setNbrp("6+1CDG");
        rotation48.setIrmenu("43,96");
        rotation48.setTotalir("282,16");
        rotation48.setTep("74H00");
        rotation48.setRnumvol("379");
        rotation48.setRtpsvol("09H45");
        rotation48.setRpc("4 RNN");
        rotation48.setPdj("oui");
        rotation48.setChcdg("Si retard > 1h15mn");
        list.add((rotation48));

        Rotation rotation49 = new Rotation();
        rotation49.setCont("amo");
        rotation49.setId("49");
        rotation49.setEsc("ABJ");
        rotation49.setOn("3");
        rotation49.setAnumvol("702");
        rotation49.setAtpsvol("06H25");
        rotation49.setNbrp("2");
        rotation49.setIrmenu("41,14");
        rotation49.setTotalir("82,28");
        rotation49.setTep("27H20");
        rotation49.setRnumvol("703");
        rotation49.setRtpsvol("06H30");
        rotation49.setRpc("2 RNN");
        rotation49.setPdj("non");
        rotation49.setChcdg("Si retard > 4H30mn");
        list.add((rotation49));

        Rotation rotation50 = new Rotation();
        rotation50.setCont("amo");
        rotation50.setId("50");
        rotation50.setEsc("ABJ");
        rotation50.setOn("4");
        rotation50.setAnumvol("702");
        rotation50.setAtpsvol("06H25");
        rotation50.setNbrp("4");
        rotation50.setIrmenu("41,14");
        rotation50.setTotalir("164,56");
        rotation50.setTep("51H20");
        rotation50.setRnumvol("703");
        rotation50.setRtpsvol("06H30");
        rotation50.setRpc("2 RNN");
        rotation50.setPdj("non");
        rotation50.setChcdg("Si retard > 4H30mn");
        list.add((rotation50));

        Rotation rotation51 = new Rotation();
        rotation51.setCont("amo");
        rotation51.setId("51");
        rotation51.setEsc("ABJ-BKO");
        rotation51.setOn("3");
        rotation51.setAnumvol("520");
        rotation51.setAtpsvol("07H15");
        rotation51.setNbrp("3");
        rotation51.setIrmenu("41,14");
        rotation51.setTotalir("123,42");
        rotation51.setTep("30H45");
        rotation51.setRnumvol("703");
        rotation51.setRtpsvol("06H30");
        rotation51.setRpc("2 RNN");
        rotation51.setPdj("non");
        rotation51.setChcdg("Si retard > 4H30mn");
        list.add((rotation51));

        Rotation rotation52 = new Rotation();
        rotation52.setCont("amo");
        rotation52.setId("52");
        rotation52.setEsc("ABJ-BKO");
        rotation52.setOn("3");
        rotation52.setAnumvol("702");
        rotation52.setAtpsvol("06H25");
        rotation52.setNbrp("2");
        rotation52.setIrmenu("41,14");
        rotation52.setTotalir("82,28");
        rotation52.setTep("22H25");
        rotation52.setRnumvol("521");
        rotation52.setRtpsvol("07H20");
        rotation52.setRpc("2 RNN");
        rotation52.setPdj("non");
        rotation52.setChcdg("Si retard > 3H40mn");
        list.add((rotation52));

        Rotation rotation53 = new Rotation();
        rotation53.setCont("amo");
        rotation53.setId("53");
        rotation53.setEsc("ACC");
        rotation53.setOn("4");
        rotation53.setAnumvol("780");
        rotation53.setAtpsvol("06H35");
        rotation53.setNbrp("5");
        rotation53.setIrmenu("51,52");
        rotation53.setTotalir("257,60");
        rotation53.setTep("51H20");
        rotation53.setRnumvol("731");
        rotation53.setRtpsvol("06H35");
        rotation53.setRpc("2 RNN");
        rotation53.setPdj("non");
        rotation53.setChcdg("Si retard > 4H35mn");
        list.add((rotation53));

        Rotation rotation54 = new Rotation();
        rotation54.setCont("amo");
        rotation54.setId("54");
        rotation54.setEsc("ACC");
        rotation54.setOn("5");
        rotation54.setAnumvol("780");
        rotation54.setAtpsvol("06H35");
        rotation54.setNbrp("7");
        rotation54.setIrmenu("51,52");
        rotation54.setTotalir("360,64");
        rotation54.setTep("75H20");
        rotation54.setRnumvol("731");
        rotation54.setRtpsvol("06H35");
        rotation54.setRpc("2 RNN");
        rotation54.setPdj("non");
        rotation54.setChcdg("Si retard > 4H35mn");
        list.add((rotation54));

        Rotation rotation55 = new Rotation();
        rotation55.setCont("amo");
        rotation55.setId("55");
        rotation55.setEsc("BZV");
        rotation55.setOn("3");
        rotation55.setAnumvol("896");
        rotation55.setAtpsvol("07H55");
        rotation55.setNbrp("3");
        rotation55.setIrmenu("48,95");
        rotation55.setTotalir("146,85");
        rotation55.setTep("27H45");
        rotation55.setRnumvol("897");
        rotation55.setRtpsvol("07H55");
        rotation55.setRpc("2 RNN");
        rotation55.setPdj("non");
        rotation55.setChcdg("Si retard > 3H05mn");
        list.add((rotation55));

        Rotation rotation56 = new Rotation();
        rotation56.setCont("amo");
        rotation56.setId("56");
        rotation56.setEsc("BZV-FIH");
        rotation56.setOn("3");
        rotation56.setAnumvol("896");
        rotation56.setAtpsvol("07H55");
        rotation56.setNbrp("2");
        rotation56.setIrmenu("48,95");
        rotation56.setTotalir("97,90");
        rotation56.setTep("26H00");
        rotation56.setRnumvol("889");
        rotation56.setRtpsvol("08H40");
        rotation56.setRpc("2 RNN");
        rotation56.setPdj("non");
        rotation56.setChcdg("Si retard > 2H20mn");
        list.add((rotation56));

        Rotation rotation57 = new Rotation();
        rotation57.setCont("amo");
        rotation57.setId("57");
        rotation57.setEsc("BZV-FIH");
        rotation57.setOn("3");
        rotation57.setAnumvol("888");
        rotation57.setAtpsvol("07H55");
        rotation57.setNbrp("3");
        rotation57.setIrmenu("48,95");
        rotation57.setTotalir("146,85");
        rotation57.setTep("24H15");
        rotation57.setRnumvol("897");
        rotation57.setRtpsvol("07H55");
        rotation57.setRpc("2 RNN");
        rotation57.setPdj("non");
        rotation57.setChcdg("Si retard > 3H05mn");
        list.add((rotation57));

        Rotation rotation58 = new Rotation();
        rotation58.setCont("amo");
        rotation58.setId("58");
        rotation58.setEsc("BEY");
        rotation58.setOn("2");
        rotation58.setAnumvol("566");
        rotation58.setAtpsvol("04H10");
        rotation58.setNbrp("1+1CDG");
        rotation58.setIrmenu("41,37");
        rotation58.setTotalir("59,77");
        rotation58.setTep("25H45");
        rotation58.setRnumvol("565");
        rotation58.setRtpsvol("04H40");
        rotation58.setRpc("2 RNN");
        rotation58.setPdj("non");
        rotation58.setChcdg("Si retard > 6H20mn");
        list.add((rotation58));

        Rotation rotation59 = new Rotation();
        rotation59.setCont("amo");
        rotation59.setId("59");
        rotation59.setEsc("CAI");
        rotation59.setOn("2");
        rotation59.setAnumvol("508");
        rotation59.setAtpsvol("04H30");
        rotation59.setNbrp("1");
        rotation59.setIrmenu("19,90");
        rotation59.setTotalir("19,90");
        rotation59.setTep("12H35");
        rotation59.setRnumvol("503");
        rotation59.setRtpsvol("04H55");
        rotation59.setRpc("1 RNN");
        rotation59.setPdj("non");
        rotation59.setChcdg("Si retard > 6H05mn");
        list.add((rotation59));

        Rotation rotation60 = new Rotation();
        rotation60.setCont("amo");
        rotation60.setId("60");
        rotation60.setEsc("CKY-NKC");
        rotation60.setOn("3");
        rotation60.setAnumvol("724");
        rotation60.setAtpsvol("07H10");
        rotation60.setNbrp("3");
        rotation60.setIrmenu("28,11");
        rotation60.setTotalir("84,33");
        rotation60.setTep("29H10");
        rotation60.setRnumvol("596");
        rotation60.setRtpsvol("06H15");
        rotation60.setRpc("2 RNN");
        rotation60.setPdj("non");
        rotation60.setChcdg("Si retard > 4H45mn");
        list.add((rotation60));

        Rotation rotation61 = new Rotation();
        rotation61.setCont("amo");
        rotation61.setId("61");
        rotation61.setEsc("CKY-NKC");
        rotation61.setOn("3");
        rotation61.setAnumvol("724");
        rotation61.setAtpsvol("07H10");
        rotation61.setNbrp("2");
        rotation61.setIrmenu("28,11");
        rotation61.setTotalir("56,22");
        rotation61.setTep("26H20");
        rotation61.setRnumvol("727");
        rotation61.setRtpsvol("07H00");
        rotation61.setRpc("2 RNN");
        rotation61.setPdj("non");
        rotation61.setChcdg("Si retard > 4H00mn");
        list.add((rotation61));

        Rotation rotation62 = new Rotation();
        rotation62.setCont("amo");
        rotation62.setId("62");
        rotation62.setEsc("CKY-NKC");
        rotation62.setOn("4");
        rotation62.setAnumvol("724");
        rotation62.setAtpsvol("07H10");
        rotation62.setNbrp("4");
        rotation62.setIrmenu("28,11");
        rotation62.setTotalir("112,44");
        rotation62.setTep("50H20");
        rotation62.setRnumvol("727");
        rotation62.setRtpsvol("07H00");
        rotation62.setRpc("2 RNN");
        rotation62.setPdj("non");
        rotation62.setChcdg("Si retard > 4H00mn");
        list.add((rotation62));

        Rotation rotation63 = new Rotation();
        rotation63.setCont("amo");
        rotation63.setId("63");
        rotation63.setEsc("CKY-NKC");
        rotation63.setOn("4");
        rotation63.setAnumvol("724");
        rotation63.setAtpsvol("07H10");
        rotation63.setNbrp("5");
        rotation63.setIrmenu("28,11");
        rotation63.setTotalir("140,55");
        rotation63.setTep("53H10");
        rotation63.setRnumvol("596");
        rotation63.setRtpsvol("06H15");
        rotation63.setRpc("2 RNN");
        rotation63.setPdj("non");
        rotation63.setChcdg("Si retard > 4H45mn");
        list.add((rotation63));

        Rotation rotation64 = new Rotation();
        rotation64.setCont("amo");
        rotation64.setId("64");
        rotation64.setEsc("CKY-FNA");
        rotation64.setOn("4");
        rotation64.setAnumvol("596");
        rotation64.setAtpsvol("07H20");
        rotation64.setNbrp("4");
        rotation64.setIrmenu("28,11");
        rotation64.setTotalir("112,44");
        rotation64.setTep("49H50");
        rotation64.setRnumvol("596");
        rotation64.setRtpsvol("06H15");
        rotation64.setRpc("2 RNN");
        rotation64.setPdj("non");
        rotation64.setChcdg("Si retard > 4H45mn");
        list.add((rotation64));

        Rotation rotation65 = new Rotation();
        rotation65.setCont("amo");
        rotation65.setId("65");
        rotation65.setEsc("KY-FNA/NKC");
        rotation65.setOn("3");
        rotation65.setAnumvol("596");
        rotation65.setAtpsvol("07H20");
        rotation65.setNbrp("1");
        rotation65.setIrmenu("28,11");
        rotation65.setTotalir("28,11");
        rotation65.setTep("23H00");
        rotation65.setRnumvol("727");
        rotation65.setRtpsvol("07H00");
        rotation65.setRpc("2 RNN");
        rotation65.setPdj("non");
        rotation65.setChcdg("Si retard > 4H00mn");
        list.add((rotation65));

        Rotation rotation66 = new Rotation();
        rotation66.setCont("amo");
        rotation66.setId("66");
        rotation66.setEsc("KY-FNA/NKC");
        rotation66.setOn("4");
        rotation66.setAnumvol("596");
        rotation66.setAtpsvol("07H20");
        rotation66.setNbrp("3");
        rotation66.setIrmenu("28,11");
        rotation66.setTotalir("84,33");
        rotation66.setTep("47H00");
        rotation66.setRnumvol("727");
        rotation66.setRtpsvol("07H00");
        rotation66.setRpc("2 RNN");
        rotation66.setPdj("non");
        rotation66.setChcdg("Si retard > 4H00mn");
        list.add((rotation66));

        Rotation rotation67 = new Rotation();
        rotation67.setCont("amo");
        rotation67.setId("67");
        rotation67.setEsc("CPT");
        rotation67.setOn("4");
        rotation67.setAnumvol("864");
        rotation67.setAtpsvol("11H30");
        rotation67.setNbrp("4");
        rotation67.setIrmenu("19,74");
        rotation67.setTotalir("78,96");
        rotation67.setTep("50H00");
        rotation67.setRnumvol("871");
        rotation67.setRtpsvol("11H35");
        rotation67.setRpc("3 RNN");
        rotation67.setPdj("non");
        rotation67.setChcdg("oui");
        list.add((rotation67));

        Rotation rotation68 = new Rotation();
        rotation68.setCont("amo");
        rotation68.setId("68");
        rotation68.setEsc("CPT");
        rotation68.setOn("5");
        rotation68.setAnumvol("864");
        rotation68.setAtpsvol("11H30");
        rotation68.setNbrp("6");
        rotation68.setIrmenu("19,74");
        rotation68.setTotalir("118,44");
        rotation68.setTep("74H00");
        rotation68.setRnumvol("871");
        rotation68.setRtpsvol("11H35");
        rotation68.setRpc("3 RNN");
        rotation68.setPdj("non");
        rotation68.setChcdg("oui");
        list.add((rotation68));

        Rotation rotation69 = new Rotation();
        rotation69.setCont("amo");
        rotation69.setId("69");
        rotation69.setEsc("CPT");
        rotation69.setOn("6");
        rotation69.setAnumvol("864");
        rotation69.setAtpsvol("11H30");
        rotation69.setNbrp("8");
        rotation69.setIrmenu("19,74");
        rotation69.setTotalir("157,92");
        rotation69.setTep("98H00");
        rotation69.setRnumvol("871");
        rotation69.setRtpsvol("11H35");
        rotation69.setRpc("3 RNN");
        rotation69.setPdj("non");
        rotation69.setChcdg("oui");
        list.add((rotation69));

        Rotation rotation70 = new Rotation();
        rotation70.setCont("amo");
        rotation70.setId("70");
        rotation70.setEsc("COO");
        rotation70.setOn("3");
        rotation70.setAnumvol("804");
        rotation70.setAtpsvol("06H25");
        rotation70.setNbrp("3");
        rotation70.setIrmenu("39,86");
        rotation70.setTotalir("119,58");
        rotation70.setTep("26H05");
        rotation70.setRnumvol("805");
        rotation70.setRtpsvol("06H20");
        rotation70.setRpc("2 RNN");
        rotation70.setPdj("non");
        rotation70.setChcdg("Si retard > 4H40mn");
        list.add((rotation70));

        Rotation rotation71 = new Rotation();
        rotation71.setCont("amo");
        rotation71.setId("71");
        rotation71.setEsc("COO");
        rotation71.setOn("4");
        rotation71.setAnumvol("804");
        rotation71.setAtpsvol("06H25");
        rotation71.setNbrp("5");
        rotation71.setIrmenu("39,86");
        rotation71.setTotalir("199,30");
        rotation71.setTep("50H05");
        rotation71.setRnumvol("805");
        rotation71.setRtpsvol("06H20");
        rotation71.setRpc("2 RNN");
        rotation71.setPdj("non");
        rotation71.setChcdg("Si retard > 4H40mn");
        list.add((rotation71));

        Rotation rotation72 = new Rotation();
        rotation72.setCont("amo");
        rotation72.setId("72");
        rotation72.setEsc("DKR");
        rotation72.setOn("3");
        rotation72.setAnumvol("718");
        rotation72.setAtpsvol("05H45");
        rotation72.setNbrp("2");
        rotation72.setIrmenu("47,25");
        rotation72.setTotalir("94,50");
        rotation72.setTep("26H45");
        rotation72.setRnumvol("719");
        rotation72.setRtpsvol("05H40");
        rotation72.setRpc("2 RNN");
        rotation72.setPdj("non");
        rotation72.setChcdg("Si retard > 5H20mn");
        list.add((rotation72));

        Rotation rotation73 = new Rotation();
        rotation73.setCont("amo");
        rotation73.setId("73");
        rotation73.setEsc("DXB");
        rotation73.setOn("3");
        rotation73.setAnumvol("662");
        rotation73.setAtpsvol("06H55");
        rotation73.setNbrp("3");
        rotation73.setIrmenu("47,25");
        rotation73.setTotalir("141,75");
        rotation73.setTep("26H05");
        rotation73.setRnumvol("655");
        rotation73.setRtpsvol("07H25");
        rotation73.setRpc("2 RNN");
        rotation73.setPdj("non");
        rotation73.setChcdg("Si retard > 3H35mn");
        list.add((rotation73));

        Rotation rotation74 = new Rotation();
        rotation74.setCont("amo");
        rotation74.setId("74");
        rotation74.setEsc("IKA");
        rotation74.setOn("2");
        rotation74.setAnumvol("738");
        rotation74.setAtpsvol("05H30");
        rotation74.setNbrp("1");
        rotation74.setIrmenu("21,52");
        rotation74.setTotalir("21,52");
        rotation74.setTep("12H40");
        rotation74.setRnumvol("755");
        rotation74.setRtpsvol("06H15");
        rotation74.setRpc("2 RNN");
        rotation74.setPdj("non");
        rotation74.setChcdg("Si retard > 4H45mn");
        list.add((rotation74));

        Rotation rotation75 = new Rotation();
        rotation75.setCont("amo");
        rotation75.setId("75");
        rotation75.setEsc("JIB");
        rotation75.setOn("3");
        rotation75.setAnumvol("668");
        rotation75.setAtpsvol("07H25");
        rotation75.setNbrp("2");
        rotation75.setIrmenu("31,40");
        rotation75.setTotalir("62,80");
        rotation75.setTep("14H15");
        rotation75.setRnumvol("669");
        rotation75.setRtpsvol("08H20");
        rotation75.setRpc("2 RNN");
        rotation75.setPdj("oui");
        rotation75.setChcdg("Si retard > 2H40mn");
        list.add((rotation75));

        Rotation rotation76 = new Rotation();
        rotation76.setCont("amo");
        rotation76.setId("76");
        rotation76.setEsc("JNB");
        rotation76.setOn("4");
        rotation76.setAnumvol("990");
        rotation76.setAtpsvol("10H50");
        rotation76.setNbrp("3");
        rotation76.setIrmenu("19,74");
        rotation76.setTotalir("59,22");
        rotation76.setTep("32H40");
        rotation76.setRnumvol("995");
        rotation76.setRtpsvol("10H55");
        rotation76.setRpc("3 RNN");
        rotation76.setPdj("oui");
        rotation76.setChcdg("Si retard > 0H05mn");
        list.add((rotation76));

        Rotation rotation77 = new Rotation();
        rotation77.setCont("amo");
        rotation77.setId("77");
        rotation77.setEsc("LAD");
        rotation77.setOn("3");
        rotation77.setAnumvol("928");
        rotation77.setAtpsvol("08H10");
        rotation77.setNbrp("1");
        rotation77.setIrmenu("80,94");
        rotation77.setTotalir("80,94");
        rotation77.setTep("15H20");
        rotation77.setRnumvol("929");
        rotation77.setRtpsvol("08h15");
        rotation77.setRpc("2 RNN");
        rotation77.setPdj("oui");
        rotation77.setChcdg("Si retard > 2H45mn");
        list.add((rotation77));

        Rotation rotation78 = new Rotation();
        rotation78.setCont("amo");
        rotation78.setId("78");
        rotation78.setEsc("LBV");
        rotation78.setOn("3");
        rotation78.setAnumvol("976");
        rotation78.setAtpsvol("07H00");
        rotation78.setNbrp("3");
        rotation78.setIrmenu("41,93");
        rotation78.setTotalir("125,79");
        rotation78.setTep("29H50");
        rotation78.setRnumvol("977");
        rotation78.setRtpsvol("07H10");
        rotation78.setRpc("2 RNN");
        rotation78.setPdj("non");
        rotation78.setChcdg("Si retard > 3H50mn");
        list.add((rotation78));

        Rotation rotation79 = new Rotation();
        rotation79.setCont("amo");
        rotation79.setId("79");
        rotation79.setEsc("LBV");
        rotation79.setOn("4");
        rotation79.setAnumvol("976");
        rotation79.setAtpsvol("07H00");
        rotation79.setNbrp("5");
        rotation79.setIrmenu("41,93");
        rotation79.setTotalir("209,65");
        rotation79.setTep("53H50");
        rotation79.setRnumvol("977");
        rotation79.setRtpsvol("07H10");
        rotation79.setRpc("2 RNN");
        rotation79.setPdj("non");
        rotation79.setChcdg("Si retard > 3H50mn");
        list.add((rotation79));

        Rotation rotation80 = new Rotation();
        rotation80.setCont("amo");
        rotation80.setId("80");
        rotation80.setEsc("LFW-NIM");
        rotation80.setOn("3");
        rotation80.setAnumvol("306");
        rotation80.setAtpsvol("07H00");
        rotation80.setNbrp("2");
        rotation80.setIrmenu("30,37");
        rotation80.setTotalir("60,74");
        rotation80.setTep("26H15");
        rotation80.setRnumvol("339");
        rotation80.setRtpsvol("07H05");
        rotation80.setRpc("2 RNN");
        rotation80.setPdj("non");
        rotation80.setChcdg("Si retard > 3H55mn");
        list.add((rotation80));

        Rotation rotation81 = new Rotation();
        rotation81.setCont("amo");
        rotation81.setId("81");
        rotation81.setEsc("LOS");
        rotation81.setOn("3");
        rotation81.setAnumvol("104");
        rotation81.setAtpsvol("06H20");
        rotation81.setNbrp("3");
        rotation81.setIrmenu("35,14");
        rotation81.setTotalir("105,42");
        rotation81.setTep("27H10");
        rotation81.setRnumvol("149");
        rotation81.setRtpsvol("06H25");
        rotation81.setRpc("2 RNN");
        rotation81.setPdj("non");
        rotation81.setChcdg("Si retard > 4H35mn");
        list.add((rotation81));

        Rotation rotation82 = new Rotation();
        rotation82.setCont("amo");
        rotation82.setId("82");
        rotation82.setEsc("LOS");
        rotation82.setOn("4");
        rotation82.setAnumvol("104");
        rotation82.setAtpsvol("06H20");
        rotation82.setNbrp("5");
        rotation82.setIrmenu("35,14");
        rotation82.setTotalir("175,70");
        rotation82.setTep("51H10");
        rotation82.setRnumvol("149");
        rotation82.setRtpsvol("06H25");
        rotation82.setRpc("2 RNN");
        rotation82.setPdj("non");
        rotation82.setChcdg("Si retard > 4H35mn");
        list.add((rotation82));

        Rotation rotation83 = new Rotation();
        rotation83.setCont("amo");
        rotation83.setId("83");
        rotation83.setEsc("NDJ");
        rotation83.setOn("4");
        rotation83.setAnumvol("558");
        rotation83.setAtpsvol("05H45");
        rotation83.setNbrp("5");
        rotation83.setIrmenu("56,69");
        rotation83.setTotalir("283,45");
        rotation83.setTep("51H00");
        rotation83.setRnumvol("559");
        rotation83.setRtpsvol("05H55");
        rotation83.setRpc("2 RNN");
        rotation83.setPdj("non");
        rotation83.setChcdg("Si retard > 5H05mn");
        list.add((rotation83));

        Rotation rotation84 = new Rotation();
        rotation84.setCont("amo");
        rotation84.setId("84");
        rotation84.setEsc("NDJ");
        rotation84.setOn("5");
        rotation84.setAnumvol("558");
        rotation84.setAtpsvol("05H45");
        rotation84.setNbrp("7");
        rotation84.setIrmenu("56,69");
        rotation84.setTotalir("396,83");
        rotation84.setTep("75H00");
        rotation84.setRnumvol("559");
        rotation84.setRtpsvol("05H55");
        rotation84.setRpc("2 RNN");
        rotation84.setPdj("non");
        rotation84.setChcdg("Si retard > 5H05mn");
        list.add((rotation84));

        Rotation rotation85 = new Rotation();
        rotation85.setCont("amo");
        rotation85.setId("85");
        rotation85.setEsc("NSI");
        rotation85.setOn("3");
        rotation85.setAnumvol("900");
        rotation85.setAtpsvol("06H30");
        rotation85.setNbrp("3");
        rotation85.setIrmenu("48,96");
        rotation85.setTotalir("146,88");
        rotation85.setTep("27H35");
        rotation85.setRnumvol("775");
        rotation85.setRtpsvol("06H45");
        rotation85.setRpc("2 RNN");
        rotation85.setPdj("non");
        rotation85.setChcdg("Si retard > 4H15mn");
        list.add((rotation85));

        Rotation rotation86 = new Rotation();
        rotation86.setCont("amo");
        rotation86.setId("86");
        rotation86.setEsc("NSI");
        rotation86.setOn("3");
        rotation86.setAnumvol("982");
        rotation86.setAtpsvol("06H30");
        rotation86.setNbrp("3");
        rotation86.setIrmenu("48,96");
        rotation86.setTotalir("146,88");
        rotation86.setTep("28H15");
        rotation86.setRnumvol("775");
        rotation86.setRtpsvol("06H45");
        rotation86.setRpc("2 RNN");
        rotation86.setPdj("non");
        rotation86.setChcdg("Si retard > 4H15mn");
        list.add((rotation86));

        Rotation rotation87 = new Rotation();
        rotation87.setCont("amo");
        rotation87.setId("87");
        rotation87.setEsc("NSI");
        rotation87.setOn("4");
        rotation87.setAnumvol("982");
        rotation87.setAtpsvol("06H30");
        rotation87.setNbrp("5");
        rotation87.setIrmenu("48,96");
        rotation87.setTotalir("244,80");
        rotation87.setTep("52H15");
        rotation87.setRnumvol("901");
        rotation87.setRtpsvol("06H45");
        rotation87.setRpc("2 RNN");
        rotation87.setPdj("non");
        rotation87.setChcdg("Si retard > 4H15mn");
        list.add((rotation87));

        Rotation rotation88 = new Rotation();
        rotation88.setCont("amo");
        rotation88.setId("88");
        rotation88.setEsc("NSI");
        rotation88.setOn("3");
        rotation88.setAnumvol("900");
        rotation88.setAtpsvol("06H30");
        rotation88.setNbrp("5");
        rotation88.setIrmenu("48,96");
        rotation88.setTotalir("244,80");
        rotation88.setTep("51H35");
        rotation88.setRnumvol("901");
        rotation88.setRtpsvol("06H45");
        rotation88.setRpc("2 RNN");
        rotation88.setPdj("non");
        rotation88.setChcdg("Si retard > 4H15mn");
        list.add((rotation88));

        Rotation rotation89 = new Rotation();
        rotation89.setCont("amo");
        rotation89.setId("89");
        rotation89.setEsc("NSI-BGF");
        rotation89.setOn("3");
        rotation89.setAnumvol("775");
        rotation89.setAtpsvol("08H00");
        rotation89.setNbrp("3");
        rotation89.setIrmenu("48,96");
        rotation89.setTotalir("146,88");
        rotation89.setTep("29H05");
        rotation89.setRnumvol("901");
        rotation89.setRtpsvol("06H45");
        rotation89.setRpc("2 RNN");
        rotation89.setPdj("non");
        rotation89.setChcdg("Si retard > 4H15mn");
        list.add((rotation89));

        Rotation rotation90 = new Rotation();
        rotation90.setCont("amo");
        rotation90.setId("90");
        rotation90.setEsc("NSI-BGF");
        rotation90.setOn("5");
        rotation90.setAnumvol("775");
        rotation90.setAtpsvol("08H00");
        rotation90.setNbrp("7");
        rotation90.setIrmenu("48,96");
        rotation90.setTotalir("342,72");
        rotation90.setTep("77H05");
        rotation90.setRnumvol("775");
        rotation90.setRtpsvol("06H45");
        rotation90.setRpc("2 RNN");
        rotation90.setPdj("non");
        rotation90.setChcdg("Si retard > 4H15mn");
        list.add((rotation90));

        Rotation rotation91 = new Rotation();
        rotation91.setCont("amo");
        rotation91.setId("91");
        rotation91.setEsc("NSI-DLA");
        rotation91.setOn("3");
        rotation91.setAnumvol("900");
        rotation91.setAtpsvol("06H30");
        rotation91.setNbrp("2");
        rotation91.setIrmenu("48,96");
        rotation91.setTotalir("97,92");
        rotation91.setTep("25H00");
        rotation91.setRnumvol("982");
        rotation91.setRtpsvol("07H25");
        rotation91.setRpc("2 RNN");
        rotation91.setPdj("non");
        rotation91.setChcdg("Si retard > 3H35mn");
        list.add((rotation91));

        Rotation rotation92 = new Rotation();
        rotation92.setCont("amo");
        rotation92.setId("92");
        rotation92.setEsc("NSI-DLA");
        rotation92.setOn("5");
        rotation92.setAnumvol("900");
        rotation92.setAtpsvol("06H30");
        rotation92.setNbrp("6");
        rotation92.setIrmenu("48,96");
        rotation92.setTotalir("293,76");
        rotation92.setTep("73H00");
        rotation92.setRnumvol("982");
        rotation92.setRtpsvol("07H25");
        rotation92.setRpc("2 RNN");
        rotation92.setPdj("non");
        rotation92.setChcdg("Si retard > 3H35mn");
        list.add((rotation92));

        Rotation rotation93 = new Rotation();
        rotation93.setCont("amo");
        rotation93.setId("93");
        rotation93.setEsc("NSI-BGF/DLA");
        rotation93.setOn("4");
        rotation93.setAnumvol("775");
        rotation93.setAtpsvol("08H00");
        rotation93.setNbrp("4");
        rotation93.setIrmenu("48,96");
        rotation93.setTotalir("195,84");
        rotation93.setTep("50H30");
        rotation93.setRnumvol("982");
        rotation93.setRtpsvol("07H25");
        rotation93.setRpc("2 RNN");
        rotation93.setPdj("non");
        rotation93.setChcdg("Si retard > 3H35mn");
        list.add((rotation93));

        Rotation rotation94 = new Rotation();
        rotation94.setCont("amo");
        rotation94.setId("94");
        rotation94.setEsc("OUA");
        rotation94.setOn("3");
        rotation94.setAnumvol("536");
        rotation94.setAtpsvol("05H40");
        rotation94.setNbrp("3");
        rotation94.setIrmenu("50,65");
        rotation94.setTotalir("151,95");
        rotation94.setTep("26H30");
        rotation94.setRnumvol("535");
        rotation94.setRtpsvol("05H30");
        rotation94.setRpc("2 RNN");
        rotation94.setPdj("non");
        rotation94.setChcdg("Si retard > 5H30mn");
        list.add((rotation94));

        Rotation rotation95 = new Rotation();
        rotation95.setCont("amo");
        rotation95.setId("95");
        rotation95.setEsc("OUA");
        rotation95.setOn("4");
        rotation95.setAnumvol("536");
        rotation95.setAtpsvol("05H40");
        rotation95.setNbrp("5");
        rotation95.setIrmenu("50,65");
        rotation95.setTotalir("253,25");
        rotation95.setTep("50H30");
        rotation95.setRnumvol("535");
        rotation95.setRtpsvol("05H30");
        rotation95.setRpc("2 RNN");
        rotation95.setPdj("non");
        rotation95.setChcdg("Si retard > 5H30mn");
        list.add((rotation95));

        Rotation rotation96 = new Rotation();
        rotation96.setCont("amo");
        rotation96.setId("96");
        rotation96.setEsc("PHC");
        rotation96.setOn("3");
        rotation96.setAnumvol("878");
        rotation96.setAtpsvol("06H40");
        rotation96.setNbrp("3");
        rotation96.setIrmenu("17,32");
        rotation96.setTotalir("51,96");
        rotation96.setTep("27H35");
        rotation96.setRnumvol("584");
        rotation96.setRtpsvol("06H45");
        rotation96.setRpc("2 RNN");
        rotation96.setPdj("non");
        rotation96.setChcdg("Si retard > 4H15mn");
        list.add((rotation96));

        Rotation rotation97 = new Rotation();
        rotation97.setCont("amo");
        rotation97.setId("97");
        rotation97.setEsc("PHC");
        rotation97.setOn("3");
        rotation97.setAnumvol("878");
        rotation97.setAtpsvol("06H40");
        rotation97.setNbrp("5");
        rotation97.setIrmenu("17,32");
        rotation97.setTotalir("86,60");
        rotation97.setTep("51H35");
        rotation97.setRnumvol("584");
        rotation97.setRtpsvol("06H45");
        rotation97.setRpc("2 RNN");
        rotation97.setPdj("non");
        rotation97.setChcdg("Si retard > 4H15mn");
        list.add((rotation97));

        Rotation rotation98 = new Rotation();
        rotation98.setCont("amo");
        rotation98.setId("98");
        rotation98.setEsc("PHC-ABV");
        rotation98.setOn("4");
        rotation98.setAnumvol("584");
        rotation98.setAtpsvol("07H15");
        rotation98.setNbrp("4");
        rotation98.setIrmenu("17,32");
        rotation98.setTotalir("69,28");
        rotation98.setTep("47H25");
        rotation98.setRnumvol("878");
        rotation98.setRtpsvol("07H15");
        rotation98.setRpc("2 RNN");
        rotation98.setPdj("non");
        rotation98.setChcdg("Si retard > 3H45mn");
        list.add((rotation98));

        Rotation rotation99 = new Rotation();
        rotation99.setCont("amo");
        rotation99.setId("99");
        rotation99.setEsc("PNR");
        rotation99.setOn("3");
        rotation99.setAnumvol("830");
        rotation99.setAtpsvol("07H55");
        rotation99.setNbrp("2");
        rotation99.setIrmenu("48,95");
        rotation99.setTotalir("97,90");
        rotation99.setTep("26H50");
        rotation99.setRnumvol("833");
        rotation99.setRtpsvol("07H55");
        rotation99.setRpc("2 RNN");
        rotation99.setPdj("non");
        rotation99.setChcdg("Si retard > 3H05mn");
        list.add((rotation99));

        Rotation rotation100 = new Rotation();
        rotation100.setCont("amo");
        rotation100.setId("100");
        rotation100.setEsc("PNR");
        rotation100.setOn("4");
        rotation100.setAnumvol("830");
        rotation100.setAtpsvol("07H55");
        rotation100.setNbrp("4");
        rotation100.setIrmenu("48,95");
        rotation100.setTotalir("195,80");
        rotation100.setTep("50H50");
        rotation100.setRnumvol("833");
        rotation100.setRtpsvol("07H55");
        rotation100.setRpc("2 RNN");
        rotation100.setPdj("non");
        rotation100.setChcdg("Si retard > 3H05mn");
        list.add((rotation100));

        Rotation rotation101 = new Rotation();
        rotation101.setCont("amo");
        rotation101.setId("101");
        rotation101.setEsc("RUH");
        rotation101.setOn("3");
        rotation101.setAnumvol("448");
        rotation101.setAtpsvol("06H10");
        rotation101.setNbrp("3");
        rotation101.setIrmenu("30,59");
        rotation101.setTotalir("91,77");
        rotation101.setTep("28H40");
        rotation101.setRnumvol("489");
        rotation101.setRtpsvol("07H20");
        rotation101.setRpc("2 RNN");
        rotation101.setPdj("non");
        rotation101.setChcdg("Si retard > 3H40mn");
        list.add((rotation101));

        Rotation rotation102 = new Rotation();
        rotation102.setCont("amo");
        rotation102.setId("102");
        rotation102.setEsc("RUH");
        rotation102.setOn("4");
        rotation102.setAnumvol("448");
        rotation102.setAtpsvol("06H10");
        rotation102.setNbrp("5");
        rotation102.setIrmenu("30,59");
        rotation102.setTotalir("152,95");
        rotation102.setTep("52H40");
        rotation102.setRnumvol("489");
        rotation102.setRtpsvol("07H20");
        rotation102.setRpc("2 RNN");
        rotation102.setPdj("non");
        rotation102.setChcdg("Si retard > 3H40mn");
        list.add((rotation102));

        Rotation rotation103 = new Rotation();
        rotation103.setCont("amo");
        rotation103.setId("103");
        rotation103.setEsc("RUH");
        rotation103.setOn("5");
        rotation103.setAnumvol("448");
        rotation103.setAtpsvol("06H10");
        rotation103.setNbrp("7");
        rotation103.setIrmenu("30,59");
        rotation103.setTotalir("214,13");
        rotation103.setTep("76H40");
        rotation103.setRnumvol("489");
        rotation103.setRtpsvol("07H20");
        rotation103.setRpc("2 RNN");
        rotation103.setPdj("non");
        rotation103.setChcdg("Si retard > 3H40mn");
        list.add((rotation103));

        Rotation rotation104 = new Rotation();
        rotation104.setCont("amo");
        rotation104.setId("104");
        rotation104.setEsc("SSG-DLA");
        rotation104.setOn("3");
        rotation104.setAnumvol("958");
        rotation104.setAtpsvol("07H30");
        rotation104.setNbrp("2");
        rotation104.setIrmenu("32,58");
        rotation104.setTotalir("65,16");
        rotation104.setTep("26H10");
        rotation104.setRnumvol("953");
        rotation104.setRtpsvol("07H35");
        rotation104.setRpc("2 RNN");
        rotation104.setPdj("non");
        rotation104.setChcdg("Si retard > 3H25mn");
        list.add((rotation104));

        Rotation rotation105 = new Rotation();
        rotation105.setCont("amo");
        rotation105.setId("105");
        rotation105.setEsc("SSG-DLA");
        rotation105.setOn("4");
        rotation105.setAnumvol("958");
        rotation105.setAtpsvol("07H30");
        rotation105.setNbrp("5");
        rotation105.setIrmenu("32,58");
        rotation105.setTotalir("162,90");
        rotation105.setTep("50H10");
        rotation105.setRnumvol("953");
        rotation105.setRtpsvol("07H35");
        rotation105.setRpc("2 RNN");
        rotation105.setPdj("non");
        rotation105.setChcdg("Si retard > 3H25mn");
        list.add((rotation105));

        Rotation rotation106 = new Rotation();
        rotation106.setCont("amo");
        rotation106.setId("106");
        rotation106.setEsc("SSG-DLA");
        rotation106.setOn("5");
        rotation106.setAnumvol("958");
        rotation106.setAtpsvol("07H30");
        rotation106.setNbrp("7");
        rotation106.setIrmenu("32,58");
        rotation106.setTotalir("228,06");
        rotation106.setTep("74H10");
        rotation106.setRnumvol("953");
        rotation106.setRtpsvol("07H35");
        rotation106.setRpc("2 RNN");
        rotation106.setPdj("non");
        rotation106.setChcdg("Si retard > 3H25mn");
        list.add((rotation106));

        Rotation rotation107 = new Rotation();
        rotation107.setCont("asi");
        rotation107.setId("107");
        rotation107.setEsc("BKK");
        rotation107.setOn("4");
        rotation107.setAnumvol("166");
        rotation107.setAtpsvol("11H00");
        rotation107.setNbrp("5");
        rotation107.setIrmenu("46,03");
        rotation107.setTotalir("230,15");
        rotation107.setTep("50H15");
        rotation107.setRnumvol("165");
        rotation107.setRtpsvol("12H55");
        rotation107.setRpc("4RNN");
        rotation107.setPdj("oui");
        rotation107.setChcdg("oui");
        list.add((rotation107));

        Rotation rotation108 = new Rotation();
        rotation108.setCont("asi");
        rotation108.setId("108");
        rotation108.setEsc("BKK");
        rotation108.setOn("5");
        rotation108.setAnumvol("166");
        rotation108.setAtpsvol("11H00");
        rotation108.setNbrp("7");
        rotation108.setIrmenu("46,03");
        rotation108.setTotalir("322,21");
        rotation108.setTep("74H15");
        rotation108.setRnumvol("165");
        rotation108.setRtpsvol("12H55");
        rotation108.setRpc("4RNN");
        rotation108.setPdj("oui");
        rotation108.setChcdg("oui");
        list.add((rotation108));

        Rotation rotation109 = new Rotation();
        rotation109.setCont("asi");
        rotation109.setId("109");
        rotation109.setEsc("BLR");
        rotation109.setOn("4");
        rotation109.setAnumvol("192");
        rotation109.setAtpsvol("09H15");
        rotation109.setNbrp("4");
        rotation109.setIrmenu("39,78");
        rotation109.setTotalir("159,12");
        rotation109.setTep("49H10");
        rotation109.setRnumvol("191");
        rotation109.setRtpsvol("10H45");
        rotation109.setRpc("3RNN");
        rotation109.setPdj("oui");
        rotation109.setChcdg("Si retard > 0H45mn");
        list.add((rotation109));

        Rotation rotation110 = new Rotation();
        rotation110.setCont("asi");
        rotation110.setId("110");
        rotation110.setEsc("BOM");
        rotation110.setOn("3");
        rotation110.setAnumvol("218");
        rotation110.setAtpsvol("08H45");
        rotation110.setNbrp("2");
        rotation110.setIrmenu("39,78");
        rotation110.setTotalir("79,56");
        rotation110.setTep("26H15");
        rotation110.setRnumvol("217");
        rotation110.setRtpsvol("09H55");
        rotation110.setRpc("3RNN");
        rotation110.setPdj("non");
        rotation110.setChcdg("Si retard > 0H45mn");
        list.add((rotation110));

        Rotation rotation111 = new Rotation();
        rotation111.setCont("asi");
        rotation111.setId("111");
        rotation111.setEsc("CAN");
        rotation111.setOn("4");
        rotation111.setAnumvol("102");
        rotation111.setAtpsvol("12H00");
        rotation111.setNbrp("3");
        rotation111.setIrmenu("41,15");
        rotation111.setTotalir("123,45");
        rotation111.setTep("28H45");
        rotation111.setRnumvol("107");
        rotation111.setRtpsvol("13H20");
        rotation111.setRpc("4RNN");
        rotation111.setPdj("non");
        rotation111.setChcdg("oui");
        list.add((rotation111));

        Rotation rotation112 = new Rotation();
        rotation112.setCont("asi");
        rotation112.setId("112");
        rotation112.setEsc("CAN");
        rotation112.setOn("5");
        rotation112.setAnumvol("102");
        rotation112.setAtpsvol("12H00");
        rotation112.setNbrp("5");
        rotation112.setIrmenu("41,15");
        rotation112.setTotalir("205,75");
        rotation112.setTep("52H45");
        rotation112.setRnumvol("107");
        rotation112.setRtpsvol("13H20");
        rotation112.setRpc("4RNN");
        rotation112.setPdj("non");
        rotation112.setChcdg("oui");
        list.add((rotation112));

        Rotation rotation113 = new Rotation();
        rotation113.setCont("asi");
        rotation113.setId("113");
        rotation113.setEsc("DEL");
        rotation113.setOn("3");
        rotation113.setAnumvol("226");
        rotation113.setAtpsvol("8H20");
        rotation113.setNbrp("2");
        rotation113.setIrmenu("39,78");
        rotation113.setTotalir("79,56");
        rotation113.setTep("26H10");
        rotation113.setRnumvol("225");
        rotation113.setRtpsvol("09H20");
        rotation113.setRpc("3RNN");
        rotation113.setPdj("non");
        rotation113.setChcdg("Si retard > 1H40mn");
        list.add((rotation113));

        Rotation rotation114 = new Rotation();
        rotation114.setCont("asi");
        rotation114.setId("114");
        rotation114.setEsc("HKG");
        rotation114.setOn("5");
        rotation114.setAnumvol("188");
        rotation114.setAtpsvol("12H05");
        rotation114.setNbrp("5");
        rotation114.setIrmenu("53,49");
        rotation114.setTotalir("267,45");
        rotation114.setTep("52H45");
        rotation114.setRnumvol("185");
        rotation114.setRtpsvol("13H20");
        rotation114.setRpc("4RNN");
        rotation114.setPdj("non");
        rotation114.setChcdg("oui");
        list.add((rotation114));

        Rotation rotation115 = new Rotation();
        rotation115.setCont("asi");
        rotation115.setId("115");
        rotation115.setEsc("HND");
        rotation115.setOn("4");
        rotation115.setAnumvol("272");
        rotation115.setAtpsvol("12H05");
        rotation115.setNbrp("4+1CDG");
        rotation115.setIrmenu("56,07");
        rotation115.setTotalir("242,68");
        rotation115.setTep("49H40");
        rotation115.setRnumvol("279");
        rotation115.setRtpsvol("12H50");
        rotation115.setRpc("4RNN");
        rotation115.setPdj("non");
        rotation115.setChcdg("oui");
        list.add((rotation115));

        Rotation rotation116 = new Rotation();
        rotation116.setCont("asi");
        rotation116.setId("116");
        rotation116.setEsc("HND");
        rotation116.setOn("5");
        rotation116.setAnumvol("274");
        rotation116.setAtpsvol("12H05");
        rotation116.setNbrp("5");
        rotation116.setIrmenu("56,07");
        rotation116.setTotalir("280,35");
        rotation116.setTep("52H25");
        rotation116.setRnumvol("293");
        rotation116.setRtpsvol("12H55");
        rotation116.setRpc("4RNN");
        rotation116.setPdj("non");
        rotation116.setChcdg("oui");
        list.add((rotation116));

        Rotation rotation117 = new Rotation();
        rotation117.setCont("asi");
        rotation117.setId("117");
        rotation117.setEsc("HND");
        rotation117.setOn("5");
        rotation117.setAnumvol("274");
        rotation117.setAtpsvol("12H05");
        rotation117.setNbrp("5+1CDG");
        rotation117.setIrmenu("56,07");
        rotation117.setTotalir("298,75");
        rotation117.setTep("66H20");
        rotation117.setRnumvol("279");
        rotation117.setRtpsvol("12H50");
        rotation117.setRpc("4RNN");
        rotation117.setPdj("non");
        rotation117.setChcdg("oui");
        list.add((rotation117));

        Rotation rotation118 = new Rotation();
        rotation118.setCont("asi");
        rotation118.setId("118");
        rotation118.setEsc("HND");
        rotation118.setOn("5");
        rotation118.setAnumvol("272");
        rotation118.setAtpsvol("12H00");
        rotation118.setNbrp("6");
        rotation118.setIrmenu("56,07");
        rotation118.setTotalir("336,42");
        rotation118.setTep("59H45");
        rotation118.setRnumvol("293");
        rotation118.setRtpsvol("12H55");
        rotation118.setRpc("4RNN");
        rotation118.setPdj("non");
        rotation118.setChcdg("oui");
        list.add((rotation118));

        Rotation rotation119 = new Rotation();
        rotation119.setCont("asi");
        rotation119.setId("119");
        rotation119.setEsc("HND");
        rotation119.setOn("5");
        rotation119.setAnumvol("272");
        rotation119.setAtpsvol("12H00");
        rotation119.setNbrp("6+1CDG");
        rotation119.setIrmenu("56,07");
        rotation119.setTotalir("354,82");
        rotation119.setTep("73H40");
        rotation119.setRnumvol("279");
        rotation119.setRtpsvol("12H50");
        rotation119.setRpc("4RNN");
        rotation119.setPdj("non");
        rotation119.setChcdg("oui");
        list.add((rotation119));

        Rotation rotation120 = new Rotation();
        rotation120.setCont("asi");
        rotation120.setId("120");
        rotation120.setEsc("HND");
        rotation120.setOn("6");
        rotation120.setAnumvol("274");
        rotation120.setAtpsvol("12H05");
        rotation120.setNbrp("7");
        rotation120.setIrmenu("56,07");
        rotation120.setTotalir("392,49");
        rotation120.setTep("76H25");
        rotation120.setRnumvol("293");
        rotation120.setRtpsvol("12H55");
        rotation120.setRpc("4RNN");
        rotation120.setPdj("non");
        rotation120.setChcdg("oui");
        list.add((rotation120));

        Rotation rotation121 = new Rotation();
        rotation121.setCont("asi");
        rotation121.setId("121");
        rotation121.setEsc("ICN");
        rotation121.setOn("4");
        rotation121.setAnumvol("264");
        rotation121.setAtpsvol("11H00");
        rotation121.setNbrp("4+2CDG");
        rotation121.setIrmenu("62,99");
        rotation121.setTotalir("288,76");
        rotation121.setTep("49H45");
        rotation121.setRnumvol("267");
        rotation121.setRtpsvol("12H15");
        rotation121.setRpc("4RNN");
        rotation121.setPdj("oui");
        rotation121.setChcdg("oui");
        list.add((rotation121));

        Rotation rotation122 = new Rotation();
        rotation122.setCont("asi");
        rotation122.setId("122");
        rotation122.setEsc("MLE");
        rotation122.setOn("5");
        rotation122.setAnumvol("223");
        rotation122.setAtpsvol("09H50");
        rotation122.setNbrp("7");
        rotation122.setIrmenu("0,00");
        rotation122.setTotalir("0,00");
        rotation122.setTep("73H45");
        rotation122.setRnumvol("223");
        rotation122.setRtpsvol("10H50");
        rotation122.setRpc("3RNN");
        rotation122.setPdj("oui");
        rotation122.setChcdg("Si retard > 0H10mn");
        list.add((rotation122));

        Rotation rotation123 = new Rotation();
        rotation123.setCont("asi");
        rotation123.setId("123");
        rotation123.setEsc("MLE");
        rotation123.setOn("6");
        rotation123.setAnumvol("223");
        rotation123.setAtpsvol("09H50");
        rotation123.setNbrp("9");
        rotation123.setIrmenu("0,00");
        rotation123.setTotalir("0,00");
        rotation123.setTep("97H45");
        rotation123.setRnumvol("223");
        rotation123.setRtpsvol("10H50");
        rotation123.setRpc("3RNN");
        rotation123.setPdj("oui");
        rotation123.setChcdg("Si retard > 0H10mn");
        list.add((rotation123));

        Rotation rotation124 = new Rotation();
        rotation124.setCont("asi");
        rotation124.setId("124");
        rotation124.setEsc("KIX");
        rotation124.setOn("5");
        rotation124.setAnumvol("292");
        rotation124.setAtpsvol("12H00");
        rotation124.setNbrp("6+2CDG");
        rotation124.setIrmenu("56,07");
        rotation124.setTotalir("373,22");
        rotation124.setTep("73H45");
        rotation124.setRnumvol("291");
        rotation124.setRtpsvol("12H50");
        rotation124.setRpc("4RNN");
        rotation124.setPdj("oui");
        rotation124.setChcdg("oui");
        list.add((rotation124));

        Rotation rotation125 = new Rotation();
        rotation125.setCont("asi");
        rotation125.setId("125");
        rotation125.setEsc("KIX");
        rotation125.setOn("6");
        rotation125.setAnumvol("292");
        rotation125.setAtpsvol("12H00");
        rotation125.setNbrp("8+2CDG");
        rotation125.setIrmenu("56,07");
        rotation125.setTotalir("485,36");
        rotation125.setTep("97H45");
        rotation125.setRnumvol("291");
        rotation125.setRtpsvol("12H50");
        rotation125.setRpc("4RNN");
        rotation125.setPdj("oui");
        rotation125.setChcdg("oui");
        list.add((rotation125));

        Rotation rotation126 = new Rotation();
        rotation126.setCont("asi");
        rotation126.setId("126");
        rotation126.setEsc("NRT");
        rotation126.setOn("4");
        rotation126.setAnumvol("276");
        rotation126.setAtpsvol("11H50");
        rotation126.setNbrp("4+2CDG");
        rotation126.setIrmenu("56,07");
        rotation126.setTotalir("261,08");
        rotation126.setTep("49H40");
        rotation126.setRnumvol("275");
        rotation126.setRtpsvol("12H50");
        rotation126.setRpc("4RNN");
        rotation126.setPdj("oui");
        rotation126.setChcdg("oui");
        list.add((rotation126));

        Rotation rotation127 = new Rotation();
        rotation127.setCont("asi");
        rotation127.setId("127");
        rotation127.setEsc("PEK");
        rotation127.setOn("4");
        rotation127.setAnumvol("382");
        rotation127.setAtpsvol("10H05");
        rotation127.setNbrp("3+1CDG");
        rotation127.setIrmenu("41,15");
        rotation127.setTotalir("141,85");
        rotation127.setTep("41H35");
        rotation127.setRnumvol("125");
        rotation127.setRtpsvol("11H00");
        rotation127.setRpc("3RNN");
        rotation127.setPdj("oui");
        rotation127.setChcdg("oui");
        list.add((rotation127));

        Rotation rotation128 = new Rotation();
        rotation128.setCont("asi");
        rotation128.setId("128");
        rotation128.setEsc("PEK");
        rotation128.setOn("4");
        rotation128.setAnumvol("382");
        rotation128.setAtpsvol("10H05");
        rotation128.setNbrp("3");
        rotation128.setIrmenu("41,15");
        rotation128.setTotalir("123,45");
        rotation128.setTep("32H35");
        rotation128.setRnumvol("381");
        rotation128.setRtpsvol("11H00");
        rotation128.setRpc("3RNN");
        rotation128.setPdj("oui");
        rotation128.setChcdg("oui");
        list.add((rotation128));

        Rotation rotation129 = new Rotation();
        rotation129.setCont("asi");
        rotation129.setId("129");
        rotation129.setEsc("PEK");
        rotation129.setOn("4");
        rotation129.setAnumvol("128");
        rotation129.setAtpsvol("10H05");
        rotation129.setNbrp("4+1CDG");
        rotation129.setIrmenu("41,15");
        rotation129.setTotalir("183,00");
        rotation129.setTep("42H20");
        rotation129.setRnumvol("381");
        rotation129.setRtpsvol("11H00");
        rotation129.setRpc("3RNN");
        rotation129.setPdj("oui");
        rotation129.setChcdg("oui");
        list.add((rotation129));

        Rotation rotation130 = new Rotation();
        rotation130.setCont("asi");
        rotation130.setId("130");
        rotation130.setEsc("PEK");
        rotation130.setOn("5");
        rotation130.setAnumvol("382");
        rotation130.setAtpsvol("10H05");
        rotation130.setNbrp("5");
        rotation130.setIrmenu("41,15");
        rotation130.setTotalir("205,75");
        rotation130.setTep("56H35");
        rotation130.setRnumvol("381");
        rotation130.setRtpsvol("11H00");
        rotation130.setRpc("3RNN");
        rotation130.setPdj("oui");
        rotation130.setChcdg("oui");
        list.add((rotation130));

        Rotation rotation131 = new Rotation();
        rotation131.setCont("asi");
        rotation131.setId("131");
        rotation131.setEsc("PVG");
        rotation131.setOn("4");
        rotation131.setAnumvol("112");
        rotation131.setAtpsvol("11H15");
        rotation131.setNbrp("4+2CDG");
        rotation131.setIrmenu("41,15");
        rotation131.setTotalir("201,40");
        rotation131.setTep("51H10");
        rotation131.setRnumvol("117");
        rotation131.setRtpsvol("12H45");
        rotation131.setRpc("4RNN");
        rotation131.setPdj("oui");
        rotation131.setChcdg("oui");
        list.add((rotation131));

        Rotation rotation132 = new Rotation();
        rotation132.setCont("asi");
        rotation132.setId("132");
        rotation132.setEsc("PVG");
        rotation132.setOn("5");
        rotation132.setAnumvol("112");
        rotation132.setAtpsvol("11H15");
        rotation132.setNbrp("6+1CDG");
        rotation132.setIrmenu("41,15");
        rotation132.setTotalir("265,30");
        rotation132.setTep("64H20");
        rotation132.setRnumvol("111");
        rotation132.setRtpsvol("12H45");
        rotation132.setRpc("4RNN");
        rotation132.setPdj("oui");
        rotation132.setChcdg("oui");
        list.add((rotation132));

        Rotation rotation133 = new Rotation();
        rotation133.setCont("asi");
        rotation133.setId("133");
        rotation133.setEsc("PVG");
        rotation133.setOn("5");
        rotation133.setAnumvol("116");
        rotation133.setAtpsvol("11H20");
        rotation133.setNbrp("5+1CDG");
        rotation133.setIrmenu("41,15");
        rotation133.setTotalir("224,15");
        rotation133.setTep("65H15");
        rotation133.setRnumvol("117");
        rotation133.setRtpsvol("12H45");
        rotation133.setRpc("4RNN");
        rotation133.setPdj("non");
        rotation133.setChcdg("oui");
        list.add((rotation133));

        Rotation rotation134 = new Rotation();
        rotation134.setCont("asi");
        rotation134.setId("134");
        rotation134.setEsc("PVG");
        rotation134.setOn("5");
        rotation134.setAnumvol("116");
        rotation134.setAtpsvol("11H20");
        rotation134.setNbrp("5");
        rotation134.setIrmenu("41,15");
        rotation134.setTotalir("205,75");
        rotation134.setTep("54H25");
        rotation134.setRnumvol("111");
        rotation134.setRtpsvol("12H45");
        rotation134.setRpc("4RNN");
        rotation134.setPdj("non");
        rotation134.setChcdg("oui");
        list.add((rotation134));

        Rotation rotation135 = new Rotation();
        rotation135.setCont("asi");
        rotation135.setId("135");
        rotation135.setEsc("PVG");
        rotation135.setOn("5");
        rotation135.setAnumvol("112");
        rotation135.setAtpsvol("11H15");
        rotation135.setNbrp("6+2CDG");
        rotation135.setIrmenu("41,15");
        rotation135.setTotalir("283,70");
        rotation135.setTep("75H10");
        rotation135.setRnumvol("117");
        rotation135.setRtpsvol("12H45");
        rotation135.setRpc("4RNN");
        rotation135.setPdj("oui");
        rotation135.setChcdg("oui");
        list.add((rotation135));

        Rotation rotation136 = new Rotation();
        rotation136.setCont("asi");
        rotation136.setId("136");
        rotation136.setEsc("PVG");
        rotation136.setOn("6");
        rotation136.setAnumvol("116");
        rotation136.setAtpsvol("11H20");
        rotation136.setNbrp("7");
        rotation136.setIrmenu("41,15");
        rotation136.setTotalir("288,05");
        rotation136.setTep("78H25");
        rotation136.setRnumvol("111");
        rotation136.setRtpsvol("12H45");
        rotation136.setRpc("4RNN");
        rotation136.setPdj("non");
        rotation136.setChcdg("oui");
        list.add((rotation136));

        Rotation rotation137 = new Rotation();
        rotation137.setCont("asi");
        rotation137.setId("137");
        rotation137.setEsc("PVG");
        rotation137.setOn("6");
        rotation137.setAnumvol("116");
        rotation137.setAtpsvol("11H20");
        rotation137.setNbrp("7+1CDG");
        rotation137.setIrmenu("41,15");
        rotation137.setTotalir("306,45");
        rotation137.setTep("89H15");
        rotation137.setRnumvol("117");
        rotation137.setRtpsvol("12H45");
        rotation137.setRpc("4RNN");
        rotation137.setPdj("non");
        rotation137.setChcdg("oui");
        list.add((rotation137));

        Rotation rotation138 = new Rotation();
        rotation138.setCont("asi");
        rotation138.setId("138");
        rotation138.setEsc("SGN");
        rotation138.setOn("4");
        rotation138.setAnumvol("258");
        rotation138.setAtpsvol("11H55");
        rotation138.setNbrp("4+1CDG");
        rotation138.setIrmenu("39,40");
        rotation138.setTotalir("176,00");
        rotation138.setTep("50H00");
        rotation138.setRnumvol("253");
        rotation138.setRtpsvol("13H30");
        rotation138.setRpc("4RNN");
        rotation138.setPdj("oui");
        rotation138.setChcdg("oui");
        list.add((rotation138));

        Rotation rotation139 = new Rotation();
        rotation139.setCont("asi");
        rotation139.setId("139");
        rotation139.setEsc("SGN");
        rotation139.setOn("5");
        rotation139.setAnumvol("258");
        rotation139.setAtpsvol("11H55");
        rotation139.setNbrp("6+1CDG");
        rotation139.setIrmenu("39,40");
        rotation139.setTotalir("254,80");
        rotation139.setTep("74H00");
        rotation139.setRnumvol("253");
        rotation139.setRtpsvol("13H30");
        rotation139.setRpc("4RNN");
        rotation139.setPdj("oui");
        rotation139.setChcdg("oui");
        list.add((rotation139));

        Rotation rotation140 = new Rotation();
        rotation140.setCont("asi");
        rotation140.setId("140");
        rotation140.setEsc("SIN");
        rotation140.setOn("5");
        rotation140.setAnumvol("256");
        rotation140.setAtpsvol("12H45");
        rotation140.setNbrp("5+1CDG");
        rotation140.setIrmenu("49,51");
        rotation140.setTotalir("265,95");
        rotation140.setTep("54H30");
        rotation140.setRnumvol("257");
        rotation140.setRtpsvol("14H05");
        rotation140.setRpc("4RNN");
        rotation140.setPdj("non");
        rotation140.setChcdg("oui");
        list.add((rotation140));

        Rotation rotation141 = new Rotation();
        rotation141.setCont("asi");
        rotation141.setId("141");
        rotation141.setEsc("WUH");
        rotation141.setOn("4");
        rotation141.setAnumvol("132");
        rotation141.setAtpsvol("11H15");
        rotation141.setNbrp("4+1CDG");
        rotation141.setIrmenu("41,15");
        rotation141.setTotalir("223,00");
        rotation141.setTep("49H55");
        rotation141.setRnumvol("139");
        rotation141.setRtpsvol("12H20");
        rotation141.setRpc("4RNN");
        rotation141.setPdj("oui");
        rotation141.setChcdg("oui");
        list.add((rotation141));

        Rotation rotation142 = new Rotation();
        rotation142.setCont("asi");
        rotation142.setId("142");
        rotation142.setEsc("WUH");
        rotation142.setOn("4");
        rotation142.setAnumvol("132");
        rotation142.setAtpsvol("11H15");
        rotation142.setNbrp("6+1CDG");
        rotation142.setIrmenu("41,15");
        rotation142.setTotalir("265,30");
        rotation142.setTep("73H55");
        rotation142.setRnumvol("139");
        rotation142.setRtpsvol("12H20");
        rotation142.setRpc("4RNN");
        rotation142.setPdj("oui");
        rotation142.setChcdg("oui");
        list.add((rotation142));

        Rotation rotation143 = new Rotation();
        rotation143.setCont("amsu");
        rotation143.setId("143");
        rotation143.setEsc("BOG");
        rotation143.setOn("4");
        rotation143.setAnumvol("422");
        rotation143.setAtpsvol("11H25");
        rotation143.setNbrp("4");
        rotation143.setIrmenu("19,66");
        rotation143.setTotalir("78,64");
        rotation143.setTep("50H10");
        rotation143.setRnumvol("429");
        rotation143.setRtpsvol("10H55");
        rotation143.setRpc("3 RNN");
        rotation143.setPdj("non");
        rotation143.setChcdg("Si retard > 0h05mn");
        list.add((rotation143));

        Rotation rotation144 = new Rotation();
        rotation144.setCont("amsu");
        rotation144.setId("144");
        rotation144.setEsc("BOG");
        rotation144.setOn("5");
        rotation144.setAnumvol("422");
        rotation144.setAtpsvol("11H05");
        rotation144.setNbrp("6");
        rotation144.setIrmenu("19,66");
        rotation144.setTotalir("117,96");
        rotation144.setTep("74h10");
        rotation144.setRnumvol("429");
        rotation144.setRtpsvol("10H50");
        rotation144.setRpc("3 RNN");
        rotation144.setPdj("non");
        rotation144.setChcdg("Si retard > 0h05mn");
        list.add((rotation144));

        Rotation rotation145 = new Rotation();
        rotation145.setCont("amsu");
        rotation145.setId("145");
        rotation145.setEsc("CCS");
        rotation145.setOn("3");
        rotation145.setAnumvol("368");
        rotation145.setAtpsvol("10H10");
        rotation145.setNbrp("2");
        rotation145.setIrmenu("FOnB");
        rotation145.setTotalir("0,00");
        rotation145.setTep("26H00");
        rotation145.setRnumvol("385");
        rotation145.setRtpsvol("09H20");
        rotation145.setRpc("3 RNN");
        rotation145.setPdj("non");
        rotation145.setChcdg("Si retard > 1h40mn");
        list.add((rotation145));

        Rotation rotation146 = new Rotation();
        rotation146.setCont("amsu");
        rotation146.setId("146");
        rotation146.setEsc("CCS");
        rotation146.setOn("4");
        rotation146.setAnumvol("368");
        rotation146.setAtpsvol("10H10");
        rotation146.setNbrp("4");
        rotation146.setIrmenu("FOnB");
        rotation146.setTotalir("0,00");
        rotation146.setTep("50H00");
        rotation146.setRnumvol("385");
        rotation146.setRtpsvol("09H20");
        rotation146.setRpc("2 RNN");
        rotation146.setPdj("non");
        rotation146.setChcdg("Si retard > 1h40mn");
        list.add((rotation146));

        Rotation rotation147 = new Rotation();
        rotation147.setCont("amsu");
        rotation147.setId("147");
        rotation147.setEsc("EZE");
        rotation147.setOn("65");
        rotation147.setAnumvol("228");
        rotation147.setAtpsvol("13H55");
        rotation147.setNbrp("5");
        rotation147.setIrmenu("27,29");
        rotation147.setTotalir("136,45");
        rotation147.setTep("53H20");
        rotation147.setRnumvol("229");
        rotation147.setRtpsvol("13H10");
        rotation147.setRpc("4 RNN");
        rotation147.setPdj("oui");
        rotation147.setChcdg("oui");
        list.add((rotation147));

        Rotation rotation148 = new Rotation();
        rotation148.setCont("amsu");
        rotation148.setId("148");
        rotation148.setEsc("GIG");
        rotation148.setOn("5");
        rotation148.setAnumvol("442");
        rotation148.setAtpsvol("11h35");
        rotation148.setNbrp("5");
        rotation148.setIrmenu("33,87");
        rotation148.setTotalir("169,35");
        rotation148.setTep("57H25");
        rotation148.setRnumvol("443");
        rotation148.setRtpsvol("11h05");
        rotation148.setRpc("3 RNN");
        rotation148.setPdj("oui");
        rotation148.setChcdg("oui");
        list.add((rotation148));

        Rotation rotation149 = new Rotation();
        rotation149.setCont("amsu");
        rotation149.setId("149");
        rotation149.setEsc("GRU");
        rotation149.setOn("3");
        rotation149.setAnumvol("456");
        rotation149.setAtpsvol("12H00");
        rotation149.setNbrp("2");
        rotation149.setIrmenu("33,87");
        rotation149.setTotalir("67,74");
        rotation149.setTep("26H00");
        rotation149.setRnumvol("459");
        rotation149.setRtpsvol("11H15");
        rotation149.setRpc("4 RNN");
        rotation149.setPdj("non");
        rotation149.setChcdg("oui");
        list.add((rotation149));

        Rotation rotation150 = new Rotation();
        rotation150.setCont("amsu");
        rotation150.setId("150");
        rotation150.setEsc("GRU");
        rotation150.setOn("4");
        rotation150.setAnumvol("456");
        rotation150.setAtpsvol("12H00");
        rotation150.setNbrp("4");
        rotation150.setIrmenu("33,87");
        rotation150.setTotalir("135,48");
        rotation150.setTep("46H40");
        rotation150.setRnumvol("457");
        rotation150.setRtpsvol("11H30");
        rotation150.setRpc("3 RNN");
        rotation150.setPdj("non");
        rotation150.setChcdg("oui");
        list.add((rotation150));

        Rotation rotation151 = new Rotation();
        rotation151.setCont("amsu");
        rotation151.setId("151");
        rotation151.setEsc("GRU");
        rotation151.setOn("4");
        rotation151.setAnumvol("456");
        rotation151.setAtpsvol("12H00");
        rotation151.setNbrp("4");
        rotation151.setIrmenu("33,87");
        rotation151.setTotalir("135,48");
        rotation151.setTep("50H00");
        rotation151.setRnumvol("459");
        rotation151.setRtpsvol("11H15");
        rotation151.setRpc("3 RNN");
        rotation151.setPdj("non");
        rotation151.setChcdg("oui");
        list.add((rotation151));

        Rotation rotation152 = new Rotation();
        rotation152.setCont("amsu");
        rotation152.setId("152");
        rotation152.setEsc("GRU");
        rotation152.setOn("4");
        rotation152.setAnumvol("454");
        rotation152.setAtpsvol("12H00");
        rotation152.setNbrp("3");
        rotation152.setIrmenu("33,87");
        rotation152.setTotalir("101,61");
        rotation152.setTep("33H25");
        rotation152.setRnumvol("457");
        rotation152.setRtpsvol("11H30");
        rotation152.setRpc("3 RNN");
        rotation152.setPdj("oui");
        rotation152.setChcdg("oui");
        list.add((rotation152));

        Rotation rotation153 = new Rotation();
        rotation153.setCont("amsu");
        rotation153.setId("153");
        rotation153.setEsc("GRU");
        rotation153.setOn("4");
        rotation153.setAnumvol("454");
        rotation153.setAtpsvol("12H00");
        rotation153.setNbrp("3");
        rotation153.setIrmenu("33,87");
        rotation153.setTotalir("101,61");
        rotation153.setTep("36H45");
        rotation153.setRnumvol("459");
        rotation153.setRtpsvol("11H15");
        rotation153.setRpc("3 RNN");
        rotation153.setPdj("oui");
        rotation153.setChcdg("oui");
        list.add((rotation153));

        Rotation rotation154 = new Rotation();
        rotation154.setCont("amsu");
        rotation154.setId("154");
        rotation154.setEsc("GRU");
        rotation154.setOn("5");
        rotation154.setAnumvol("456");
        rotation154.setAtpsvol("12H00");
        rotation154.setNbrp("6");
        rotation154.setIrmenu("33,87");
        rotation154.setTotalir("203,22");
        rotation154.setTep("70H40");
        rotation154.setRnumvol("457");
        rotation154.setRtpsvol("11H30");
        rotation154.setRpc("3 RNN");
        rotation154.setPdj("non");
        rotation154.setChcdg("oui");
        list.add((rotation154));

        Rotation rotation155 = new Rotation();
        rotation155.setCont("amsu");
        rotation155.setId("155");
        rotation155.setEsc("GRU");
        rotation155.setOn("5");
        rotation155.setAnumvol("454");
        rotation155.setAtpsvol("12H00");
        rotation155.setNbrp("5");
        rotation155.setIrmenu("33,87");
        rotation155.setTotalir("169,35");
        rotation155.setTep("57H25");
        rotation155.setRnumvol("457");
        rotation155.setRtpsvol("11H30");
        rotation155.setRpc("3 RNN");
        rotation155.setPdj("oui");
        rotation155.setChcdg("oui");
        list.add((rotation155));

        Rotation rotation156 = new Rotation();
        rotation156.setCont("amsu");
        rotation156.setId("156");
        rotation156.setEsc("GRU");
        rotation156.setOn("5");
        rotation156.setAnumvol("454");
        rotation156.setAtpsvol("12H00");
        rotation156.setNbrp("5");
        rotation156.setIrmenu("33,87");
        rotation156.setTotalir("169,35");
        rotation156.setTep("60h45");
        rotation156.setRnumvol("459");
        rotation156.setRtpsvol("11H15");
        rotation156.setRpc("3 RNN");
        rotation156.setPdj("oui");
        rotation156.setChcdg("oui");
        list.add((rotation156));

        Rotation rotation157 = new Rotation();
        rotation157.setCont("amsu");
        rotation157.setId("157");
        rotation157.setEsc("GRU");
        rotation157.setOn("6");
        rotation157.setAnumvol("454");
        rotation157.setAtpsvol("12H00");
        rotation157.setNbrp("7");
        rotation157.setIrmenu("33,87");
        rotation157.setTotalir("237,09");
        rotation157.setTep("81H25");
        rotation157.setRnumvol("457");
        rotation157.setRtpsvol("11H30");
        rotation157.setRpc("3 RNN");
        rotation157.setPdj("oui");
        rotation157.setChcdg("oui");
        list.add((rotation157));

        Rotation rotation158 = new Rotation();
        rotation158.setCont("amsu");
        rotation158.setId("158");
        rotation158.setEsc("LIM");
        rotation158.setOn("4");
        rotation158.setAnumvol("480");
        rotation158.setAtpsvol("12H25");
        rotation158.setNbrp("4+1CDG");
        rotation158.setIrmenu("39,64");
        rotation158.setTotalir("176,96");
        rotation158.setTep("50H20");
        rotation158.setRnumvol("483");
        rotation158.setRtpsvol("12H25");
        rotation158.setRpc("4 RNN");
        rotation158.setPdj("non");
        rotation158.setChcdg("oui");
        list.add((rotation158));

        Rotation rotation159 = new Rotation();
        rotation159.setCont("amsu");
        rotation159.setId("159");
        rotation159.setEsc("LIM");
        rotation159.setOn("5");
        rotation159.setAnumvol("480");
        rotation159.setAtpsvol("12H25");
        rotation159.setNbrp("6+1CDG");
        rotation159.setIrmenu("39,64");
        rotation159.setTotalir("256,24");
        rotation159.setTep("74H20");
        rotation159.setRnumvol("483");
        rotation159.setRtpsvol("12H25");
        rotation159.setRpc("4 RNN");
        rotation159.setPdj("non");
        rotation159.setChcdg("oui");
        list.add((rotation159));

        Rotation rotation160 = new Rotation();
        rotation160.setCont("amsu");
        rotation160.setId("160");
        rotation160.setEsc("PTY");
        rotation160.setOn("4");
        rotation160.setAnumvol("474");
        rotation160.setAtpsvol("11H20");
        rotation160.setNbrp("4+1CDG");
        rotation160.setIrmenu("34,72");
        rotation160.setTotalir("157,28");
        rotation160.setTep("50H05");
        rotation160.setRnumvol("475");
        rotation160.setRtpsvol("10H00");
        rotation160.setRpc("3 RNN");
        rotation160.setPdj("non");
        rotation160.setChcdg("Si retard > 1h00mn");
        list.add((rotation160));

        Rotation rotation161 = new Rotation();
        rotation161.setCont("amsu");
        rotation161.setId("161");
        rotation161.setEsc("PTY");
        rotation161.setOn("5");
        rotation161.setAnumvol("474");
        rotation161.setAtpsvol("11H20");
        rotation161.setNbrp("6+1CDG");
        rotation161.setIrmenu("34,72");
        rotation161.setTotalir("226,72");
        rotation161.setTep("74H05");
        rotation161.setRnumvol("475");
        rotation161.setRtpsvol("10H05");
        rotation161.setRpc("3 RNN");
        rotation161.setPdj("non");
        rotation161.setChcdg("Si retard > 0h55mn");
        list.add((rotation161));

        Rotation rotation162 = new Rotation();
        rotation162.setCont("amsu");
        rotation162.setId("162");
        rotation162.setEsc("SCL");
        rotation162.setOn("5");
        rotation162.setAnumvol("406");
        rotation162.setAtpsvol("14h30");
        rotation162.setNbrp("5");
        rotation162.setIrmenu("30,02");
        rotation162.setTotalir("150,10");
        rotation162.setTep("52H05");
        rotation162.setRnumvol("401");
        rotation162.setRtpsvol("14H00");
        rotation162.setRpc("4 RNN");
        rotation162.setPdj("non");
        rotation162.setChcdg("oui");
        list.add((rotation162));

        Rotation rotation163 = new Rotation();
        rotation163.setCont("amsu");
        rotation163.setId("163");
        rotation163.setEsc("SJO");
        rotation163.setOn("4");
        rotation163.setAnumvol("430");
        rotation163.setAtpsvol("11H40");
        rotation163.setNbrp("5");
        rotation163.setIrmenu("32,87");
        rotation163.setTotalir("164,35");
        rotation163.setTep("52H25");
        rotation163.setRnumvol("431");
        rotation163.setRtpsvol("10H35");
        rotation163.setRpc("3 RNN");
        rotation163.setPdj("non");
        rotation163.setChcdg("Si retard > 0h25mn");
        list.add((rotation163));

        Rotation rotation164 = new Rotation();
        rotation164.setCont("amsu");
        rotation164.setId("164");
        rotation164.setEsc("SJO");
        rotation164.setOn("5");
        rotation164.setAnumvol("430");
        rotation164.setAtpsvol("11H40");
        rotation164.setNbrp("7");
        rotation164.setIrmenu("32,87");
        rotation164.setTotalir("230,09");
        rotation164.setTep("76H25");
        rotation164.setRnumvol("431");
        rotation164.setRtpsvol("10H35");
        rotation164.setRpc("4 RNN");
        rotation164.setPdj("non");
        rotation164.setChcdg("Si retard > 0h25mn");
        list.add((rotation164));

        Rotation rotation165 = new Rotation();
        rotation165.setCont("acoi");
        rotation165.setId("165");
        rotation165.setEsc("CAY");
        rotation165.setOn("3");
        rotation165.setAnumvol("852");
        rotation165.setAtpsvol("09H05");
        rotation165.setNbrp("2");
        rotation165.setIrmenu("50,40");
        rotation165.setTotalir("100,80");
        rotation165.setTep("26h45");
        rotation165.setRnumvol("853");
        rotation165.setRtpsvol("08H25");
        rotation165.setRpc("3 RNN");
        rotation165.setPdj("non");
        rotation165.setChcdg("Si retard > 2h35mn");
        list.add((rotation165));

        Rotation rotation166 = new Rotation();
        rotation166.setCont("acoi");
        rotation166.setId("166");
        rotation166.setEsc("FDF");
        rotation166.setOn("3");
        rotation166.setAnumvol("842");
        rotation166.setAtpsvol("08H45");
        rotation166.setNbrp("2");
        rotation166.setIrmenu("50,40");
        rotation166.setTotalir("100,80");
        rotation166.setTep("26H20");
        rotation166.setRnumvol("841");
        rotation166.setRtpsvol("08H15");
        rotation166.setRpc("2 RNN");
        rotation166.setPdj("non");
        rotation166.setChcdg("Si retard > 2h45mn");
        list.add((rotation166));

        Rotation rotation167 = new Rotation();
        rotation167.setCont("acoi");
        rotation167.setId("167");
        rotation167.setEsc("FDF");
        rotation167.setOn("3");
        rotation167.setAnumvol("842");
        rotation167.setAtpsvol("08H45");
        rotation167.setNbrp("2");
        rotation167.setIrmenu("50,40");
        rotation167.setTotalir("100,80");
        rotation167.setTep("29H10");
        rotation167.setRnumvol("847");
        rotation167.setRtpsvol("08H20");
        rotation167.setRpc("2 RNN");
        rotation167.setPdj("non");
        rotation167.setChcdg("Si retard > 2h40mn");
        list.add((rotation167));

        Rotation rotation168 = new Rotation();
        rotation168.setCont("acoi");
        rotation168.setId("168");
        rotation168.setEsc("FDF");
        rotation168.setOn("3");
        rotation168.setAnumvol("848");
        rotation168.setAtpsvol("08H50");
        rotation168.setNbrp("1");
        rotation168.setIrmenu("50,40");
        rotation168.setTotalir("50,40");
        rotation168.setTep("26H20");
        rotation168.setRnumvol("847");
        rotation168.setRtpsvol("08H20");
        rotation168.setRpc("2 RNN");
        rotation168.setPdj("non");
        rotation168.setChcdg("Si retard > 2h40mn");
        list.add((rotation168));

        Rotation rotation169 = new Rotation();
        rotation169.setCont("acoi");
        rotation169.setId("169");
        rotation169.setEsc("FDF");
        rotation169.setOn("4");
        rotation169.setAnumvol("848");
        rotation169.setAtpsvol("08H50");
        rotation169.setNbrp("3");
        rotation169.setIrmenu("50,40");
        rotation169.setTotalir("151,20");
        rotation169.setTep("47H30");
        rotation169.setRnumvol("841");
        rotation169.setRtpsvol("08H15");
        rotation169.setRpc("2 RNN");
        rotation169.setPdj("non");
        rotation169.setChcdg("Si retard > 2h45mn");
        list.add((rotation169));

        Rotation rotation170 = new Rotation();
        rotation170.setCont("acoi");
        rotation170.setId("170");
        rotation170.setEsc("FDF");
        rotation170.setOn("4");
        rotation170.setAnumvol("842");
        rotation170.setAtpsvol("08H45");
        rotation170.setNbrp("4");
        rotation170.setIrmenu("50,40");
        rotation170.setTotalir("201,60");
        rotation170.setTep("50H20");
        rotation170.setRnumvol("841");
        rotation170.setRtpsvol("08H15");
        rotation170.setRpc("2 RNN");
        rotation170.setPdj("non");
        rotation170.setChcdg("Si retard > 2h45mn");
        list.add((rotation170));

        Rotation rotation171 = new Rotation();
        rotation171.setCont("acoi");
        rotation171.setId("171");
        rotation171.setEsc("HAV");
        rotation171.setOn("3");
        rotation171.setAnumvol("946");
        rotation171.setAtpsvol("10H25");
        rotation171.setNbrp("2");
        rotation171.setIrmenu("22,40");
        rotation171.setTotalir("44,80");
        rotation171.setTep("26H20");
        rotation171.setRnumvol("943");
        rotation171.setRtpsvol("08H55");
        rotation171.setRpc("3 RNN");
        rotation171.setPdj("non");
        rotation171.setChcdg("Si retard > 2h05mn");
        list.add((rotation171));

        Rotation rotation172 = new Rotation();
        rotation172.setCont("acoi");
        rotation172.setId("172");
        rotation172.setEsc("HAV");
        rotation172.setOn("3");
        rotation172.setAnumvol("940");
        rotation172.setAtpsvol("10H25");
        rotation172.setNbrp("2");
        rotation172.setIrmenu("22,40");
        rotation172.setTotalir("44,80");
        rotation172.setTep("26H10");
        rotation172.setRnumvol("945");
        rotation172.setRtpsvol("08H55");
        rotation172.setRpc("3 RNN");
        rotation172.setPdj("non");
        rotation172.setChcdg("Si retard > 2h05mn");
        list.add((rotation172));

        Rotation rotation173 = new Rotation();
        rotation173.setCont("acoi");
        rotation173.setId("173");
        rotation173.setEsc("HAV");
        rotation173.setOn("4");
        rotation173.setAnumvol("940");
        rotation173.setAtpsvol("10H25");
        rotation173.setNbrp("3");
        rotation173.setIrmenu("22,40");
        rotation173.setTotalir("67,20");
        rotation173.setTep("47H35");
        rotation173.setRnumvol("943");
        rotation173.setRtpsvol("08H55");
        rotation173.setRpc("3 RNN");
        rotation173.setPdj("non");
        rotation173.setChcdg("Si retard > 2h05mn");
        list.add((rotation173));

        Rotation rotation174 = new Rotation();
        rotation174.setCont("acoi");
        rotation174.setId("174");
        rotation174.setEsc("HAV");
        rotation174.setOn("4");
        rotation174.setAnumvol("946");
        rotation174.setAtpsvol("10H25");
        rotation174.setNbrp("5");
        rotation174.setIrmenu("22,40");
        rotation174.setTotalir("112,00");
        rotation174.setTep("52H25");
        rotation174.setRnumvol("945");
        rotation174.setRtpsvol("08H55");
        rotation174.setRpc("3 RNN");
        rotation174.setPdj("non");
        rotation174.setChcdg("Si retard > 2h05mn");
        list.add((rotation174));

        Rotation rotation175 = new Rotation();
        rotation175.setCont("acoi");
        rotation175.setId("175");
        rotation175.setEsc("HAV");
        rotation175.setOn("5");
        rotation175.setAnumvol("940");
        rotation175.setAtpsvol("10H25");
        rotation175.setNbrp("6");
        rotation175.setIrmenu("22,40");
        rotation175.setTotalir("134,40");
        rotation175.setTep("74H10");
        rotation175.setRnumvol("945");
        rotation175.setRtpsvol("08H55");
        rotation175.setRpc("3 RNN");
        rotation175.setPdj("non");
        rotation175.setChcdg("Si retard > 2h05mn");
        list.add((rotation175));

        Rotation rotation176 = new Rotation();
        rotation176.setCont("acoi");
        rotation176.setId("176");
        rotation176.setEsc("MRU");
        rotation176.setOn("4");
        rotation176.setAnumvol("460");
        rotation176.setAtpsvol("11H00");
        rotation176.setNbrp("3");
        rotation176.setIrmenu("43,69");
        rotation176.setTotalir("131,07");
        rotation176.setTep("31H25");
        rotation176.setRnumvol("463");
        rotation176.setRtpsvol("11H50");
        rotation176.setRpc("3 RNN");
        rotation176.setPdj("non");
        rotation176.setChcdg("oui");
        list.add((rotation176));

        Rotation rotation177 = new Rotation();
        rotation177.setCont("acoi");
        rotation177.setId("177");
        rotation177.setEsc("MRU");
        rotation177.setOn("4");
        rotation177.setAnumvol("464");
        rotation177.setAtpsvol("11H00");
        rotation177.setNbrp("4");
        rotation177.setIrmenu("43,69");
        rotation177.setTotalir("174,76");
        rotation177.setTep("37H50");
        rotation177.setRnumvol("465");
        rotation177.setRtpsvol("11H50");
        rotation177.setRpc("3 RNN");
        rotation177.setPdj("oui");
        rotation177.setChcdg("oui");
        list.add((rotation177));

        Rotation rotation178 = new Rotation();
        rotation178.setCont("acoi");
        rotation178.setId("178");
        rotation178.setEsc("MRU");
        rotation178.setOn("5");
        rotation178.setAnumvol("464");
        rotation178.setAtpsvol("11H00");
        rotation178.setNbrp("6");
        rotation178.setIrmenu("43,69");
        rotation178.setTotalir("262,14");
        rotation178.setTep("61H50");
        rotation178.setRnumvol("465");
        rotation178.setRtpsvol("11H50");
        rotation178.setRpc("3 RNN");
        rotation178.setPdj("oui");
        rotation178.setChcdg("oui");
        list.add((rotation178));

        Rotation rotation179 = new Rotation();
        rotation179.setCont("acoi");
        rotation179.setId("179");
        rotation179.setEsc("MRU");
        rotation179.setOn("6");
        rotation179.setAnumvol("464");
        rotation179.setAtpsvol("11H00");
        rotation179.setNbrp("8");
        rotation179.setIrmenu("43,69");
        rotation179.setTotalir("349,52");
        rotation179.setTep("85H50");
        rotation179.setRnumvol("465");
        rotation179.setRtpsvol("11H50");
        rotation179.setRpc("3 RNN");
        rotation179.setPdj("oui");
        rotation179.setChcdg("oui");
        list.add((rotation179));

        Rotation rotation180 = new Rotation();
        rotation180.setCont("acoi");
        rotation180.setId("180");
        rotation180.setEsc("PTP");
        rotation180.setOn("3");
        rotation180.setAnumvol("792");
        rotation180.setAtpsvol("08H40");
        rotation180.setNbrp("2");
        rotation180.setIrmenu("48,96");
        rotation180.setTotalir("97,92");
        rotation180.setTep("26H25");
        rotation180.setRnumvol("793");
        rotation180.setRtpsvol("08H00");
        rotation180.setRpc("2 RNN");
        rotation180.setPdj("non");
        rotation180.setChcdg("Si retard > 3h00mn");
        list.add((rotation180));

        Rotation rotation181 = new Rotation();
        rotation181.setCont("acoi");
        rotation181.setId("181");
        rotation181.setEsc("PTP");
        rotation181.setOn("3");
        rotation181.setAnumvol("766");
        rotation181.setAtpsvol("08H45");
        rotation181.setNbrp("2");
        rotation181.setIrmenu("48,96");
        rotation181.setTotalir("97,92");
        rotation181.setTep("26H45");
        rotation181.setRnumvol("797");
        rotation181.setRtpsvol("08H00");
        rotation181.setRpc("2 RNN");
        rotation181.setPdj("non");
        rotation181.setChcdg("Si retard > 3h00mn");
        list.add((rotation181));

        Rotation rotation182 = new Rotation();
        rotation182.setCont("acoi");
        rotation182.setId("182");
        rotation182.setEsc("PTP");
        rotation182.setOn("3");
        rotation182.setAnumvol("792");
        rotation182.setAtpsvol("08H40");
        rotation182.setNbrp("2");
        rotation182.setIrmenu("48,96");
        rotation182.setTotalir("97,92");
        rotation182.setTep("29H55");
        rotation182.setRnumvol("797");
        rotation182.setRtpsvol("08H00");
        rotation182.setRpc("2 RNN");
        rotation182.setPdj("non");
        rotation182.setChcdg("Si retard > 3h00mn");
        list.add((rotation182));

        Rotation rotation183 = new Rotation();
        rotation183.setCont("acoi");
        rotation183.setId("183");
        rotation183.setEsc("PTP");
        rotation183.setOn("4");
        rotation183.setAnumvol("766");
        rotation183.setAtpsvol("08H45");
        rotation183.setNbrp("4");
        rotation183.setIrmenu("48,96");
        rotation183.setTotalir("195,84");
        rotation183.setTep("47H15");
        rotation183.setRnumvol("793");
        rotation183.setRtpsvol("08H00");
        rotation183.setRpc("2 RNN");
        rotation183.setPdj("non");
        rotation183.setChcdg("Si retard > 3h00mn");
        list.add((rotation183));

        Rotation rotation184 = new Rotation();
        rotation184.setCont("acoi");
        rotation184.setId("184");
        rotation184.setEsc("PTP");
        rotation184.setOn("4");
        rotation184.setAnumvol("792");
        rotation184.setAtpsvol("08H40");
        rotation184.setNbrp("4");
        rotation184.setIrmenu("48,96");
        rotation184.setTotalir("195,84");
        rotation184.setTep("50H25");
        rotation184.setRnumvol("793");
        rotation184.setRtpsvol("08H00");
        rotation184.setRpc("2 RNN");
        rotation184.setPdj("non");
        rotation184.setChcdg("Si retard > 3h00mn");
        list.add((rotation184));

        Rotation rotation185 = new Rotation();
        rotation185.setCont("acoi");
        rotation185.setId("185");
        rotation185.setEsc("PUJ");
        rotation185.setOn("3");
        rotation185.setAnumvol("968");
        rotation185.setAtpsvol("09H20");
        rotation185.setNbrp("2");
        rotation185.setIrmenu("0,00");
        rotation185.setTotalir("0,00");
        rotation185.setTep("26H20");
        rotation185.setRnumvol("969");
        rotation185.setRtpsvol("08H20");
        rotation185.setRpc("3 RNN");
        rotation185.setPdj("non");
        rotation185.setChcdg("Si retard > 2h40mn");
        list.add((rotation185));

        Rotation rotation186 = new Rotation();
        rotation186.setCont("acoi");
        rotation186.setId("186");
        rotation186.setEsc("PUJ");
        rotation186.setOn("3");
        rotation186.setAnumvol("741");
        rotation186.setAtpsvol("09H15");
        rotation186.setNbrp("2");
        rotation186.setIrmenu("0,00");
        rotation186.setTotalir("0,00");
        rotation186.setTep("27H55");
        rotation186.setRnumvol("969");
        rotation186.setRtpsvol("08H20");
        rotation186.setRpc("3 RNN");
        rotation186.setPdj("non");
        rotation186.setChcdg("Si retard > 2h40mn");
        list.add((rotation186));

        Rotation rotation187 = new Rotation();
        rotation187.setCont("acoi");
        rotation187.setId("187");
        rotation187.setEsc("PUJ(SDQ)");
        rotation187.setOn("3");
        rotation187.setAnumvol("741");
        rotation187.setAtpsvol("09H15");
        rotation187.setNbrp("2");
        rotation187.setIrmenu("0,00");
        rotation187.setTotalir("0,00");
        rotation187.setTep("25H45");
        rotation187.setRnumvol("741");
        rotation187.setRtpsvol("09H20");
        rotation187.setRpc("3 RNN");
        rotation187.setPdj("non");
        rotation187.setChcdg("Si retard > 1h40mn");
        list.add((rotation187));

        Rotation rotation188 = new Rotation();
        rotation188.setCont("acoi");
        rotation188.setId("188");
        rotation188.setEsc("PUJ(SDQ)");
        rotation188.setOn("3");
        rotation188.setAnumvol("968");
        rotation188.setAtpsvol("09H20");
        rotation188.setNbrp("2");
        rotation188.setIrmenu("0,00");
        rotation188.setTotalir("0,00");
        rotation188.setTep("24H10");
        rotation188.setRnumvol("741");
        rotation188.setRtpsvol("09H20");
        rotation188.setRpc("3 RNN");
        rotation188.setPdj("non");
        rotation188.setChcdg("Si retard > 1h40mn");
        list.add((rotation188));

        Rotation rotation189 = new Rotation();
        rotation189.setCont("acoi");
        rotation189.setId("189");
        rotation189.setEsc("PUJ(SDQ)");
        rotation189.setOn("4");
        rotation189.setAnumvol("741");
        rotation189.setAtpsvol("09H15");
        rotation189.setNbrp("4");
        rotation189.setIrmenu("0,00");
        rotation189.setTotalir("0,00");
        rotation189.setTep("49H45");
        rotation189.setRnumvol("741");
        rotation189.setRtpsvol("09H2O");
        rotation189.setRpc("3 RNN");
        rotation189.setPdj("non");
        rotation189.setChcdg("Si retard > 1h40mn");
        list.add((rotation189));

        Rotation rotation190 = new Rotation();
        rotation190.setCont("acoi");
        rotation190.setId("190");
        rotation190.setEsc("RUN");
        rotation190.setOn("4");
        rotation190.setAnumvol("644");
        rotation190.setAtpsvol("10H55");
        rotation190.setNbrp("4");
        rotation190.setIrmenu("49,80");
        rotation190.setTotalir("199,20");
        rotation190.setTep("50H35");
        rotation190.setRnumvol("645");
        rotation190.setRtpsvol("11H35");
        rotation190.setRpc("3 RNN");
        rotation190.setPdj("oui");
        rotation190.setChcdg("oui");
        list.add((rotation190));

        Rotation rotation191 = new Rotation();
        rotation191.setCont("acoi");
        rotation191.setId("191");
        rotation191.setEsc("RUN");
        rotation191.setOn("4");
        rotation191.setAnumvol("644");
        rotation191.setAtpsvol("10H55");
        rotation191.setNbrp("3");
        rotation191.setIrmenu("49,80");
        rotation191.setTotalir("149,40");
        rotation191.setTep("38H20");
        rotation191.setRnumvol("671");
        rotation191.setRtpsvol("11H40");
        rotation191.setRpc("3 RNN");
        rotation191.setPdj("oui");
        rotation191.setChcdg("oui");
        list.add((rotation191));

        Rotation rotation192 = new Rotation();
        rotation192.setCont("acoi");
        rotation192.setId("192");
        rotation192.setEsc("RUN");
        rotation192.setOn("4");
        rotation192.setAnumvol("642");
        rotation192.setAtpsvol("10H50");
        rotation192.setNbrp("4");
        rotation192.setIrmenu("49,80");
        rotation192.setTotalir("199,20");
        rotation192.setTep("47H15");
        rotation192.setRnumvol("645");
        rotation192.setRtpsvol("11H35");
        rotation192.setRpc("3 RNN");
        rotation192.setPdj("oui");
        rotation192.setChcdg("oui");
        list.add((rotation192));

        Rotation rotation193 = new Rotation();
        rotation193.setCont("acoi");
        rotation193.setId("193");
        rotation193.setEsc("RUN");
        rotation193.setOn("4");
        rotation193.setAnumvol("642");
        rotation193.setAtpsvol("10H50");
        rotation193.setNbrp("3");
        rotation193.setIrmenu("49,80");
        rotation193.setTotalir("149,40");
        rotation193.setTep("35H00");
        rotation193.setRnumvol("671");
        rotation193.setRtpsvol("11H40");
        rotation193.setRpc("3 RNN");
        rotation193.setPdj("oui");
        rotation193.setChcdg("oui");
        list.add((rotation193));

        Rotation rotation194 = new Rotation();
        rotation194.setCont("acoi");
        rotation194.setId("194");
        rotation194.setEsc("RUN");
        rotation194.setOn("5");
        rotation194.setAnumvol("642");
        rotation194.setAtpsvol("10H50");
        rotation194.setNbrp("5");
        rotation194.setIrmenu("49,80");
        rotation194.setTotalir("249,00");
        rotation194.setTep("59H00");
        rotation194.setRnumvol("671");
        rotation194.setRtpsvol("11H40");
        rotation194.setRpc("3 RNN");
        rotation194.setPdj("oui");
        rotation194.setChcdg("oui");
        list.add((rotation194));

        Rotation rotation195 = new Rotation();
        rotation195.setCont("acoi");
        rotation195.setId("195");
        rotation195.setEsc("RUN");
        rotation195.setOn("5");
        rotation195.setAnumvol("642");
        rotation195.setAtpsvol("10H50");
        rotation195.setNbrp("6");
        rotation195.setIrmenu("49,80");
        rotation195.setTotalir("298,80");
        rotation195.setTep("71H15");
        rotation195.setRnumvol("645");
        rotation195.setRtpsvol("11H35");
        rotation195.setRpc("3 RNN");
        rotation195.setPdj("oui");
        rotation195.setChcdg("oui");
        list.add((rotation195));

        Rotation rotation196 = new Rotation();
        rotation196.setCont("acoi");
        rotation196.setId("196");
        rotation196.setEsc("RUN");
        rotation196.setOn("5");
        rotation196.setAnumvol("644");
        rotation196.setAtpsvol("10H55");
        rotation196.setNbrp("5");
        rotation196.setIrmenu("49,80");
        rotation196.setTotalir("249,00");
        rotation196.setTep("62H00");
        rotation196.setRnumvol("671");
        rotation196.setRtpsvol("11H40");
        rotation196.setRpc("3 RNN");
        rotation196.setPdj("oui");
        rotation196.setChcdg("oui");
        list.add((rotation196));

        Rotation rotation197 = new Rotation();
        rotation197.setCont("acoi");
        rotation197.setId("197");
        rotation197.setEsc("SDQ");
        rotation197.setOn("3");
        rotation197.setAnumvol("734");
        rotation197.setAtpsvol("09H20");
        rotation197.setNbrp("2");
        rotation197.setIrmenu("36,09");
        rotation197.setTotalir("72,18");
        rotation197.setTep("26H10");
        rotation197.setRnumvol("733");
        rotation197.setRtpsvol("08H45");
        rotation197.setRpc("3 RNN");
        rotation197.setPdj("non");
        rotation197.setChcdg("Si retard > 2h45mn");
        list.add((rotation197));

        Rotation rotation198 = new Rotation();
        rotation198.setCont("acoi");
        rotation198.setId("198");
        rotation198.setEsc("SDQ");
        rotation198.setOn("5");
        rotation198.setAnumvol("734");
        rotation198.setAtpsvol("09H20");
        rotation198.setNbrp("6");
        rotation198.setIrmenu("36,09");
        rotation198.setTotalir("216,54");
        rotation198.setTep("74H10");
        rotation198.setRnumvol("733");
        rotation198.setRtpsvol("08H45");
        rotation198.setRpc("3 RNN");
        rotation198.setPdj("non");
        rotation198.setChcdg("Si retard > 2h45mn");
        list.add((rotation198));

        Rotation rotation199 = new Rotation();
        rotation199.setCont("acoi");
        rotation199.setId("199");
        rotation199.setEsc("SXM");
        rotation199.setOn("3");
        rotation199.setAnumvol("498");
        rotation199.setAtpsvol("09H20");
        rotation199.setNbrp("2");
        rotation199.setIrmenu("45,60");
        rotation199.setTotalir("91,20");
        rotation199.setTep("26H00");
        rotation199.setRnumvol("499");
        rotation199.setRtpsvol("08H20");
        rotation199.setRpc("3 RNN");
        rotation199.setPdj("non");
        rotation199.setChcdg("Si retard > 2h40mn");
        list.add((rotation199));

        Rotation rotation200 = new Rotation();
        rotation200.setCont("acoi");
        rotation200.setId("200");
        rotation200.setEsc("SXM");
        rotation200.setOn("4");
        rotation200.setAnumvol("498");
        rotation200.setAtpsvol("09H20");
        rotation200.setNbrp("4");
        rotation200.setIrmenu("45,60");
        rotation200.setTotalir("182,40");
        rotation200.setTep("50H00");
        rotation200.setRnumvol("499");
        rotation200.setRtpsvol("08H20");
        rotation200.setRpc("3 RNN");
        rotation200.setPdj("non");
        rotation200.setChcdg("Si retard > 2h40mn");
        list.add((rotation200));

        Rotation rotation201 = new Rotation();
        rotation201.setCont("acoi");
        rotation201.setId("201");
        rotation201.setEsc("TNR");
        rotation201.setOn("3");
        rotation201.setAnumvol("934");
        rotation201.setAtpsvol("11H00");
        rotation201.setNbrp("3");
        rotation201.setIrmenu("16,33");
        rotation201.setTotalir("48,99");
        rotation201.setTep("26H10");
        rotation201.setRnumvol("935");
        rotation201.setRtpsvol("11H25");
        rotation201.setRpc("4 RNN");
        rotation201.setPdj("non");
        rotation201.setChcdg("oui");
        list.add((rotation201));

        Rotation rotation202 = new Rotation();
        rotation202.setCont("acoi");
        rotation202.setId("202");
        rotation202.setEsc("TNR");
        rotation202.setOn("4");
        rotation202.setAnumvol("934");
        rotation202.setAtpsvol("11H00");
        rotation202.setNbrp("5");
        rotation202.setIrmenu("16,33");
        rotation202.setTotalir("81,65");
        rotation202.setTep("50H10");
        rotation202.setRnumvol("935");
        rotation202.setRtpsvol("11H25");
        rotation202.setRpc("3 RNN");
        rotation202.setPdj("non");
        rotation202.setChcdg("oui");
        list.add((rotation202));


    }

    public void initVariables() {
        String ame = getResources().getString(R.string.ame);
        String amo = getResources().getString(R.string.amo);
        String asi = getResources().getString(R.string.asi);
        String acoi = getResources().getString(R.string.acoi);
        String amsu = getResources().getString(R.string.amsu);
        name_regions = new String[5];
        name_regions_short = new String[5];
        name_regions[0] = ame;
        name_regions[1] = amo;
        name_regions[2] = asi;
        name_regions[3] = acoi;
        name_regions[4] = amsu;

        name_regions_short[0] = "ame";
        name_regions_short[1] = "amo";
        name_regions_short[2] = "asi";
        name_regions_short[3] = "acoi";
        name_regions_short[4] = "amsu";
    }
}
