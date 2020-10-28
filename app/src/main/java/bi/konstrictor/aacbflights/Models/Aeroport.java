package bi.konstrictor.aacbflights.Models;

public class Aeroport {
    public String id, nom, ville;

    public Aeroport(String id, String nom, String ville) {
        this.id = id;
        this.nom = nom;
        this.ville = ville;
    }

    @Override
    public String toString() {
        return nom + '(' + ville + ')';
    }
}
