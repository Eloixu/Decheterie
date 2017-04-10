package fr.trackoe.decheterie.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

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

    public TypeCarte cursorToTypeCarte(Cursor c){
        TypeCarte t = new TypeCarte();
        if(c.moveToFirst()) {
            t.setId(c.getInt(DecheterieDatabase.TableDchTypeCarte.NUM_ID));
            t.setNom(c.getString(DecheterieDatabase.TableDchTypeCarte.NUM_NOM));
        }
        return t;
    }

    /*
    Vider la table
     */

    public void clearCarteTypeCarte() {
        db.execSQL("delete from " + DecheterieDatabase.TableDchTypeCarte.TABLE_NAME);
    }

}
