package bi.konstrictor.aacbflights;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

import bi.konstrictor.aacbflights.Dialogs.FormPassager;
import bi.konstrictor.aacbflights.Dialogs.FormReservation;
import bi.konstrictor.aacbflights.Dialogs.FormVol;

public class MainActivity extends AppCompatActivity {

    TabLayout tablayout;
    ViewPager viewpager;
    FragmentStatePA main_fspa;
    private Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tablayout = findViewById(R.id.tablayout);
        viewpager = findViewById(R.id.viewpager);
        main_fspa = new FragmentStatePA(getSupportFragmentManager(), 1);
        tablayout.setupWithViewPager(viewpager);
        viewpager.setAdapter(main_fspa);
        tablayout.getTabAt(0).setIcon(R.drawable.ic_reservation);
        tablayout.getTabAt(1).setIcon(R.drawable.ic_vol);
        tablayout.getTabAt(2).setIcon(R.drawable.ic_passagers);
    }
    @Override
    public boolean onCreateOptionsMenu( Menu menu) {
        getMenuInflater().inflate( R.menu.menu_main, menu);
        this.menu = menu;
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
