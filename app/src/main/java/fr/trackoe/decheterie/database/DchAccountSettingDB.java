package fr.trackoe.decheterie.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

import fr.trackoe.decheterie.model.bean.global.AccountSetting;

/**
 * Created by Haocheng on 10/04/2017.
 */

public class DchAccountSettingDB extends MyDb {

    public DchAccountSettingDB(Context ctx) {
        mydb = new DecheterieDatabase(ctx);
    }

    /*
    Insérer un AccountSetting dans la bdd
     */
    public long insertAccountSetting(AccountSetting accountSetting) {
        ContentValues values = new ContentValues();
        values.put(DecheterieDatabase.TableDchAccountSetting.ID, accountSetting.getId());
        values.put(DecheterieDatabase.TableDchAccountSetting.DCH_ACCOUNT_ID, accountSetting.getDchAccountId());
        values.put(DecheterieDatabase.TableDchAccountSetting.DCH_TYPE_CARTE_ID, accountSetting.getDchTypeCarteId());
        values.put(DecheterieDatabase.TableDchAccountSetting.UNITE_DEPOT_DECHETERIE_ID, accountSetting.getUniteDepotDecheterieId());
        values.put(DecheterieDatabase.TableDchAccountSetting.DECOMPTE_DEPOT, accountSetting.isDecompteDepot());
        values.put(DecheterieDatabase.TableDchAccountSetting.DECOMPTE_UDD, accountSetting.isDecompteUDD());
        values.put(DecheterieDatabase.TableDchAccountSetting.PAGE_SIGNATURE, accountSetting.isPageSignature());
        values.put(DecheterieDatabase.TableDchAccountSetting.COUT_UDD_PR_POINT, accountSetting.getCoutUDDPrPoint());
        values.put(DecheterieDatabase.TableDchAccountSetting.COUT_POINT, accountSetting.getCoutPoint());
        values.put(DecheterieDatabase.TableDchAccountSetting.UNITE_POINT, accountSetting.getUnitePoint());
        values.put(DecheterieDatabase.TableDchAccountSetting.DATE_DEBUT_PARAM, accountSetting.getDateDebutParam());
        values.put(DecheterieDatabase.TableDchAccountSetting.DATE_FIN_PARAM, accountSetting.getDateFinParam());
        values.put(DecheterieDatabase.TableDchAccountSetting.DCH_CHOIX_DECOMPTE_TOTAL_ID, accountSetting.getDchChoixDecompteTotalId());
        values.put(DecheterieDatabase.TableDchAccountSetting.NB_DEPOT_RESTANT, accountSetting.getNbDepotRestant());

        return db.insertOrThrow(DecheterieDatabase.TableDchAccountSetting.TABLE_NAME, null, values);
    }

    public AccountSetting getAccountSettingFromID(int id) {
        String query = "SELECT * FROM " + DecheterieDatabase.TableDchAccountSetting.TABLE_NAME + " WHERE " + DecheterieDatabase.TableDchAccountSetting.ID + " = " + id;
        Cursor cursor = db.rawQuery(query, null);
        return cursorToAccountSetting(cursor);
    }

    public ArrayList<AccountSetting> getListeAccountSettingByAccountIdAndTypeCarteId(int accountId, int typeCarteId) {
        ArrayList<AccountSetting> accountSettingList;
        String query = "SELECT * FROM " + DecheterieDatabase.TableDchAccountSetting.TABLE_NAME + " WHERE " + DecheterieDatabase.TableDchAccountSetting.DCH_ACCOUNT_ID + "=" + accountId
                + " AND " + DecheterieDatabase.TableDchAccountSetting.DCH_TYPE_CARTE_ID + "=" + typeCarteId + ";";
        Cursor cursor = db.rawQuery(query, null);
        accountSettingList = cursorToListeAccountSetting(cursor);
        return accountSettingList;
    }

    public AccountSetting cursorToAccountSetting(Cursor c){
        AccountSetting a = new AccountSetting();
        if(c.moveToFirst()) {
            a.setId(c.getInt(DecheterieDatabase.TableDchAccountSetting.NUM_ID));
            a.setDchAccountId(c.getInt(DecheterieDatabase.TableDchAccountSetting.NUM_DCH_ACCOUNT_ID));
            a.setDchTypeCarteId(c.getInt(DecheterieDatabase.TableDchAccountSetting.NUM_DCH_TYPE_CARTE_ID));
            a.setUniteDepotDecheterieId(c.getInt(DecheterieDatabase.TableDchAccountSetting.NUM_UNITE_DEPOT_DECHETERIE_ID));
            a.setDecompteDepot(c.getInt(DecheterieDatabase.TableDchAccountSetting.NUM_DECOMPTE_DEPOT) == 1);
            a.setDecompteUDD(c.getInt(DecheterieDatabase.TableDchAccountSetting.NUM_DECOMPTE_UDD) == 1);
            a.setPageSignature(c.getInt(DecheterieDatabase.TableDchAccountSetting.NUM_PAGE_SIGNATURE) == 1);
            a.setCoutUDDPrPoint(c.getString(DecheterieDatabase.TableDchAccountSetting.NUM_COUT_UDD_PR_POINT));
            a.setCoutPoint(c.getString(DecheterieDatabase.TableDchAccountSetting.NUM_COUT_POINT));
            a.setUnitePoint(c.getString(DecheterieDatabase.TableDchAccountSetting.NUM_UNITE_POINT));
            a.setDateDebutParam(c.getString(DecheterieDatabase.TableDchAccountSetting.NUM_DATE_DEBUT_PARAM));
            a.setDateFinParam(c.getString(DecheterieDatabase.TableDchAccountSetting.NUM_DATE_FIN_PARAM));
            a.setDchChoixDecompteTotalId(c.getInt(DecheterieDatabase.TableDchAccountSetting.NUM_DCH_CHOIX_DECOMPTE_TOTAL_ID));
            a.setNbDepotRestant(c.getInt(DecheterieDatabase.TableDchAccountSetting.NUM_NB_DEPOT_RESTANT));
        }
        return a;
    }

    private ArrayList<AccountSetting> cursorToListeAccountSetting(Cursor c) {
        ArrayList<AccountSetting> accountSettingList = new ArrayList<>();

        if (c.moveToFirst()) {
            do {
                AccountSetting a = new AccountSetting();
                a.setId(c.getInt(DecheterieDatabase.TableDchAccountSetting.NUM_ID));
                a.setDchAccountId(c.getInt(DecheterieDatabase.TableDchAccountSetting.NUM_DCH_ACCOUNT_ID));
                a.setDchTypeCarteId(c.getInt(DecheterieDatabase.TableDchAccountSetting.NUM_DCH_TYPE_CARTE_ID));
                a.setUniteDepotDecheterieId(c.getInt(DecheterieDatabase.TableDchAccountSetting.NUM_UNITE_DEPOT_DECHETERIE_ID));
                a.setDecompteDepot(c.getInt(DecheterieDatabase.TableDchAccountSetting.NUM_DECOMPTE_DEPOT) == 1);
                a.setDecompteUDD(c.getInt(DecheterieDatabase.TableDchAccountSetting.NUM_DECOMPTE_UDD) == 1);
                a.setPageSignature(c.getInt(DecheterieDatabase.TableDchAccountSetting.NUM_PAGE_SIGNATURE) == 1);
                a.setCoutUDDPrPoint(c.getString(DecheterieDatabase.TableDchAccountSetting.NUM_COUT_UDD_PR_POINT));
                a.setCoutPoint(c.getString(DecheterieDatabase.TableDchAccountSetting.NUM_COUT_POINT));
                a.setUnitePoint(c.getString(DecheterieDatabase.TableDchAccountSetting.NUM_UNITE_POINT));
                a.setDateDebutParam(c.getString(DecheterieDatabase.TableDchAccountSetting.NUM_DATE_DEBUT_PARAM));
                a.setDateFinParam(c.getString(DecheterieDatabase.TableDchAccountSetting.NUM_DATE_FIN_PARAM));
                a.setDchChoixDecompteTotalId(c.getInt(DecheterieDatabase.TableDchAccountSetting.NUM_DCH_CHOIX_DECOMPTE_TOTAL_ID));
                a.setNbDepotRestant(c.getInt(DecheterieDatabase.TableDchAccountSetting.NUM_NB_DEPOT_RESTANT));
                accountSettingList.add(a);
            } while (c.moveToNext());

            c.close();
            return accountSettingList;
        }
        else{
            return null;
        }

    }

    /*
    Vider la table
     */

    public void clearAccountSetting() {
        db.execSQL("delete from " + DecheterieDatabase.TableDchAccountSetting.TABLE_NAME);
    }

}
