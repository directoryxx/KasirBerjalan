package com.example.directoryx.projectuasmob.Helper;

/**
 * Created by directoryx on 23/12/17.
 */
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;
import com.example.directoryx.projectuasmob.Helper.User;
import android.widget.Toast;

import com.example.directoryx.projectuasmob.DashboardAdminActivity;
import com.example.directoryx.projectuasmob.DashboardKasirActivity;
import com.example.directoryx.projectuasmob.MainActivity;
import com.example.directoryx.projectuasmob.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.HashMap;

/**
 * Created by DirectoryX on 19/10/2017.
 */

public class SQLController {
    private  DBHelper dbhelper;
    private Context ourcontext;
    private SQLiteDatabase database;
    private User user;
    Cursor saldo;
    private Array[] barangArray;

    public SQLController (Context c){
        ourcontext = c;
    }

    public SQLController open() throws SQLException{
        dbhelper = new DBHelper(ourcontext);
        database = dbhelper.getWritableDatabase();
        return this;
    }

    public void close(){
        dbhelper.close();
    }

    //------------------------- Start ITEMS ---------------------------------------
    //CREATE
    public void insertitem(int id ,String nama,int stok,int harga){
        ContentValues cv = new ContentValues();
        try {
            cv.put(DBHelper.ITEMS_ID,id);
            cv.put(DBHelper.ITEMS_NAMA,nama);
            cv.put(DBHelper.ITEMS_STOK,stok);
            cv.put(DBHelper.ITEMS_HARGA,harga);
            database.insert(DBHelper.TABLE_ITEMS,null,cv);

        } catch (SQLException e) {
            Log.d("Error INSERT : ",e.getMessage());
        }

    }

    //READ
    public Cursor readDataItems(){
        String[] allcolumns = {
                DBHelper.ITEMS_ID,
                DBHelper.ITEMS_NAMA,
                DBHelper.ITEMS_STOK,
                DBHelper.ITEMS_HARGA

        };
        Cursor c = database.query(DBHelper.TABLE_ITEMS,allcolumns,null,null,null,null,null);
        if (c!= null){
            c.moveToFirst();
        }
        return c;
    }

    //UPDATE
    public int updateDataItems(long memberID,String nama,int stok,int harga){
        ContentValues cvUpdate = new ContentValues();
        cvUpdate.put(DBHelper.ITEMS_NAMA,nama);
        cvUpdate.put(DBHelper.ITEMS_STOK,stok);
        cvUpdate.put(DBHelper.ITEMS_HARGA,harga);
        int i = database.update(DBHelper.TABLE_ITEMS,cvUpdate,DBHelper.ITEMS_ID+" = "+memberID,null);
        return  i;
    }

    //DELETE
    public void deleteDataItems(long ID){
        database.delete(DBHelper.TABLE_ITEMS,DBHelper.ITEMS_ID+" = "+ID,null);
    }

    public int count(){
        Cursor c = database.rawQuery("select count(*) from items ",null);
        if (c!= null) {
            c.moveToFirst();
        }
        int count = c.getInt(0);
        return count;
    }



    //------------------------- END ITEMS ---------------------------------------


    //------------------------- Start USERS ---------------------------------------
    //CREATE
    public void insertuser(String username,String password,String status,String notelp){
        ContentValues cv = new ContentValues();
        try {
            cv.put(DBHelper.USERS_USERNAME,username);
            cv.put(DBHelper.USERS_PASSWORD,password);
            cv.put(DBHelper.USERS_STATUS,status);
            cv.put(DBHelper.USERS_notelp,notelp);
            database.insert(DBHelper.TABLE_USERS,null,cv);

        } catch (SQLException e) {
            Log.d("Error INSERT : ",e.getMessage());
        }

    }

    //READ
    public Cursor readDataUsers(){
        String[] allcolumns = {
                DBHelper.USERS_ID,
                DBHelper.USERS_USERNAME,
                DBHelper.USERS_PASSWORD,
                DBHelper.USERS_STATUS,
                DBHelper.USERS_notelp

        };
        Cursor c = database.query(DBHelper.TABLE_USERS,allcolumns,null,null,null,null,null);
        if (c!= null){
            c.moveToFirst();
        }
        return c;
    }

    //UPDATE
    public int updateDataUser(long memberID,String username,String password,String status,String notelp){
        ContentValues cvUpdate = new ContentValues();
        cvUpdate.put(DBHelper.USERS_USERNAME,username);
        cvUpdate.put(DBHelper.USERS_PASSWORD,password);
        cvUpdate.put(DBHelper.USERS_STATUS,status);
        cvUpdate.put(DBHelper.USERS_notelp,notelp);

        int i = database.update(DBHelper.TABLE_USERS,cvUpdate,DBHelper.USERS_ID+" = "+memberID,null);
        return  i;
    }

    //DELETE
    public void deleteDataUser(long ID){
        database.delete(DBHelper.TABLE_USERS,DBHelper.USERS_ID+" = "+ID,null);
    }



    //------------------------- END ITEMS ---------------------------------------


    //------------------------- Start Transaksi ---------------------------------------
    //CREATE
    public void inserttransaksi(int idtransaksi,int iduser,int total,String tanggal,int bayar){
        ContentValues cv = new ContentValues();
        try {
            cv.put(DBHelper.ITEMS_ID,idtransaksi);
            cv.put(DBHelper.TRANSAKSI_USER,iduser);
            cv.put(DBHelper.TRANSAKSI_TOTAL,total);
            cv.put(DBHelper.TRANSAKSI_TANGGAL,tanggal);
            cv.put(DBHelper.TRANSAKSI_BAYAR,bayar);
            database.insert(DBHelper.TABLE_TRANSAKSI,null,cv);

        } catch (SQLException e) {
            Log.d("Error INSERT : ",e.getMessage());
        }

    }

    //READ
    public Cursor readDataTransaksi(){
        String[] allcolumns = {
                DBHelper.TRANSAKSI_ID,
                DBHelper.TRANSAKSI_USER,
                DBHelper.TRANSAKSI_TOTAL,
                DBHelper.TRANSAKSI_TANGGAL,
                DBHelper.TRANSAKSI_BAYAR

        };
        Cursor c = database.query(DBHelper.TABLE_TRANSAKSI,allcolumns,null,null,null,null,null);
        if (c!= null){
            c.moveToFirst();
        }
        return c;
    }

    //UPDATE
    public int updateDataTransaksi(long memberID,int iduser,int total,String tanggal,int bayar){
        ContentValues cvUpdate = new ContentValues();
        cvUpdate.put(DBHelper.TRANSAKSI_USER,iduser);
        cvUpdate.put(DBHelper.TRANSAKSI_TOTAL,total);
        cvUpdate.put(DBHelper.TRANSAKSI_TANGGAL,tanggal);
        cvUpdate.put(DBHelper.TRANSAKSI_BAYAR,bayar);

        int i = database.update(DBHelper.TABLE_TRANSAKSI,cvUpdate,DBHelper.TRANSAKSI_ID+" = "+memberID,null);
        return  i;
    }

    //DELETE
    public void deleteDataTransaksi(long ID){
        database.delete(DBHelper.TABLE_TRANSAKSI,DBHelper.TRANSAKSI_ID+" = "+ID,null);
    }
    //------------------------- End Transaksi ---------------------------------------

    //------------------------- Start Detail_Transaksi ---------------------------------------
    //CREATE
    public void insertdetailtransaksi(int idtransaksi,int iditems,int unit,int totalharga){
        ContentValues cv = new ContentValues();
        try {
            cv.put(DBHelper.DETAIL_TRANSAKSI_TRANSAKSI,idtransaksi);
            cv.put(DBHelper.DETAIL_TRANSAKSI_ITEMS,iditems);
            cv.put(DBHelper.DETAIL_TRANSAKSI_UNIT,unit);
            cv.put(DBHelper.DETAIL_TRANSAKSI_TOTALHARGA,totalharga);
            database.insert(DBHelper.TABLE_DETAIL_TRANSAKSI,null,cv);

        } catch (SQLException e) {
            Log.d("Error INSERT : ",e.getMessage());
        }

    }

    //READ
    public Cursor readDataDetailTransaksi(){
        String[] allcolumns = {
                DBHelper.DETAIL_TRANSAKSI_ID,
                DBHelper.DETAIL_TRANSAKSI_TRANSAKSI,
                DBHelper.DETAIL_TRANSAKSI_ITEMS,
                DBHelper.DETAIL_TRANSAKSI_UNIT,
                DBHelper.DETAIL_TRANSAKSI_TOTALHARGA

        };
        Cursor c = database.query(DBHelper.TABLE_DETAIL_TRANSAKSI,allcolumns,null,null,null,null,null);
        if (c!= null){
            c.moveToFirst();
        }
        return c;
    }

    //UPDATE
    public int updateDataDetailTransaksi(long memberID,int idtransaksi,int iditems,int unit,int totalharga){
        ContentValues cvUpdate = new ContentValues();
        cvUpdate.put(DBHelper.DETAIL_TRANSAKSI_TRANSAKSI,idtransaksi);
        cvUpdate.put(DBHelper.DETAIL_TRANSAKSI_ITEMS,iditems);
        cvUpdate.put(DBHelper.DETAIL_TRANSAKSI_UNIT,unit);
        cvUpdate.put(DBHelper.DETAIL_TRANSAKSI_TOTALHARGA,totalharga);

        int i = database.update(DBHelper.TABLE_DETAIL_TRANSAKSI,cvUpdate,DBHelper.TRANSAKSI_ID+" = "+memberID,null);
        return  i;
    }

    //DELETE
    public void deleteDataDetailTransaksi(long ID){
        database.delete(DBHelper.TABLE_DETAIL_TRANSAKSI,DBHelper.DETAIL_TRANSAKSI_ID+" = "+ID,null);
    }
    //------------------------- End Detail_Transaksi ---------------------------------------




    public void syncSQLITEMYSQL(String username,String password) {
        //first getting the values

        final String user = username;
        final String pass = password;

        //validating inputs

        //if everything is fine

        class syncSQLITEMYSQL extends AsyncTask<Void, Void, String> {


            @Override
            protected void onPreExecute() {
                //dbcon.execSQL("DELETE FROM "+DBHelper.TABLE_SICYCA);

                super.onPreExecute();

            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Log.d("responsesync",s);


                try {
                    //converting response to json object
                    JSONObject obj = new JSONObject(s);

                    //if no error in response
                    if (!obj.getBoolean("error")) {

                        //getting the user from the response
                        JSONArray users = obj.getJSONArray("users");
                        JSONArray barang = obj.getJSONArray("barang");
                        JSONArray transaksi = obj.getJSONArray("transaksi");
                        JSONArray detail_transaksi = obj.getJSONArray("detail_transaksi");
                        //JSONObject user = obj.getJSONObject("user");

                        if (!obj.isNull("users")){
                            //JSONObject jArray = (JSONObject) barang.getJSONObject(0);
                            execSQL("DELETE  FROM USERS; ");
                            execSQL("VACUUM;");
                            execSQL("UPDATE SQLITE_SEQUENCE SET SEQ=0 WHERE NAME='USERS';");

                            for (int i = 0;i< users.length();i++){
                                JSONObject jArray = (JSONObject) users.getJSONObject(i);
                                insertuser(String.valueOf(jArray.get("username")),String.valueOf(jArray.get("password")),String.valueOf(jArray.get("status")),String.valueOf(jArray.get("nohp")));
                            }

                        }

                        if (!obj.isNull("barang")){
                            //JSONObject jArray = (JSONObject) barang.getJSONObject(0);
                            execSQL("DELETE  FROM ITEMS; ");
                            execSQL("VACUUM;");
                            execSQL("UPDATE SQLITE_SEQUENCE SET SEQ=0 WHERE NAME='ITEMS';");
                            for (int i = 0;i< barang.length();i++){
                                JSONObject jArray = (JSONObject) barang.getJSONObject(i);
                                insertitem(Integer.parseInt(jArray.get("iditem").toString()),String.valueOf(jArray.get("namaproduk")),Integer.parseInt(jArray.get("stokproduk").toString()),Integer.parseInt(jArray.get("harga").toString()));
                            }

                        }


                        if (!obj.isNull("transaksi")){
                            //JSONObject jArray = (JSONObject) barang.getJSONObject(0);
                            execSQL("DELETE  FROM TRANSAKSI; ");
                            execSQL("VACUUM;");
                            execSQL("UPDATE SQLITE_SEQUENCE SET SEQ=0 WHERE NAME='TRANSAKSI';");
                            for (int i = 0;i< transaksi.length();i++){
                                JSONObject jArray = (JSONObject) transaksi.getJSONObject(i);

                                //Log.d("idtransaksi",jArray.getString("idtransaksi"));
                                inserttransaksi(Integer.parseInt(jArray.getString("idtransaksi")),Integer.parseInt(jArray.getString("iduser")),Integer.parseInt(jArray.getString("total")),String.valueOf(jArray.get("tanggal")),Integer.parseInt(jArray.getString("bayar")));
                            }

                        }


                        if (!obj.isNull("detail_transaksi")){
                            //JSONObject jArray = (JSONObject) barang.getJSONObject(0);
                            execSQL("DELETE  FROM DETAIL_TRANSAKSI; ");
                            execSQL("VACUUM;");
                            execSQL("UPDATE SQLITE_SEQUENCE SET SEQ=0 WHERE NAME='DETAIL_TRANSAKSI';");
                            for (int i = 0;i< detail_transaksi.length();i++){
                                JSONObject jArray = (JSONObject) detail_transaksi.getJSONObject(i);
                                insertdetailtransaksi(Integer.parseInt(jArray.get("idtransaksi").toString()),Integer.parseInt(jArray.get("iditem").toString()),Integer.parseInt(jArray.get("unit").toString()),Integer.parseInt(jArray.get("totalharga").toString()));
                                Log.d("Proses : ","DETAIL_TRANSAKSI");
                            }

                        }



                        //creating a new user object
                        /*
                        User user = new User(
                                userJson.getString("username"),
                                userJson.getString("password")
                        );
                        */

                        //Log.d("Pesan" , userJson.getString("status"));


                        //storing the user in shared preferences

                    } else {

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
                params.put("username", user);
                params.put("password", pass);

                //returing the response
                return requestHandler.sendPostRequest(Config.URL_SYNC, params);
            }
        }

        syncSQLITEMYSQL ul = new syncSQLITEMYSQL();
        ul.execute();
    }



    public void execSQL(String query){
        database.execSQL(query);
    }
}
