package bi.konstrictor.aacbflights.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import bi.konstrictor.aacbflights.Host;
import bi.konstrictor.aacbflights.MainActivity;
import bi.konstrictor.aacbflights.Models.Reservation;
import bi.konstrictor.aacbflights.R;

public class AdapterReservation extends RecyclerView.Adapter<AdapterReservation.ViewHolder> {
    ArrayList<Reservation> reservations;

    public AdapterReservation(ArrayList<Reservation> reservations) {
        this.reservations = reservations;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_reservation, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Reservation reservation = reservations.get(position);
        holder.lbl_card_res_fullname.setText(reservation.getFullname());
        holder.lbl_card_res_depart.setText(Host.getStrDate(reservation.depart));
        holder.lbl_card_res_arrivee.setText(Host.getStrDate(reservation.arrivee));
        holder.lbl_card_res_vol.setText(reservation.vol);
    }
    @Override
    public int getItemCount() {
        return reservations.size();
    }

    public void setReservations(ArrayList<Reservation> reservations) {
        this.reservations = reservations;
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
