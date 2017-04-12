package fr.trackoe.decheterie.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import fr.trackoe.decheterie.model.bean.global.ChoixDecompteTotal;

/**
 * Created by Haochegn on 11/04/2017.
 */

public class DchChoixDecompteTotalDB extends MyDb {

    public DchChoixDecompteTotalDB(Context ctx) {
        mydb = new DecheterieDatabase(ctx);
    }

    /*
    Insérer un ChoixDecompteTotal dans la bdd
     */
    public long insertChoixDecompteTotal(ChoixDecompteTotal choixDecompteTotal) {
        ContentValues values = new ContentValues();
        values.put(DecheterieDatabase.TableDchChoixDecompteTotal.ID, choixDecompteTotal.getId());
        values.put(DecheterieDatabase.TableDchChoixDecompteTotal.NOM, choixDecompteTotal.getNom());
        return db.insertOrThrow(DecheterieDatabase.TableDchChoixDecompteTotal.TABLE_NAME, null, values);
    }

    public ChoixDecompteTotal getChoixDecompteTotalFromID(int id) {
        String query = "SELECT * FROM " + DecheterieDatabase.TableDchChoixDecompteTotal.TABLE_NAME + " WHERE " + DecheterieDatabase.TableDchChoixDecompteTotal.ID + " = " + id;
        Cursor cursor = db.rawQuery(query, null);
        return cursorToChoixDecompteTotal(cursor);
    }

    public ChoixDecompteTotal cursorToChoixDecompteTotal(Cursor c){
        ChoixDecompteTotal choixDecompteTotal = new ChoixDecompteTotal();
        if(c.moveToFirst()) {
            choixDecompteTotal.setId(c.getInt(DecheterieDatabase.TableDchChoixDecompteTotal.NUM_ID));
            choixDecompteTotal.setNom(c.getString(DecheterieDatabase.TableDchChoixDecompteTotal.NUM_NOM));
        }
        return choixDecompteTotal;
    }

    /*
    Vider la table
     */

    public void clearChoixDecompteTotal() {
        db.execSQL("delete from " + DecheterieDatabase.TableDchChoixDecompteTotal.TABLE_NAME);
    }

}
