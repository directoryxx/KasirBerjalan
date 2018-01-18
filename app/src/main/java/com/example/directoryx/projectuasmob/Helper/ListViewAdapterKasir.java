package com.example.directoryx.projectuasmob.Helper;

/**
 * Created by directoryx on 21/12/17.
 */

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.directoryx.projectuasmob.DashboardAdminActivity;
import com.example.directoryx.projectuasmob.Kasir;
import com.example.directoryx.projectuasmob.ManajemenGudang;
import com.example.directoryx.projectuasmob.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class ListViewAdapterKasir extends ArrayAdapter
{
    private Context context;
    private ArrayList<Item> item;
    private Button tambah,kurang;
    EditText id;
    SQLController sqlC;
    int pos1,uni1;
    String iditem,idtransaksi,unit;



    public ListViewAdapterKasir(Context context, int textViewResourceId, ArrayList objects,Intent intent) {
        super(context,textViewResourceId, objects);

        this.context= context;
        this.item = objects;

    }

    private class ViewHolder
    {
        TextView id_tv;
        TextView namabarang;
        TextView hargabarang;
        TextView stokbarang;
        EditText unit;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        Intent intent = ((Activity) context).getIntent();
        idtransaksi = intent.getExtras().getString("idtransaksi");

        sqlC = new SQLController(context);
        sqlC.open();
        //id = getIntent().getExtras().getString("id");
        //username = getIntent().getExtras().getString("username");

        //password = getIntent().getExtras().getString("password");

        ViewHolder holder=null;
        if (convertView == null)
        {

            LayoutInflater vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = vi.inflate(R.layout.listviewkasir, null);
            tambah = (Button) convertView.findViewById(R.id.tambah);
            kurang = (Button) convertView.findViewById(R.id.kurang);
            id = (EditText) convertView.findViewById(R.id.edbeli);
            //unit = (EditText) convertView.findViewById(R.id.edbeli);

            holder = new ViewHolder();
            holder.id_tv = (TextView) convertView.findViewById(R.id.id_tv);
            holder.namabarang = (TextView) convertView.findViewById(R.id.namabarang);
            holder.hargabarang = (TextView) convertView.findViewById(R.id.hargabarang);
            holder.stokbarang=(TextView)convertView.findViewById(R.id.stokbarang);
            Item tempValues = ( Item ) item.get(position);
            pos1 = Integer.valueOf(tempValues.getId());
            iditem = String.valueOf(pos1);


            convertView.setTag(holder);

        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        Item individualCar= item.get(position);
        holder.id_tv.setText(individualCar.getId());
        holder.namabarang.setText(individualCar.getNama());
        holder.hargabarang.setText(individualCar.getHarga());
        holder.stokbarang.setText(individualCar.getUnit());



        final ViewHolder finalHolder = holder;
        final View finalConvertView = convertView;
        finalHolder.unit=(EditText) convertView.findViewById(R.id.edbeli);
        tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                 uni1 = Integer.parseInt(finalHolder.unit.getText().toString());
                 if (uni1 < Integer.parseInt(finalHolder.stokbarang.getText().toString())){
                     uni1 += 1;
                     Item tempValues = ( Item ) item.get(position);
                     pos1 = Integer.valueOf(tempValues.getId());
                     iditem = String.valueOf(pos1);
                     Toast.makeText(context, iditem, Toast.LENGTH_SHORT).show();
                     finalHolder.unit.setText(String.valueOf(uni1));
                     unit = String.valueOf(uni1);
                     Updatedetailtransaksi();

                     //bayar.add(new Bayar())

                 }


                //Log.d("Test",String.valueOf(uni1));                                                       
            }
        });

        kurang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int uni1 = Integer.parseInt(finalHolder.unit.getText().toString());
                if (uni1 > 0){
                    uni1 -= 1;
                    finalHolder.unit.setText(String.valueOf(uni1));
                    unit = String.valueOf(uni1);
                    Updatedetailtransaksi();
                }
            }
        });


        return convertView;


    }

    private void Updatedetailtransaksi() {
        //first getting the values
        //final String id = String.valueOf();
        //Log.d("ID", id);
        //Log.d("NAMA", nama1);
        //Log.d("STOK", stok1);
        //Log.d("HARGA", harga1);

        //validating inputs

        //if everything is fine

        class Updatedetailtransaksi extends AsyncTask<Void, Void, String> {

            final ProgressDialog progressDialog = new ProgressDialog(context,
                    R.style.AppTheme_Dark_Dialog);

            @Override
            protected void onPreExecute() {
                //dbcon.execSQL("DELETE FROM "+DBHelper.TABLE_SICYCA);

                //progressDialog.setIndeterminate(true);
                //progressDialog.setMessage("Menyimpan...");
                //progressDialog.show();
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
                        //progressDialog.dismiss();
                        //Toast.makeText(getContext(), "Berhasil Menghapus", Toast.LENGTH_SHORT).show();

                        //sqlC.syncSQLITEMYSQL(username,password);
                        //getting the user from the response
                        //JSONObject userJson = obj.getJSONObject("message");
                        Log.d("iditemkasir",iditem);
                        Log.d("unitkasir",String.valueOf(unit));


                    } else {
                        //progressDialog.dismiss();
                        Toast.makeText(context, "Gagal Menyimpan", Toast.LENGTH_SHORT).show();
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
                params.put("iditem",iditem);
                params.put("idtransaksi",idtransaksi);
                params.put("unit",unit);
                params.put("action", "dettransaksi");

                //returing the response
                return requestHandler.sendPostRequest(Config.URL_TRANS, params);
            }
        }

        Updatedetailtransaksi delb = new Updatedetailtransaksi();
        delb.execute();
    }

}

