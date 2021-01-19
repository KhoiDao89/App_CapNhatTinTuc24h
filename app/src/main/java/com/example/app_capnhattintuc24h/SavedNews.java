package com.example.app_capnhattintuc24h;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class SavedNews extends SQLiteOpenHelper {

    public static final String TableName = "SavedNews";
    public static final String Title = "title";
    public static final String Link = "link";
    public static final String Thumbnail = "thumbnail";
    public static final String Pubdate = "pubDate";

    public SavedNews(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "Create Table if not exists "
                +TableName+" ( "
                +Title+ " Text, "
                +Link+ " Text Primary Key, "
                +Thumbnail+ " Text, "
                +Pubdate + " Text)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("Drop table if exists " + TableName);
        onCreate(db);
    }

    public ArrayList<News> getAllNews(){
        ArrayList<News> list_news = new ArrayList<>();
        String sql = "Select * From "+TableName;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor != null){
            while (cursor.moveToNext()){
                News news = new News(cursor.getString(0), cursor.getString(1),
                        cursor.getString(2), cursor.getString(3));
                list_news.add(news);
            }
        }
        return list_news;
    }
    public void addNew(News news){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("title", news.getTitle());
        values.put("link", news.getLink());
        values.put("thumbnail", news.getThumbnail());
        values.put("pubDate", news.getPubDate());
        db.insert(TableName, null, values);
        db.close();
    }
    public void deleteNew(String link1){
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "Delete From "+TableName+" Where "+Link + " = " + link1;
        db.execSQL(sql);
        db.close();
    }

    public void deleteAll(){
        SQLiteDatabase db=this.getWritableDatabase();
        String sql="Delete From "+TableName;
        db.execSQL(sql);
        db.close();
    }
}
