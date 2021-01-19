package com.example.app_capnhattintuc24h;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import java.util.ArrayList;

public class PlaceholderFragment extends Fragment {
    private static final String ARG_SECTION_NUMBER = "section_number";

    private ArrayList<News> arrayList=new ArrayList<>();
    private PageViewModel pageViewModel;
    static Context mcontext;
    private SavedNews sqliteNews;

    public static PlaceholderFragment newInstance(int index,Context context) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle bundle = new Bundle();
        mcontext=context;
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageViewModel = ViewModelProviders.of(this).get(PageViewModel.class);
        int index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        pageViewModel.setIndex(index);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment, container, false);
        final ListView lstview = root.findViewById(R.id.listview_News);
        pageViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable final String s12) {
                MyTask myTask = new MyTask(lstview,mcontext);
                String url="";
                switch (s12){
                    case "0": {url="https://cdn.24h.com.vn/upload/rss/tintuctrongngay.rss"; break;}
                    case "1": {url="https://cdn.24h.com.vn/upload/rss/bongda.rss"; break;}
                    case "2":{ url="https://cdn.24h.com.vn/upload/rss/taichinhbatdongsan.rss"; break;}
                    case "3":{ url="https://cdn.24h.com.vn/upload/rss/thoitrang.rss"; break;}
                    case "4":{ url="https://cdn.24h.com.vn/upload/rss/anninhhinhsu.rss"; break;}
                    case "5": {url="https://cdn.24h.com.vn/upload/rss/amthuc.rss"; break;}
                    case "6":{ url="https://cdn.24h.com.vn/upload/rss/canhacmtv.rss"; break;}
                    case "7": { url="https://cdn.24h.com.vn/upload/rss/dulich24h.rss";break; }
                }
                myTask.execute(url);
                arrayList=myTask.getArrayList_News1();
                lstview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        String link=arrayList.get(i).getLink();
                        String title=arrayList.get(i).getTitle();
                        String thumbnail=arrayList.get(i).getThumbnail();
                        String pubDate=arrayList.get(i).getPubDate();

                        sqliteNews=new SavedNews(mcontext,"DatabaseHistory",null,1);
                        ArrayList<News> listt=new ArrayList<>();
                        listt=sqliteNews.getAllNews();
                        for(int j=0;j<listt.size();j++){
                            if(listt.get(j).getLink().equals(link)){
                                sqliteNews.deleteNew("'"+link+"'");
                                break;
                            }
                        }
                        sqliteNews.addNew(new News(title,link,thumbnail,pubDate));
                        Intent intent=new Intent(mcontext,Main2Activity.class);
                        intent.putExtra("link",link);
                        intent.putExtra("title",title);
                        intent.putExtra("thumbnail",thumbnail);
                        intent.putExtra("pubDate",pubDate);
                        startActivity(intent);
                    }
                });
            }
        });
        return root;
    }
}
