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
        values.put(DecheterieDatabase.TableDchApportFlux.QTY_APPORTE, apportFlux.getQtyApporte());

        return db.insertOrThrow(DecheterieDatabase.TableDchApportFlux.TABLE_DCH_APPORT_FLUX, null, values);
    }

    /*
    Vider la table
     */

    public void clearApportFlux() {
        db.execSQL("delete from " + DecheterieDatabase.TableDchApportFlux.TABLE_DCH_APPORT_FLUX);
    }

    public ApportFlux getDepotByDepotId(int depotId) {
        ApportFlux apportFlux;
        String query = "SELECT * FROM " + DecheterieDatabase.TableDchApportFlux.TABLE_DCH_APPORT_FLUX + " WHERE " + DecheterieDatabase.TableDchApportFlux.DCH_DEPOT_ID + "=" + depotId + ";";
        Cursor cursor = db.rawQuery(query, null);
        apportFlux = cursorToApportFlux(cursor);
        return apportFlux;
    }



    private ApportFlux cursorToApportFlux(Cursor c){
        if(c.getCount() == 0) {
            return null;
        }
        c.moveToFirst();
        ApportFlux a = new ApportFlux();
        a.setDepotId(c.getInt(DecheterieDatabase.TableDchApportFlux.NUM_DCH_DEPOT_ID));
        a.setFluxId(c.getInt(DecheterieDatabase.TableDchApportFlux.NUM_DCH_FLUX_ID));
        a.setQtyApporte(c.getFloat(DecheterieDatabase.TableDchApportFlux.NUM_QTY_APPORTE));

        c.close();

        return a;
    }

}


