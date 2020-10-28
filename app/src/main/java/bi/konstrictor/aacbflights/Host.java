package bi.konstrictor.aacbflights;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class Host {
//    public static String URL = "http://192.168.8.100:8000";
//    public static String URL = "https://bank.so-mas.net";
    public static String URL = "http://10.0.2.2:8000";

    public static Date getDate(String str_date) {
        Date date;
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:S'Z'");
            formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
            date = formatter.parse(str_date);
        }catch (Exception e) {
            e.printStackTrace();
            date = null;
        }
        return date;
    }
    public static String getStrDate(Date date) {
        String str_date;
        try {
            SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MMM-yyyy HH:mm"); //this format changeable
            dateFormatter.setTimeZone(TimeZone.getDefault());
            str_date = "Le "+ dateFormatter.format(date);
        }catch (Exception e) {
            str_date = null;
        }
        return str_date;
    }
}