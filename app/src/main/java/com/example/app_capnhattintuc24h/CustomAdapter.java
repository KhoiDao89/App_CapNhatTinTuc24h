package com.example.app_capnhattintuc24h;

import android.app.Activity;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter<News> {
    Activity context;
    int resource;
    ArrayList<News> objects;

    public void setObjects(ArrayList<News> objects) {
        this.objects = objects;
    }

    public CustomAdapter(@NonNull Activity context, int resource, @NonNull ArrayList<News> objects) {
        super(context, resource,objects);
        this.context=context;
        this.resource=resource;
        this.objects=objects;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        LayoutInflater inflater=this.context.getLayoutInflater();
        convertView=inflater.inflate(this.resource,null);
        ImageView imageView_Thumbnail=convertView.findViewById(R.id.imageView_Thumbnail);
        TextView txtV_Title=convertView.findViewById(R.id.textview_title);
        TextView txtV_PubDate=convertView.findViewById(R.id.textview_pubDate);
        News news=this.objects.get(position);
        txtV_Title.setText(Html.fromHtml(news.getTitle()));
        txtV_PubDate.setText(Html.fromHtml(news.getPubDate()));
        Picasso.get().load(news.getThumbnail()).into(imageView_Thumbnail);
        return convertView;
    }
}
