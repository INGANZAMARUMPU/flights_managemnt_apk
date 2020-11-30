package bi.konstrictor.aacbflights.Models;

public class Compagnie {
    public String id, nom;

    public Compagnie(String id, String nom) {
        this.id = id;
        this.nom = nom;
    }

    @Override
    public String toString() {
        return nom;
    }
}
