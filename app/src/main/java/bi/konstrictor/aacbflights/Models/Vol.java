package bi.konstrictor.aacbflights.Models;

import java.security.Key;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import bi.konstrictor.aacbflights.Host;

public class Vol {
    public String id, source, destination, avion, compagnie, id_source, id_destination, id_avion;
    public Date depart, arrivee;

    public Vol(String id, String source, String destination, String avion, String compagnie,
               String depart, String arrivee, String id_avion, String id_source, String id_destination) {
        this.id = id;
        this.source = source;
        this.destination = destination;
        this.avion = avion;
        this.compagnie = compagnie;
        this.depart = Host.getDate(depart);
        this.arrivee = Host.getDate(arrivee);
        this.id_avion = id_avion;
        this.id_source = id_source;
        this.id_destination = id_destination;
    }

    @Override
    public String toString() {
        return source + " -> " + destination +" "+ Host.getStrDate(depart);
    }
}
