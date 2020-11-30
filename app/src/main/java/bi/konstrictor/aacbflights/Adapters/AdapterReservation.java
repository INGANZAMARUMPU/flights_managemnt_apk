package bi.konstrictor.aacbflights.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import bi.konstrictor.aacbflights.Dialogs.FormReservation;
import bi.konstrictor.aacbflights.Host;
import bi.konstrictor.aacbflights.Fragments.FragmentReservation;
import bi.konstrictor.aacbflights.Models.Reservation;
import bi.konstrictor.aacbflights.R;

public class AdapterReservation extends RecyclerView.Adapter<AdapterReservation.ViewHolder> {
    private final FragmentReservation parent;
    ArrayList<Reservation> reservations;

    public AdapterReservation(ArrayList<Reservation> reservations, FragmentReservation parent) {
        this.reservations = reservations;
        this.parent = parent;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_reservation, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Reservation reservation = reservations.get(position);
        holder.lbl_card_res_fullname.setText(reservation.getFullname());
        holder.lbl_card_res_depart.setText(Host.getStrDate(reservation.depart));
        holder.lbl_card_res_arrivee.setText(Host.getStrDate(reservation.arrivee));
        holder.lbl_card_res_vol.setText(reservation.vol);
        if(parent.context.group.equalsIgnoreCase("admin")) {
            holder.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FormReservation f_reservation = new FormReservation(parent);
                    f_reservation.setEdition(reservation);
                    f_reservation.show();
                }
            });
        }
    }
    @Override
    public int getItemCount() {
        return reservations.size();
    }

    public void setReservations(ArrayList<Reservation> reservations) {
        this.reservations = reservations;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView lbl_card_res_fullname, lbl_card_res_depart, lbl_card_res_arrivee, lbl_card_res_vol;
        View view;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            lbl_card_res_fullname = itemView.findViewById(R.id.lbl_card_res_fullname);
            lbl_card_res_depart = itemView.findViewById(R.id.lbl_card_res_depart);
            lbl_card_res_arrivee = itemView.findViewById(R.id.lbl_card_res_arrivee);
            lbl_card_res_vol = itemView.findViewById(R.id.lbl_card_res_vol);
        }
    }
}
