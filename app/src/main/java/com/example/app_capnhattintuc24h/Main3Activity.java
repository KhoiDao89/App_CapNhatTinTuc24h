package com.example.app_capnhattintuc24h;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;

public class Main3Activity extends AppCompatActivity {
    private SavedNews sqliteNews;
    private ArrayList<News> lstData;
    private ListView listView;
    private CustomAdapter customAdapter;
    private SavedNews sqliteHistory;
    String a1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        sqliteNews=new SavedNews(this,"DatabaseNews",null,1);
        sqliteHistory=new SavedNews(this,"DatabaseHistory",null,1);
        lstData=new ArrayList<>();
        listView=findViewById(R.id.lstView);
        Button btnDel=findViewById(R.id.btnDel);

        Intent intentMain1=getIntent();
        a1="";
        a1=intentMain1.getStringExtra("pb");
        if(Integer.parseInt(a1)==0){
            lstData=sqliteNews.getAllNews();
            customAdapter=new CustomAdapter(Main3Activity.this,R.layout.custom_listitem,lstData);
            listView.setAdapter(customAdapter);
            btnDel.setVisibility(View.INVISIBLE);
        }
        else {
            lstData=sqliteHistory.getAllNews();
            Collections.reverse(lstData);
            customAdapter=new CustomAdapter(Main3Activity.this,R.layout.custom_listitem,lstData);
            listView.setAdapter(customAdapter);
            btnDel.setVisibility(View.VISIBLE);
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(Main3Activity.this,Main2Activity.class);
                intent.putExtra("link",lstData.get(i).getLink());
                intent.putExtra("title",lstData.get(i).getTitle());
                intent.putExtra("thumbnail",lstData.get(i).getThumbnail());
                intent.putExtra("pubDate",lstData.get(i).getPubDate());
                startActivity(intent);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(final AdapterView<?> adapterView, View view, final int position, final long l) {
                AlertDialog.Builder alertDialog=new AlertDialog.Builder(Main3Activity.this);
                alertDialog.setMessage("Bạn có chắc muốn xóa không?");
                alertDialog.setTitle("Thông Báo");
                alertDialog.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String s="'";
                        s+=lstData.get(position).getLink()+"'";
                        lstData=new ArrayList<>();
                        if(Integer.parseInt(a1)==0){
                            sqliteNews.deleteNew(s);
                            lstData=sqliteNews.getAllNews();
                        }
                        else {
                            sqliteHistory.deleteNew(s);
                            lstData=sqliteHistory.getAllNews();
                        }
                        customAdapter=new CustomAdapter(Main3Activity.this,R.layout.custom_listitem,lstData);
                        listView.setAdapter(customAdapter);
                    }
                });
                alertDialog.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //do nothing
                    }
                });
                alertDialog.show();
                return false;
            }
        });

        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sqliteHistory.deleteAll();
                lstData=new ArrayList<>();
                lstData=sqliteHistory.getAllNews();
                customAdapter=new CustomAdapter(Main3Activity.this,R.layout.custom_listitem,lstData);
                listView.setAdapter(customAdapter);
            }
        });
    }
}
