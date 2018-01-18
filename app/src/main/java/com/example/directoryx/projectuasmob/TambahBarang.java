package com.example.directoryx.projectuasmob;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.directoryx.projectuasmob.Helper.Config;
import com.example.directoryx.projectuasmob.Helper.RequestHandler;
import com.example.directoryx.projectuasmob.Helper.SQLController;
import com.example.directoryx.projectuasmob.Helper.SharedPrefManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class TambahBarang extends AppCompatActivity {
    EditText nama,stok,harga;
    Button simpan;
    SQLController sqlC;
    String id,username,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_barang);
        id = getIntent().getExtras().getString("id");
        username = getIntent().getExtras().getString("username");

        password = getIntent().getExtras().getString("password");
        nama = (EditText) findViewById(R.id.txtnama);
        stok = (EditText) findViewById(R.id.txtstok);
        harga = (EditText) findViewById(R.id.txtharga);
        simpan = (Button) findViewById(R.id.btnsimpan);
        Log.d("iduser", SharedPrefManager.getKeyId());
        sqlC = new SQLController(this);
        sqlC.open();
        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Tambahbarangserver();
            }
        });

    }


    private void Tambahbarangserver() {
        //first getting the values
        final String nama1 = nama.getText().toString();
        final String stok1 = stok.getText().toString();
        final String harga1 = harga.getText().toString();

        //validating inputs
        if (TextUtils.isEmpty(nama1)) {
            nama.setError("Tolong Masukkan nama barang anda !");
            nama.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(stok1)) {
            stok.setError("Tolong masukkan stok barang anda !");
            stok.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(harga1)) {
            harga.setError("Tolong masukkan harga barang anda !");
            harga.requestFocus();
            return;
        }

        //if everything is fine

        class Tambahbarangserver extends AsyncTask<Void, Void, String> {

            final ProgressDialog progressDialog = new ProgressDialog(TambahBarang.this,
                    R.style.AppTheme_Dark_Dialog);

            @Override
            protected void onPreExecute() {
                //dbcon.execSQL("DELETE FROM "+DBHelper.TABLE_SICYCA);

                progressDialog.setIndeterminate(true);
                progressDialog.setMessage("Menyimpan...");
                progressDialog.show();
                super.onPreExecute();

            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);


                try {
                    //converting response to json object
                    JSONObject obj = new JSONObject(s);

                    //if no error in response
                    if (!obj.getBoolean("error")) {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), "Berhasil Menyimpan", Toast.LENGTH_SHORT).show();

                        sqlC.syncSQLITEMYSQL(username,password);
                        //getting the user from the response
                        //JSONObject userJson = obj.getJSONObject("user");
                        Intent i = new Intent(TambahBarang.this, ManajemenGudang.class);
                        i.putExtra("id",id);
                        i.putExtra("username",username);
                        i.putExtra("password",password);
                        startActivity(i);


                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), "Gagal Menyimpan", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(Void... voids) {
                //creating request handler object
                RequestHandler requestHandler = new RequestHandler();

                //creating request parameters
                HashMap<String, String> params = new HashMap<>();
                params.put("nama", nama1);
                params.put("stok", stok1);
                params.put("harga", harga1);
                params.put("action", "simpan");

                //returing the response
                return requestHandler.sendPostRequest(Config.URL_ACTION, params);
            }
        }

        Tambahbarangserver tb = new Tambahbarangserver();
        tb.execute();
    }
}
