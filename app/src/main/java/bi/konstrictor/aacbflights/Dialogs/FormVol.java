package bi.konstrictor.aacbflights.Dialogs;

import android.app.Dialog;
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

    public FormVol(MainActivity activity) {
        super(activity, R.style.Theme_AppCompat_DayNight_Dialog);
        setContentView(R.layout.form_vol);
    }
}