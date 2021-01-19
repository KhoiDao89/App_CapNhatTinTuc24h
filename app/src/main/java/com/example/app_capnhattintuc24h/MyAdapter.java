package com.example.app_capnhattintuc24h;

import android.app.Activity;
import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MyAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater layoutInflater;
    private ArrayList<News> data;
    private ArrayList<News> dataBackup;

    public MyAdapter(Activity activity, ArrayList<News> data) {
        this.activity = activity;
        this.data = data;
        layoutInflater=(LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public ArrayList<News> getData() {
        return data;
    }

    public void setData(ArrayList<News> data) {
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v=convertView;
        if(v==null)
        {
            v=layoutInflater.inflate(R.layout.custom_listitem,null);
        }
        TextView txt_Title=v.findViewById(R.id.textview_title);
        TextView txt_pubDate=v.findViewById(R.id.textview_pubDate);
        ImageView imgView_thumbnail=v.findViewById(R.id.imageView_Thumbnail);

        txt_Title.setText(Html.fromHtml(data.get(position).getTitle()));
//        txt_pubDate.setText(Utility);
        Picasso.get().load(data.get(position).getThumbnail()).into(imgView_thumbnail);
        return v;
    }

//    @Override
//    public Filter getFilter() {
//        final Filter f = new Filter() {
//            @Override
//            protected FilterResults performFiltering(CharSequence charSequence) {
//                FilterResults fr = new FilterResults();
//                if (dataBackup == null){
//                    dataBackup = new ArrayList<>(data);
//                }
//                if (charSequence == null || charSequence.length() == 0){
//                    fr.values = dataBackup;
//                    fr.count = dataBackup.size();
//                }
//                else {
//                    ArrayList<News> newData = new ArrayList<>();
//                    for (News ts:data){
//                        if (ts.getTitle().toLowerCase().contains(charSequence.toString().toLowerCase())){
//                            newData.add(ts);
//                        }
//                        fr.values = newData;
//                        fr.count = newData.size();
//                    }
//                }
//                return fr;
//            }
//
//            @Override
//            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
//                ArrayList<News> tmp = (ArrayList<News>) filterResults.values;
//                data.clear();
//                for (News ts:tmp){
//                    data.add(ts);
//                    notifyDataSetChanged();
//                }
//            }
//        };
//        return f;
//    }
}
