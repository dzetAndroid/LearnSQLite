package com.zet.learnsqlite.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import com.zet.learnsqlite.bean.People;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zet on 2017/7/2.
 * people dao
 * private Long _id;
 * private String name;
 * private int gender;
 * private int age;
 */

public class PeopleDao {
    private static final String table = "people";
    private static final String column_id = "_id";
    private static final String column_name = "name";
    private static final String column_gender = "gender";
    private static final String column_age = "age";

    private static PeopleDao PEOPEL_DAO = null;
    private DBHelper mDBHelper;
    private SQLiteDatabase db;
    private static final String TAG = "PeopleDao";

    private PeopleDao(Context context) {
        mDBHelper = new DBHelper(context);
        db = mDBHelper.getWritableDatabase();
    }

    public static PeopleDao with(Context context) {
        if (PEOPEL_DAO == null) {
            PEOPEL_DAO = new PeopleDao(context);
        }
        return PEOPEL_DAO;
    }

    public long insert(People people) {
        long l = db.insert(table, null, getContentValues(people));
        return l;
    }

    public int delete(long _id) {
        String whereClause = " _id = ? ";
        String[] whereArgs = new String[]{String.valueOf(_id)};
        int i = db.delete(table, whereClause, whereArgs);
        return i;
    }

    public int deleteAll() {
        int i = db.delete(table, null, null);
        return i;
    }

    public int update(People people) {
        long _id = people.get_id();
        String whereClause = " _id = ? ";
        String[] whereArgs = new String[]{String.valueOf(_id)};
        int i = db.update(table, getContentValues(people), whereClause, whereArgs);
        return i;
    }

    public List<People> queryAll() {
        List<People> peopleList = new ArrayList<>();
        Cursor cursor = db.query(table, null, null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            long _id = cursor.getLong(cursor.getColumnIndex(column_id));
            String name = cursor.getString(cursor.getColumnIndex(column_name));
            int gender = cursor.getInt(cursor.getColumnIndex(column_gender));
            int age = cursor.getInt(cursor.getColumnIndex(column_age));
            People people = People.create(_id, name, gender, age);
//            Log.e(TAG, "queryAll: " + people.toString() );
            peopleList.add(people);
        }
        cursor.close();

        return peopleList;
    }

    /**
     * 根据实体类获取ContentValues
     *
     * @param people people 实体类
     * @return 转换后的content values
     */
    @NonNull
    private ContentValues getContentValues(People people) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(column_id, people.get_id());
        contentValues.put(column_name, people.getName());
        contentValues.put(column_gender, people.getGender());
        contentValues.put(column_age, people.getAge());
        return contentValues;
    }

    /**
     * 销毁对象，释放资源
     */
    public void destroy() {
        if (db != null) {
            db.close();
        }
        if (db != null) {
            mDBHelper = null;
        }
        if (PEOPEL_DAO != null) {
            PEOPEL_DAO = null;
        }
    }

}
