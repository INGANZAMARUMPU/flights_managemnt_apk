package bi.konstrictor.aacbflights.Dialogs;

import android.app.Dialog;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import bi.konstrictor.aacbflights.Fragments.FragmentPassager;
import bi.konstrictor.aacbflights.Fragments.FragmentReservation;
import bi.konstrictor.aacbflights.Host;
import bi.konstrictor.aacbflights.MainActivity;
import bi.konstrictor.aacbflights.Models.Passager;
import bi.konstrictor.aacbflights.Models.Reservation;
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
                delete();
            }
        });
        btn_res_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edition) edit(); else add();
            }
        });

        fillSpinners();
    }
    private void add() {
        String json = "{" +
                "\"user\":\"" + ((Passager) spinner_res_passager.getSelectedItem()).id +
                "\",\"vol\":\"" + ((Vol) spinner_res_vol.getSelectedItem()).id
                + "\"}";

        RequestBody body = RequestBody.create(json, MediaType.parse("application/json; charset=utf-8"));

        OkHttpClient client = new OkHttpClient();
        HttpUrl.Builder urlBuilder = HttpUrl.parse(Host.URL + "/reservation/").newBuilder();

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
                    final Reservation res = new Reservation(
                            json_obj.getString("id"),
                            json_obj.getString("nom"),
                            json_obj.getString("prenom"),
                            json_obj.getString("depart"),
                            json_obj.getString("arrivee"),
                            json_obj.getString("user"),
                            json_obj.getString("vol"),
                            json_obj.getString("id_passager"),
                            json_obj.getString("id_vol")
                    );
                    context.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            parent.pushReservation(res);
                        }
                    });
                    FormReservation.this.dismiss();
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
                "\"user\":\"" + ((Passager) spinner_res_passager.getSelectedItem()).id +
                "\",\"vol\":\"" + ((Vol) spinner_res_vol.getSelectedItem()).id
                + "\"}";

        RequestBody body = RequestBody.create(json, MediaType.parse("application/json; charset=utf-8"));

        OkHttpClient client = new OkHttpClient();
        HttpUrl.Builder urlBuilder = HttpUrl.parse(Host.URL + "/reservation/"+reservation.id+"/").newBuilder();

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
                    final Reservation res = new Reservation(
                            json_obj.getString("id"),
                            json_obj.getString("nom"),
                            json_obj.getString("prenom"),
                            json_obj.getString("depart"),
                            json_obj.getString("arrivee"),
                            json_obj.getString("user"),
                            json_obj.getString("vol"),
                            json_obj.getString("id_passager"),
                            json_obj.getString("id_vol")
                    );
                    context.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            parent.editReservation(res);
                        }
                    });
                    FormReservation.this.dismiss();
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
        HttpUrl.Builder urlBuilder = HttpUrl.parse(Host.URL + "/reservation/"+reservation.id+"/").newBuilder();

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
                        parent.removeReservation(reservation);
                    }
                });
                FormReservation.this.dismiss();
            }
        });
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
            if(context.vols.get(i).id.equals(id_vol)) return i;
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