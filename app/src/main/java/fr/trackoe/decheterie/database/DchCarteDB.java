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
        values.put(DecheterieDatabase.TableDchCarte.NUM_CARTE, carte.getNum_carte());
        values.put(DecheterieDatabase.TableDchCarte.NUM_RFID, carte.getNum_RFID());
        values.put(DecheterieDatabase.TableDchCarte.DCH_TYPE_CARTE_ID, carte.getDch_type_carte_id());
        values.put(DecheterieDatabase.TableDchCarte.DCH_ACCOUNT_ID, carte.getDch_account_id());

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
            carte.setNum_carte(c.getString(DecheterieDatabase.TableDchCarte.NUM_NUM_CARTE));
            carte.setNum_RFID(c.getString(DecheterieDatabase.TableDchCarte.NUM_NUM_RFID));
            carte.setDch_type_carte_id(c.getInt(DecheterieDatabase.TableDchCarte.NUM_DCH_TYPE_CARTE_ID));
            carte.setDch_account_id(c.getInt(DecheterieDatabase.TableDchCarte.NUM_DCH_ACCOUNT_ID));
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