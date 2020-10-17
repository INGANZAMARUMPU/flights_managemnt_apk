package bi.konstrictor.aacbflights.Dialogs;

import android.app.Dialog;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import bi.konstrictor.aacbflights.MainActivity;
import bi.konstrictor.aacbflights.R;

public class FormReservation extends Dialog {

    private final MainActivity activity;
    private final Button btn_res_cancel, btn_res_submit, btn_res_delete;
    private Spinner spinner_res_passager, spinner_res_vol;

    public FormReservation(MainActivity activity) {
        super(activity, R.style.Theme_AppCompat_DayNight_Dialog);
        setContentView(R.layout.form_reservation);
        this.activity = activity;
        spinner_res_passager = findViewById(R.id.spinner_res_passager);
        spinner_res_vol = findViewById(R.id.spinner_res_vol);
        btn_res_cancel = findViewById(R.id.btn_res_cancel);
        btn_res_delete = findViewById(R.id.btn_res_delete);
        btn_res_submit = findViewById(R.id.btn_res_submit);

        fillSpinner();
    }

    private void fillSpinner() {
        ArrayAdapter adapter_passager = new ArrayAdapter(
                activity,
                R.layout.support_simple_spinner_dropdown_item,
                activity.passagers);
        ArrayAdapter adapter_vol = new ArrayAdapter(
                activity,
                R.layout.support_simple_spinner_dropdown_item,
                activity.vols);
        spinner_res_passager.setAdapter(adapter_passager);
        spinner_res_vol.setAdapter(adapter_vol);
    }
}