package fr.trackoe.decheterie.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

import fr.trackoe.decheterie.model.bean.global.Depot;

/**
 * Created by Haocheng on 27/03/2017.
 */

public class DchDepotDB extends MyDb {

    public DchDepotDB(Context ctx) {
        mydb = new DecheterieDatabase(ctx);
    }

    /*
    Insérer un Depot dans la bdd
     */
    public long insertDepot(Depot depot) {
        ContentValues values = new ContentValues();
        values.put(DecheterieDatabase.TableDchDepot.ID, depot.getId());
        values.put(DecheterieDatabase.TableDchDepot.DATEHEURE, depot.getDateHeure());
        values.put(DecheterieDatabase.TableDchDepot.DCH_DECHETERIE_ID, depot.getDecheterieId());
        values.put(DecheterieDatabase.TableDchDepot.DCH_COMPTE_PREPAYE_ID, depot.getComptePrepayeId());
        values.put(DecheterieDatabase.TableDchDepot.QTY_TOTAL_UDD, depot.getQtyTotalUDD());
        values.put(DecheterieDatabase.TableDchDepot.NOM, depot.getNom());
        values.put(DecheterieDatabase.TableDchDepot.STATUT, depot.getStatut());
        values.put(DecheterieDatabase.TableDchDepot.IS_SENT, depot.isSent()? 1 : 0);

        return db.insertOrThrow(DecheterieDatabase.TableDchDepot.TABLE_DCH_DEPOT, null, values);
    }

    /*
    Vider la table
     */

    public void clearDepot() {
        db.execSQL("delete from " + DecheterieDatabase.TableDchDepot.TABLE_DCH_DEPOT);
    }

    public Depot getDepotByIdentifiant(long id) {
        Depot depot;
        String query = "SELECT * FROM " + DecheterieDatabase.TableDchDepot.TABLE_DCH_DEPOT + " WHERE " + DecheterieDatabase.TableDchDepot.ID + "=" + id + ";";
        Cursor cursor = db.rawQuery(query, null);
        depot = cursorToDepot(cursor);
        return depot;
    }

    public Depot getDepotByStatut(int statut) {
        Depot depot;
        String query = "SELECT * FROM " + DecheterieDatabase.TableDchDepot.TABLE_DCH_DEPOT + " WHERE " + DecheterieDatabase.TableDchDepot.STATUT + "=" + statut + ";";
        Cursor cursor = db.rawQuery(query, null);
        depot = cursorToDepot(cursor);
        return depot;
    }

    public void changeDepotStatutByIdentifiant(long id, int statut) {
        String query = "UPDATE " + DecheterieDatabase.TableDchDepot.TABLE_DCH_DEPOT + " SET " + DecheterieDatabase.TableDchDepot.STATUT + "=" + statut
                + " WHERE " + DecheterieDatabase.TableDchDepot.ID + "=" + id;
        db.execSQL(query);
    }

    public void deleteDepotByIdentifiant(long id) {
        db.execSQL("delete from " + DecheterieDatabase.TableDchDepot.TABLE_DCH_DEPOT + " WHERE " + DecheterieDatabase.TableDchDepot.ID + "=" + id);
    }

    public ArrayList<Depot> getAllDepot() {
        ArrayList<Depot> depotList;
        String query = "SELECT * FROM " + DecheterieDatabase.TableDchDepot.TABLE_DCH_DEPOT;
        Cursor cursor = db.rawQuery(query, null);
        depotList = cursorToListeDepot(cursor);
        return depotList;
    }

    public ArrayList<Depot> getAllDepotByDecheterieId(int decheterieId) {
        ArrayList<Depot> depotList;
        String query = "SELECT * FROM " + DecheterieDatabase.TableDchDepot.TABLE_DCH_DEPOT + " WHERE " + DecheterieDatabase.TableDchDepot.DCH_DECHETERIE_ID + "=" + decheterieId + ";";
        Cursor cursor = db.rawQuery(query, null);
        depotList = cursorToListeDepot(cursor);
        return depotList;
    }

    public Depot getDepotByName(String name) {
        Depot d = new Depot();
        String query = "SELECT * FROM " + DecheterieDatabase.TableDchDepot.TABLE_DCH_DEPOT + " WHERE " + DecheterieDatabase.TableDchDepot.NOM + " LIKE " + "'" + name + "'" +  ";";
        Cursor cursor = db.rawQuery(query, null);
        d = cursorToDepot(cursor);
        return d;
    }


    private Depot cursorToDepot(Cursor c){
        if(c.getCount() == 0) {
            return null;
        }
        c.moveToFirst();
        Depot d = new Depot();
        d.setId(c.getLong(DecheterieDatabase.TableDchDepot.NUM_ID));
        d.setDateHeure(c.getString(DecheterieDatabase.TableDchDepot.NUM_DATEHEURE));
        d.setDecheterieId(c.getInt(DecheterieDatabase.TableDchDepot.NUM_DCH_DECHETERIE_ID));
        d.setComptePrepayeId(c.getInt(DecheterieDatabase.TableDchDepot.NUM_DCH_COMPTE_PREPAYE_ID));
        d.setQtyTotalUDD(c.getFloat(DecheterieDatabase.TableDchDepot.NUM_DCH_COMPTE_PREPAYE_ID));
        d.setNom(c.getString(DecheterieDatabase.TableDchDepot.NUM_NOM));
        d.setStatut(c.getInt(DecheterieDatabase.TableDchDepot.NUM_STATUT));
        d.setSent((c.getInt(DecheterieDatabase.TableDchDepot.NUM_IS_SENT) == 1)? true : false);

        c.close();

        return d;
    }

    private ArrayList<Depot> cursorToListeDepot(Cursor c) {
        ArrayList<Depot> depotList = new ArrayList<>();

        if (c.moveToFirst()) {
            do {
                Depot d = new Depot();
                d.setId(c.getLong(DecheterieDatabase.TableDchDepot.NUM_ID));
                d.setDateHeure(c.getString(DecheterieDatabase.TableDchDepot.NUM_DATEHEURE));
                d.setDecheterieId(c.getInt(DecheterieDatabase.TableDchDepot.NUM_DCH_DECHETERIE_ID));
                d.setComptePrepayeId(c.getInt(DecheterieDatabase.TableDchDepot.NUM_DCH_COMPTE_PREPAYE_ID));
                d.setQtyTotalUDD(c.getFloat(DecheterieDatabase.TableDchDepot.NUM_DCH_COMPTE_PREPAYE_ID));
                d.setNom(c.getString(DecheterieDatabase.TableDchDepot.NUM_NOM));
                d.setStatut(c.getInt(DecheterieDatabase.TableDchDepot.NUM_STATUT));
                d.setSent((c.getInt(DecheterieDatabase.TableDchDepot.NUM_IS_SENT) == 1)? true : false);
                depotList.add(d);
            } while (c.moveToNext());

            c.close();
        }
        return depotList;
    }
}

