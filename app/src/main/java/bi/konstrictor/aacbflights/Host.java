package bi.konstrictor.aacbflights;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class Host {
    //    public static String URL = "http://192.168.1.2:80";
//    public static String URL = "https://bank.so-mas.net";
    public static String URL = "http://10.0.2.2:8000";
    private static SharedPreferences sessionPreference;

    public static void logOut(Activity context) {
        sessionPreference = context.getSharedPreferences("user_session", Context.MODE_PRIVATE);
        SharedPreferences.Editor session = sessionPreference.edit();
        session.clear();
        session.commit();
        context.finish();
    }
}