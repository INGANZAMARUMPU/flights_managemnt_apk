package bi.konstrictor.aacbflights.Dialogs;

import android.app.Dialog;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import bi.konstrictor.aacbflights.MainActivity;
import bi.konstrictor.aacbflights.Models.Passager;
import bi.konstrictor.aacbflights.Models.Reservation;
import bi.konstrictor.aacbflights.R;

public class FormPassager extends Dialog {

    private final MainActivity activity;
    private final EditText field_user_nom, field_user_code, field_user_prenom;
    private final Button btn_product_cancel, btn_product_submit, btn_product_delete;
    private Passager passager;
    private boolean edition = false;

    public FormPassager(MainActivity activity) {
        super(activity, R.style.Theme_AppCompat_DayNight_Dialog);
        setContentView(R.layout.form_passager);
        this.activity = activity;
        field_user_nom = findViewById(R.id.field_user_nom);
        field_user_code = findViewById(R.id.field_user_code);
        field_user_prenom = findViewById(R.id.field_user_prenom);

        btn_product_cancel = findViewById(R.id.btn_product_cancel);
        btn_product_delete = findViewById(R.id.btn_product_delete);
        btn_product_submit = findViewById(R.id.btn_product_submit);

        btn_product_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        btn_product_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        btn_product_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    public void setEdition(Passager passager) {
        edition = true;
        this.passager = passager;
        field_user_nom.setText(passager.nom);
        field_user_prenom.setText(passager.prenom);
        btn_product_delete.setVisibility(View.VISIBLE);
    }
}