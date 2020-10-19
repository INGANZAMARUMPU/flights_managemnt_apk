package bi.konstrictor.aacbflights.Models;

public class Avion {
    public String id, model, compagnie;

    public Avion(String id, String model, String compagnie) {
        this.id = id;
        this.model = model;
        this.compagnie = compagnie;
    }

    @Override
    public String toString() {
        return model + '(' + compagnie + ')';
    }
}
