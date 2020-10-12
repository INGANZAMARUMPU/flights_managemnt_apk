package bi.konstrictor.aacbflights;

public class Passager {
    String id, nom, prenom, vol, depart, arrivee, user;

    public Passager(String id, String nom, String prenom, String vol, String depart, String arrivee, String user) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.vol = vol;
        this.depart = depart;
        this.arrivee = arrivee;
        this.user = user;
    }
    public String getFullname(){
        return this.nom+" "+this.prenom;
    }
}
