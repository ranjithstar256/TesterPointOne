package com.am.testerpointone;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Lincoln on 05/05/16.
 */
public class PrefManager {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;

    // shared pref mode
    int PRIVATE_MODE = 0;

    // Shared preferences file name
    private static final String PREF_NAME = "tester";

    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";
    private static final String IS_Registered = "IS_Registered";
    private static final String IS_FirebasePrefEnabled = "IS_FirebasePrefEnabled";

    public PrefManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setFirebasePrefEnabled(boolean isuploaded) {
        editor.putBoolean(IS_FirebasePrefEnabled, isuploaded);
        editor.commit();
    }

    public boolean isFirebasePrefEnabled() {
        return pref.getBoolean(IS_FirebasePrefEnabled, false);
    }

    public void setisregistered(boolean isuploaded) {
        editor.putBoolean(IS_Registered, isuploaded);
        editor.commit();
    }

    public boolean isregistered() {
        return pref.getBoolean(IS_Registered, false);
    }

    public void setFirstTimeLaunch(boolean isFirstTime) {
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
        editor.commit();
    }

    public void setMyMobNum(String num) {
        editor.putString("num", num);
        editor.commit();
    }

    public String getMyMobNum() {
        return pref.getString("num", "123456");
    }

    public boolean isFirstTimeLaunch() {
        return pref.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }

    public void setResult(String contactdetails) {
        editor.putString("res", contactdetails);
        editor.commit();
    }

    public String getResult() {
        return pref.getString("res", "no result");

    }

    public void putusername(String username1, String password1) {
        editor.putString("usern", username1);
        editor.putString("passw", password1);
        editor.commit();
    }

    public String getusername() {
        return pref.getString("usern", " ");
    }

    public String getpassword() {
        return pref.getString("passw", " ");
    }

    public void removeusername(String getusername, String pw) {
        editor.putString("usern", "");
        editor.putString("passw", "");
        editor.commit();
    }

    public void setiamfrom(String gotoexam) {
        editor.putString("setiamfrom",gotoexam);
        editor.commit();
    }

    public String getiamfrom() {
            return pref.getString("setiamfrom","default");
    }

    public boolean isotpdone() {

        return pref.getBoolean("isoptdone",false);
        //editor.putBoolean("isoptdone",);
    }
    public void setotpdone(boolean b) {
        editor.putBoolean("isoptdone",b);
        editor.commit();
        //editor.putBoolean("isoptdone",);
    }

    public void setiseventregistered(boolean b) {
        editor.putBoolean("setiseventregistered",b);
        editor.commit();
    }

    public boolean iseventreg() {

        return pref.getBoolean("setiseventregistered",false);
        //editor.putBoolean("isoptdone",);
    }


}