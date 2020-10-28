package bi.konstrictor.aacbflights.Adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import bi.konstrictor.aacbflights.Dialogs.FormVol;
import bi.konstrictor.aacbflights.Fragments.FragmentVol;
import bi.konstrictor.aacbflights.Host;
import bi.konstrictor.aacbflights.MainActivity;
import bi.konstrictor.aacbflights.R;
import bi.konstrictor.aacbflights.Models.Vol;

public class AdapterVol extends RecyclerView.Adapter<AdapterVol.ViewHolder> {
    ArrayList<Vol> vols;
    private FragmentVol parent;

    public AdapterVol(ArrayList<Vol> vols, FragmentVol parent) {
        this.vols = vols;
        this.parent = parent;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_vol, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Vol vol = vols.get(position);
        holder.lbl_card_vol_source.setText(vol.source);
        holder.lbl_card_vol_destination.setText(vol.destination);
        holder.lbl_card_vol_depart.setText(Host.getStrDate(vol.depart));
        holder.lbl_card_vol_arrivee.setText(Host.getStrDate(vol.arrivee));
        holder.lbl_card_vol_compagnie.setText(vol.compagnie);
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FormVol form_vol = new FormVol(parent);
                form_vol.setEdition(vol);
                form_vol.show();
            }
        });
    }
    @Override
    public int getItemCount() {
        return vols.size();
    }

    public void setVols(ArrayList<Vol> vols) {
        this.vols = vols;
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
