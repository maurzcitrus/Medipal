package sg.edu.nus.iss.ft08.medipal;

/**
 * Created by cmani on 26/3/2017.
 */

import android.content.Context;
import android.content.SharedPreferences;


public class PreferenceManagerActivity
{
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context context;

    public PreferenceManagerActivity(Context context){
        this.context=context;
        pref=context.getSharedPreferences("first",0);
        editor=pref.edit();

    }
    public void setFirst(Boolean isFirst){
        editor.putBoolean("check",isFirst);
        editor.commit();
    }
    public boolean check(){
        return pref.getBoolean("check",true);
    }
}
