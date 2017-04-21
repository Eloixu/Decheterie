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
        values.put(DecheterieDatabase.TableMenage.NOM, menage.getNom());
        values.put(DecheterieDatabase.TableMenage.PRENOM, menage.getPrenom());
        values.put(DecheterieDatabase.TableMenage.EMAIL, menage.getEmail());
        values.put(DecheterieDatabase.TableMenage.NB_HABITANTS, menage.getNbHabitants());
        values.put(DecheterieDatabase.TableMenage.REFERENCE, menage.getReference());
        values.put(DecheterieDatabase.TableMenage.HABITAT_ID, menage.getHabitatId());
        values.put(DecheterieDatabase.TableMenage.ACTIF, menage.isActif()? 1 : 0);
        values.put(DecheterieDatabase.TableMenage.TELEPHONE, menage.getTelephone());
        values.put(DecheterieDatabase.TableMenage.CIVILITE, menage.getCivilite());
        values.put(DecheterieDatabase.TableMenage.LOCAL_ID, menage.getLocalId());

        return db.insertOrThrow(DecheterieDatabase.TableMenage.TABLE_NAME, null, values);
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


    private Menage cursorToMenage(Cursor c){
        if(c.getCount() == 0) {
            return null;
        }
        c.moveToFirst();
        Menage m = new Menage();
        m.setId(c.getInt(DecheterieDatabase.TableMenage.NUM_ID_MENAGE));
        m.setNom(c.getString(DecheterieDatabase.TableMenage.NUM_NOM));
        m.setPrenom(c.getString(DecheterieDatabase.TableMenage.NUM_PRENOM));
        m.setEmail(c.getString(DecheterieDatabase.TableMenage.NUM_EMAIL));
        m.setNbHabitants(c.getInt(DecheterieDatabase.TableMenage.NUM_NB_HABITANTS));
        m.setReference(c.getString(DecheterieDatabase.TableMenage.NUM_REFERENCE));
        m.setHabitatId(c.getInt(DecheterieDatabase.TableMenage.NUM_HABITAT_ID));
        m.setActif(c.getInt(DecheterieDatabase.TableMenage.NUM_ACTIF) == 1);
        m.setTelephone(c.getString(DecheterieDatabase.TableMenage.NUM_TELEPHONE));
        m.setCivilite(c.getString(DecheterieDatabase.TableMenage.NUM_CIVILITE));
        m.setLocalId(c.getInt(DecheterieDatabase.TableMenage.NUM_LOCAL_ID));

        c.close();

        return m;
    }



}
