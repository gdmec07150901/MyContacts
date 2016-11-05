package com.example.acer_.mycontacts2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

/**
 * Created by acer- on 2016/10/26.
 */
public class ContactsTable {

    //数据表名常量
    private final static String TABLENAME = "contactstable";
    //声明数据库对象
    private MyDB db;

    public ContactsTable(Context context) {
        //创建MyDB对象
        db = new MyDB(context);
        if(!db.isTableExits(TABLENAME)){
        String createTableSql = "CREATE TABLE IF NOT EXISTS"+TABLENAME+"(id_DB integer"+
                "primary key AUTOINCREMENT,"+
                User.NAME + "VARCHAR,"+
                User.MOBILE + "VARCHAR,"+
                User.QQ + "VARCHAR,"+
                User.DANWEI + "VARCHAR,"+
                User.ADDRESS + "VARCHAR)";
            db.createTable(createTableSql);
        }
    }
    public boolean addData(User user){
        ContentValues values = new ContentValues();
        values.put(User.NAME,user.getName());
        values.put(User.MOBILE,user.getMobile());
        values.put(User.QQ,user.getQq());
        values.put(User.DANWEI,user.getDanwei());
        values.put(User.ADDRESS,user.getAddress());

        return db.save(TABLENAME,values);

    }
    public User getUserByID(int id){
        Cursor cursor = null;
        User temp = new User();
        try {
           cursor = db.find("select * from "+TABLENAME+"where"+
                   "id_DB=?",new String[]{id+""});
            cursor.moveToNext();
            temp.setId_DB(cursor.getInt(cursor.getColumnIndex("id_DB")));
            temp.setName(cursor.getString(cursor.getColumnIndex(User.NAME)));
            temp.setMobile(cursor.getString(cursor.getColumnIndex(User.MOBILE)));
            temp.setDanwei(cursor.getString(cursor.getColumnIndex(User.DANWEI)));
            temp.setQq(cursor.getString(cursor.getColumnIndex(User.QQ)));
            temp.setAddress(cursor.getString(cursor.getColumnIndex(User.ADDRESS)));
            Log.d("aa",temp.getName());
            return temp;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor!=null){
                cursor.close();
            }
            db.closeConnection();
        }
        return null;
    }
    public boolean updateUser(User user){
       ContentValues values = new ContentValues();
        values.put(User.NAME,user.getName());
        values.put(User.MOBILE,user.getMobile());
        values.put(User.DANWEI,user.getDanwei());
        values.put(User.QQ,user.getQq());
        values.put(User.ADDRESS,user.getAddress());
        return db.update(TABLENAME,values,"id_DB=?",new String[]{user.getId_DB()+""});
    }
}
