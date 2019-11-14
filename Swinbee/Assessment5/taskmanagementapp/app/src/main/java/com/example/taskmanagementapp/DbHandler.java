package com.example.taskmanagementapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class DbHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "taskObject_database", TABLE_NAME = "taskObject_table";

    private static final String KEY_ID = "id", KEY_TITLE = "title", KEY_DUE_DATE = "due_date", KEY_DETAILS = "details", KEY_PRIORITY = "priority", KEY_COMPLETION_STATUS = "completion_status";

    public DbHandler(@Nullable Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String create_table_query = "CREATE TABLE " + TABLE_NAME + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_TITLE + " TEXT, " + KEY_DUE_DATE + " TEXT, " + KEY_DETAILS + " TEXT, " + KEY_PRIORITY + " TEXT, " + KEY_COMPLETION_STATUS + " TEXT)";
        sqLiteDatabase.execSQL(create_table_query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public void addTaskObject(TaskObject object){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_TITLE, object.getTitle());
        contentValues.put(KEY_DUE_DATE, object.getDue_date());
        contentValues.put(KEY_DETAILS, object.getDetails());
        contentValues.put(KEY_PRIORITY, object.getPriority());
        contentValues.put(KEY_COMPLETION_STATUS, object.getCompletion_status());

        db.insert(TABLE_NAME, null, contentValues);
        db.close();
    }

    public void updateTaskObject(TaskObject object){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_TITLE, object.getTitle());
        contentValues.put(KEY_DUE_DATE, object.getDue_date());
        contentValues.put(KEY_DETAILS, object.getDetails());
        contentValues.put(KEY_PRIORITY, object.getPriority());
        contentValues.put(KEY_COMPLETION_STATUS, object.getCompletion_status());

        db.update(TABLE_NAME, contentValues, KEY_ID + "=" + object.getId(), null);
        db.close();
    }

    public void deleteTaskObject(TaskObject object){
        SQLiteDatabase db = this.getReadableDatabase();
        db.delete(TABLE_NAME, KEY_ID + "=?", new String[]{String.valueOf(object.getId())});
    }

    public ArrayList<TaskObject> getAllTaskObjects(){
        ArrayList<TaskObject> objectList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_NAME + " ORDER BY " + KEY_DUE_DATE + " ASC";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()){
            do {
                TaskObject object = new TaskObject(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5));
                objectList.add(object);
            } while(cursor.moveToNext());
            cursor.close();
        }
        return objectList;
    }

    public ArrayList<TaskObject> getOverdueTaskObjects(){
        ArrayList<TaskObject> objectList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_NAME + " ORDER BY " + KEY_DUE_DATE + " ASC";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()){
            do{
                TaskObject object = new TaskObject(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5));
                try {
                    Date date = simpleDateFormat.parse(object.getDue_date());
                    if (new Date().after(date)){
                        objectList.add(object);
                    }
                } catch (ParseException e){
                    e.printStackTrace();
                }
            } while (cursor.moveToNext());
            cursor.close();
        }
        return objectList;
    }

    public ArrayList<TaskObject> getAllPending(){
        ArrayList<TaskObject> objectList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_NAME + " ORDER BY " + KEY_DUE_DATE + " ASC";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()){
            do{
                TaskObject object = new TaskObject(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5));
                try {
                    Date date = simpleDateFormat.parse(object.getDue_date());
                    if (new Date().before(date)){
                        objectList.add(object);
                    }
                } catch (ParseException e){
                    e.printStackTrace();
                }
            } while (cursor.moveToNext());
            cursor.close();
        }
        return objectList;
    }
}
