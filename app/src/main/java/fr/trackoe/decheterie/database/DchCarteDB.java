package fr.trackoe.decheterie.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import fr.trackoe.decheterie.model.bean.global.Carte;

/**
 * Created by Haocheng on 06/04/2017.
 */

public class DchCarteDB extends MyDb {

    public DchCarteDB(Context ctx) {
        mydb = new DecheterieDatabase(ctx);
    }

    /*
    Insérer un Carte dans la bdd
     */
    public long insertCarte(Carte carte) {
        ContentValues values = new ContentValues();
        //values.put(DecheterieDatabase.TableDchCarte.ID, carte.getId());
        values.put(DecheterieDatabase.TableDchCarte.NUM_CARTE, carte.getNumCarte());
        values.put(DecheterieDatabase.TableDchCarte.NUM_RFID, carte.getNumRFID());
        values.put(DecheterieDatabase.TableDchCarte.DCH_TYPE_CARTE_ID, carte.getDchTypeCarteId());
        values.put(DecheterieDatabase.TableDchCarte.DCH_ACCOUNT_ID, carte.getDchAccountId());

        return db.insertOrThrow(DecheterieDatabase.TableDchCarte.TABLE_DCH_CARTE, null, values);
    }

    public Carte getCarteFromID(long id) {
        String query = "SELECT * FROM " + DecheterieDatabase.TableDchCarte.TABLE_DCH_CARTE + " WHERE " + DecheterieDatabase.TableDchCarte.ID + " = " + id;
        Cursor cursor = db.rawQuery(query, null);
        return cursorToCarte(cursor);
    }

    public Carte getCarteByNumCarteAndAccountId(String numCarte, int accountId) {
        String query = "SELECT * FROM " + DecheterieDatabase.TableDchCarte.TABLE_DCH_CARTE + " WHERE " + DecheterieDatabase.TableDchCarte.NUM_CARTE + " LIKE " + "'" + numCarte + "'" + " AND "
                                                                                                       + DecheterieDatabase.TableDchCarte.DCH_ACCOUNT_ID  + "=" + accountId +";";
        Cursor cursor = db.rawQuery(query, null);
        return cursorToCarte(cursor);
    }

    public Carte cursorToCarte(Cursor c){
        Carte carte = new Carte();
        if(c.moveToFirst()) {
            carte.setId(c.getLong(DecheterieDatabase.TableDchCarte.NUM_ID));
            carte.setNumCarte(c.getString(DecheterieDatabase.TableDchCarte.NUM_NUM_CARTE));
            carte.setNumRFID(c.getString(DecheterieDatabase.TableDchCarte.NUM_NUM_RFID));
            carte.setDchTypeCarteId(c.getInt(DecheterieDatabase.TableDchCarte.NUM_DCH_TYPE_CARTE_ID));
            carte.setDchAccountId(c.getInt(DecheterieDatabase.TableDchCarte.NUM_DCH_ACCOUNT_ID));
        }
        return carte;
    }

    /*
    Vider la table
     */

    public void clearCarte() {
        db.execSQL("delete from " + DecheterieDatabase.TableDchCarte.TABLE_DCH_CARTE);
    }

}