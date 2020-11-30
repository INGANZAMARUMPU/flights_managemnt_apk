package bi.konstrictor.aacbflights;

import java.util.Date;

import bi.konstrictor.aacbflights.Models.Compagnie;

public interface Filterable {
    public void performFiltering(Date debut, Date fin, Compagnie compagnie);
    public void cancelFiltering();
}
