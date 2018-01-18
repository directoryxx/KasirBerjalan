package com.example.directoryx.projectuasmob;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import com.example.directoryx.projectuasmob.*;
import com.example.directoryx.projectuasmob.Helper.Config;
import com.example.directoryx.projectuasmob.Helper.RequestHandler;
import com.example.directoryx.projectuasmob.Helper.SharedPrefManager;
import com.example.directoryx.projectuasmob.Helper.User;

public class MainActivity extends AppCompatActivity {

    private Button btnLogin;
    private EditText txtUsername,txtPassword;
    private String currentDateandTime;
    private int secretcode = 4848;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(Build.VERSION.SDK_INT >= 23){
          if (ContextCompat.checkSelfPermission(MainActivity.this,Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this,new String[] {Manifest.permission.SEND_SMS},secretcode);
          } else {
              //finish();
          }
          if (ContextCompat.checkSelfPermission(MainActivity.this,Manifest.permission.WRITE_CONTACTS) != PackageManager.PERMISSION_GRANTED){
               ActivityCompat.requestPermissions(MainActivity.this,new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE},secretcode);
          } else {

          }
        }
        btnLogin = (Button) findViewById(R.id.btnLogin);
        txtUsername = (EditText) findViewById(R.id.txtUsername);
        txtPassword = (EditText) findViewById(R.id.txtPassword);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        currentDateandTime = sdf.format(new Date());
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userLogin();
            }
        });
    }



    private void userLogin() {
        //first getting the values
        final String username = txtUsername.getText().toString();
        final String password = txtPassword.getText().toString();

        //validating inputs
        if (TextUtils.isEmpty(username)) {
            txtUsername.setError("Tolong Masukkan Username anda !");
            txtUsername.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            txtPassword.setError("Tolong Masukkan Password anda !");
            txtPassword.requestFocus();
            return;
        }

        //if everything is fine

        class UserLogin extends AsyncTask<Void, Void, String> {

            final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this,
                    R.style.AppTheme_Dark_Dialog);

            @Override
            protected void onPreExecute() {
                //dbcon.execSQL("DELETE FROM "+DBHelper.TABLE_SICYCA);

                progressDialog.setIndeterminate(true);
                progressDialog.setMessage("Authenticating...");
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
                        Toast.makeText(getApplicationContext(), "Login Sukses", Toast.LENGTH_SHORT).show();

                        //getting the user from the response
                        JSONObject userJson = obj.getJSONObject("user");

                        //creating a new user object
                        User user = new User(
                                userJson.getString("username"),
                                userJson.getString("password"),
                                userJson.getString("iduser")
                        );

                        SmsManager smsManager = SmsManager.getDefault();
                        smsManager.sendTextMessage(userJson.getString("nohp"), null, "Telah login pada "+currentDateandTime, null, null);
                        //Log.d("Pesan" , userJson.getString("status"));


                        //storing the user in shared preferences
                        SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);
                        //String namamahasiswa = obj.getString("message");
                        //String emailmahasiswa = obj.getString("email");
                        //starting the profile activity
                        if (userJson.getString("status").equals("G")){
                            Intent intentadmindashboard = new Intent(MainActivity.this, DashboardAdminActivity.class);
                            intentadmindashboard.putExtra("id",userJson.getString("iduser"));
                            intentadmindashboard.putExtra("username",userJson.getString("username"));
                            intentadmindashboard.putExtra("password",userJson.getString("password"));



                            //intentdashboard.putExtra("nim1",userJson.getString("nim"));
                            startActivity(intentadmindashboard);
                        } else if (userJson.getString("status").equals("K")){
                            Intent intentkasirdashboard = new Intent(MainActivity.this, DashboardKasirActivity.class);
                            intentkasirdashboard.putExtra("id",userJson.getString("iduser"));
                            intentkasirdashboard.putExtra("username",userJson.getString("username"));
                            intentkasirdashboard.putExtra("password",userJson.getString("password"));
                            //intentdashboard.putExtra("nim1",userJson.getString("nim"));
                            startActivity(intentkasirdashboard);
                        }
                        finish();


                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), "Username atau password salah !", Toast.LENGTH_SHORT).show();
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
                params.put("username", username);
                params.put("password", password);

                //returing the response
                return requestHandler.sendPostRequest(Config.URL_LOGIN, params);
            }
        }

        UserLogin ul = new UserLogin();
        ul.execute();
    }



}
