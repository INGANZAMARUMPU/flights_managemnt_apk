package bi.konstrictor.aacbflights.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import bi.konstrictor.aacbflights.Dialogs.FormPassager;
import bi.konstrictor.aacbflights.Fragments.FragmentPassager;
import bi.konstrictor.aacbflights.Models.Passager;
import bi.konstrictor.aacbflights.R;

public class AdapterPassager extends RecyclerView.Adapter<AdapterPassager.ViewHolder> {
    ArrayList<Passager> passagers;
    private FragmentPassager parent;

    public AdapterPassager(ArrayList<Passager> passagers, FragmentPassager parent) {
        this.passagers = passagers;
        this.parent = parent;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_passager, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Passager passager = passagers.get(position);
        holder.lbl_card_pass_fullname.setText(passager.getFullname());
        holder.lbl_card_pass_serial.setText(passager.code);
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FormPassager f_passager = new FormPassager(parent);
                f_passager.setEdition(passager);
                f_passager.show();
            }
        });
    }
    @Override
    public int getItemCount() {
        return passagers.size();
    }

    public void setPassagers(ArrayList<Passager> passagers) {
        this.passagers = passagers;
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
