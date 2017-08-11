package fr.trackoe.decheterie.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import fr.trackoe.decheterie.model.bean.global.Unite;

/**
 * Created by Trackoe on 12/04/2017.
 */

public class DchUniteDB extends MyDb {

    public DchUniteDB(Context ctx) {
        mydb = new DecheterieDatabase(ctx);
    }

    /*
    Insérer un Unite dans la bdd
     */
    public long insertUnite(Unite unite) {
        ContentValues values = new ContentValues();
        values.put(DecheterieDatabase.TableDchUnite.ID, unite.getId());
        values.put(DecheterieDatabase.TableDchUnite.NOM, unite.getNom());
        return db.insertOrThrow(DecheterieDatabase.TableDchUnite.TABLE_NAME, null, values);
    }

    public void updateUnite(Unite unite) {
        ContentValues values = new ContentValues();
        values.put(DecheterieDatabase.TableDchUnite.ID, unite.getId());
        values.put(DecheterieDatabase.TableDchUnite.NOM, unite.getNom());
        db.update(DecheterieDatabase.TableDchUnite.TABLE_NAME, values,DecheterieDatabase.TableDchUnite.ID + "=" + unite.getId(),null);
    }

    public Unite getUniteFromID(int id) {
        String query = "SELECT * FROM " + DecheterieDatabase.TableDchUnite.TABLE_NAME + " WHERE " + DecheterieDatabase.TableDchUnite.ID + " = " + id;
        Cursor cursor = db.rawQuery(query, null);
        return cursorToUnite(cursor);
    }

    public Unite cursorToUnite(Cursor c){
        Unite u = new Unite();
        if(c.moveToFirst()) {
            u.setId(c.getInt(DecheterieDatabase.TableDchUnite.NUM_ID));
            u.setNom(c.getString(DecheterieDatabase.TableDchUnite.NUM_NOM));
            return u;
        }
        else{
            return null;
        }
    }

    /*
    Vider la table
     */

    public void clearUnite() {
        db.execSQL("delete from " + DecheterieDatabase.TableDchUnite.TABLE_NAME);
    }

}
