package bi.konstrictor.aacbflights.Models;

public class Reservation {
    public String id, nom, prenom, depart, arrivee, user, vol;

    public Reservation(String id, String nom, String prenom, String depart, String arrivee, String user, String vol) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.depart = depart;
        this.arrivee = arrivee;
        this.user = user;
        this.vol = vol;
    }
    public String getFullname(){
        return this.nom+" "+this.prenom;
    }
}
