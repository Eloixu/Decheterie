package fr.trackoe.decheterie.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import fr.trackoe.decheterie.model.bean.global.TypeHabitat;


/**
 * Created by Haocheng on 20/04/2017.
 */

public class TypeHabitatDB extends MyDb {

    public TypeHabitatDB(Context ctx) {
        mydb = new DecheterieDatabase(ctx);
    }

    /*
    Insérer un TypeHabitat dans la bdd
     */
    public long insertTypeHabitat(TypeHabitat typeHabitat) {
        ContentValues values = new ContentValues();
        values.put(DecheterieDatabase.TableTypeHabitat.ID, typeHabitat.getId());
        values.put(DecheterieDatabase.TableTypeHabitat.TYPE, typeHabitat.getType());

        return db.insertOrThrow(DecheterieDatabase.TableTypeHabitat.TABLE_NAME, null, values);
    }

    public void updateTypeHabitat(TypeHabitat typeHabitat) {
        ContentValues values = new ContentValues();
        values.put(DecheterieDatabase.TableTypeHabitat.ID, typeHabitat.getId());
        values.put(DecheterieDatabase.TableTypeHabitat.TYPE, typeHabitat.getType());

        db.update(DecheterieDatabase.TableTypeHabitat.TABLE_NAME, values,DecheterieDatabase.TableDchDecheterie.ID + "=" + typeHabitat.getId(),null);
    }

    public TypeHabitat getTypeHabitatFromID(int id) {
        String query = "SELECT * FROM " + DecheterieDatabase.TableTypeHabitat.TABLE_NAME + " WHERE " + DecheterieDatabase.TableTypeHabitat.ID + " = " + id;
        Cursor cursor = db.rawQuery(query, null);
        return cursorToTypeHabitat(cursor);
    }

    public TypeHabitat cursorToTypeHabitat(Cursor c){
        TypeHabitat t = new TypeHabitat();
        if(c.moveToFirst()) {
            t.setId(c.getInt(DecheterieDatabase.TableTypeHabitat.NUM_ID));
            t.setType(c.getString(DecheterieDatabase.TableTypeHabitat.NUM_TYPE));
            return t;
        }
        else{
            return null;
        }

    }

    /*
    Vider la table
     */

    public void clearTypeHabitat() {
        db.execSQL("delete from " + DecheterieDatabase.TableTypeHabitat.TABLE_NAME);
    }

}

