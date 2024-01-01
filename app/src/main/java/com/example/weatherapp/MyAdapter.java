package com.example.weatherapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class MyAdapter extends BaseAdapter {
    Context c;
    ArrayList<Movie> data;
    public MyAdapter(Context c, ArrayList<Movie> data) {
        this.c=c;
        this.data=data;
    }
    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Log.d("MyAdapter", "getView called for position: " + i);

        LayoutInflater li = LayoutInflater.from(c);
        View root=  li.inflate(R.layout.item, null,false);

        TextView Rating =  root.findViewById(R.id.rating);
        TextView Title_tv = root.findViewById(R.id.title_tv);
        TextView Overview_tv = root.findViewById(R.id.overview_tv);
        ImageView Image = root.findViewById(R.id.imageview);

        Rating.setText(String.valueOf(data.get(i).getRating()));
        Title_tv.setText(data.get(i).getTitle());
        Overview_tv.setText(data.get(i).getOverview());
        Movie movie = data.get(i);
        Glide.with(c)
                .load(movie.getPoster())
                .into(Image);
        return root;
    }

}
