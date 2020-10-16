package bi.konstrictor.aacbflights.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import bi.konstrictor.aacbflights.R;

public class FragmentReservation extends Fragment {

    RecyclerView recycler_reservation;
    SwipeRefreshLayout swipe_reservation_refresh;

    public FragmentReservation() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reservation, container, false);
        recycler_reservation = view.findViewById(R.id.recycler_reservation);
        swipe_reservation_refresh = view.findViewById(R.id.swipe_reservation_refresh);

        recycler_reservation.setLayoutManager(new GridLayoutManager(getContext(), 1));
        recycler_reservation.addItemDecoration(new DividerItemDecoration(recycler_reservation.getContext(), DividerItemDecoration.VERTICAL));
//        recycler_reservation.setLayoutManager(new FlexboxLayoutManager(this, FlexDirection.ROW, FlexWrap.WRAP));

//        reservations = new ArrayList<>();
//        adaptateur = new Adapt(MainActivity.this, actions);
//        recycler_reservation.setAdapter(adaptateur);
//
//        if(!Host.isLogedIn(this)){
//            Host.logOut(this);
//        }
//        getAccountInfo(false);
//        getActions(false);
//        swipe_reservation_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                getActions(false);
//            }
//        });
        return view;
    }
}
