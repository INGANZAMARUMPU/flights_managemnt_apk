package bi.konstrictor.aacbflights.Dialogs;

import android.app.Dialog;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

import bi.konstrictor.aacbflights.Fragments.FragmentVol;
import bi.konstrictor.aacbflights.Host;
import bi.konstrictor.aacbflights.MainActivity;
import bi.konstrictor.aacbflights.Models.Aeroport;
import bi.konstrictor.aacbflights.Models.Avion;
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

public class FormFilter extends Dialog {
    Spinner spinner_compagnie;
    DatePicker picker_date_debut, picker_date_fin;
    private Button btn_cancel, btn_submit;
    private MainActivity context;

    public FormFilter(MainActivity context) {
        super(context, R.style.Theme_AppCompat_DayNight_Dialog);
        setContentView(R.layout.form_vol);
        this.context = context;

        spinner_compagnie = findViewById(R.id.spinner_compagnie);
        picker_date_debut = findViewById(R.id.picker_date_debut);
        picker_date_fin = findViewById(R.id.picker_date_fin);

        btn_cancel = findViewById(R.id.btn_cancel);
        btn_submit = findViewById(R.id.btn_submit);

        fillSpinner();
    }
    private void fillSpinner() {
//        ArrayAdapter adapter_compagnie = new ArrayAdapter(
//                context,
//                R.layout.support_simple_spinner_dropdown_item,
//                context.compagies);
//        spinner_compagnie.setAdapter(adapter_compagnie);
    }

    private int getIndexOfAeroport(String id_aeroport) {
        for (int i = 0; i < context.passagers.size(); i++) {
            if(context.aeroports.get(i).id.equals(id_aeroport)) return i;
        }
        return 0;
    }
    private String getDateTimeFromPickers(DatePicker dp, TimePicker tp ) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(dp.getYear(), dp.getMonth(), dp.getDayOfMonth(), tp.getCurrentHour(), tp.getCurrentMinute());
        DateFormat gmtFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:S'Z'");
        TimeZone gmtTime = TimeZone.getTimeZone("GMT");
        gmtFormat.setTimeZone(gmtTime);
        String mydate = ""+ gmtFormat.format(calendar.getTime());
        return mydate;
    }
}