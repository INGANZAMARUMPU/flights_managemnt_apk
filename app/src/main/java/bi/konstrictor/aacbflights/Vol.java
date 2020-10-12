package bi.konstrictor.aacbflights;

public class Vol {
    String id, source, destination, avion, compagnie, depart, arrivee;

    public Vol(String id, String source, String destination, String avion, String compagnie, String depart, String arrivee) {
        this.id = id;
        this.source = source;
        this.destination = destination;
        this.avion = avion;
        this.compagnie = compagnie;
        this.depart = depart;
        this.arrivee = arrivee;
    }
}
