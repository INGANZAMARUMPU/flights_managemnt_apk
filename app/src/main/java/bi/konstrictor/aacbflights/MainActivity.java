package bi.konstrictor.aacbflights;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import bi.konstrictor.aacbflights.Dialogs.FormPassager;
import bi.konstrictor.aacbflights.Dialogs.FormReservation;
import bi.konstrictor.aacbflights.Dialogs.FormVol;
import bi.konstrictor.aacbflights.Models.Aeroport;
import bi.konstrictor.aacbflights.Models.Avion;
import bi.konstrictor.aacbflights.Models.Passager;
import bi.konstrictor.aacbflights.Models.Vol;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    TabLayout tablayout;
    ViewPager viewpager;
    FragmentStatePA main_fspa;
    private Menu menu;
    private SharedPreferences sessionPreference;
    public String token, group;
    public ArrayList<Passager> passagers = new ArrayList<>();
    public ArrayList<Vol> vols = new ArrayList<>();
    public ArrayList<Avion> avions = new ArrayList<>();
    public ArrayList<Aeroport> aeroports = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tablayout = findViewById(R.id.tablayout);
        viewpager = findViewById(R.id.viewpager);
        main_fspa = new FragmentStatePA(getSupportFragmentManager(), 1, this);
        tablayout.setupWithViewPager(viewpager);
        viewpager.setAdapter(main_fspa);
        viewpager.setOffscreenPageLimit(3);
        tablayout.getTabAt(0).setIcon(R.drawable.ic_reservation);
        tablayout.getTabAt(1).setIcon(R.drawable.ic_vol);
        tablayout.getTabAt(2).setIcon(R.drawable.ic_passagers);

        sessionPreference = getSharedPreferences("user_session", Context.MODE_PRIVATE);
        token = sessionPreference.getString("token", "");
        group = sessionPreference.getString("type", "");
        if(token.trim().isEmpty()){
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        }
        chargerAvions();
        chargerAeroports();
    }

    private void chargerAeroports() {
        OkHttpClient client = new OkHttpClient();
        HttpUrl.Builder urlBuilder = HttpUrl.parse(Host.URL + "/aeroport/").newBuilder();

        String url = urlBuilder.build().toString();
        Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) { }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string();
                try {
                    JSONArray json_array = new JSONArray(json);
                    for (int i=0; i<json_array.length(); i++){
                        JSONObject json_obj = json_array.getJSONObject(i);
                        Aeroport aeroport = new Aeroport(
                                json_obj.getString("id"),
                                json_obj.getString("nom"),
                                json_obj.getString("ville")
                        );
                        aeroports.add(aeroport);
                    }
                } catch (Exception e) {
                    Log.i("==== MAIN ACTIVITY ====", e.getMessage());
                }
            }
        });
    }

    private void chargerAvions() {
        OkHttpClient client = new OkHttpClient();
        HttpUrl.Builder urlBuilder = HttpUrl.parse(Host.URL + "/avion/").newBuilder();

        String url = urlBuilder.build().toString();
        Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) { }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string();
                try {
                    JSONArray json_array = new JSONArray(json);
                    for (int i=0; i<json_array.length(); i++){
                        JSONObject json_obj = json_array.getJSONObject(i);
                        Avion avion = new Avion(
                                json_obj.getString("id"),
                                json_obj.getString("model"),
                                json_obj.getString("compagnie")
                        );
                        avions.add(avion);
                    }
                } catch (Exception e) {
                    Log.i("==== MAIN ACTIVITY ====", e.getMessage());
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu( Menu menu) {
        getMenuInflater().inflate( R.menu.menu_main, menu);
        this.menu = menu;
        if (group.equalsIgnoreCase("admin")) menu.findItem(R.id.menu_add).setVisible(true);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.menu_logout){
            SharedPreferences.Editor session = sessionPreference.edit();
            session.remove("token");
            session.remove("type");
            session.commit();
            // Lancement de MainActivity
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
