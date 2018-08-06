package com.rahmahnajiyahimtihan.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {

    private EditText logemail, logpass;
    private Button btnlogin;
    private TextView txtregis;

    AQuery aQuery;

    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sessionManager = new SessionManager(getApplicationContext());

        setView();


    }

    private void setView() {
        logemail = (EditText) findViewById(R.id.logEmail);
        logpass = (EditText) findViewById(R.id.logPass);
        btnlogin = (Button)  findViewById(R.id.btnLogin);
        txtregis = (TextView) findViewById(R.id.txtRegis);

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               aQuery =  new AQuery(LoginActivity.this);
                loginUser();

            }
        });

        txtregis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(i);
            }
        });
    }

    private void loginUser() {
        logemail.setError(null);
        logpass.setError(null);

        //cek input
        if (Helper.isEmpty(logemail)){
            logemail.setError("Email Tidak Boleh Kosong");
            logemail.requestFocus();

        } else if (!Helper.isEmailValid(logemail)) {
            logemail.setError("Format Email Salah");
            logemail.requestFocus();
        }else if (Helper.isEmpty(logpass)){
            logpass.setError("Password Tidak kosong");
            logpass.requestFocus();
        }else {
            //proses login
            String url = "http://192.168.100.28/data/index.php/api/login";

            HashMap<String,String> params = new HashMap<>();
            params.put("email", logemail.getText().toString());
            params.put("password",logpass.getText().toString());

            ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
            progressDialog.setMessage("Login...INNALLAHA MA'ASHOBIRIIN");
            progressDialog.setCancelable(false);
            progressDialog.setIndeterminate(false);

            aQuery.progress(progressDialog).ajax(url, params, String.class, new AjaxCallback<String>(){
                @Override
                public void callback(String url, String object, AjaxStatus status) {
                if (object!=null) {
                    Toast.makeText(LoginActivity.this, object, Toast.LENGTH_SHORT).show();
                    try {
                        JSONObject json = new JSONObject(object);
                        String hasil = json.getString("result");
                        String pesan = json.getString("pesan");

                        //if result true
                        if (hasil.equalsIgnoreCase("true")){
                            //buat session
                            sessionManager.createSession(logemail.getText().toString());
                            Toast.makeText(LoginActivity.this, pesan, Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(i);
                            finish();
                        }else{
                            Toast.makeText(LoginActivity.this, pesan, Toast.LENGTH_SHORT).show();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(LoginActivity.this, "Gagal Mengambil Json", Toast.LENGTH_SHORT).show();
                    }
                }
                }
            });
        }
    }
}
