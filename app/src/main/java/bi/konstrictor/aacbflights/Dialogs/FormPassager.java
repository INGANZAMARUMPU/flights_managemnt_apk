package bi.konstrictor.aacbflights.Dialogs;

import android.app.Dialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import bi.konstrictor.aacbflights.Fragments.FragmentPassager;
import bi.konstrictor.aacbflights.Host;
import bi.konstrictor.aacbflights.MainActivity;
import bi.konstrictor.aacbflights.Models.Passager;
import bi.konstrictor.aacbflights.R;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class FormPassager extends Dialog {

    private FragmentPassager parent;
    private MainActivity context;
    private EditText field_user_nom, field_user_code, field_user_prenom;
    private Button btn_product_cancel, btn_product_submit, btn_product_delete;
    private Passager passager;
    private boolean edition = false;

    public FormPassager(FragmentPassager parent) {
        super(parent.context, R.style.Theme_AppCompat_DayNight_Dialog);
        setContentView(R.layout.form_passager);
        this.parent = parent;
        this.context = parent.context;
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
                delete();
            }
        });
        btn_product_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edition) edit(); else add();
            }
        });
    }

    private void add() {
        String json = "{" +
                "\"username\":\"" + field_user_prenom.getText() +
                "\",\"first_name\":\"" + field_user_prenom.getText() +
                "\",\"last_name\":\"" + field_user_nom.getText() +
                "\",\"password\":\"" + field_user_code.getText() +
                "\"}";

        RequestBody body = RequestBody.create(json, MediaType.parse("application/json; charset=utf-8"));

        OkHttpClient client = new OkHttpClient();
        HttpUrl.Builder urlBuilder = HttpUrl.parse(Host.URL + "/user/").newBuilder();

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
                    final Passager res = new Passager(
                            json_obj.getString("id"),
                            json_obj.getString("first_name"),
                            json_obj.getString("last_name")
                    );
                    context.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            parent.pushPassager(res);
                        }
                    });
                    FormPassager.this.dismiss();
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
                "\"username\":\"" + field_user_prenom.getText() +
                "\",\"first_name\":\"" + field_user_prenom.getText() +
                "\",\"last_name\":\"" + field_user_nom.getText() +
                "\",\"password\":\"" + field_user_code.getText() +
                "\"}";

        RequestBody body = RequestBody.create(json, MediaType.parse("application/json; charset=utf-8"));

        OkHttpClient client = new OkHttpClient();
        HttpUrl.Builder urlBuilder = HttpUrl.parse(Host.URL + "/user/"+passager.id+"/").newBuilder();

        String url = urlBuilder.build().toString();
        Request request = new Request.Builder()
                .url(url)
                .header("Authorization", "Token "+context.token)
                .patch(body)
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
                    final Passager res = new Passager(
                            json_obj.getString("id"),
                            json_obj.getString("first_name"),
                            json_obj.getString("last_name")
                    );
                    context.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            parent.editPassager(res);
                        }
                    });
                    FormPassager.this.dismiss();
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
        HttpUrl.Builder urlBuilder = HttpUrl.parse(Host.URL + "/user/"+passager.id+"/").newBuilder();

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
                        parent.removePassager(passager);
                    }
                });
                FormPassager.this.dismiss();
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