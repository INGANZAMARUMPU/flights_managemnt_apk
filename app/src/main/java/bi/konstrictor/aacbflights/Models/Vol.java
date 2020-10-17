package bi.konstrictor.aacbflights.Models;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import bi.konstrictor.aacbflights.Host;

public class Vol {
    public String id, source, destination, avion, compagnie;
    public Date depart, arrivee;

    public Vol(String id, String source, String destination, String avion, String compagnie, String depart, String arrivee) {
        this.id = id;
        this.source = source;
        this.destination = destination;
        this.avion = avion;
        this.compagnie = compagnie;
        this.depart = Host.getDate(depart);
        this.arrivee = Host.getDate(arrivee);
    }

    @Override
    public String toString() {
        return source + " -> " + destination +" "+ Host.getStrDate(depart);
    }
}
