package bi.konstrictor.aacbflights.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import bi.konstrictor.aacbflights.Adapters.AdapterPassager;
import bi.konstrictor.aacbflights.Dialogs.FormPassager;
import bi.konstrictor.aacbflights.Filterable;
import bi.konstrictor.aacbflights.Host;
import bi.konstrictor.aacbflights.MainActivity;
import bi.konstrictor.aacbflights.Models.Compagnie;
import bi.konstrictor.aacbflights.Models.Passager;
import bi.konstrictor.aacbflights.R;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class FragmentPassager extends Fragment{

    public MainActivity context;
    private SwipeRefreshLayout swipe_passager_refresh;
    private RecyclerView recycler_passager;
    private ArrayList<Passager> passagers;
    private AdapterPassager adaptateur;

    public FragmentPassager(MainActivity context) {
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_passager, container, false);;
        swipe_passager_refresh = view.findViewById(R.id.swipe_passager_refresh);
        recycler_passager = view.findViewById(R.id.recycler_passager);
        setHasOptionsMenu(true);

        recycler_passager.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recycler_passager.addItemDecoration(new DividerItemDecoration(recycler_passager.getContext(), DividerItemDecoration.VERTICAL));
        recycler_passager.addItemDecoration(new DividerItemDecoration(recycler_passager.getContext(), DividerItemDecoration.HORIZONTAL));

        passagers = new ArrayList<>();
        adaptateur = new AdapterPassager(passagers, this);
        recycler_passager.setAdapter(adaptateur);

        swipe_passager_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getResevations();
                swipe_passager_refresh.setRefreshing(false);
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
            new FormPassager(this).show();
        } else if (id == R.id.menu_filter) {
            Toast.makeText(context, "FILTRAGE EN COURS...", Toast.LENGTH_LONG).show();
        }
        return super.onOptionsItemSelected(item);
    }
    private void getResevations() {
        OkHttpClient client = new OkHttpClient();
        HttpUrl.Builder urlBuilder = HttpUrl.parse(Host.URL + "/user/").newBuilder();

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
                    passagers.clear();
                    for (int i=0; i<json_array.length(); i++){
                        JSONObject json_obj = json_array.getJSONObject(i);
                        Passager passager = new Passager(
                                json_obj.getString("id"),
                                json_obj.getString("first_name"),
                                json_obj.getString("last_name")
                        );
                        passagers.add(passager);
                    }
                    adaptateur.setPassagers(passagers);
                    context.passagers = passagers;
                    adaptateur.notifyDataSetChanged();

                } catch (Exception e) {
                    Log.i("==== HOST ====", e.getMessage());
                }
            }
        });
    }
    public void pushPassager(Passager pas) {
        passagers.add(pas);
        adaptateur.setPassager(passagers);
        adaptateur.notifyDataSetChanged();
    }

    public void editPassager(Passager pas) {
        for (int i=0; i<passagers.size(); i++){
            if(passagers.get(i).id == pas.id){
                passagers.set(i, pas);
                adaptateur.setPassager(passagers);
                adaptateur.notifyDataSetChanged();
                return;
            }
        }
    }

    public void removePassager(Passager pas) {
        passagers.remove(pas);
        adaptateur.setPassager(passagers);
        adaptateur.notifyDataSetChanged();
    }
}
