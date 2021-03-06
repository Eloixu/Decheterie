package fr.trackoe.decheterie.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

import fr.trackoe.decheterie.model.bean.global.ApportFlux;

/**
 * Created by Haocheng on 27/03/2017.
 */

public class DchApportFluxDB extends MyDb {

    public DchApportFluxDB(Context ctx) {
        mydb = new DecheterieDatabase(ctx);
    }

    /*
    Insérer un ApportFlux dans la bdd
     */
    public long insertApportFlux(ApportFlux apportFlux) {
        ContentValues values = new ContentValues();

        values.put(DecheterieDatabase.TableDchApportFlux.DCH_DEPOT_ID, apportFlux.getDepotId());
        values.put(DecheterieDatabase.TableDchApportFlux.DCH_FLUX_ID, apportFlux.getFluxId());
        values.put(DecheterieDatabase.TableDchApportFlux.QTY_COMPTAGE, apportFlux.getQtyComptage() == -1 ? null : apportFlux.getQtyComptage());
        values.put(DecheterieDatabase.TableDchApportFlux.QTY_UDD, apportFlux.getQtyUDD() == -1 ? null : apportFlux.getQtyUDD());
        values.put(DecheterieDatabase.TableDchApportFlux.IS_SENT, apportFlux.isSent()? 1 : 0);

        return db.insertOrThrow(DecheterieDatabase.TableDchApportFlux.TABLE_DCH_APPORT_FLUX, null, values);
    }

    /*
    Vider la table
     */

    public void clearApportFlux() {
        db.execSQL("delete from " + DecheterieDatabase.TableDchApportFlux.TABLE_DCH_APPORT_FLUX);
    }

    public ApportFlux getApportFluxByDepotIdAndFluxId(long depotId, int fluxId) {
        ApportFlux apportFlux;
        String query = "SELECT * FROM " + DecheterieDatabase.TableDchApportFlux.TABLE_DCH_APPORT_FLUX + " WHERE " + DecheterieDatabase.TableDchApportFlux.DCH_DEPOT_ID + "=" + depotId + " AND "
                                                                                                                  + DecheterieDatabase.TableDchApportFlux.DCH_FLUX_ID  + "=" + fluxId +";";
        Cursor cursor = db.rawQuery(query, null);
        apportFlux = cursorToApportFlux(cursor);
        return apportFlux;
    }

    public ArrayList<ApportFlux> getListeApportFluxByDepotId(long depotId) {
        ArrayList<ApportFlux> apportFluxList;
        String query = "SELECT * FROM " + DecheterieDatabase.TableDchApportFlux.TABLE_DCH_APPORT_FLUX + " WHERE " + DecheterieDatabase.TableDchApportFlux.DCH_DEPOT_ID + "=" + depotId + ";";
        Cursor cursor = db.rawQuery(query, null);
        apportFluxList = cursorToListeApportFlux(cursor);
        return apportFluxList;
    }

    public void updateApportFlux(ApportFlux apportFlux) {
        deleteApportFluxByDepotIdAndFluxId(apportFlux.getDepotId(),apportFlux.getFluxId());
        insertApportFlux(apportFlux);
    }

    public void deleteApportFluxByDepotIdAndFluxId(long depotId, int fluxId) {
        String query = "DELETE FROM " + DecheterieDatabase.TableDchApportFlux.TABLE_DCH_APPORT_FLUX + " WHERE " + DecheterieDatabase.TableDchApportFlux.DCH_DEPOT_ID + "=" + depotId + " AND "
                + DecheterieDatabase.TableDchApportFlux.DCH_FLUX_ID  + "=" + fluxId +";";
        db.execSQL(query);
    }



    private ApportFlux cursorToApportFlux(Cursor c){
        if(c.getCount() == 0) {
            return null;
        }
        c.moveToFirst();
        ApportFlux a = new ApportFlux();
        a.setDepotId(c.getInt(DecheterieDatabase.TableDchApportFlux.NUM_DCH_DEPOT_ID));
        a.setFluxId(c.getInt(DecheterieDatabase.TableDchApportFlux.NUM_DCH_FLUX_ID));
        a.setQtyComptage(c.getFloat(DecheterieDatabase.TableDchApportFlux.NUM_QTY_COMPTAGE));
        a.setQtyUDD(c.getFloat(DecheterieDatabase.TableDchApportFlux.NUM_QTY_UDD));
        a.setSent((c.getInt(DecheterieDatabase.TableDchApportFlux.NUM_IS_SENT) == 1)? true : false);

        c.close();

        return a;
    }

    private ArrayList<ApportFlux> cursorToListeApportFlux(Cursor c) {
        ArrayList<ApportFlux> apportFluxList = new ArrayList<>();

        if (c.moveToFirst()) {
            do {
                ApportFlux af = new ApportFlux();
                af.setDepotId(c.getLong(DecheterieDatabase.TableDchApportFlux.NUM_DCH_DEPOT_ID));
                af.setFluxId(c.getInt(DecheterieDatabase.TableDchApportFlux.NUM_DCH_FLUX_ID));
                af.setQtyComptage(c.getFloat(DecheterieDatabase.TableDchApportFlux.NUM_QTY_COMPTAGE));
                af.setQtyUDD(c.getFloat(DecheterieDatabase.TableDchApportFlux.NUM_QTY_UDD));
                af.setSent(c.getInt(DecheterieDatabase.TableDchApportFlux.NUM_IS_SENT) == 1);
                apportFluxList.add(af);
            } while (c.moveToNext());

            c.close();
        }
        return apportFluxList;
    }

}


