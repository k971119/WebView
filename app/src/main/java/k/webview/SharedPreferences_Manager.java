package k.webview;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

public class SharedPreferences_Manager{

    private final String PREF_NAME = "com.test.pref";

    static Context mContenxt;

    public SharedPreferences_Manager(Context c){
        mContenxt = c;
    }
    // 문자열
    public void put(String key, String value){
        SharedPreferences pref = mContenxt.getSharedPreferences(PREF_NAME, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        editor.putString(key, value);
        editor.commit();
    }
    // boolean형
    public void put(String key, boolean value) {
        SharedPreferences pref = mContenxt.getSharedPreferences(PREF_NAME,
                Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        editor.putBoolean(key, value);
        editor.commit();
    }
    // 정수형
    public void put(String key, int value) {
        SharedPreferences pref = mContenxt.getSharedPreferences(PREF_NAME,
                Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        editor.putInt(key, value);
        editor.commit();
    }
    // 문자열 값
    public String getValue(String key, String dftValue){
        SharedPreferences pref = mContenxt.getSharedPreferences(PREF_NAME, Activity.MODE_PRIVATE);
        try{
            return pref.getString(key, dftValue);
        }catch (Exception e){
            return dftValue;
        }
    }
    // 정수형 값
    public int getValue(String key, int dftValue) {
        SharedPreferences pref = mContenxt.getSharedPreferences(PREF_NAME,
                Activity.MODE_PRIVATE);

        try {
            return pref.getInt(key, dftValue);
        } catch (Exception e) {
            return dftValue;
        }

    }
    // boolean형 값
    public boolean getValue(String key, boolean dftValue) {
        SharedPreferences pref = mContenxt.getSharedPreferences(PREF_NAME,
                Activity.MODE_PRIVATE);

        try {
            return pref.getBoolean(key, dftValue);
        } catch (Exception e) {
            return dftValue;
        }
    }
    //값 삭제
    public void removeValue(String key){
        SharedPreferences pref = mContenxt.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.remove(key);
        editor.commit();
    }
}
