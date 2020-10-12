package bi.konstrictor.aacbflights;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterVol extends RecyclerView.Adapter<AdapterVol.ViewHolder> {
    ArrayList<Vol> vols;
    MainActivity context;

    public AdapterVol(ArrayList<Vol> vols, MainActivity context) {
        this.vols = vols;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_vol, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Vol vol = vols.get(position);
        holder.lbl_card_vol_source.setText(vol.source);
        holder.lbl_card_vol_destination.setText(vol.destination);
        holder.lbl_card_vol_depart.setText(vol.depart);
        holder.lbl_card_vol_arrivee.setText(vol.arrivee);
        holder.lbl_card_vol_compagnie.setText(vol.compagnie);
    }
    @Override
    public int getItemCount() {
        return vols.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView lbl_card_vol_source, lbl_card_vol_destination, lbl_card_vol_depart,
                lbl_card_vol_arrivee, lbl_card_vol_compagnie;
        View view;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            lbl_card_vol_source = itemView.findViewById(R.id.lbl_card_vol_source);
            lbl_card_vol_destination = itemView.findViewById(R.id.lbl_card_vol_destination);
            lbl_card_vol_depart = itemView.findViewById(R.id.lbl_card_vol_depart);
            lbl_card_vol_arrivee = itemView.findViewById(R.id.lbl_card_vol_arrivee);
            lbl_card_vol_compagnie = itemView.findViewById(R.id.lbl_card_vol_compagnie);
        }
    }
}
