package com.example.directoryx.projectuasmob;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.directoryx.projectuasmob.Helper.Config;
import com.example.directoryx.projectuasmob.Helper.DBHelper;
import com.example.directoryx.projectuasmob.Helper.Item;
import com.example.directoryx.projectuasmob.Helper.ListViewAdapter;
import com.example.directoryx.projectuasmob.Helper.RequestHandler;
import com.example.directoryx.projectuasmob.Helper.SQLController;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class ManajemenGudang extends AppCompatActivity {

    SQLController sqlC;


    ListViewAdapter adapter;
    ArrayList<Item> mArrayList = new ArrayList<Item>();
    EditText userInput,userInput1,userInput2;
    int pos1;
    String id,username,password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manajemen_gudang);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        id = getIntent().getExtras().getString("id");
        username = getIntent().getExtras().getString("username");

        password = getIntent().getExtras().getString("password");


        sqlC = new SQLController(this);
        //sqlC.syncSQLITEMYSQL();
        sqlC.open();


        Cursor cursor = sqlC.readDataItems();
        String[] from =  new  String[]{
                DBHelper.ITEMS_ID,
                DBHelper.ITEMS_NAMA,
                DBHelper.ITEMS_STOK,
                DBHelper.ITEMS_HARGA
        };
        int[] to = new int[] {
                R.id.id_tv,
                R.id.namabarang,
                R.id.stokbarang,
                R.id.hargabarang
        };
        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            mArrayList.add(new Item(cursor.getString(cursor.getColumnIndex(DBHelper.ITEMS_ID)),cursor.getString(cursor.getColumnIndex(DBHelper.ITEMS_NAMA)),cursor.getString(cursor.getColumnIndex(DBHelper.ITEMS_HARGA)),cursor.getString(cursor.getColumnIndex(DBHelper.ITEMS_STOK)),0)); //add the item
            //CustomListViewValuesArr.add(new Item(cursor.getString(cursor.getColumnIndex(DBHelper.ITEMS_ID)),cursor.getString(cursor.getColumnIndex(DBHelper.ITEMS_NAMA)),cursor.getString(cursor.getColumnIndex(DBHelper.ITEMS_HARGA)),cursor.getString(cursor.getColumnIndex(DBHelper.ITEMS_STOK)))); //add the item
            cursor.moveToNext();
        }

        ListView listview =(ListView) findViewById(R.id.listbarangadmin);

        adapter = new ListViewAdapter(this,R.layout.listviewadmin,mArrayList);
        adapter.notifyDataSetChanged();
        listview.setAdapter(adapter);


        //sqlC.close();



        //listview.setAdapter(adapter);

        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                       int position, long arg3) {

                Item tempValues = ( Item ) mArrayList.get(position);
                pos1 = Integer.valueOf(tempValues.getId());
                //Log.d("get",String.valueOf(pos1));
                //Log.d("get",tempValues.getNama());

                Dialog(position);

                return true;

            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ManajemenGudang.this, TambahBarang.class);
                i.putExtra("id",id);
                i.putExtra("username",username);
                i.putExtra("password",password);
                startActivity(i);
            }
        });



    }

    public void Dialog(final int position)    {
        CharSequence options[] = new CharSequence[] {"Edit", "Hapus"};
        //Toast.makeText(ManajemenGudang.this, te  , Toast.LENGTH_SHORT).show();

        final AlertDialog.Builder dialogAlert = new AlertDialog.Builder(new ContextThemeWrapper(this,R.style.AppTheme_Dark_Dialog));
        //dialogAlert.setTitle("Demo");
        dialogAlert.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 1) {
                    Log.d("Pilihan" , "Hapus");
                    Deletebarangserver();
                } else {
                    Log.d("Pilihan" , "Edit");
                    //finish();
                    tampiledit(position);


                }
            }
        });
        dialogAlert.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //the user clicked on Cancel
            }
        });

        dialogAlert.show();
    }

    public void tampiledit(final int position){
        LayoutInflater li = LayoutInflater.from(this);
        View promptsView = li.inflate(R.layout.dialogeditadmin, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);

        // set prompts.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);

        userInput = (EditText) promptsView
                .findViewById(R.id.editTextDialogUserInput);
        userInput1 = (EditText) promptsView
                .findViewById(R.id.editTextDialogUserInput2);
        userInput2 = (EditText) promptsView
                .findViewById(R.id.editTextDialogUserInput3);
        Item tempValues = ( Item ) mArrayList.get(position);
        userInput.setText(tempValues.getNama());
        userInput1.setText(tempValues.getHarga());
        userInput2.setText(tempValues.getUnit());



        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                Updatebarangserver();
                                // get user input and set it to result
                                // edit text
                                //result.setText(userInput.getText());
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                dialog.cancel();
                            }
                        });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }

    private void Updatebarangserver() {
        //first getting the values
        final String id1 = String.valueOf(pos1);
        final String nama1 = userInput.getText().toString();
        final String stok1 = userInput2.getText().toString();
        final String harga1 = userInput1.getText().toString();
        //Log.d("ID", id);
        //Log.d("NAMA", nama1);
        //Log.d("STOK", stok1);
        //Log.d("HARGA", harga1);

        //validating inputs
        if (TextUtils.isEmpty(nama1)) {
            userInput.setError("Tolong Masukkan nama barang anda !");
            userInput.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(stok1)) {
            userInput1.setError("Tolong masukkan stok barang anda !");
            userInput1.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(harga1)) {
            userInput2.setError("Tolong masukkan harga barang anda !");
            userInput2.requestFocus();
            return;
        }

        //if everything is fine

        class Updatebarangserver extends AsyncTask<Void, Void, String> {

            final ProgressDialog progressDialog = new ProgressDialog(ManajemenGudang.this,
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

                Log.d("testeditresponse",s);

                try {
                    //converting response to json object
                    JSONObject obj = new JSONObject(s);

                    //if no error in response
                    if (!obj.getBoolean("error")) {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), "Berhasil Menyimpan", Toast.LENGTH_SHORT).show();

                        sqlC.syncSQLITEMYSQL(username,password);
                        //getting the user from the response
                        //JSONObject userJson = obj.getJSONObject("message");
                        //Log.d("id",String.valueOf(pos1));

                        //Toast.makeText(ManajemenGudang.this, userJson.toString(), Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(ManajemenGudang.this, DashboardAdminActivity.class);
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
                params.put("id",id1);
                params.put("nama", nama1);
                params.put("stok", stok1);
                params.put("harga", harga1);
                params.put("action", "update");

                //returing the response
                return requestHandler.sendPostRequest(Config.URL_ACTION, params);
            }
        }

        Updatebarangserver ub = new Updatebarangserver();
        ub.execute();
    }

    private void Deletebarangserver() {
        //first getting the values
        final String id1 = String.valueOf(pos1);
        //Log.d("ID", id);
        //Log.d("NAMA", nama1);
        //Log.d("STOK", stok1);
        //Log.d("HARGA", harga1);

        //validating inputs

        //if everything is fine

        class Deletebarangserver extends AsyncTask<Void, Void, String> {

            final ProgressDialog progressDialog = new ProgressDialog(ManajemenGudang.this,
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
                Log.d("testdeletebarang",s);


                try {
                    //converting response to json object
                    JSONObject obj = new JSONObject(s);

                    //if no error in response
                    if (!obj.getBoolean("error")) {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), "Berhasil Menghapus", Toast.LENGTH_SHORT).show();

                        sqlC.syncSQLITEMYSQL(username,password);
                        //getting the user from the response
                        //JSONObject userJson = obj.getJSONObject("message");
                        //Log.d("id",String.valueOf(pos1));

                        //Toast.makeText(ManajemenGudang.this, userJson.toString(), Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(ManajemenGudang.this, DashboardAdminActivity.class);
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
                params.put("id",id1);
                params.put("action", "delete");

                //returing the response
                return requestHandler.sendPostRequest(Config.URL_ACTION, params);
            }
        }

        Deletebarangserver delb = new Deletebarangserver();
        delb.execute();
    }



}
