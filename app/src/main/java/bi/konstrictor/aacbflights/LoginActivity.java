package bi.konstrictor.aacbflights;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {

    EditText field_username, field_password;
    private SharedPreferences sessionPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        field_username = findViewById(R.id.field_username);
        field_password = findViewById(R.id.field_password);

        // gucrea session
        sessionPreference = getSharedPreferences("user_session", Context.MODE_PRIVATE);

        // kuraba ko ubuheruka hari uwari akiri connectee
        String token = sessionPreference.getString("token", "");
        if(!token.trim().isEmpty()){
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    public void performLogin(View view) {
        // creation ya string json
        String json = "{" +
                "\"username\":\"" + field_username.getText() +
                "\",\"password\":\"" + field_password.getText()
                + "\"}";

        // transformation ya string muri paquet reseaux
        RequestBody body = RequestBody.create(json, MediaType.parse("application/json; charset=utf-8"));

        // creation ya socket
        OkHttpClient client = new OkHttpClient();
        HttpUrl.Builder urlBuilder = HttpUrl.parse(Host.URL + "/login/").newBuilder();

        // creation ya requette wisunze propriete za paquets ushaka kurungika
        String url = urlBuilder.build().toString();
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        // envoie ya requette muri reseaux
        client.newCall(request).enqueue(new Callback() {
            // igarukanye inyishu mbi
            @Override
            public void onFailure(Call call, final IOException e) {
                LoginActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }

            // bigenze neza
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                // recuperation de la reponse dans un string JSON
                String json = response.body().string();
                try {
                    //transformation du string en objet JAVA
                    JSONObject jsonObject = new JSONObject(json);
                    // extraction du contenue
                    String token = jsonObject.getString("token");
                    String type = jsonObject.getString("group");
                    // enregistrement ya contenue muri session kugira ntibihanagurike n'aho app yozima
                    SharedPreferences.Editor session = sessionPreference.edit();
                    session.putString("token", token);
                    session.putString("type", type);
                    session.commit();
                    // Lancement de MainActivity
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                } catch (Exception e) {
                    Log.i("==== HOST ====", e.getMessage());
                }
            }
        });
        LoginActivity.this.finish();
    }
}
