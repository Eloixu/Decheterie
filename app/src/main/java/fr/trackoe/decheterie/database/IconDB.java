package fr.trackoe.decheterie.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

import fr.trackoe.decheterie.model.bean.global.Icon;

/**
 * Created by Haocheng on 21/03/2017.
 */

public class IconDB extends MyDb {

    public IconDB(Context ctx) {
        mydb = new DecheterieDatabase(ctx);
    }

    /*
    Insérer un icon dans la bdd
     */
    public long insertIcon(Icon icon) {
        ContentValues values = new ContentValues();

        values.put(DecheterieDatabase.TableIcon.ID, icon.getId());
        values.put(DecheterieDatabase.TableIcon.NOM, icon.getNom());
        values.put(DecheterieDatabase.TableIcon.DOMAINE, icon.getDomaine());
        values.put(DecheterieDatabase.TableIcon.PATH, icon.getPath());

        return db.insertOrThrow(DecheterieDatabase.TableIcon.TABLE_ICON, null, values);
    }

    /*
    Vider la table
     */

    public void clearIcon() {
        db.execSQL("delete from " + DecheterieDatabase.TableIcon.TABLE_ICON);
    }

    public Icon getIconByIdentifiant(int id) {
        Icon icon;
        String query = "SELECT * FROM " + DecheterieDatabase.TableIcon.TABLE_ICON + " WHERE " + DecheterieDatabase.TableIcon.ID + "=" + id + ";";
        Cursor cursor = db.rawQuery(query, null);
        icon = cursorToIcon(cursor);
        return icon;
    }

    public ArrayList<Icon> getAllIcons() {
        ArrayList<Icon> iconList;
        String query = "SELECT * FROM " + DecheterieDatabase.TableIcon.TABLE_ICON;
        Cursor cursor = db.rawQuery(query, null);
        iconList = cursorToListeIcon(cursor);
        return iconList;
    }

    public Icon getIconByName(String name) {
        Icon icon;
        String query = "SELECT * FROM " + DecheterieDatabase.TableIcon.TABLE_ICON + " WHERE " + DecheterieDatabase.TableIcon.NOM + " LIKE " + "'" + name + "%" +  ";";
        Cursor cursor = db.rawQuery(query, null);
        icon = cursorToIcon(cursor);
        return icon;
    }


    private Icon cursorToIcon(Cursor c){
        if(c.getCount() == 0) {
            return null;
        }
        c.moveToFirst();
        Icon i = new Icon();
        i.setId(c.getInt(DecheterieDatabase.TableIcon.NUM_ID));
        i.setNom(c.getString(DecheterieDatabase.TableIcon.NUM_NOM));
        i.setDomaine(c.getString(DecheterieDatabase.TableIcon.NUM_DOMAINE));
        i.setPath(c.getString(DecheterieDatabase.TableIcon.NUM_PATH));

        c.close();

        return i;
    }

    private ArrayList<Icon> cursorToListeIcon(Cursor c) {
        ArrayList<Icon> iconList = new ArrayList<>();

        if (c.moveToFirst()) {
            do {
                Icon i = new Icon();
                i.setId(c.getInt(DecheterieDatabase.TableIcon.NUM_ID));
                i.setNom(c.getString(DecheterieDatabase.TableIcon.NUM_NOM));
                i.setDomaine(c.getString(DecheterieDatabase.TableIcon.NUM_DOMAINE));
                i.setPath(c.getString(DecheterieDatabase.TableIcon.NUM_PATH));
                iconList.add(i);
            } while (c.moveToNext());

            c.close();
        }
        return iconList;
    }
}
