package com.example.app_capnhattintuc24h;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.ListView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class MyTask extends AsyncTask<String,Void, ArrayList<News>> {


    ArrayList<News> arrayList_News1=new ArrayList<>();
    CustomAdapter adapter;
    ListView listView_News;
    Context mcontext;

    public ArrayList<News> getArrayList_News1() {
        return arrayList_News1;
    }

    public MyTask(ListView listView_News, Context context) {
        //this.arrayList_News;
        this.listView_News = listView_News;
        this.mcontext= context;
    }

    @Override
    protected ArrayList<News> doInBackground(String... strings) {
        //arrayList_News1=new ArrayList<>();
        try {
            Document document = Jsoup.connect(strings[0]).get();
            Elements elements = document.select("item");
            News news=null;

            for(Element element:elements){
                news=new News();
                news.setTitle(element.select("title").text());
                news.setThumbnail(Jsoup.parse(element.select("description").text()).select("img").attr("src"));
                news.setLink(element.select("link").text());
                news.setPubDate(element.select("pubDate").text());
                arrayList_News1.add(news);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return arrayList_News1;
    }

    @Override
    protected void onPostExecute(ArrayList<News> news) {
        super.onPostExecute(news);
        adapter = new CustomAdapter((Activity) mcontext, R.layout.custom_listitem, arrayList_News1);
        listView_News.setAdapter(adapter);
    }
}
