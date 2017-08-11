package fr.trackoe.decheterie.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import fr.trackoe.decheterie.model.bean.global.ComptePrepaye;

/**
 * Created by Haocheng on 27/03/2017.
 */

public class DchComptePrepayeDB extends MyDb {
    public DchComptePrepayeDB(Context ctx) {
        mydb = new DecheterieDatabase(ctx);
    }

    /*
    Insérer un ComptePrepaye dans la bdd
     */
    public long insertComptePrepaye(ComptePrepaye comptePrepaye) {
        ContentValues values = new ContentValues();
        values.put(DecheterieDatabase.TableDchComptePrepaye.ID, comptePrepaye.getId());
        values.put(DecheterieDatabase.TableDchComptePrepaye.DCH_USAGER_ID, comptePrepaye.getDchUsagerId());
        values.put(DecheterieDatabase.TableDchComptePrepaye.QTY_POINT, comptePrepaye.getQtyPoint());
        values.put(DecheterieDatabase.TableDchComptePrepaye.NB_DEPOT_RESTANT, comptePrepaye.getNbDepotRestant());

        return db.insertOrThrow(DecheterieDatabase.TableDchComptePrepaye.TABLE_DCH_COMPTE_PREPAYE, null, values);
    }

    public ComptePrepaye getComptePrepayeFromID(long id) {
        String query = "SELECT * FROM " + DecheterieDatabase.TableDchComptePrepaye.TABLE_DCH_COMPTE_PREPAYE + " WHERE " + DecheterieDatabase.TableDchComptePrepaye.ID + " = " + id;
        Cursor cursor = db.rawQuery(query, null);
        return cursorToComptePrepaye(cursor);
    }

    public ComptePrepaye getComptePrepayeFromUsagerId(int usagerId) {
        String query = "SELECT * FROM " + DecheterieDatabase.TableDchComptePrepaye.TABLE_DCH_COMPTE_PREPAYE + " WHERE " + DecheterieDatabase.TableDchComptePrepaye.DCH_USAGER_ID + " = " + usagerId;
        Cursor cursor = db.rawQuery(query, null);
        return cursorToComptePrepaye(cursor);
    }

    public void deleteComptePrepayeByIdentifiant(long id) {
        db.execSQL("delete from " + DecheterieDatabase.TableDchComptePrepaye.TABLE_DCH_COMPTE_PREPAYE + " WHERE " + DecheterieDatabase.TableDchComptePrepaye.ID + "=" + id);
    }

    public void updateComptePrepaye(ComptePrepaye comptePrepaye){
        ContentValues values = new ContentValues();
        values.put(DecheterieDatabase.TableDchComptePrepaye.ID, comptePrepaye.getId());
        values.put(DecheterieDatabase.TableDchComptePrepaye.DCH_USAGER_ID, comptePrepaye.getDchUsagerId());
        values.put(DecheterieDatabase.TableDchComptePrepaye.QTY_POINT, comptePrepaye.getQtyPoint());
        values.put(DecheterieDatabase.TableDchComptePrepaye.NB_DEPOT_RESTANT, comptePrepaye.getNbDepotRestant());

        db.update(DecheterieDatabase.TableDchComptePrepaye.TABLE_DCH_COMPTE_PREPAYE, values,DecheterieDatabase.TableDchComptePrepaye.ID + "=" +  comptePrepaye.getId(),null);
    }


    public ComptePrepaye cursorToComptePrepaye(Cursor c){
        ComptePrepaye cp = new ComptePrepaye();
        try {
            if (c.moveToFirst()) {
                cp.setId(c.getLong(DecheterieDatabase.TableDchComptePrepaye.NUM_ID));
                cp.setDchUsagerId(c.getInt(DecheterieDatabase.TableDchComptePrepaye.NUM_DCH_USAGER_ID));
                cp.setQtyPoint(c.getFloat(DecheterieDatabase.TableDchComptePrepaye.NUM_QTY_POINT));
                cp.setNbDepotRestant(c.getInt(DecheterieDatabase.TableDchComptePrepaye.NUM_NB_DEPOT_RESTANT));
                c.close();
                return cp;
            }
            else{
                c.close();
                return null;
            }
        }catch(Exception e){
            return cp;
        }

    }


    /*
    Vider la table
     */

    public void clearComptePrepaye() {
        db.execSQL("delete from " + DecheterieDatabase.TableDchComptePrepaye.TABLE_DCH_COMPTE_PREPAYE);
    }


}
