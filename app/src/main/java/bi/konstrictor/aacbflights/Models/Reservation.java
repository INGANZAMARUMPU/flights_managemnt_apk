package bi.konstrictor.aacbflights.Models;

import java.util.Date;

import bi.konstrictor.aacbflights.Host;

public class Reservation {
    public String id, nom, prenom, user, vol, id_passager, id_vol;
    public Date  depart, arrivee;

    public Reservation(String id, String nom, String prenom, String depart, String arrivee,
                       String user, String vol, String id_passager, String id_vol) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.depart = Host.getDate(depart);
        this.arrivee = Host.getDate(arrivee);
        this.user = user;
        this.vol = vol;
        this.id_passager = id_passager;
        this.id_vol = id_vol;
    }
    public String getFullname(){
        return this.nom+" "+this.prenom;
    }

    @Override
    public String toString() {
        return getFullname();
    }
}
