package com.rahmahnajiyahimtihan.login;

import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by User on 1/25/2018.
 */

public class Helper {

    public static boolean isEmailValid(EditText email) {

        boolean isValid = false;
        String expression = "[\\w\\.-]+@([\\w\\-]+\\.)+[A-z]{2,4}$";//<<simbol/tanda email//expression(bentuk) email
        CharSequence inputString = email.getText().toString();//tipe data charsequence
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputString);

        //jika true
        if (matcher.matches()) {
            isValid = true;
        }
        return  isValid;
    }

    //method untuk mengecek input ada atau tidak
    public static boolean isEmpty(EditText text) {
        //jika text tidak kosong
        if (text.getText().toString().trim().length() > 0) {
            //tdk dikembalikan
            return  false;
        }else {
            return  true;

        }
    }

    //method untuk mengecek kesamaan inputan password mengCompare
    public static boolean isCompare (EditText text1, EditText text2) {
        String a = text1.getText().toString();
        String b = text2.getText().toString();

        //jika variabel a dan b sama persis
        if (a.equals(b)){
            //tdk dikembalikan
            return  false;
        }else{
            return true;
        }

    }

}
