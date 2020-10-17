package bi.konstrictor.aacbflights;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

import bi.konstrictor.aacbflights.Dialogs.FormPassager;
import bi.konstrictor.aacbflights.Dialogs.FormReservation;
import bi.konstrictor.aacbflights.Dialogs.FormVol;
import bi.konstrictor.aacbflights.Models.Passager;
import bi.konstrictor.aacbflights.Models.Vol;

public class MainActivity extends AppCompatActivity {

    TabLayout tablayout;
    ViewPager viewpager;
    FragmentStatePA main_fspa;
    private Menu menu;
    private SharedPreferences sessionPreference;
    private String group;
    public ArrayList<Passager> passagers;
    public ArrayList<Vol> vols;

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
        String token = sessionPreference.getString("token", "");
        group = sessionPreference.getString("type", "");
        if(token.trim().isEmpty()){
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        }
    }
    @Override
    public boolean onCreateOptionsMenu( Menu menu) {
        getMenuInflater().inflate( R.menu.menu_main, menu);
        this.menu = menu;
        if (group.equalsIgnoreCase("admin") & tablayout.getSelectedTabPosition()==0)
            menu.findItem(R.id.menu_add).setVisible(true);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_search) {
            Toast.makeText(this, "RECHERCHE EN COURS...", Toast.LENGTH_LONG).show();
        }else if(id == R.id.menu_add){
            if(tablayout.getTabAt(0).isSelected()){
                new FormReservation(this).show();
            }else if(tablayout.getTabAt(1).isSelected()){
                new FormVol(this).show();
            }else if(tablayout.getTabAt(2).isSelected()){
                new FormPassager(this).show();
            }
        }else if(id == R.id.menu_filter){
            Toast.makeText(this, "FILTRAGE EN COURS...", Toast.LENGTH_LONG).show();
        }else if(id == R.id.menu_logout){
            Toast.makeText(this, "DECONNEXION EN COURS...", Toast.LENGTH_LONG).show();
        }
        return super.onOptionsItemSelected(item);
    }
}
