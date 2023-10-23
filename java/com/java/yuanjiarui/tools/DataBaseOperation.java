package com.java.yuanjiarui.tools;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.java.yuanjiarui.tools.MyDatabaseHelper;

public class DataBaseOperation {
    public static void  insert(Context context,String newsId,String title,String content,String publishData,String images,String publisher,String publishTime,String category){
        MyDatabaseHelper dbHelper = new MyDatabaseHelper(context,"Mydatabase.db",null,1);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
//        String createTableQuery = "CREATE TABLE my_table (newsId TEXT PRIMARY KEY, liked TEXT,title TEXT,content TEXT,publishData TEXT )";
//        db.execSQL(createTableQuery);
       // db.delete("my_table",null,null);
        ContentValues values=new ContentValues();
        values.put("newsId",newsId);
        values.put("title",title);
        values.put("content",content);
        values.put("publishData",publishData);
        values.put("images",images);
        values.put("publisher",publisher);
        values.put("publishDate",publishTime);
        values.put("category",category);
        java.util.Date date = new java.util.Date();
        long datetime = date.getTime();
        values.put("readTime",datetime);
        long rowId=db.replace("my_table_version2",null,values);
    }
    public static void update (Context context,String newsId,String[] update_what,String[] update_to_what){
        MyDatabaseHelper dbHelper = new MyDatabaseHelper(context,"Mydatabase.db",null,1);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values=new ContentValues();
        for(int i=0;i<update_to_what.length;i++){
            values.put(update_what[i],update_to_what[i]);
            Log.d("what",update_what[i]+update_to_what[i]);
        }
        String[] the_newsId={newsId};
        db.update("my_table_version2",values,"newsId=?",the_newsId);
    }
    public static String query(Context context,String newsId,String what){
        MyDatabaseHelper dbHelper = new MyDatabaseHelper(context,"Mydatabase.db",null,1);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String[] projection={what};
        String [] selectionArgs={newsId};
        Cursor cursor=db.query("my_table_version2",projection,"newsId=?",selectionArgs,null,null,null);
        String ans=null;
        if(cursor.moveToFirst()){
            ans=cursor.getString(0);
        }
        else {
            ans=null;
        }
        cursor.close();
        return ans;
    }
    public static void updateReadTime (Context context,String newsId){
        MyDatabaseHelper dbHelper = new MyDatabaseHelper(context,"Mydatabase.db",null,1);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values=new ContentValues();
//        for(int i=0;i<update_to_what.length;i++){
//            values.put(update_what[i],update_to_what[i]);
//            Log.d("what",update_what[i]+update_to_what[i]);
//        }
        java.util.Date date = new java.util.Date();
        long datetime = date.getTime();
        values.put("readTime",datetime);
        String[] the_newsId={newsId};
        db.update("my_table_version2",values,"newsId=?",the_newsId);
    }

}
