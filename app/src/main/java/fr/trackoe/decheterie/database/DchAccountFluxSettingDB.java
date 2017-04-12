package fr.trackoe.decheterie.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import fr.trackoe.decheterie.model.bean.global.AccountFluxSetting;

/**
 * Created by Haocheng on 10/04/2017.
 */

public class DchAccountFluxSettingDB extends MyDb {

    public DchAccountFluxSettingDB(Context ctx) {
        mydb = new DecheterieDatabase(ctx);
    }

    /*
    Insérer un AccountFluxSetting dans la bdd
     */
    public long insertAccountFluxSetting(AccountFluxSetting accountFluxSetting) {
        ContentValues values = new ContentValues();
        values.put(DecheterieDatabase.TableDchAccountFluxSetting.DCH_ACCOUNT_SETTING_ID, accountFluxSetting.getDchAccountSettingId());
        values.put(DecheterieDatabase.TableDchAccountFluxSetting.DCH_FLUX_ID, accountFluxSetting.getDchFluxId());
        values.put(DecheterieDatabase.TableDchAccountFluxSetting.CONVERT_COMPTAGE_PR_UDD, accountFluxSetting.isConvertComptagePrUDD()? 1 : 0);
        values.put(DecheterieDatabase.TableDchAccountFluxSetting.COUT_UC_PR_POINT, accountFluxSetting.getCoutUCPrPoint());

        return db.insertOrThrow(DecheterieDatabase.TableDchAccountFluxSetting.TABLE_NAME, null, values);
    }
/*
    public AccountFluxSetting getAccountFluxSettingFromID(int id) {
        String query = "SELECT * FROM " + DecheterieDatabase.TableDchAccountSetting.TABLE_NAME + " WHERE " + DecheterieDatabase.TableDchAccountSetting.ID + " = " + id;
        Cursor cursor = db.rawQuery(query, null);
        return cursorToAccountSetting(cursor);
    }*/

    public AccountFluxSetting cursorToAccountFluxSetting(Cursor c){
        AccountFluxSetting a = new AccountFluxSetting();
        if(c.moveToFirst()) {
            a.setDchAccountSettingId(c.getInt(DecheterieDatabase.TableDchAccountFluxSetting.NUM_DCH_ACCOUNT_SETTING_ID));
            a.setDchFluxId(c.getInt(DecheterieDatabase.TableDchAccountFluxSetting.NUM_DCH_FLUX_ID));
            a.setConvertComptagePrUDD(c.getInt(DecheterieDatabase.TableDchAccountFluxSetting.NUM_CONVERT_COMPTAGE_PR_UDD) == 1);
            a.setCoutUCPrPoint(c.getString(DecheterieDatabase.TableDchAccountFluxSetting.NUM_COUT_UC_PR_POINT));
        }
        return a;
    }

    /*
    Vider la table
     */

    public void clearAccountFluxSetting() {
        db.execSQL("delete from " + DecheterieDatabase.TableDchAccountFluxSetting.TABLE_NAME);
    }

}
