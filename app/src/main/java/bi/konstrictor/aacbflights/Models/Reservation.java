package bi.konstrictor.aacbflights.Models;

import java.util.Date;

import bi.konstrictor.aacbflights.Host;

public class Reservation {
    public String id, nom, prenom, user, vol;
    public Date  depart, arrivee;

    public Reservation(String id, String nom, String prenom, String depart, String arrivee, String user, String vol) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.depart = Host.getDate(depart);
        this.arrivee = Host.getDate(arrivee);
        this.user = user;
        this.vol = vol;
    }
    public String getFullname(){
        return this.nom+" "+this.prenom;
    }

    @Override
    public String toString() {
        return getFullname();
    }
}
