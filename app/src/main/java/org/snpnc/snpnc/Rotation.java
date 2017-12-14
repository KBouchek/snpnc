package org.snpnc.snpnc;

import android.util.Log;

/**
 * Created by K on 12/12/2017.
 */

public class Rotation {

    public String  id;
    public String cont;
    public String esc;
    public String on;
    public String anumvol;
    public String atpsvol;
    public String nbrp;
    public String irmenu;
    public String totalir;
    public String tep;
    public String rnumvol;
    public String rtpsvol;
    public String rpc;
    public String pdj;
    public String chcdg;
    public String tps_vol;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCont() {
        return cont;
    }

    public void setCont(String cont) {
        this.cont = cont;
    }

    public String getEsc() {
        return esc;
    }

    public void setEsc(String esc) {
        this.esc = esc;
    }

    public String getOn() {
        return on;
    }

    public void setOn(String on) {
        this.on = on;
    }

    public String getAnumvol() {
        return anumvol;
    }

    public void setAnumvol(String anumvol) {
        this.anumvol = anumvol;
    }

    public String getAtpsvol() {
        return atpsvol;
    }

    public void setAtpsvol(String atpsvol) {
        this.atpsvol = atpsvol;
        setTpsvol();
    }

    public String getNbrp() {
        return nbrp;
    }

    public void setNbrp(String nbrp) {
        this.nbrp = nbrp;
    }

    public String getIrmenu() {
        return irmenu;
    }

    public void setIrmenu(String irmenu) {
        this.irmenu = irmenu;
    }

    public String getTotalir() {
        return totalir;
    }

    public void setTotalir(String totalir) {
        this.totalir = totalir;
    }

    public String getTep() {
        return tep;
    }

    public void setTep(String tep) {
        this.tep = tep;
    }

    public String getRnumvol() {
        return rnumvol;
    }

    public void setRnumvol(String rnumvol) {
        this.rnumvol = rnumvol;
    }

    public String getRtpsvol() {
        return rtpsvol;
    }

    public void setRtpsvol(String rtpsvol) {
        this.rtpsvol = rtpsvol;
        setTpsvol();
    }

    public String getRpc() {
        return rpc;
    }

    public void setRpc(String rpc) {
        this.rpc = rpc;
    }

    public String getPdj() {
        return pdj;
    }

    public void setPdj(String pdj) {
        this.pdj = pdj;
    }

    public String getChcdg() {
        return chcdg;
    }

    public void setChcdg(String chcdg) {
        this.chcdg = chcdg;
    }

    public String getTpsvol() {
        return tps_vol;
    }

    public void setTpsvol() {
        this.tps_vol = "";
        if(this.getAtpsvol() != null && this.getRtpsvol() != null) {
            String[] separated_atps = this.getAtpsvol().split("H");
            String[] separated_rtps = this.getRtpsvol().split("H");

            try {
                int a_h = Integer.parseInt(separated_atps[0]);
                int a_m = Integer.parseInt(separated_atps[1]);
                int r_h = Integer.parseInt(separated_rtps[0]);
                int r_m = Integer.parseInt(separated_rtps[1]);

                int mm =  (a_m+r_m)%60;
                int hh =  (a_h+r_h)+ ((a_m+r_m)/60);

                this.tps_vol = String.valueOf(hh)+"h"+String.valueOf(mm);
            }catch (NumberFormatException e){
                System.out.println("not a number");
            }

            //Log.i("KK", ""+ a_h +" "+a_m+" "+r_h+" "+r_m);


            //this.tps_vol = String.valueOf(hh)+"h"+String.valueOf(mm);

        }


    }


}
