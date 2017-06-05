package fr.trackoe.decheterie.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

import fr.trackoe.decheterie.model.bean.global.Prepaiement;

/**
 * Created by Haocheng on 30/05/2017.
 */

public class DchPrepaiementDB extends MyDb {

    public DchPrepaiementDB(Context ctx) {
        mydb = new DecheterieDatabase(ctx);
    }

    /*
    Insérer un Prepaiement dans la bdd
     */
    public long insertPrepaiement(Prepaiement prepaiement) {
        ContentValues values = new ContentValues();

        values.put(DecheterieDatabase.TableDchPrepaiement.ID, prepaiement.getId());
        values.put(DecheterieDatabase.TableDchPrepaiement.DATE, prepaiement.getDate());
        values.put(DecheterieDatabase.TableDchPrepaiement.QTY_POINT_PREPAYE, prepaiement.getQtyPointPrepaye());
        values.put(DecheterieDatabase.TableDchPrepaiement.COUTS_HT, prepaiement.getCoutsHT());
        values.put(DecheterieDatabase.TableDchPrepaiement.COUTS_TVA, prepaiement.getCoutsTVA());
        values.put(DecheterieDatabase.TableDchPrepaiement.COUTS_TTC, prepaiement.getCoutsTTC());
        values.put(DecheterieDatabase.TableDchPrepaiement.ID_MODE_PAIEMENT, prepaiement.getIdModePaiement());
        values.put(DecheterieDatabase.TableDchPrepaiement.ID_COMPTE_PREPAYE, prepaiement.getIdComptePrepaye());


        return db.insertOrThrow(DecheterieDatabase.TableDchPrepaiement.TABLE_NAME, null, values);
    }

    /*
    Vider la table
     */

    public void clearPrepaiement() {
        db.execSQL("delete from " + DecheterieDatabase.TableDchPrepaiement.TABLE_NAME);
    }

    public Prepaiement getPrepaiementByIdentifiant(int id) {
        Prepaiement prepaiement;
        String query = "SELECT * FROM " + DecheterieDatabase.TableDchPrepaiement.TABLE_NAME + " WHERE " + DecheterieDatabase.TableDchPrepaiement.ID + "=" + id + ";";
        Cursor cursor = db.rawQuery(query, null);
        prepaiement = cursorToPrepaiemnt(cursor);
        return prepaiement;
    }



    private Prepaiement cursorToPrepaiemnt(Cursor c){
        if(c.getCount() == 0) {
            return null;
        }
        c.moveToFirst();
        Prepaiement p = new Prepaiement();
        p.setId(c.getInt(DecheterieDatabase.TableDchPrepaiement.NUM_ID));
        p.setDate(c.getString(DecheterieDatabase.TableDchPrepaiement.NUM_DATE));
        p.setQtyPointPrepaye(c.getFloat(DecheterieDatabase.TableDchPrepaiement.NUM_QTY_POINT_PREPAYE));
        p.setCoutsHT(c.getFloat(DecheterieDatabase.TableDchPrepaiement.NUM_COUTS_HT));
        p.setCoutsTVA(c.getFloat(DecheterieDatabase.TableDchPrepaiement.NUM_COUTS_TVA));
        p.setCoutsTTC(c.getFloat(DecheterieDatabase.TableDchPrepaiement.NUM_COUTS_TTC));
        p.setIdModePaiement(c.getInt(DecheterieDatabase.TableDchPrepaiement.NUM_ID_MODE_PAIEMENT));
        p.setIdComptePrepaye(c.getInt(DecheterieDatabase.TableDchPrepaiement.NUM_ID_COMPTE_PREPAYE));

        c.close();

        return p;
    }

    private ArrayList<Prepaiement> cursorToListePrepaiemnt(Cursor c) {
        ArrayList<Prepaiement> prepaiementList = new ArrayList<>();

        if (c.moveToFirst()) {
            do {
                Prepaiement p = new Prepaiement();
                p.setId(c.getInt(DecheterieDatabase.TableDchPrepaiement.NUM_ID));
                p.setDate(c.getString(DecheterieDatabase.TableDchPrepaiement.NUM_DATE));
                p.setQtyPointPrepaye(c.getFloat(DecheterieDatabase.TableDchPrepaiement.NUM_QTY_POINT_PREPAYE));
                p.setCoutsHT(c.getFloat(DecheterieDatabase.TableDchPrepaiement.NUM_COUTS_HT));
                p.setCoutsTVA(c.getFloat(DecheterieDatabase.TableDchPrepaiement.NUM_COUTS_TVA));
                p.setCoutsTTC(c.getFloat(DecheterieDatabase.TableDchPrepaiement.NUM_COUTS_TTC));
                p.setIdModePaiement(c.getInt(DecheterieDatabase.TableDchPrepaiement.NUM_ID_MODE_PAIEMENT));
                p.setIdComptePrepaye(c.getInt(DecheterieDatabase.TableDchPrepaiement.NUM_ID_COMPTE_PREPAYE));
                prepaiementList.add(p);
            } while (c.moveToNext());

            c.close();
        }
        return prepaiementList;
    }
}
