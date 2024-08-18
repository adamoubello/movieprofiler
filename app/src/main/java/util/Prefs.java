package util;

import android.app.Activity;
import android.content.SharedPreferences;

public class Prefs {

    SharedPreferences preferences;

    public Prefs(Activity activity){
        preferences = activity.getPreferences(Activity.MODE_PRIVATE);
    }

    public void setMovie(String movie){
        preferences.edit().putString("movie", movie).commit();
    }

    public String getMovie(){
        return preferences.getString("movie", "tt0455944");
    }
}
