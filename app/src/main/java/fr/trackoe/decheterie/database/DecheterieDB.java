package fr.trackoe.decheterie.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import fr.trackoe.decheterie.model.bean.global.Decheterie;


/**
 * Created by Haocheng on 14/03/2017.
 */
public class DecheterieDB extends MyDb {

    public DecheterieDB(Context ctx) {
        mydb = new DecheterieDatabase(ctx);
    }

    /*
    Insérer un Decheterie dans la bdd
     */
    public long insertDecheterie(Decheterie decheterie) {
        ContentValues values = new ContentValues();

        values.put(DecheterieDatabase.TableDchDecheterie.ID, decheterie.getId());
        values.put(DecheterieDatabase.TableDchDecheterie.ID_ACCOUNT, decheterie.getIdAccount());
        values.put(DecheterieDatabase.TableDchDecheterie.NOM, decheterie.getNom());
        values.put(DecheterieDatabase.TableDchDecheterie.CONSIGNE_COMPTAGE, decheterie.getConsigneComptage());
        values.put(DecheterieDatabase.TableDchDecheterie.CONSIGNE_AV_SIGNATURE, decheterie.getConsigneAvSignature());
        values.put(DecheterieDatabase.TableDchDecheterie.APPORT_FLUX, decheterie.isApportFlux()? 1 : 0);

        return db.insertOrThrow(DecheterieDatabase.TableDchDecheterie.TABLE_DCH_DECHETERIE, null, values);
    }

    /*
    Vider la table
     */

    public void clearDecheterie() {
        db.execSQL("delete from " + DecheterieDatabase.TableDchDecheterie.TABLE_DCH_DECHETERIE);
    }

    public Decheterie getDecheterieByIdentifiant(int id) {
        Decheterie decheterie;
        String query = "SELECT * FROM " + DecheterieDatabase.TableDchDecheterie.TABLE_DCH_DECHETERIE + " WHERE " + DecheterieDatabase.TableDchDecheterie.ID + "=" + id + ";";
        Cursor cursor = db.rawQuery(query, null);
        decheterie = cursorToDechetrie(cursor);
        return decheterie;
    }

    public ArrayList<Decheterie> getAllDecheteries() {
        ArrayList<Decheterie> decheterieList;
        String query = "SELECT * FROM " + DecheterieDatabase.TableDchDecheterie.TABLE_DCH_DECHETERIE;
        Cursor cursor = db.rawQuery(query, null);
        decheterieList = cursorToListeDechetrie(cursor);
        return decheterieList;
    }

    public ArrayList<Decheterie> getDecheteriesByName(String name) {
        ArrayList<Decheterie> decheterieList;
        String query = "SELECT * FROM " + DecheterieDatabase.TableDchDecheterie.TABLE_DCH_DECHETERIE + " WHERE " + DecheterieDatabase.TableDchDecheterie.NOM + " LIKE " + "'%" + name + "%'" +  ";";
        Cursor cursor = db.rawQuery(query, null);
        decheterieList = cursorToListeDechetrie(cursor);
        return decheterieList;
    }

    public Decheterie getDecheterieByName(String name) {
        Decheterie decheterie;
        String query = "SELECT * FROM " + DecheterieDatabase.TableDchDecheterie.TABLE_DCH_DECHETERIE + " WHERE " + DecheterieDatabase.TableDchDecheterie.NOM + " LIKE " + "'" + name + "'" +  ";";
        Cursor cursor = db.rawQuery(query, null);
        decheterie = cursorToDechetrie(cursor);
        return decheterie;
    }


    private Decheterie cursorToDechetrie(Cursor c){
        if(c.getCount() == 0) {
            return null;
        }
        c.moveToFirst();
        Decheterie d = new Decheterie();
        d.setId(c.getInt(DecheterieDatabase.TableDchDecheterie.NUM_ID));
        d.setIdAccount(c.getInt(DecheterieDatabase.TableDchDecheterie.NUM_ID_ACCOUNT));
        d.setNom(c.getString(DecheterieDatabase.TableDchDecheterie.NUM_NOM));
        d.setConsigneComptage(c.getString(DecheterieDatabase.TableDchDecheterie.NUM_CONSIGNE_COMPTAGE));
        d.setConsigneAvSignature(c.getString(DecheterieDatabase.TableDchDecheterie.NUM_CONSIGNE_AV_SIGNATURE));
        d.setApportFlux((c.getInt(DecheterieDatabase.TableDchDecheterie.NUM_APPORT_FLUX) == 1)? true : false);

        c.close();

        return d;
    }

    private ArrayList<Decheterie> cursorToListeDechetrie(Cursor c) {
        ArrayList<Decheterie> decheterieList = new ArrayList<>();

        if (c.moveToFirst()) {
            do {
                Decheterie d = new Decheterie();
                d.setId(c.getInt(DecheterieDatabase.TableDchDecheterie.NUM_ID));
                d.setIdAccount(c.getInt(DecheterieDatabase.TableDchDecheterie.NUM_ID_ACCOUNT));
                d.setNom(c.getString(DecheterieDatabase.TableDchDecheterie.NUM_NOM));
                d.setConsigneComptage(c.getString(DecheterieDatabase.TableDchDecheterie.NUM_CONSIGNE_COMPTAGE));
                d.setConsigneAvSignature(c.getString(DecheterieDatabase.TableDchDecheterie.NUM_CONSIGNE_AV_SIGNATURE));
                d.setApportFlux((c.getInt(DecheterieDatabase.TableDchDecheterie.NUM_APPORT_FLUX) == 1)? true : false);
                decheterieList.add(d);
            } while (c.moveToNext());

            c.close();
        }
        return decheterieList;
    }
}
