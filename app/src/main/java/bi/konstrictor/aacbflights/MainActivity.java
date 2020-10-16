package bi.konstrictor.aacbflights;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    TabLayout tablayout;
    ViewPager viewpager;
    FragmentStatePA main_fspa;

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
}
