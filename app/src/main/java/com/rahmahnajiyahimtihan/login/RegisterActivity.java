package com.rahmahnajiyahimtihan.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    private EditText ednama, edemail, edpass1, edpass2, edalamat;
    private Button btnregis;
    private TextView txtlogin;

    AQuery aQuery;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setView();
    }

    private void setView() {
        ednama = (EditText) findViewById(R.id.regNama);
        edemail = (EditText) findViewById(R.id.regEmail);
        edpass1 = (EditText) findViewById(R.id.regPassword);
        edpass2 = (EditText) findViewById(R.id.regKonPass);
        edalamat = (EditText) findViewById(R.id.regAlamat);
        btnregis = (Button) findViewById(R.id.btnRegis);
        txtlogin = (TextView) findViewById(R.id.txtLogin);

        txtlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(i);
                finish();

            }
        });

        btnregis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                aQuery = new AQuery(RegisterActivity.this);

                registerUser();
            }
        });

    }

    private void registerUser() {
        //set error jado null
        ednama.setError(null);
        edemail.setError(null);
        edpass1.setError(null);
        edpass2.setError(null);
        edalamat.setError(null);

        //cek inputan
        if (Helper.isEmpty(ednama)) {
            ednama.setError("Nama Tidak Boleh Kosong");
            ednama.requestFocus();
        }else    if (Helper.isEmpty(edemail)) {
            edemail.setError("Email Tidak Boleh Kosong");
            edemail.requestFocus();
        }else if (!Helper.isEmailValid(edemail)){
            edemail.setError("Format Email Salah");
            ednama.requestFocus();
        }else if (Helper.isEmpty(edpass1)){
            edpass1.setError("Password Tidak Boleh Kosong");
            edpass1.requestFocus();
        }else if (Helper.isEmpty(edpass2)){
            edpass2.setError("Password tidak boleh kosong");
            edpass2.requestFocus();
        }else if (Helper.isEmpty(edalamat)){
            edalamat.setError("Alamat tidak boleh kosong");
            edalamat.requestFocus();
        }else if (Helper.isCompare(edpass1, edpass2)){
            edpass2.setError("Password Tidak Sama");
            edpass2.requestFocus();
        }else {
            //proses register
            String url = "http://192.168.100.28/data/index.php/api/daftar";

            HashMap<String, String> params = new HashMap<>();
            //bawa nilai (kunci, nilai yg akn dikirim)
            params.put("nama", ednama.getText().toString());
            params.put("email", edemail.getText().toString());
            params.put("password", edpass2.getText().toString());
            params.put("alamat", edalamat.getText().toString());

            ProgressDialog progressDialog = new ProgressDialog(RegisterActivity.this);
            progressDialog.setMessage("Register....");
            progressDialog.setCancelable(false);
            progressDialog.setIndeterminate(false);

            aQuery.progress(progressDialog).ajax(url, params, String.class, new AjaxCallback<String>(){
                @Override
                public void callback(String url, String object, AjaxStatus status) {
                    if (object !=null) {
                        Toast.makeText(RegisterActivity.this, object, Toast.LENGTH_SHORT).show();
                        try { //temannya catch
                            JSONObject json = new JSONObject(object);
                            String hasil = json.getString("result");//samain kyk yg diphp (yg didlm kurung/string ijo)
                            String pesan = json.getString("pesan");

                            //jk hasil true
                            if (hasil.equalsIgnoreCase("true")){
                                //maka pindah ke login activity

                                /*buat session*/
//                                    JSONArray jsonArray = json.getJSONArray("user");
//
//                                    for (int a = 0;a<jsonArray.length();a++){
//                                        JSONObject object1 = jsonArray.getJSONObject(a);
//                                        sessionManager.createSession(object1.getString("email"),object1.getString("nama"),object1.getString("password"),object1.getString("id"));
//
//                                    }
                                Toast.makeText(RegisterActivity.this, pesan, Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                                startActivity(i);
                                finish();
                            }else {
                                //jk false/gagal
                                Toast.makeText(RegisterActivity.this, pesan, Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(RegisterActivity.this, "Gagal Mengambil JSON", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });

        }

    }
}
