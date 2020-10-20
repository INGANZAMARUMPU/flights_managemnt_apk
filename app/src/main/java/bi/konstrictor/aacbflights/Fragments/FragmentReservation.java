package bi.konstrictor.aacbflights.Fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import bi.konstrictor.aacbflights.Adapters.AdapterReservation;
import bi.konstrictor.aacbflights.Dialogs.FormPassager;
import bi.konstrictor.aacbflights.Dialogs.FormReservation;
import bi.konstrictor.aacbflights.Dialogs.FormVol;
import bi.konstrictor.aacbflights.Host;
import bi.konstrictor.aacbflights.LoginActivity;
import bi.konstrictor.aacbflights.MainActivity;
import bi.konstrictor.aacbflights.Models.Reservation;
import bi.konstrictor.aacbflights.R;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class FragmentReservation extends Fragment{

    public MainActivity context;
    RecyclerView recycler_reservation;
    SwipeRefreshLayout swipe_reservation_refresh;
    private ArrayList<Reservation> reservations;
    private AdapterReservation adaptateur;

    public FragmentReservation(MainActivity context) {
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reservation, container, false);
        recycler_reservation = view.findViewById(R.id.recycler_reservation);
        swipe_reservation_refresh = view.findViewById(R.id.swipe_reservation_refresh);
        setHasOptionsMenu(true);

        recycler_reservation.setLayoutManager(new GridLayoutManager(getContext(), 1));
        recycler_reservation.addItemDecoration(new DividerItemDecoration(recycler_reservation.getContext(), DividerItemDecoration.VERTICAL));

        reservations = new ArrayList<>();
        adaptateur = new AdapterReservation(reservations, this);
        recycler_reservation.setAdapter(adaptateur);

        swipe_reservation_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getResevations();
                swipe_reservation_refresh.setRefreshing(false);
            }
        });
        getResevations();
        return view;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_search) {
            Toast.makeText(context, "RECHERCHE EN COURS...", Toast.LENGTH_LONG).show();
        } else if (id == R.id.menu_add) {
//            if (tablayout.getTabAt(0).isSelected()) {
                new FormReservation(this).show();
//            } else if (tablayout.getTabAt(1).isSelected()) {
//                new FormVol(this).show();
//            } else if (tablayout.getTabAt(2).isSelected()) {
//                new FormPassager(this).show();
//            }
        } else if (id == R.id.menu_filter) {
            Toast.makeText(context, "FILTRAGE EN COURS...", Toast.LENGTH_LONG).show();
        }
        return super.onOptionsItemSelected(item);
    }
    private void getResevations() {

        OkHttpClient client = new OkHttpClient();
        HttpUrl.Builder urlBuilder = HttpUrl.parse(Host.URL + "/reservation/").newBuilder();

        String url = urlBuilder.build().toString();
        Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string();
                try {
                    JSONArray json_array = new JSONArray(json);
                    reservations.clear();
                    for (int i=0; i<json_array.length(); i++){
                        JSONObject json_obj = json_array.getJSONObject(i);
                        Reservation res = new Reservation(
                                json_obj.getString("id"),
                                json_obj.getString("nom"),
                                json_obj.getString("prenom"),
                                json_obj.getString("depart"),
                                json_obj.getString("arrivee"),
                                json_obj.getString("user"),
                                json_obj.getString("vol"),
                                json_obj.getString("id_passager"),
                                json_obj.getString("id_vol")
                        );
                        reservations.add(res);
                    }
                    adaptateur.setReservations(reservations);
                    adaptateur.notifyDataSetChanged();

                } catch (Exception e) {
                    Log.i("==== HOST ====", e.getMessage());
                }
            }
        });
    }

    public void pushReservation(Reservation res) {
        reservations.add(res);
        adaptateur.setReservations(reservations);
        adaptateur.notifyDataSetChanged();
    }

    public void editReservation(Reservation res) {

    }

    public void removeReservation(Reservation reservation) {
        reservations.remove(reservation);
        adaptateur.setReservations(reservations);
        adaptateur.notifyDataSetChanged();
    }
}
