package com.example.directoryx.projectuasmob;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.directoryx.projectuasmob.Helper.Config;
import com.example.directoryx.projectuasmob.Helper.FileDownloader;
import com.example.directoryx.projectuasmob.Helper.RequestHandler;
import com.example.directoryx.projectuasmob.Helper.SQLController;
import com.example.directoryx.projectuasmob.Helper.SharedPrefManager;
import com.example.directoryx.projectuasmob.Helper.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class DashboardKasirActivity extends AppCompatActivity {

    Button logout,kasir,generate;
    SQLController sqlC;
    String currentDateandTime,id,username,password;
    private User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        id = getIntent().getExtras().getString("id");
        username = getIntent().getExtras().getString("username");

        password = getIntent().getExtras().getString("password");
        setContentView(R.layout.activity_dashboard_kasir);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        sqlC = new SQLController(this);

        sqlC.open();
        sqlC.syncSQLITEMYSQL(username,password);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        currentDateandTime = sdf.format(new Date());

        logout = (Button) findViewById(R.id.logout);
        kasir = (Button) findViewById(R.id.kasir);
        generate = (Button) findViewById(R.id.genreport);

        generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(Config.URL_REPORT+id));
                startActivity(browserIntent);

                /*

                Uri path = Uri.fromFile(pdfFile);
                Intent pdfIntent = new Intent(Intent.ACTION_V   IEW);
                pdfIntent.setDataAndType(path, "application/pdf");
                pdfIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                try{
                    startActivity(pdfIntent);
                }catch(ActivityNotFoundException e){
                    Toast.makeText(DashboardKasirActivity.this, "No Application available to view PDF", Toast.LENGTH_SHORT).show();
                }
                */
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                SharedPrefManager.getInstance(getApplicationContext()).logout();
            }
        });


        kasir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("iduserkasir",id);
                //Log.d("idtransaksi",idtransaksi);
                Log.d("usernamekasir",username);
                Log.d("passwordkasir",password);
                createtransaction();

            }
        });


    }

    private void createtransaction() {

        //first getting the values

        //if everything is fine

        class createtransaction extends AsyncTask<Void, Void, String> {

            final ProgressDialog progressDialog = new ProgressDialog(DashboardKasirActivity.this,
                    R.style.AppTheme_Dark_Dialog);

            @Override
            protected void onPreExecute() {
                //dbcon.execSQL("DELETE FROM "+DBHelper.TABLE_SICYCA);

                progressDialog.setIndeterminate(true);
                progressDialog.setMessage("Creating...");
                progressDialog.show();
                super.onPreExecute();

            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Log.d("createtransaction",s);



                try {
                    //converting response to json object
                    JSONObject obj = new JSONObject(s);
                    Log.d("response",s);
                    //Log.d("iduser",SharedPrefManager.getKeyId());

                    //if no error in response
                    if (!obj.getBoolean("error")) {
                        progressDialog.dismiss();
                        //Toast.makeText(getApplicationContext(), "Login Sukses", Toast.LENGTH_SHORT).show();

                        //getting the user from the response
                        JSONObject userJson = obj.getJSONObject("transaksi");
                        String idtransaksi = userJson.getString("idtransaksi");
                        //Log.d("transaksiid",idtransaksi);

                        Intent kasir = new Intent(DashboardKasirActivity.this,Kasir.class);

                        kasir.putExtra("id",id);
                        kasir.putExtra("idtransaksi",idtransaksi);
                        kasir.putExtra("username",username);
                        kasir.putExtra("password",password);
                        startActivity(kasir);


                        //Log.d("Pesan" , userJson.getString("status"));




                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), "Error!", Toast.LENGTH_SHORT).show();
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
                params.put("id", id);
                params.put("tanggal", currentDateandTime);
                params.put("action", "transaksi");

                //returing the response
                return requestHandler.sendPostRequest(Config.URL_TRANS, params);
            }
        }

        createtransaction ul = new createtransaction();
        ul.execute();
    }





}
