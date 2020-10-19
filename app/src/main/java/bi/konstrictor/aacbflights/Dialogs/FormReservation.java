package bi.konstrictor.aacbflights.Dialogs;

import android.app.Dialog;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import bi.konstrictor.aacbflights.Fragments.FragmentPassager;
import bi.konstrictor.aacbflights.Fragments.FragmentReservation;
import bi.konstrictor.aacbflights.MainActivity;
import bi.konstrictor.aacbflights.Models.Reservation;
import bi.konstrictor.aacbflights.R;

public class FormReservation extends Dialog {

    private FragmentReservation parent;
    private  Button btn_res_cancel, btn_res_submit, btn_res_delete;
    private Spinner spinner_res_passager, spinner_res_vol;
    private boolean edition = false;
    private Reservation reservation;
    private MainActivity context;

    public FormReservation(FragmentReservation parent) {
        super(parent.context, R.style.Theme_AppCompat_DayNight_Dialog);
        setContentView(R.layout.form_reservation);
        this.parent = parent;
        this.context = parent.context;
        init();
    }

    public FormReservation(MainActivity context) {
        super(context, R.style.Theme_AppCompat_DayNight_Dialog);
        setContentView(R.layout.form_reservation);
        this.context = context;
        init();
    }

    private void init() {
        spinner_res_passager = findViewById(R.id.spinner_res_passager);
        spinner_res_vol = findViewById(R.id.spinner_res_vol);
        btn_res_cancel = findViewById(R.id.btn_res_cancel);
        btn_res_delete = findViewById(R.id.btn_res_delete);
        btn_res_submit = findViewById(R.id.btn_res_submit);

        btn_res_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        btn_res_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        btn_res_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        fillSpinners();
    }

    private void fillSpinners() {
        ArrayAdapter adapter_passager = new ArrayAdapter(
                context,
                R.layout.support_simple_spinner_dropdown_item,
                context.passagers);
        ArrayAdapter adapter_vol = new ArrayAdapter(
                context,
                R.layout.support_simple_spinner_dropdown_item,
                context.vols);
        spinner_res_passager.setAdapter(adapter_passager);
        spinner_res_vol.setAdapter(adapter_vol);
    }

    private int getIndexOfVol(String id_vol) {
        for (int i = 0; i < parent.context.vols.size(); i++) {
            if(context.vols.get(i).id.equals(id_vol)){
                Log.i("==== DIALOG ====", context.vols.get(i).toString());
                return i;
            }
        }
        return 0;
    }

    private int getIndexOfPassager(String id_passager) {
        for (int i = 0; i < context.passagers.size(); i++) {
            if(context.passagers.get(i).id.equals(id_passager)){
                return i;
            }
        }
        return 0;
    }

    public void setEdition(Reservation reservation) {
        this.edition = true;
        this.reservation = reservation;
        spinner_res_passager.setSelection(getIndexOfPassager(reservation.id_passager));
        spinner_res_vol.setSelection(getIndexOfVol(reservation.id_vol));
        btn_res_delete.setVisibility(View.VISIBLE);
    }
}