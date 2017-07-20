package fr.trackoe.decheterie.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.widget.SimpleCursorAdapter;

import java.util.ArrayList;

import fr.trackoe.decheterie.model.bean.global.Module;
import fr.trackoe.decheterie.model.bean.global.Modules;
import fr.trackoe.decheterie.model.bean.usager.Usager;

/**
 * Created by Remi on 05/04/2017.
 */
public class UsagerDB extends MyDb {

    public UsagerDB(Context ctx) {
        mydb = new DecheterieDatabase(ctx);
    }

    /*
    Insérer un Usager dans la bdd
     */
    public long insertUsager(Usager usager) {
        ContentValues values = new ContentValues();
        values.put(DecheterieDatabase.TableUsager.ID_USAGER, usager.getId());
        values.put(DecheterieDatabase.TableUsager.ID_ACCOUNT, usager.getIdAccount());
        values.put(DecheterieDatabase.TableUsager.NOM, usager.getNom());
        values.put(DecheterieDatabase.TableUsager.DATE_MAJ, usager.getDateMaj());
        values.put(DecheterieDatabase.TableUsager.PRENOM, usager.getPrenom());
        values.put(DecheterieDatabase.TableUsager.EMAIL, usager.getEmail());
        values.put(DecheterieDatabase.TableUsager.CIVILITE, usager.getCivilite());
        values.put(DecheterieDatabase.TableUsager.REFERENCE, usager.getReference());
        values.put(DecheterieDatabase.TableUsager.RAISON_SOCIALE, usager.getRaisonSociale());
        values.put(DecheterieDatabase.TableUsager.ACTIVITE, usager.getActivite());
        values.put(DecheterieDatabase.TableUsager.TELEPHONE1, usager.getTelephone1());
        values.put(DecheterieDatabase.TableUsager.TELEPHONE2, usager.getTelephone2());
        values.put(DecheterieDatabase.TableUsager.PASSWORD, usager.getPassword());
        values.put(DecheterieDatabase.TableUsager.COMMENTAIRE, usager.getCommentaire());
        values.put(DecheterieDatabase.TableUsager.IS_ACTIF, usager.isActif() ? 1 : 0);
        values.put(DecheterieDatabase.TableUsager.SIREN, usager.getSiren());
        values.put(DecheterieDatabase.TableUsager.SIRET, usager.getSiret());
        values.put(DecheterieDatabase.TableUsager.CODE_APE, usager.getCodeApe());
        values.put(DecheterieDatabase.TableUsager.SOUMIS_RS, usager.getSoumisRS());

        return db.insertOrThrow(DecheterieDatabase.TableUsager.TABLE_NAME, null, values);
    }

    public void updateUsager(Usager usager) {
        deleteUsagerByIdentifiant(usager.getId());
        insertUsager(usager);
    }

    public void deleteUsagerByIdentifiant(int id){
        db.execSQL("delete from " + DecheterieDatabase.TableUsager.TABLE_NAME + " WHERE " + DecheterieDatabase.TableUsager.ID_USAGER + "=" + id);
    }

    public Usager getUsagerFromID(int id) {
        String query = "SELECT * FROM " + DecheterieDatabase.TableUsager.TABLE_NAME + " WHERE " + DecheterieDatabase.TableUsager.ID_USAGER + " = " + id;
        Cursor cursor = db.rawQuery(query, null);
        return cursorToUsager(cursor);
    }

    public ArrayList<Usager> getAllUsager() {
        ArrayList<Usager> usagerList;
        String query = "SELECT * FROM " + DecheterieDatabase.TableUsager.TABLE_NAME  + ";";
        Cursor cursor = db.rawQuery(query, null);
        usagerList = cursorToListeUsager(cursor);
        return usagerList;
    }

    public ArrayList<Usager> getUsagerListByName(String nom) {
        ArrayList<Usager> usagerList;
        String query = "SELECT * FROM " + DecheterieDatabase.TableUsager.TABLE_NAME + " WHERE " + DecheterieDatabase.TableUsager.NOM + " LIKE " + "'%" + nom + "%'" +  ";";
        Cursor cursor = db.rawQuery(query, null);
        usagerList = cursorToListeUsager(cursor);
        return usagerList;
    }

    public Usager cursorToUsager(Cursor c){
        Usager u = new Usager();
        try {
            if (c.moveToFirst()) {
                u.setId(c.getInt(DecheterieDatabase.TableUsager.NUM_ID_USAGER));
                u.setIdAccount(c.getInt(DecheterieDatabase.TableUsager.NUM_ID_ACCOUNT));
                u.setNom(c.getString(DecheterieDatabase.TableUsager.NUM_NOM));
                u.setDateMaj(c.getString(DecheterieDatabase.TableUsager.NUM_DATE_MAJ));
                u.setPrenom(c.getString(DecheterieDatabase.TableUsager.NUM_PRENOM));
                u.setEmail(c.getString(DecheterieDatabase.TableUsager.NUM_EMAIL));
                u.setCivilite(c.getString(DecheterieDatabase.TableUsager.NUM_CIVILITE));
                u.setReference(c.getString(DecheterieDatabase.TableUsager.NUM_REFERENCE));
                u.setRaisonSociale(c.getString(DecheterieDatabase.TableUsager.NUM_RAISON_SOCIALE));
                u.setActivite(c.getString(DecheterieDatabase.TableUsager.NUM_ACTIVITE));
                u.setTelephone1(c.getString(DecheterieDatabase.TableUsager.NUM_TELEPHONE1));
                u.setTelephone2(c.getString(DecheterieDatabase.TableUsager.NUM_TELEPHONE2));
                u.setPassword(c.getString(DecheterieDatabase.TableUsager.NUM_PASSWORD));
                u.setCommentaire(c.getString(DecheterieDatabase.TableUsager.NUM_COMMENTAIRE));
                u.setActif(c.getInt(DecheterieDatabase.TableUsager.NUM_IS_ACTIF) == 1 ? true : false);
                u.setSiren(c.getString(DecheterieDatabase.TableUsager.NUM_SIREN));
                u.setSiret(c.getString(DecheterieDatabase.TableUsager.NUM_SIRET));
                u.setCodeApe(c.getString(DecheterieDatabase.TableUsager.NUM_CODE_APE));
                u.setSoumisRS(c.getString(DecheterieDatabase.TableUsager.NUM_SOUMIS_RS));
                c.close();
                return u;
            }else{
                c.close();
                return null;
            }
        }
        catch(Exception e){
            return u;
        }

    }

    private ArrayList<Usager> cursorToListeUsager(Cursor c) {
        ArrayList<Usager> usagerList = new ArrayList<>();
        try {
            if (c.moveToFirst()) {
                do {
                    Usager u = new Usager();
                    u.setId(c.getInt(DecheterieDatabase.TableUsager.NUM_ID_USAGER));
                    u.setIdAccount(c.getInt(DecheterieDatabase.TableUsager.NUM_ID_ACCOUNT));
                    u.setNom(c.getString(DecheterieDatabase.TableUsager.NUM_NOM));
                    u.setDateMaj(c.getString(DecheterieDatabase.TableUsager.NUM_DATE_MAJ));
                    u.setPrenom(c.getString(DecheterieDatabase.TableUsager.NUM_PRENOM));
                    u.setEmail(c.getString(DecheterieDatabase.TableUsager.NUM_EMAIL));
                    u.setCivilite(c.getString(DecheterieDatabase.TableUsager.NUM_CIVILITE));
                    u.setReference(c.getString(DecheterieDatabase.TableUsager.NUM_REFERENCE));
                    u.setRaisonSociale(c.getString(DecheterieDatabase.TableUsager.NUM_RAISON_SOCIALE));
                    u.setActivite(c.getString(DecheterieDatabase.TableUsager.NUM_ACTIVITE));
                    u.setTelephone1(c.getString(DecheterieDatabase.TableUsager.NUM_TELEPHONE1));
                    u.setTelephone2(c.getString(DecheterieDatabase.TableUsager.NUM_TELEPHONE2));
                    u.setPassword(c.getString(DecheterieDatabase.TableUsager.NUM_PASSWORD));
                    u.setCommentaire(c.getString(DecheterieDatabase.TableUsager.NUM_COMMENTAIRE));
                    u.setActif(c.getInt(DecheterieDatabase.TableUsager.NUM_IS_ACTIF) == 1 ? true : false);
                    u.setSiren(c.getString(DecheterieDatabase.TableUsager.NUM_SIREN));
                    u.setSiret(c.getString(DecheterieDatabase.TableUsager.NUM_SIRET));
                    u.setCodeApe(c.getString(DecheterieDatabase.TableUsager.NUM_CODE_APE));
                    u.setSoumisRS(c.getString(DecheterieDatabase.TableUsager.NUM_SOUMIS_RS));
                    usagerList.add(u);
                } while (c.moveToNext());

                c.close();
            }
            return usagerList;
        }
        catch(Exception e){
            return usagerList;
        }
    }

    /*
    Vider la table
     */

    public void clearUsager() {
        db.execSQL("delete from " + DecheterieDatabase.TableUsager.TABLE_NAME);
    }

}
