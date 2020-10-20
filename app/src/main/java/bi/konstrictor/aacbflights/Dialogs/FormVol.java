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
import bi.konstrictor.aacbflights.Models.Passager;
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
    DatePicker picker_date_depart, picker_date_arrivee;
    TimePicker picker_time_depart, picker_time_arrivee;
    private boolean edition;
    private Vol vol;
    private MainActivity context;
    private FragmentVol parent;

    public FormVol(FragmentVol parent) {
        super(parent.context, R.style.Theme_AppCompat_DayNight_Dialog);
        setContentView(R.layout.form_vol);
        this.parent = parent;
        this.context = parent.context;
        spinner_avion = findViewById(R.id.spinner_avion);
        spinner_source = findViewById(R.id.spinner_source);
        spinner_destination = findViewById(R.id.spinner_destination);
        picker_date_depart = findViewById(R.id.picker_date_depart);
        picker_date_arrivee = findViewById(R.id.picker_date_arrivee);
        picker_time_arrivee = findViewById(R.id.picker_time_arrivee);
        picker_time_depart = findViewById(R.id.picker_time_depart);

        btn_vol_delete = findViewById(R.id.btn_vol_delete);
        btn_vol_cancel = findViewById(R.id.btn_vol_cancel);
        btn_vol_submit = findViewById(R.id.btn_vol_submit);

        btn_vol_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete();
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
                if(edition) edit(); else add();
            }
        });
        fillSpinner();
    }
    private void fillSpinner() {
        ArrayAdapter adapter_aeroport = new ArrayAdapter(
                context,
                R.layout.support_simple_spinner_dropdown_item,
                context.aeroports);
        ArrayAdapter adapter_avion = new ArrayAdapter(
                context,
                R.layout.support_simple_spinner_dropdown_item,
                context.avions);
        spinner_avion.setAdapter(adapter_avion);
        spinner_destination.setAdapter(adapter_aeroport);
        spinner_source.setAdapter(adapter_aeroport);
    }

    private void add() {
        String json = "{" +
                "\"source\":\"" + ((Aeroport) spinner_source.getSelectedItem()).id +
                "\",\"destination\":\"" + ((Aeroport) spinner_destination.getSelectedItem()).id +
                "\",\"depart\":\"" + getDateTimeFromPickers(picker_date_depart, picker_time_depart) +
                "\",\"arrivee\":\"" + getDateTimeFromPickers(picker_date_arrivee, picker_time_arrivee) +
                "\",\"avion\":\"" + ((Avion) spinner_avion.getSelectedItem()).id
                + "\"}";

        RequestBody body = RequestBody.create(json, MediaType.parse("application/json; charset=utf-8"));

        OkHttpClient client = new OkHttpClient();
        HttpUrl.Builder urlBuilder = HttpUrl.parse(Host.URL + "/vol/").newBuilder();

        String url = urlBuilder.build().toString();
        Request request = new Request.Builder()
                .url(url)
                .header("Authorization", "Token "+context.token)
                .post(body)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string();
                JSONObject json_obj = null;
                try {
                    json_obj = new JSONObject(json);
                    final Vol vol = new Vol(
                            json_obj.getString("id"),
                            json_obj.getString("source"),
                            json_obj.getString("destination"),
                            json_obj.getString("avion"),
                            json_obj.getString("compagnie"),
                            json_obj.getString("depart"),
                            json_obj.getString("arrivee"),
                            json_obj.getString("id_avion"),
                            json_obj.getString("id_source"),
                            json_obj.getString("id_destination")
                    );
                    context.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            parent.pushVol(vol);
                        }
                    });
                    FormVol.this.dismiss();
                } catch (JSONException e) {
                    context.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(context, "Ajout échouée", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }
    private void edit() {
        String json = "{" +
                "\"source\":\"" + ((Aeroport) spinner_source.getSelectedItem()).id +
                "\",\"destination\":\"" + ((Aeroport) spinner_destination.getSelectedItem()).id +
                "\",\"depart\":\"" + getDateTimeFromPickers(picker_date_depart, picker_time_depart) +
                "\",\"arrivee\":\"" + getDateTimeFromPickers(picker_date_arrivee, picker_time_arrivee) +
                "\",\"avion\":\"" + ((Avion) spinner_avion.getSelectedItem()).id
                + "\"}";

        RequestBody body = RequestBody.create(json, MediaType.parse("application/json; charset=utf-8"));

        OkHttpClient client = new OkHttpClient();
        HttpUrl.Builder urlBuilder = HttpUrl.parse(Host.URL + "/vol/"+vol.id+"/").newBuilder();

        String url = urlBuilder.build().toString();
        Request request = new Request.Builder()
                .url(url)
                .header("Authorization", "Token "+context.token)
                .put(body)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string();
                try {
                    JSONObject json_obj = new JSONObject(json);
                    final Vol vol = new Vol(
                            json_obj.getString("id"),
                            json_obj.getString("source"),
                            json_obj.getString("destination"),
                            json_obj.getString("avion"),
                            json_obj.getString("compagnie"),
                            json_obj.getString("depart"),
                            json_obj.getString("arrivee"),
                            json_obj.getString("id_avion"),
                            json_obj.getString("id_source"),
                            json_obj.getString("id_destination")
                    );
                    context.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            parent.editVol(vol);
                        }
                    });
                    FormVol.this.dismiss();
                } catch (JSONException e) {
                    context.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(context, "Ajout échouée", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }
    private void delete() {
        OkHttpClient client = new OkHttpClient();
        HttpUrl.Builder urlBuilder = HttpUrl.parse(Host.URL + "/vol/"+vol.id+"/").newBuilder();

        String url = urlBuilder.build().toString();
        Request request = new Request.Builder()
                .url(url)
                .header("Authorization", "Token "+context.token)
                .delete()
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string();
                if (!json.trim().isEmpty()){
                    context.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(context, "Suppression échouée", Toast.LENGTH_SHORT).show();
                        }
                    });
                    return;
                }
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        parent.removeVol(vol);
                    }
                });
                FormVol.this.dismiss();
            }
        });
    }

    private int getIndexOfAvion(String id_avion) {
        for (int i = 0; i < context.vols.size(); i++) {
            if(context.avions.get(i).id.equals(id_avion)) return i;
        }
        return 0;
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
    public void setEdition(Vol vol) {
        edition = true;
        this.vol = vol;
        btn_vol_delete.setVisibility(View.VISIBLE);
        spinner_avion.setSelection(getIndexOfAvion(vol.id_avion));
        spinner_source.setSelection(getIndexOfAeroport(vol.id_source));
        spinner_destination.setSelection(getIndexOfAeroport(vol.id_destination));
    }
}