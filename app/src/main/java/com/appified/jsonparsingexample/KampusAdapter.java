package com.appified.jsonparsingexample;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.util.List;

/**
 * Created by PP on 6/18/2015.
 */
public class KampusAdapter extends BaseAdapter {
    Context context;
    List<Kampus> listData;
    private static KampusAdapter mInstance;

    public void onCreate() {
        mInstance = this;
    }

    public KampusAdapter(Context context, List<Kampus> listData) {
        this.context = context;
        this.listData = listData;
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    class ViewHolder {
        public TextView textViewCityName, txtVienama_kampus;
        private ImageView logo;
        ListView list;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.city_item, null);
            viewHolder = new ViewHolder();
            //holder = new ImageHolder();
            viewHolder.textViewCityName = (TextView) view.findViewById(R.id.txtViewCityName);
            viewHolder.txtVienama_kampus = (TextView) view.findViewById(R.id.txtVienama_kampus);
            viewHolder.logo = (ImageView) view.findViewById(R.id.logo);
            viewHolder.list = (ListView) view.findViewById(R.id.listview);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
            //holder = (ImageHolder)view.getTag();
        }
        //if(imageLoader==null);
        Kampus kampus = listData.get(position);
        String nama_kampus = kampus.getState();
        String cityName = kampus.getName();
        //String logo_kampus = city.getLogo();
        //Log.d("isine ", logo_kampus);


        //set Logo menggunakan library picasso lebih ringan untuk memory dg sistem cache
        if (!listData.get(position).getLogo().equals("")) { //mengecek apakah url logo ada atau tidak
            Picasso.with(context)
                    .load(listData.get(position).getLogo())
                    .into(viewHolder.logo);
        }else{
            viewHolder.logo.setImageResource(R.drawable.ic_star_black);
        }
        viewHolder.textViewCityName.setText(cityName);
        viewHolder.txtVienama_kampus.setText(nama_kampus);


        return view;

    }
}
