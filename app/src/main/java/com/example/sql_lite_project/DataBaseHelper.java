package com.example.sql_lite_project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.sql_lite_project.MyExceptions.InsertException;

import java.util.ArrayList;

public class DataBaseHelper extends SQLiteOpenHelper {
    public final static String DATABASE_NAME = "USER.DB";
    public final static String TABLE_NAME = "USERS";
    public final static String COL_ID = "ID";
    public final static String COL_USERNAME = "USERNAME";
    public final static String COL_PASSWORD = "PASSWORD";

    SQLiteDatabase sqLiteDatabase;

    public DataBaseHelper(@Nullable Context context){
        super(context, DATABASE_NAME,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_TABLE_QUERY = "CREATE TABLE "
                + TABLE_NAME +
                " (ID INTEGER PRIMARY KEY, USERNAME TEXT NOT NULL," +
                " PASSWORD TEXT NOT NULL)";
        sqLiteDatabase.execSQL(CREATE_TABLE_QUERY);
        this.sqLiteDatabase = sqLiteDatabase;
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String UPGRADE_QUERY = "DROP TABLE IF EXISTS " + "TABLE_NAME";
        sqLiteDatabase.execSQL(UPGRADE_QUERY);
        this.onCreate(sqLiteDatabase);
    }

    public boolean insertUser(String userName, String password)/* throws InsertException*/ {
        //String INSERT_USER_QUERY = "INSERT INTO " + TABLE_NAME + " VALUES"...
        /*
        String IS_USERNAME_EXISTS_QUERY = "SELECT EXISTS(SELECT 1 FROM" +TABLE_NAME + " WHERE " +
                COL_USERNAME + " = " + userName + ");";
        Cursor existedUserCursor = sqLiteDatabase.rawQuery(IS_USERNAME_EXISTS_QUERY,null);
        if(existedUserCursor.getCount() > 0){
            throw new InsertException();
        }
        */

        this.sqLiteDatabase = getWritableDatabase();
        String SELECT_ALL_QUERY = "SELECT * FROM " + TABLE_NAME;
        Cursor allUsersCursor = sqLiteDatabase.rawQuery(SELECT_ALL_QUERY,null);
        int id = allUsersCursor.getCount();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_ID,id + 1);
        contentValues.put(COL_USERNAME,userName);
        contentValues.put(COL_PASSWORD,password);

        Long result = sqLiteDatabase.insert(TABLE_NAME,null,contentValues);
        sqLiteDatabase.close();
        if(result == -1){
            return false;
            //throw new InsertException();
        }
        else{
            return true;
        }

    }

    public boolean isUserValid(String userName, String password) {
        this.sqLiteDatabase = getReadableDatabase();
        String getUserFromDBQuery = "SELECT * FROM " + TABLE_NAME + " WHERE USERNAME ='" +
                userName + "'";

        Cursor cursor = sqLiteDatabase.rawQuery(getUserFromDBQuery,null);
        if(cursor.moveToFirst()){
            if(cursor.getString(2).equals(password)){
                sqLiteDatabase.close();
                return true;
            }
            else{
                sqLiteDatabase.close();
                return false;
            }
        }
        else{
            sqLiteDatabase.close();
            return false;
        }
    }
    public ArrayList<String> getAllUsers(){
        this.sqLiteDatabase = getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor cursor = sqLiteDatabase.rawQuery(query,null);
        ArrayList<String> allUsers = new ArrayList<>();
        if(cursor.moveToFirst()){
            do {
                allUsers.add(cursor.getString(1));
            }while(cursor.moveToNext());
        }
        sqLiteDatabase.close();
        return allUsers;
    }
}
