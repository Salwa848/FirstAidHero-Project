package com.example.firstaidhero;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "firstaid_hero.db";
    private static final int DB_VERSION = 1;
    private final Context context;
    private final String DB_PATH;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
        this.DB_PATH = context.getApplicationInfo().dataDir + "/databases/";
    }

    public void createDatabase() {
        boolean dbExists = checkDatabase();
        if (!dbExists) {
            this.getReadableDatabase();
            this.close();
            copyDatabaseFromAssets();
        }
    }

    private boolean checkDatabase() {
        File dbFile = new File(DB_PATH + DB_NAME);
        return dbFile.exists();
    }

    private void copyDatabaseFromAssets() {
        try {
            InputStream input = context.getAssets().open(DB_NAME);
            String outFileName = DB_PATH + DB_NAME;
            OutputStream output = new FileOutputStream(outFileName);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = input.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }
            output.flush();
            output.close();
            input.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // جلب جميع الحالات الرئيسية لعرضها في القائمة
    public List<Emergency> getAllEmergencies() {
        List<Emergency> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM emergencies", null);
        if (cursor.moveToFirst()) {
            do {
                list.add(new Emergency(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3)
                ));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }

    // جلب حالة محددة بواسطة رقمها
    public Emergency getEmergencyById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM emergencies WHERE id = ?", new String[]{String.valueOf(id)});
        Emergency emergency = null;
        if (cursor.moveToFirst()) {
            emergency = new Emergency(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3)
            );
        }
        cursor.close();
        db.close();
        return emergency;
    }

    // جلب خطوات الإسعاف الخاصة بكل حالة
    public List<Step> getStepsForEmergency(int emergencyId) {
        List<Step> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT step_number, instruction FROM steps WHERE emergency_id = ?", new String[]{String.valueOf(emergencyId)});
        if (cursor.moveToFirst()) {
            do {
                list.add(new Step(cursor.getInt(0), cursor.getString(1)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }

    @Override
    public void onCreate(SQLiteDatabase db) { }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) { }
}