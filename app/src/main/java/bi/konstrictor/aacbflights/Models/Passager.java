package bi.konstrictor.aacbflights.Models;

public class Passager {
    public String id, nom, prenom, code;

    public Passager(String id, String nom, String prenom) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.code = "#"+((int)(Math.random()*1000000));
    }
    public String getFullname(){
        return this.nom+" "+this.prenom;
    }

    @Override
    public String toString() {
        return getFullname();
    }
}
