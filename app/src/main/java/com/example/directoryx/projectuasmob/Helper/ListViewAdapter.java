package com.example.directoryx.projectuasmob.Helper;

/**
 * Created by directoryx on 21/12/17.
 */

import android.app.Activity;
import android.content.Context;
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

import com.example.directoryx.projectuasmob.Kasir;
import com.example.directoryx.projectuasmob.R;

import java.util.ArrayList;

public class ListViewAdapter extends ArrayAdapter
{
    private Context context;
    private ArrayList<Item> item;
    private Button tambah,kurang;
    EditText id;
    Item tempValues=null;


    public ListViewAdapter(Context context, int textViewResourceId, ArrayList objects) {
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
    public View getView(int position, View convertView, ViewGroup parent)
    {
        //

        ViewHolder holder=null;
        if (convertView == null)
        {
            LayoutInflater vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = vi.inflate(R.layout.listviewadmin, null);
            id = (EditText) convertView.findViewById(R.id.edbeli);
            //unit = (EditText) convertView.findViewById(R.id.edbeli);

            holder = new ViewHolder();
            holder.id_tv = (TextView) convertView.findViewById(R.id.id_tv);
            holder.namabarang = (TextView) convertView.findViewById(R.id.namabarang);
            holder.hargabarang = (TextView) convertView.findViewById(R.id.hargabarang);
            holder.stokbarang=(TextView)convertView.findViewById(R.id.stokbarang);

            convertView.setTag(holder);

        }
        else {
            tempValues=null;
            tempValues = ( Item ) item.get( position );
            holder = (ViewHolder) convertView.getTag();
        }

        Item individualCar= item.get(position);
        holder.id_tv.setText(individualCar.getId());
        holder.namabarang.setText(individualCar.getNama());
        holder.hargabarang.setText(individualCar.getHarga());
        holder.stokbarang.setText(individualCar.getUnit());



        return convertView;


    }
}

