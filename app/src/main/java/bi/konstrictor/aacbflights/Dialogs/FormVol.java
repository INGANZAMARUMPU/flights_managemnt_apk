package bi.konstrictor.aacbflights.Dialogs;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import bi.konstrictor.aacbflights.MainActivity;
import bi.konstrictor.aacbflights.Models.Vol;
import bi.konstrictor.aacbflights.R;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class FormVol extends Dialog {

    Spinner spinner_avion, spinner_source, spinner_destination;
    Button btn_vol_delete, btn_vol_cancel, btn_vol_submit;
    private boolean edition;
    private Vol vol;
    private MainActivity activity;

    public FormVol(MainActivity activity) {
        super(activity, R.style.Theme_AppCompat_DayNight_Dialog);
        setContentView(R.layout.form_vol);
        this.activity = activity;
        spinner_avion = findViewById(R.id.spinner_avion);
        spinner_source = findViewById(R.id.spinner_source);
        spinner_destination = findViewById(R.id.spinner_destination);

        btn_vol_delete = findViewById(R.id.btn_vol_delete);
        btn_vol_cancel = findViewById(R.id.btn_vol_cancel);
        btn_vol_submit = findViewById(R.id.btn_vol_submit);

        btn_vol_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        btn_vol_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        btn_vol_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        fillSpinner();
    }

    private void fillSpinner() {
        ArrayAdapter adapter_aeroport = new ArrayAdapter(
                activity,
                R.layout.support_simple_spinner_dropdown_item,
                activity.aeroports);
        ArrayAdapter adapter_avion = new ArrayAdapter(
                activity,
                R.layout.support_simple_spinner_dropdown_item,
                activity.avions);
        spinner_avion.setAdapter(adapter_avion);
        spinner_destination.setAdapter(adapter_aeroport);
        spinner_source.setAdapter(adapter_aeroport);
    }

    private int getIndexOfVol(String id_vol) {
        for (int i = 0; i < activity.vols.size(); i++) {
            if(activity.vols.get(i).id.equals(id_vol)){
                Log.i("==== DIALOG ====", activity.vols.get(i).toString());
                return i;
            }
        }
        return 0;
    }

    private int getIndexOfPassager(String id_passager) {
        for (int i = 0; i < activity.passagers.size(); i++) {
            if(activity.passagers.get(i).id.equals(id_passager)){
                return i;
            }
        }
        return 0;
    }

    public void setEdition(Vol vol) {
        edition = true;
        this.vol = vol;
        btn_vol_delete.setVisibility(View.VISIBLE);
    }
}