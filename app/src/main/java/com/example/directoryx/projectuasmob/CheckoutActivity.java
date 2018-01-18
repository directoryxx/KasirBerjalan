package com.example.directoryx.projectuasmob;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.directoryx.projectuasmob.Helper.Config;
import com.example.directoryx.projectuasmob.Helper.RequestHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class CheckoutActivity extends AppCompatActivity {
    private TextView pesan,total,kembali;
    private EditText edbayar;
    private Button btnBaya;
    String idtransaksi,username,password,id;
    String order;
    int bay,sum,tot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        id = getIntent().getExtras().getString("id");
        username = getIntent().getExtras().getString("username");

        password = getIntent().getExtras().getString("password");
        idtransaksi = getIntent().getExtras().getString("idtransaksi");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        pesan = (TextView) findViewById(R.id.pesan);
        total = (TextView) findViewById(R.id.total);
        edbayar = (EditText) findViewById(R.id.edbayar);
        kembali  = (TextView) findViewById(R.id.kembali);
        btnBaya = (Button) findViewById(R.id.btnBaya);
        btnBaya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (btnBaya.getText().toString().equals("Bayar")){
                    bay = Integer.parseInt(edbayar.getText().toString());
                    tot = Integer.parseInt(total.getText().toString());
                    sum = bay-tot;
                    if (sum >= 0){
                        btnBaya.setText("Selesaikan");
                        kembali.setText("Kembalian : "+sum);
                        kembali.setVisibility(View.VISIBLE);
                    } else {
                        Toast.makeText(CheckoutActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Finishtran();
                }

            }
        });
        Ambildetail();
    }

    private void Ambildetail() {
        //first getting the values
        //final String id = String.valueOf();
        //Log.d("ID", id);
        //Log.d("NAMA", nama1);
        //Log.d("STOK", stok1);
        //Log.d("HARGA", harga1);

        //validating inputs

        //if everything is fine

        class Ambildetail extends AsyncTask<Void, Void, String> {

            final ProgressDialog progressDialog = new ProgressDialog(CheckoutActivity.this,
                    R.style.AppTheme_Dark_Dialog);

            @Override
            protected void onPreExecute() {
                //dbcon.execSQL("DELETE FROM "+DBHelper.TABLE_SICYCA);

                progressDialog.setIndeterminate(true);
                progressDialog.setMessage("Mengambil...");
                progressDialog.show();
                super.onPreExecute();

            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Log.d("responseupdate",s);


                try {
                    //converting response to json object
                    JSONObject obj = new JSONObject(s);

                    //if no error in response
                    if (!obj.getBoolean("error")) {
                        progressDialog.dismiss();

                        Toast.makeText(CheckoutActivity.this, "Berhasil", Toast.LENGTH_SHORT).show();
                        //pesan.append();
                        JSONArray transaksi = obj.getJSONArray("transaksi");
                        JSONObject totalharga = obj.getJSONObject("totalharga");
                        if (!obj.isNull("transaksi")){
                            //JSONObject jArray = (JSONObject) barang.getJSONObject(0);
                            for (int i = 0;i< transaksi.length();i++){
                                JSONObject jArray = (JSONObject) transaksi.getJSONObject(i);
                                String text = pesan.getText().toString() +"\n" + jArray.getString("namaproduk")+"\t"+jArray.getString("unit")+"\t"+jArray.getString("totalharga");
                                pesan.setText(text);
                                String tothar = totalharga.getString("total");
                                total.setText(tothar);

                                //Log.d("idtransaksi",jArray.getString("idtransaksi"));
                                //inserttransaksi(Integer.parseInt(jArray.getString("idtransaksi")),Integer.parseInt(jArray.getString("iduser")),Integer.parseInt(jArray.getString("total")),String.valueOf(jArray.get("tanggal")),Integer.parseInt(jArray.getString("bayar")));
                            }

                        }

                        //sqlC.syncSQLITEMYSQL(username,password);
                        //getting the user from the response
                        //JSONObject userJson = obj.getJSONObject("message");
                        //Log.d("id",String.valueOf(pos1));


                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(CheckoutActivity.this, "Gagal", Toast.LENGTH_SHORT).show();
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
                params.put("idtransaksi",idtransaksi);
                params.put("action", "getdettransaksi");

                //returing the response
                return requestHandler.sendPostRequest(Config.URL_TRANS, params);
            }
        }

        Ambildetail ambd = new Ambildetail();
        ambd.execute();
    }

    private void Finishtran() {
        //first getting the values
        //final String id = String.valueOf();
        //Log.d("ID", id);
        //Log.d("NAMA", nama1);
        //Log.d("STOK", stok1);
        //Log.d("HARGA", harga1);

        //validating inputs

        //if everything is fine

        class Finishtran extends AsyncTask<Void, Void, String> {

            final ProgressDialog progressDialog = new ProgressDialog(CheckoutActivity.this,
                    R.style.AppTheme_Dark_Dialog);

            @Override
            protected void onPreExecute() {
                //dbcon.execSQL("DELETE FROM "+DBHelper.TABLE_SICYCA);

                progressDialog.setIndeterminate(true);
                progressDialog.setMessage("Finishing...");
                progressDialog.show();
                super.onPreExecute();

            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Log.d("responseupdate",s);


                try {
                    //converting response to json object
                    JSONObject obj = new JSONObject(s);

                    //if no error in response
                    if (!obj.getBoolean("error")) {
                        progressDialog.dismiss();

                        //Toast.makeText(CheckoutActivity.this, "Berhasil", Toast.LENGTH_SHORT).show();
                        //pesan.append();
                        Intent kasir = new Intent(CheckoutActivity.this,DashboardKasirActivity.class);
                        kasir.putExtra("id",id);
                        kasir.putExtra("username",username);
                        kasir.putExtra("password",password);
                        startActivity(kasir);

                        //sqlC.syncSQLITEMYSQL(username,password);
                        //getting the user from the response
                        //JSONObject userJson = obj.getJSONObject("message");
                        //Log.d("id",String.valueOf(pos1));


                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(CheckoutActivity.this, "Gagal", Toast.LENGTH_SHORT).show();
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
                params.put("idtransaksi",idtransaksi);
                params.put("total", String.valueOf(tot));
                params.put("bayar", String.valueOf(bay));
                params.put("action", "finish");

                //returing the response
                return requestHandler.sendPostRequest(Config.URL_TRANS, params);
            }
        }

        Finishtran ft = new Finishtran();
        ft.execute();
    }
}

