package com.rahmahnajiyahimtihan.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

/**
 * Created by User on 1/24/2018.
 */

public class SessionManager {

    /* session manager menyimpan data penguna ke server sebagai suatu sesi agar nantinya pengguna tidak perlu login lagi
    Session ada 2 jenis :
    1. variable global = app ditutup sesinya hilang jd harus login lagi
    2. share preference = data pengguna akan disimpan bahkan setelah app ditutup, jadi gak perlu login lagi
     */

    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context context;

    //mode share preference
    int mode = 0;

    //nama dari share preference
    //static adalah property (Variable) dan method(function) yang melekat pada class
    private static final String pref_name = "crudpref";

    //kunci share preferences
    private static final String is_login = "islogin";
    public static final  String kunci_email = "keyemail";

    //constructor
    public SessionManager(Context context) {  //right click for show this script > construct > context
        this.context = context;
        pref = context.getSharedPreferences(pref_name, mode);
        editor = pref.edit();
    }
    //method membuat session
    public void  createSession(String email) {
        //jika sdh login valuenya true
        editor.putBoolean(is_login, true);
        //memasukkan email kedalam variabelkunci email
        editor.putString(kunci_email, email);
        editor.commit();//commit itu mrngerjakan / melakukan jadi kalau gk di commit, fungsi editornya gk jalan
    }

    //method cek login
    public void  checkLogin() {
        //jika login false
        if (!this.is_login()) {
            Intent i = new Intent(context, LoginActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            //activity yg dibuka bakal jadi activity paling atas, jadi yang ke tutup
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //membuat activity baru
            context.startActivity(i);
        }else{
            //jika true maka akan pindah ke main activity
            Intent i = new Intent(context, MainActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
        }
    }

    private boolean is_login() {
        return pref.getBoolean(is_login, false);

    }

    //method logout
    public  void  logout() {
        //happus semua data/sesi
        editor.clear();
        editor.commit();

        Intent i = new Intent(context, LoginActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }
}
