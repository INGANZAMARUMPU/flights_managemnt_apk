package bi.konstrictor.aacbflights.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import bi.konstrictor.aacbflights.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentPassager extends Fragment {

    public FragmentPassager() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_passager, container, false);;
        return view;
    }
}
