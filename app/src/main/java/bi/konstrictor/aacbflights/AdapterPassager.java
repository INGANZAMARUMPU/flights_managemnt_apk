package bi.konstrictor.aacbflights;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterPassager extends RecyclerView.Adapter<AdapterPassager.ViewHolder> {
    ArrayList<Passager> passagers;
    MainActivity context;

    public AdapterPassager(ArrayList<Passager> passagers, MainActivity context) {
        this.passagers = passagers;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_passager, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Passager passager = passagers.get(position);
        holder.lbl_card_pass_fullname.setText(passager.getFullname());
        holder.lbl_card_pass_depart.setText(passager.depart);
        holder.lbl_card_pass_arrivee.setText(passager.arrivee);
        holder.lbl_card_pass_vol.setText(passager.vol);
    }
    @Override
    public int getItemCount() {
        return passagers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView lbl_card_pass_fullname, lbl_card_pass_depart, lbl_card_pass_arrivee, lbl_card_pass_vol;
        View view;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            lbl_card_pass_fullname = itemView.findViewById(R.id.lbl_card_pass_fullname);
            lbl_card_pass_depart = itemView.findViewById(R.id.lbl_card_pass_depart);
            lbl_card_pass_arrivee = itemView.findViewById(R.id.lbl_card_pass_arrivee);
            lbl_card_pass_vol = itemView.findViewById(R.id.lbl_card_pass_vol);
        }
    }
}
