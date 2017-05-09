package fr.trackoe.decheterie.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

import fr.trackoe.decheterie.model.bean.global.TypeCarte;

/**
 * Created by Haocheng on 10/04/2017.
 */

public class DchTypeCarteDB extends MyDb {

    public DchTypeCarteDB(Context ctx) {
        mydb = new DecheterieDatabase(ctx);
    }

    /*
    Insérer un TypeCarte dans la bdd
     */
    public long insertTypeCarte(TypeCarte typeCarte) {
        ContentValues values = new ContentValues();
        values.put(DecheterieDatabase.TableDchTypeCarte.ID, typeCarte.getId());
        values.put(DecheterieDatabase.TableDchTypeCarte.NOM, typeCarte.getNom());

        return db.insertOrThrow(DecheterieDatabase.TableDchTypeCarte.TABLE_NAME, null, values);
    }

    public TypeCarte getTypeCarteFromID(int id) {
        String query = "SELECT * FROM " + DecheterieDatabase.TableDchTypeCarte.TABLE_NAME + " WHERE " + DecheterieDatabase.TableDchTypeCarte.ID + " = " + id;
        Cursor cursor = db.rawQuery(query, null);
        return cursorToTypeCarte(cursor);
    }

    public TypeCarte getTypeCarteByName(String nom) {
        String query = "SELECT * FROM " + DecheterieDatabase.TableDchTypeCarte.TABLE_NAME + " WHERE " + DecheterieDatabase.TableDchTypeCarte.NOM + " = " + "'" + nom + "'";
        Cursor cursor = db.rawQuery(query, null);
        return cursorToTypeCarte(cursor);
    }

    public ArrayList<TypeCarte> getAllTypeCarte() {
        ArrayList<TypeCarte> typeCarteList;
        String query = "SELECT * FROM " + DecheterieDatabase.TableDchTypeCarte.TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);
        typeCarteList = cursorToListeTypeCarte(cursor);
        return typeCarteList;
    }

    public TypeCarte cursorToTypeCarte(Cursor c){
        TypeCarte t = new TypeCarte();
        try {
            if (c.moveToFirst()) {
                t.setId(c.getInt(DecheterieDatabase.TableDchTypeCarte.NUM_ID));
                t.setNom(c.getString(DecheterieDatabase.TableDchTypeCarte.NUM_NOM));
            }
            return t;
        }catch(Exception e){
            return t;
        }
    }

    public ArrayList<TypeCarte> cursorToListeTypeCarte(Cursor c){
        ArrayList<TypeCarte> typeCarteList = new ArrayList<>();
        try {
            if (c.moveToFirst()) {
                do {
                    TypeCarte t = new TypeCarte();
                    t.setId(c.getInt(DecheterieDatabase.TableDchTypeCarte.NUM_ID));
                    t.setNom(c.getString(DecheterieDatabase.TableDchTypeCarte.NUM_NOM));
                    typeCarteList.add(t);
                } while (c.moveToNext());

                c.close();
            }
            return typeCarteList;
        }catch(Exception e){
            return typeCarteList;
        }
    }

    /*
    Vider la table
     */

    public void clearTypeCarte() {
        db.execSQL("delete from " + DecheterieDatabase.TableDchTypeCarte.TABLE_NAME);
    }

}
