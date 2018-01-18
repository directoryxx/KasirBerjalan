package com.example.directoryx.projectuasmob.Helper;

/**
 * Created by directoryx on 23/12/17.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by DirectoryX on 19/10/2017.
 */

public class DBHelper extends SQLiteOpenHelper {
    //informasi tabel users
    public static final String TABLE_USERS = "users";
    public static final String USERS_ID = "id_user";
    public static final String USERS_USERNAME = "username";
    public static final String USERS_PASSWORD = "password";
    public static final String USERS_STATUS = "status";
    public static final String USERS_notelp = "notelp";

    //informasi tabel items
    public static final String TABLE_ITEMS = "items";
    public static final String ITEMS_ID = "_id";
    public static final String ITEMS_NAMA = "namaproduk";
    public static final String ITEMS_STOK = "stok";
    public static final String ITEMS_HARGA = "harga";


    //informasi tabel transaksi
    public static final String TABLE_TRANSAKSI = "transaksi";
    public static final String TRANSAKSI_ID = "_id";
    public static final String TRANSAKSI_USER = "id_user";
    public static final String TRANSAKSI_TOTAL = "total";
    public static final String TRANSAKSI_TANGGAL = "tanggal";
    public static final String TRANSAKSI_BAYAR = "bayar";

    //informasi tabel detail_transaksi
    public static final String TABLE_DETAIL_TRANSAKSI = "detail_transaksi";
    public static final String DETAIL_TRANSAKSI_ID = "_id";
    public static final String DETAIL_TRANSAKSI_TRANSAKSI = "id_transaksi";
    public static final String DETAIL_TRANSAKSI_ITEMS = "id_items";
    public static final String DETAIL_TRANSAKSI_UNIT = "unit";
    public static final String DETAIL_TRANSAKSI_TOTALHARGA = "totalharga";

    //informasi database
    static final String DB_NAME = "KASIRBERJALAN.DB";
    static final int DB_VERSION = 3;
    // perintah membuat tabel
    private static final String CREATE_TABLE_USERS = "CREATE TABLE "+TABLE_USERS+"("+USERS_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+USERS_USERNAME+" TEXT , "+USERS_PASSWORD+" TEXT, "+ USERS_STATUS+" TEXT, " +USERS_notelp+" TEXT "+");";
    private static final String CREATE_TABLE_ITEMS = "CREATE TABLE "+TABLE_ITEMS+"("+ITEMS_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+ITEMS_NAMA+" TEXT , "+ITEMS_STOK+" INTEGER, "+ ITEMS_HARGA+" INTEGER " +");";
    private static final String CREATE_TABLE_TRANSAKSI = "CREATE TABLE "+TABLE_TRANSAKSI+"("+TRANSAKSI_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+TRANSAKSI_USER+" INTEGER , "+TRANSAKSI_TOTAL+" INTEGER, "+ TRANSAKSI_TANGGAL+" TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +TRANSAKSI_BAYAR+" INTEGER, "+ " FOREIGN KEY ("+TRANSAKSI_ID+") REFERENCES "+TABLE_USERS+"("+USERS_ID+"));";
    private static final String CREATE_TABLE_DETAIL_TRANSAKSI = "CREATE TABLE "+TABLE_DETAIL_TRANSAKSI+"("+DETAIL_TRANSAKSI_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+DETAIL_TRANSAKSI_TRANSAKSI+" INTEGER , "+DETAIL_TRANSAKSI_ITEMS+" INTEGER, "+ DETAIL_TRANSAKSI_UNIT+" INTEGER, " +DETAIL_TRANSAKSI_TOTALHARGA+" INTEGER, "+ " FOREIGN KEY ("+DETAIL_TRANSAKSI_TRANSAKSI+") REFERENCES "+TABLE_TRANSAKSI+"("+TRANSAKSI_ID+"),"+ " FOREIGN KEY ("+DETAIL_TRANSAKSI_ITEMS+") REFERENCES "+TABLE_ITEMS+"("+ITEMS_ID+"));";
            //+");";

    public DBHelper(Context context){
        super(context,DB_NAME,null,DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(CREATE_TABLE_USERS);
        db.execSQL(CREATE_TABLE_ITEMS);
        db.execSQL(CREATE_TABLE_TRANSAKSI);
        db.execSQL(CREATE_TABLE_DETAIL_TRANSAKSI);
    }

    public void onUpgrade(SQLiteDatabase db,int oldversion,int newversion){
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_ITEMS);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_TRANSAKSI);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_DETAIL_TRANSAKSI);
        onCreate(db);
    }



}
