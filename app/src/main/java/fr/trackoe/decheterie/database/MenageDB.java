package fr.trackoe.decheterie.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import fr.trackoe.decheterie.model.bean.usager.Menage;

/**
 * Created by Haocheng on 10/04/2017.
 */

public class MenageDB extends MyDb {

    public MenageDB(Context ctx) {
        mydb = new DecheterieDatabase(ctx);
    }

    /*
    Insérer un menage dans la bdd
     */
    public long insertMenage(Menage menage) {
        ContentValues values = new ContentValues();

        values.put(DecheterieDatabase.TableMenage.ID_MENAGE, menage.getId());
        values.put(DecheterieDatabase.TableMenage.NB_HABITANTS, menage.getNbHabitants());
        values.put(DecheterieDatabase.TableMenage.ACTIF, menage.isActif()? 1 : 0);
        values.put(DecheterieDatabase.TableMenage.LOCAL_ID, menage.getLocalId());
        values.put(DecheterieDatabase.TableMenage.DATE_DEBUT, menage.getDateDebut());
        values.put(DecheterieDatabase.TableMenage.DATE_FIN, menage.getDateFin());
        values.put(DecheterieDatabase.TableMenage.IS_PROPRIETAIRE, menage.isProprietaire() ? 1 : 0);

        return db.insertOrThrow(DecheterieDatabase.TableMenage.TABLE_NAME, null, values);
    }

    public void updateMenage(Menage menage) {
        deleteMenageByIdentifiant(menage.getId());
        insertMenage(menage);
    }

    public void deleteMenageByIdentifiant(int id){
        db.execSQL("delete from " + DecheterieDatabase.TableMenage.TABLE_NAME + " WHERE " + DecheterieDatabase.TableMenage.ID_MENAGE + "=" + id);
    }

    /*
    Vider la table
     */

    public void clearMenage() {
        db.execSQL("delete from " + DecheterieDatabase.TableMenage.TABLE_NAME);
    }

    public Menage getMenageById(int id) {
        Menage menage;
        String query = "SELECT * FROM " + DecheterieDatabase.TableMenage.TABLE_NAME + " WHERE " + DecheterieDatabase.TableMenage.ID_MENAGE + "=" + id + ";";
        Cursor cursor = db.rawQuery(query, null);
        menage = cursorToMenage(cursor);
        return menage;
    }

    public Menage getMenageByLocalId(int localId) {
        Menage menage;
        String query = "SELECT * FROM " + DecheterieDatabase.TableMenage.TABLE_NAME + " WHERE " + DecheterieDatabase.TableMenage.LOCAL_ID + "=" + localId;
        Cursor cursor = db.rawQuery(query, null);
        menage = cursorToMenage(cursor);
        return menage;
    }


    private Menage cursorToMenage(Cursor c){
        Menage m = new Menage();
        if(c.moveToFirst()) {
            m.setId(c.getInt(DecheterieDatabase.TableMenage.NUM_ID_MENAGE));
            m.setNbHabitants(c.getInt(DecheterieDatabase.TableMenage.NUM_NB_HABITANTS));
            m.setActif(c.getInt(DecheterieDatabase.TableMenage.NUM_ACTIF) == 1);
            m.setLocalId(c.getInt(DecheterieDatabase.TableMenage.NUM_LOCAL_ID));
            m.setDateDebut(c.getString(DecheterieDatabase.TableMenage.NUM_DATE_DEBUT));
            m.setDateFin(c.getString(DecheterieDatabase.TableMenage.NUM_DATE_FIN));
            m.setProprietaire(c.getInt(DecheterieDatabase.TableMenage.NUM_IS_PROPRIETAIRE) == 1);

            c.close();
            return m;
        }
        else{
            c.close();
            return null;
        }
    }



}
