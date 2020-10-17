package bi.konstrictor.aacbflights.Models;

public class Passager {
    public String id, nom, prenom, code;

    public Passager(String id, String nom, String prenom, String code) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.code = code;
    }
    public String getFullname(){
        return this.nom+" "+this.prenom;
    }
}
