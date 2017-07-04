package com.zet.learnsqlite.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by Zet on 2017/7/2.
 * DBHelper 继承 SQLiteOpenHelper
 * <p>
 * 重写
 * onCreate(SQLiteDatabase db)
 * onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
 * <p>
 * 实现
 * 构造方法
 */

public class DBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "mydata.db"; // 数据库名称
    private static final int version = 1; // 数据库版本

    public DBHelper(Context context){
        super(context, DB_NAME, null, version);
    }

    private ArrayList<String> sqlList = new ArrayList<>();

    @Override
    public void onCreate(SQLiteDatabase db) {
        String tablePeople = " create table people( " +
                " _id integer primary key autoincrement, " +
                " name varchar(20), " +
                " gender integer, " +
                " age integer " +
                " ); ";

        sqlList.add(tablePeople);

        execSql(db);
    }

    private void execSql(SQLiteDatabase db) {
        for (String sql : sqlList) {
            db.execSQL(sql);
        }
        sqlList.clear();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
