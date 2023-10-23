package com.java.yuanjiarui.tools;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    public MyDatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        // 创建数据库表
        String createTableQuery = "CREATE TABLE my_table_version2 (newsId TEXT PRIMARY KEY, liked TEXT,title TEXT,content TEXT,publishData TEXT ,stared TEXT,images TEXT ,publisher TEXT,readTime INTEGER ,publishDate TEXT,category TEXT)";
        db.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
//       if(i<i1) {
//           if (i < 2) {
//               String createTableQuery = "CREATE TABLE my_table_version2 (newsId TEXT PRIMARY KEY" +
//                       ", liked TEXT" +
//                       ",title TEXT" +
//                       ",content TEXT" +
//                       ",publishData TEXT )";
//               sqLiteDatabase.execSQL(createTableQuery);
//           }
//           if(i<4){
//               String addColumnQuery1="ALTER TABLE my_table_version2 ADD COLUMN stared TEXT DEFAULT''";
//               String addColumnQuery2="ALTER TABLE my_table_version2 ADD COLUMN images TEXT DEFAULT''";
//               String addColumnQuery3="ALTER TABLE my_table_version2 ADD COLUMN readTime TEXT DEFAULT''";
//               String addColumnQuery4="ALTER TABLE my_table_version2 ADD COLUMN publisher TEXT DEFAULT''";
//               String addColumnQuery5="ALTER TABLE my_table_version2 ADD COLUMN publishDate TEXT DEFAULT''";
//               String addColumnQuery6="ALTER TABLE my_table_version2 ADD COLUMN category TEXT DEFAULT''";
//               sqLiteDatabase.execSQL(addColumnQuery1);
//               sqLiteDatabase.execSQL(addColumnQuery2);
//               sqLiteDatabase.execSQL(addColumnQuery3);
//               sqLiteDatabase.execSQL(addColumnQuery4);
//               sqLiteDatabase.execSQL(addColumnQuery5);
//               sqLiteDatabase.execSQL(addColumnQuery6);
//           }
//       }
    }
    public void clearTable(String tableName) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + tableName);
    }
}