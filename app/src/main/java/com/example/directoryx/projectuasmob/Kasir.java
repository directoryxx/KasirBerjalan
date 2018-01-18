package com.example.directoryx.projectuasmob;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.directoryx.projectuasmob.Helper.DBHelper;
import com.example.directoryx.projectuasmob.Helper.Item;
import com.example.directoryx.projectuasmob.Helper.ListViewAdapterKasir;
import com.example.directoryx.projectuasmob.Helper.SQLController;

import java.util.ArrayList;

public class Kasir extends AppCompatActivity {


    ListViewAdapterKasir adapter;

    Button btnCheckout;
    SQLController sqlC;
    EditText unitbarang;
    Button tambah,kurang;
    String currentDateandTime,id2,username,password,idtransaksi;
    ArrayList<Item> mArrayList = new ArrayList<Item>();
    ArrayList<Item> ArrayBayar = new ArrayList<Item>();





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kasir);
        id2 = getIntent().getExtras().getString("id");
        username = getIntent().getExtras().getString("username");

        password = getIntent().getExtras().getString("password");
        idtransaksi = getIntent().getExtras().getString("idtransaksi");

        sqlC = new SQLController(this);
        sqlC.open();

        unitbarang = (EditText) findViewById(R.id.edbeli);
        tambah = (Button) findViewById(R.id.tambah);
        kurang = (Button) findViewById(R.id.kurang);



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
            cursor.moveToNext();
        }

        btnCheckout = (Button) findViewById(R.id.chckot);

        ListView listview =(ListView) findViewById(R.id.listbarangkasir);
        adapter = new ListViewAdapterKasir(this,R.layout.listviewkasir,mArrayList,getIntent());
        adapter.notifyDataSetChanged();
        listview.setAdapter(adapter);
        LayoutInflater vi = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View convertView = vi.inflate(R.layout.listviewkasir, null);
        tambah = convertView.findViewById(R.id.tambah);




        sqlC.close();




        btnCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater li = LayoutInflater.from(getApplicationContext());
                View promptsView = li.inflate(R.layout.listviewkasir, null);
                sqlC.open();
                int jumlahbarang = sqlC.count();
                EditText id = (EditText) promptsView.findViewById(R.id.edbeli);
                for (int i =1 ; i <= jumlahbarang ; i++){
                    //ArrayBayar.add(new Bayar())
                }
                Intent man = new Intent(Kasir.this,CheckoutActivity.class);
                man.putExtra("idtransaksi",idtransaksi);
                man.putExtra("id",id2);
                man.putExtra("username",username);
                man.putExtra("password",password);
                startActivity(man);
            }
        });



    }


}
