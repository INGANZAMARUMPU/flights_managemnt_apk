package bi.konstrictor.aacbflights.Dialogs;

import android.app.Dialog;

import bi.konstrictor.aacbflights.MainActivity;
import bi.konstrictor.aacbflights.R;

public class FormPassager extends Dialog {

    public FormPassager(MainActivity activity) {
        super(activity, R.style.Theme_AppCompat_DayNight_Dialog);
        setContentView(R.layout.form_passager);
    }
}