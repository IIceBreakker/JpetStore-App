package org.csu.app.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE = "test.db";
    private static final int DATABASE_VERSION = 1;
    private static final String tableName = "user_table";

    public DBHelper(Context context) {
        super(context, DATABASE, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE IF NOT EXISTS " + tableName + " (username VARCHAR(20) PRIMARY KEY, password VARCHAR(20))";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public static String getDATABASE() {
        return DATABASE;
    }

    public static int getDatabaseVersion() {
        return DATABASE_VERSION;
    }

    public static String getTableName() {
        return tableName;
    }
}
