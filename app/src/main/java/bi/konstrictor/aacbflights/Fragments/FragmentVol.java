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

import bi.konstrictor.aacbflights.Adapters.AdapterVol;
import bi.konstrictor.aacbflights.Dialogs.FormVol;
import bi.konstrictor.aacbflights.Dialogs.FormVol;
import bi.konstrictor.aacbflights.Host;
import bi.konstrictor.aacbflights.MainActivity;
import bi.konstrictor.aacbflights.Models.Vol;
import bi.konstrictor.aacbflights.Models.Vol;
import bi.konstrictor.aacbflights.R;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentVol extends Fragment {

    public final MainActivity context;
    private SwipeRefreshLayout swipe_vol_refresh;
    private RecyclerView recycler_vol;
    private ArrayList<Vol> vols;
    private AdapterVol adaptateur;

    public FragmentVol(MainActivity context) {
        this.context = context;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_vol, container, false);;
        swipe_vol_refresh = view.findViewById(R.id.swipe_vol_refresh);
        recycler_vol = view.findViewById(R.id.recycler_vol);
        setHasOptionsMenu(true);

        recycler_vol.setLayoutManager(new GridLayoutManager(getContext(), 1));
        recycler_vol.addItemDecoration(new DividerItemDecoration(recycler_vol.getContext(), DividerItemDecoration.VERTICAL));

        vols = new ArrayList<>();
        adaptateur = new AdapterVol(vols, this);
        recycler_vol.setAdapter(adaptateur);

        swipe_vol_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getResevations();
                swipe_vol_refresh.setRefreshing(false);
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
            new FormVol(this).show();
        } else if (id == R.id.menu_filter) {
            Toast.makeText(context, "FILTRAGE EN COURS...", Toast.LENGTH_LONG).show();
        }
        return super.onOptionsItemSelected(item);
    }

    private void getResevations() {

        OkHttpClient client = new OkHttpClient();
        HttpUrl.Builder urlBuilder = HttpUrl.parse(Host.URL + "/vol/").newBuilder();

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
                    vols.clear();
                    for (int i=0; i<json_array.length(); i++){
                        JSONObject json_obj = json_array.getJSONObject(i);
                        Vol vol = new Vol(
                                json_obj.getString("id"),
                                json_obj.getString("source"),
                                json_obj.getString("destination"),
                                json_obj.getString("avion"),
                                json_obj.getString("compagnie"),
                                json_obj.getString("depart"),
                                json_obj.getString("arrivee"),
                                json_obj.getString("id_avion"),
                                json_obj.getString("id_source"),
                                json_obj.getString("id_destination")
                        );
                        vols.add(vol);
                    }
                    adaptateur.setVols(vols);
                    context.vols = vols;
                    adaptateur.notifyDataSetChanged();

                } catch (Exception e) {
                    Log.i("==== HOST ====", e.getMessage());
                }
            }
        });
    }
    public void pushVol(Vol res) {
        vols.add(res);
        adaptateur.setVols(vols);
        adaptateur.notifyDataSetChanged();
    }

    public void editVol(Vol res) {
        for (int i=0; i<vols.size(); i++){
            if(vols.get(i).id == res.id){
                vols.set(i, res);
                adaptateur.setVols(vols);
                adaptateur.notifyDataSetChanged();
                return;
            }
        }
    }

    public void removeVol(Vol vol) {
        vols.remove(vol);
        adaptateur.setVols(vols);
        adaptateur.notifyDataSetChanged();
    }
}
