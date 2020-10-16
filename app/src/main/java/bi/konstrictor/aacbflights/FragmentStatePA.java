package bi.konstrictor.aacbflights;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import bi.konstrictor.aacbflights.Fragments.FragmentPassager;
import bi.konstrictor.aacbflights.Fragments.FragmentReservation;
import bi.konstrictor.aacbflights.Fragments.FragmentVol;

public class FragmentStatePA extends FragmentStatePagerAdapter {

    private String[] titles = new String[] {"RESERVATIONS", "VOLS", "PASSAGERS"};

    public FragmentStatePA(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 1:
                return new FragmentVol();
            case 2:
                return new FragmentPassager();
            default:
                return new FragmentReservation();
        }
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
