package fr.trackoe.decheterie.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

import fr.trackoe.decheterie.model.bean.global.Decheterie;
import fr.trackoe.decheterie.model.bean.global.Flux;

/**
 * Created by Haocheng on 21/03/2017.
 */

public class DchFluxDB extends MyDb {

    public DchFluxDB(Context ctx) {
        mydb = new DecheterieDatabase(ctx);
    }

    /*
    Insérer un Flux dans la bdd
     */
    public long insertFlux(Flux flux) {
        ContentValues values = new ContentValues();

        values.put(DecheterieDatabase.TableDchFlux.ID, flux.getId());
        values.put(DecheterieDatabase.TableDchFlux.NOM, flux.getNom());
        values.put(DecheterieDatabase.TableDchFlux.ICON_ID, flux.getIconId());
        values.put(DecheterieDatabase.TableDchFlux.UNITE_COMPTAGE_ID, flux.getUniteComptageId());
        values.put(DecheterieDatabase.TableDchFlux.DCH_ACCOUNT_ID, flux.getIdAccount());


        return db.insertOrThrow(DecheterieDatabase.TableDchFlux.TABLE_DCH_FLUX, null, values);
    }

    /*
    Vider la table
     */

    public void clearFlux() {
        db.execSQL("delete from " + DecheterieDatabase.TableDchFlux.TABLE_DCH_FLUX);
    }

    public Flux getFluxByIdentifiant(int id) {
        Flux flux;
        String query = "SELECT * FROM " + DecheterieDatabase.TableDchFlux.TABLE_DCH_FLUX + " WHERE " + DecheterieDatabase.TableDchFlux.ID + "=" + id + ";";
        Cursor cursor = db.rawQuery(query, null);
        flux = cursorToFlux(cursor);
        return flux;
    }

    public Flux getFluxByIconId(int iconId) {
        Flux flux;
        String query = "SELECT * FROM " + DecheterieDatabase.TableDchFlux.TABLE_DCH_FLUX + " WHERE " + DecheterieDatabase.TableDchFlux.ICON_ID + "=" + iconId + ";";
        Cursor cursor = db.rawQuery(query, null);
        flux = cursorToFlux(cursor);
        return flux;
    }

    public ArrayList<Flux> getAllFlux() {
        ArrayList<Flux> fluxList;
        String query = "SELECT * FROM " + DecheterieDatabase.TableDchFlux.TABLE_DCH_FLUX;
        Cursor cursor = db.rawQuery(query, null);
        fluxList = cursorToListeFlux(cursor);
        return fluxList;
    }

    public Flux getFluxByName(String name) {
        Flux f = new Flux();
        String query = "SELECT * FROM " + DecheterieDatabase.TableDchFlux.TABLE_DCH_FLUX + " WHERE " + DecheterieDatabase.TableDchFlux.NOM + " LIKE " + "'" + name + "'" +  ";";
        Cursor cursor = db.rawQuery(query, null);
        f = cursorToFlux(cursor);
        return f;
    }


    private Flux cursorToFlux(Cursor c){
        if(c.getCount() == 0) {
            return null;
        }
        c.moveToFirst();
        Flux f = new Flux();
        f.setId(c.getInt(DecheterieDatabase.TableDchFlux.NUM_ID));
        f.setNom(c.getString(DecheterieDatabase.TableDchFlux.NUM_NOM));
        f.setIconId(c.getInt(DecheterieDatabase.TableDchFlux.NUM_ICON_ID));
        f.setUniteComptageId(c.getInt(DecheterieDatabase.TableDchFlux.NUM_UNITE_COMPTAGE_ID));
        f.setIdAccount(c.getInt(DecheterieDatabase.TableDchFlux.NUM_DCH_ACCOUNT_ID));

        c.close();

        return f;
    }

    private ArrayList<Flux> cursorToListeFlux(Cursor c) {
        ArrayList<Flux> fluxList = new ArrayList<>();

        if (c.moveToFirst()) {
            do {
                Flux f = new Flux();
                f.setId(c.getInt(DecheterieDatabase.TableDchFlux.NUM_ID));
                f.setNom(c.getString(DecheterieDatabase.TableDchFlux.NUM_NOM));
                f.setIconId(c.getInt(DecheterieDatabase.TableDchFlux.NUM_ICON_ID));
                f.setUniteComptageId(c.getInt(DecheterieDatabase.TableDchFlux.NUM_UNITE_COMPTAGE_ID));
                f.setIdAccount(c.getInt(DecheterieDatabase.TableDchFlux.NUM_DCH_ACCOUNT_ID));
                fluxList.add(f);
            } while (c.moveToNext());

            c.close();
        }
        return fluxList;
    }
}
