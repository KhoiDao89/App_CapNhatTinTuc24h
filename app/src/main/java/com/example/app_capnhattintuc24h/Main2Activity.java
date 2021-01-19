package com.example.app_capnhattintuc24h;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity {
    ArrayList<News> arrayList;
    SavedNews sqliteNews;
    WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        webView=findViewById(R.id.web_view);
        Intent intent=getIntent();
        final String link=intent.getStringExtra("link");
        final String title=intent.getStringExtra("title");
        final String thumbnail=intent.getStringExtra("thumbnail");
        final String pubDate=intent.getStringExtra("pubDate");
        //webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(link);
        //webView.setWebViewClient(new WebViewClient());

        sqliteNews=new SavedNews(this,"DatabaseNews",null,1);
        Button btnBack=findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        Button btnSave=findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                arrayList=new ArrayList<>();
                arrayList=sqliteNews.getAllNews();
                int k=0;
                for(int i=0;i<arrayList.size();i++){
                    if(arrayList.get(i).getLink().equals(link)){
                        k=1;
                    }
                }
                if(k==1){
                    Toast.makeText(Main2Activity.this,"Tin đã lưu từ trước",Toast.LENGTH_SHORT).show();
                }
                else {
                    sqliteNews.addNew(new News(title,link,thumbnail,pubDate));
                    Toast.makeText(Main2Activity.this,"Lưu thành công",Toast.LENGTH_SHORT).show();
                }

            }
        });

        Button btnMain=findViewById(R.id.btnMain);
        btnMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1=new Intent(Main2Activity.this,MainActivity.class);
                startActivity(intent1);
            }
        });
    }
}
