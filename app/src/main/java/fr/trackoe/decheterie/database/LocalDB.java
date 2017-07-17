package fr.trackoe.decheterie.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

import fr.trackoe.decheterie.model.bean.usager.Local;

/**
 * Created by Haocheng on 10/04/2017.
 */

public class LocalDB extends MyDb {

    public LocalDB(Context ctx) {
        mydb = new DecheterieDatabase(ctx);
    }

    /*
    Insérer un local dans la bdd
     */
    public long insertLocal(Local local) {
        ContentValues values = new ContentValues();

        values.put(DecheterieDatabase.TableLocal.ID_LOCAL, local.getIdLocal());
        values.put(DecheterieDatabase.TableLocal.HABITAT_ID, local.getHabitatId());
        values.put(DecheterieDatabase.TableLocal.LOT, local.getLot());
        values.put(DecheterieDatabase.TableLocal.INVARIANT_DFIP, local.getInvariantDfip());
        values.put(DecheterieDatabase.TableLocal.IDENTIFIANT_INTERNE, local.getIdentifiantInterne());
        values.put(DecheterieDatabase.TableLocal.BATIMENT, local.getBatiment());
        values.put(DecheterieDatabase.TableLocal.ETAGE_PORTE, local.getEtagePorte());

        return db.insertOrThrow(DecheterieDatabase.TableLocal.TABLE_NAME, null, values);
    }

    public void updateLocal(Local local) {
        deleteLocalByIdentifiant(local.getIdLocal());
        insertLocal(local);
    }

    public void deleteLocalByIdentifiant(int id){
        db.execSQL("delete from " + DecheterieDatabase.TableLocal.TABLE_NAME + " WHERE " + DecheterieDatabase.TableLocal.ID_LOCAL + "=" + id);
    }

    /*
    Vider la table
     */
    public void clearLocal() {
        db.execSQL("delete from " + DecheterieDatabase.TableLocal.TABLE_NAME);
    }

    public Local getLocalById(int id) {
        Local local;
        String query = "SELECT * FROM " + DecheterieDatabase.TableLocal.TABLE_NAME + " WHERE " + DecheterieDatabase.TableLocal.ID_LOCAL + "=" + id + ";";
        Cursor cursor = db.rawQuery(query, null);
        local = cursorToLocal(cursor);
        return local;
    }

    public ArrayList<Local> getLocalListByHabitatId(int habitatId) {
        ArrayList<Local> localList;
        String query = "SELECT * FROM " + DecheterieDatabase.TableLocal.TABLE_NAME + " WHERE " + DecheterieDatabase.TableLocal.HABITAT_ID + "=" + habitatId;;
        Cursor cursor = db.rawQuery(query, null);
        localList = cursorToListeLocal(cursor);
        return localList;
    }


    private Local cursorToLocal(Cursor c){
        Local l = new Local();
        try {
            if (c.moveToFirst()) {
                l.setIdLocal(c.getInt(DecheterieDatabase.TableLocal.NUM_ID_LOCAL));
                l.setHabitatId(c.getInt(DecheterieDatabase.TableLocal.NUM_HABITAT_ID));
                l.setLot(c.getString(DecheterieDatabase.TableLocal.NUM_LOT));
                l.setInvariantDfip(c.getString(DecheterieDatabase.TableLocal.NUM_INVARIANT_DFIP));
                l.setIdentifiantInterne(c.getString(DecheterieDatabase.TableLocal.NUM_IDENTIFIANT_INTERNE));
                l.setBatiment(c.getString(DecheterieDatabase.TableLocal.NUM_BATIMENT));
                l.setEtagePorte(c.getString(DecheterieDatabase.TableLocal.NUM_ETAGE_PORTE));

                c.close();
                return l;
            } else {
                c.close();
                return null;
            }
        }catch (Exception e){
            return l;
        }

    }

    private ArrayList<Local> cursorToListeLocal(Cursor c) {
        ArrayList<Local> localList = new ArrayList<>();
        try {
            if (c.moveToFirst()) {
                do {
                    Local l = new Local();
                    l.setIdLocal(c.getInt(DecheterieDatabase.TableLocal.NUM_ID_LOCAL));
                    l.setHabitatId(c.getInt(DecheterieDatabase.TableLocal.NUM_HABITAT_ID));
                    l.setLot(c.getString(DecheterieDatabase.TableLocal.NUM_LOT));
                    l.setInvariantDfip(c.getString(DecheterieDatabase.TableLocal.NUM_INVARIANT_DFIP));
                    l.setIdentifiantInterne(c.getString(DecheterieDatabase.TableLocal.NUM_IDENTIFIANT_INTERNE));
                    l.setBatiment(c.getString(DecheterieDatabase.TableLocal.NUM_BATIMENT));
                    l.setEtagePorte(c.getString(DecheterieDatabase.TableLocal.NUM_ETAGE_PORTE));
                    localList.add(l);
                } while (c.moveToNext());

                c.close();
            }
            return localList;
        }catch (Exception e){
            return localList;
        }


    }
}
