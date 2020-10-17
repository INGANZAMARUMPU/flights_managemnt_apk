package bi.konstrictor.aacbflights.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import bi.konstrictor.aacbflights.MainActivity;
import bi.konstrictor.aacbflights.Models.Passager;
import bi.konstrictor.aacbflights.R;

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
        holder.lbl_card_pass_serial.setText(passager.code);
    }
    @Override
    public int getItemCount() {
        return passagers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView lbl_card_pass_fullname, lbl_card_pass_serial;
        View view;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            lbl_card_pass_fullname = itemView.findViewById(R.id.lbl_card_pass_fullname);
            lbl_card_pass_serial = itemView.findViewById(R.id.lbl_card_pass_serial);
        }
    }
}
