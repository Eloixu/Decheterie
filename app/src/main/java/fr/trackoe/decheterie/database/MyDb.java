package fr.trackoe.decheterie.database;

import android.database.sqlite.SQLiteDatabase;

import fr.trackoe.decheterie.Logger;

/**
 * Created by Remi on 03/12/2015.
 */
public abstract class MyDb {
    protected Logger logger;

    protected SQLiteDatabase db;
    protected FormulairesDatabase mydb;

    public MyDb() {
    }

    public void open() {
        try {
            db = mydb.getWritableDatabase();
        } catch (Exception e) {}
//        db = SQLiteDatabase.openDatabase("/data/data/fr.trackoe.formulaires/databases/contenants.db", null, SQLiteDatabase.NO_LOCALIZED_COLLATORS | SQLiteDatabase.OPEN_READWRITE);
    }

    public void read() {
        try {
            db = mydb.getReadableDatabase();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public SQLiteDatabase getDb() {
        return db;
    }
}
