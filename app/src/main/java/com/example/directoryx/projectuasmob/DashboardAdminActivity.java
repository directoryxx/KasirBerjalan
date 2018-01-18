package com.example.directoryx.projectuasmob;

import com.example.directoryx.projectuasmob.Helper.SQLController;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import com.example.directoryx.projectuasmob.Helper.SharedPrefManager;
import com.example.directoryx.projectuasmob.Helper.User;

public class DashboardAdminActivity extends AppCompatActivity {

    Button logout,tambahuser,manajemenstok;
    SQLController sqlC;
    User user;
    String id,username,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_admin);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        id = getIntent().getExtras().getString("id");
        username = getIntent().getExtras().getString("username");

        password = getIntent().getExtras().getString("password");

        logout = (Button) findViewById(R.id.logout);
        manajemenstok = (Button) findViewById(R.id.manajemenstok);

        sqlC = new SQLController(this);
        sqlC.open();
        sqlC.syncSQLITEMYSQL(username,password);


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                SharedPrefManager.getInstance(getApplicationContext()).logout();
            }
        });





        manajemenstok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DashboardAdminActivity.this, ManajemenGudang.class);
                i.putExtra("id",id);
                i.putExtra("username",username);
                i.putExtra("password",password);
                startActivity(i);
            }
        });


    };

}
